package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.gestoresmadrid.core.consultasTGate.model.enumerados.OrigenTGate;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteSolicitudInformacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioInformeAvpo;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.avpogestbasti.TN3270.Avpo;
import trafico.avpogestbasti.TN3270.Bsti;
import trafico.beans.avpobastigest.AvpoBean;
import trafico.beans.avpobastigest.BstiBean;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoAccion;
import trafico.utiles.imprimir.ImprimirTramitesSolicitud;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioInformeAvpoImpl implements ServicioInformeAvpo {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioInformeAvpoImpl.class);

	private Bsti basti;
	private Avpo avpo;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultBean obtenerInformeTramite(List<TramiteTrafSolInfoVehiculoVO> solicitudes, BigDecimal idUsuario, String numColegiado, BigDecimal idContrato) {
		ResultBean resultBean = new ResultBean();

		// Comprobar la disponibilidad
		comprobarServicioActivo(resultBean);

		// Crear acción
		if (!resultBean.getError()) {
			try {
				servicioAccionTramite.crearAccionTramite(idUsuario, solicitudes.get(0).getId().getNumExpediente(), TipoAccion.SolicitudAVPO.getValorEnum(), null, null);
			} catch (OegamExcepcion e) {
				LOG.error("Error al crear la accion de solicitar informacion", e, solicitudes.get(0).getId().getNumExpediente().toString());
			}

			// Se piden los informes de los vehículos
			obtenerInformeVehiculo(resultBean, solicitudes, idUsuario, numColegiado, idContrato);
		}

		return resultBean;
	}

	private void obtenerInformeVehiculo(ResultBean resultBean, List<TramiteTrafSolInfoVehiculoVO> solicitudes, BigDecimal idUsuario, String numColegiado, BigDecimal idContrato) {
		BigDecimal pendiente = new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum());
		BigDecimal recibido = new BigDecimal(EstadoTramiteSolicitudInformacion.Recibido.getValorEnum());
		int recibidosOk = 0;
		Calendar calendar = Calendar.getInstance();

		List<AvpoBean> listaAvpo = new ArrayList<>();
		for (TramiteTrafSolInfoVehiculoVO solicitud : solicitudes) {
			if (pendiente.equals(solicitud.getEstado())) {

				AvpoBean bean = new AvpoBean();

				// Si no hay matricula primero lanzamos la consulta BASTI y luego la AVPO
				if (solicitud.getVehiculo().getMatricula() == null || solicitud.getVehiculo().getMatricula().isEmpty()) {
					BstiBean bstiBean = basti.obtenerMatricula(solicitud.getVehiculo().getBastidor(), solicitud.getId().getNumExpediente(), idUsuario, numColegiado, solicitud.getVehiculo()
							.getIdVehiculo().longValue(), OrigenTGate.SolicitudAvpo.getValorEnum());

					LOG.info("BSTI Bastidor: " + solicitud.getVehiculo().getBastidor());

					if (bstiBean.getError()) {
						bean.setError(Boolean.TRUE);
						bean.setRepetir(true);
						bean.setMatricula("");
						bean.setTasa(solicitud.getTasa().getCodigoTasa());
						bean.setMensaje(bstiBean.getMensaje());
					} else {
						solicitud.getVehiculo().setMatricula(bstiBean.getMatricula());
					}
				}

				// Si todo ha ido bien y hay matrícula consulto la avpo
				if (solicitud.getVehiculo().getMatricula() != null && !solicitud.getVehiculo().getMatricula().isEmpty()) {
					bean = avpo.obtenerDatos(solicitud.getVehiculo().getMatricula(), solicitud.getTasa().getCodigoTasa(), solicitud.getId().getNumExpediente(), idUsuario, numColegiado, solicitud
							.getVehiculo().getIdVehiculo().longValue(), OrigenTGate.SolicitudAvpo.getValorEnum());
				}
				if (!bean.isRepetir()) {
					solicitud.setEstado(recibido);
					bean.setFechaInforme(calendar.getTime());
					listaAvpo.add(bean);
					recibidosOk++;
				}
				solicitud.setResultado(bean.getMensaje());
			}
		}

		if (recibidosOk > 0) {
			// Descuenta los creditos
			descontarCreditos(solicitudes.get(0).getId().getNumExpediente().toString(), BigDecimal.valueOf(recibidosOk), idUsuario, idContrato);
			// Guarda en disco los ficheros obtenidos
			imprimir(resultBean, solicitudes.get(0).getId().getNumExpediente().toString(), listaAvpo);
		}
	}

	/**
	 * Comprueba la disponibilidad del servicio AVPO y BSTI
	 * @param resultBean
	 */
	private void comprobarServicioActivo(ResultBean resultBean) {
		if ("si".equalsIgnoreCase(gestorPropiedades.valorPropertie(ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVAR))) {
			resultBean.setError(Boolean.TRUE);
			String mensaje = ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_DESACTIVADA_MENSAJE;
			resultBean.addMensajeALista((mensaje != null && !mensaje.isEmpty()) ? mensaje : ConstantesTrafico.CONSULTA_AVPO_BSTI_GEST_MENSAJE_X_DEFECTO);
		}
	}

	/**
	 * Metodo que descuenta los creditos de la consulta AVPO
	 * @param numExpediente
	 * @param numCreditos
	 * @param idUsuario
	 * @param idContrato
	 */
	private void descontarCreditos(String numExpediente, BigDecimal numCreditos, BigDecimal idUsuario, BigDecimal idContrato) {

		// Descuenta los creditos
		ResultBean resultadoCreditos = servicioCredito.descontarCreditos(TipoTramiteTrafico.Solicitud.getValorEnum(), idContrato, numCreditos, idUsuario, ConceptoCreditoFacturado.AVPO, numExpediente);
		if (resultadoCreditos.getError()) {
			LOG.error("Ocurrio un error al descontar creditos de la solicitud de informe AVPO");
			if (resultadoCreditos.getMensaje() != null && !resultadoCreditos.getMensaje().isEmpty()) {
				LOG.error(resultadoCreditos.getMensaje());
			}
			if (resultadoCreditos.getListaMensajes() != null) {
				for (String mensaje : resultadoCreditos.getListaMensajes()) {
					LOG.error(mensaje);
				}
			}
		}
	}

	/**
	 * Guarda en disco la informacion recibida
	 * @param resultBean
	 * @param numExpediente
	 * @param listaAvpo
	 */
	private void imprimir(ResultBean resultBean, String numExpediente, List<AvpoBean> listaAvpo) {
		ImprimirTramitesSolicitud imprimir = new ImprimirTramitesSolicitud();

		Map<String, byte[]> map = null;
		try {
			map = imprimir.imprimirLoteConsultaAVPO(listaAvpo);
		} catch (OegamExcepcion e1) {
			LOG.error("Error al obtener el array de bytes de los AVPOs", e1, numExpediente);
		}

		if (map != null && !map.isEmpty()) {
			try {
				FicheroBean fichero = new FicheroBean();
				fichero.setSobreescribir(false);
				fichero.setTipoDocumento(ConstantesGestorFicheros.SOLICITUD_INFORMACION);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreZip("Consultas_" + numExpediente);
				fichero.setListaByte((HashMap<String, byte[]>) map);

				gestorDocumentos.empaquetarEnZipByte(fichero);

			} catch (Throwable e) {
				LOG.error("Ocurrio un error al guardar en disco los informes AVPO generados para el tramite " + numExpediente, e, numExpediente);
				resultBean.setError(Boolean.TRUE);
				resultBean.addMensajeALista("Error al crear al guardar el/los informe/s solicitado/s");
				// Mensaje para cerrar la acción
				resultBean.setMensaje("ERROR AL FIRMAR O CREANDO EL .zip");
				// TODO esto...
				// puedoCambiarEstado = false;
			}
		} else {
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("Error al crear al guardar el/los informe/s solicitado/s");
			// Mensaje para cerrar la acción
			resultBean.setMensaje("Error al consultar AVPO");
		}
	}

	@PostConstruct
	private void inicializar() {
		basti = new Bsti();
		avpo = new Avpo();
	}

}