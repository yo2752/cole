package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoSolInfoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoPK;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.InformeInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioInformeAvpo;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans.ResultadoTramSolInfoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.utiles.enumerados.TipoAccion;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioTramiteTraficoSolInfoImpl implements ServicioTramiteTraficoSolInfo {

	private static final long serialVersionUID = 4090620276989539969L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoSolInfoImpl.class);

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioInformeAvpo servicioInformeAvpo;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private TramiteTraficoSolInfoDao tramiteTraficoSolInfoDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioFacturacion servicioFacturacion;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultInteveBean actualizarDescargaInformes(List<InformeInteveBean> listaIdSolInfoOK, List<InformeInteveBean> listaIdSolInfoError, BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultInteveBean resultado = new ResultInteveBean(Boolean.FALSE);
		try {
			if(listaIdSolInfoOK != null && !listaIdSolInfoOK.isEmpty()){
				for(InformeInteveBean informeOK : listaIdSolInfoOK){
					TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = getTramiteSolInfoVehiculoVO(informeOK.getIdSolInfo(), numExpediente);
					if(tramiteTrafSolInfoVehiculoVO != null){
						tramiteTrafSolInfoVehiculoVO.setEstado(new BigDecimal(EstadoTramiteSolicitudInformacion.Finalizado_PDF.getValorEnum()));
						tramiteTraficoSolInfoDao.actualizarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
					}
				}
			}
			if(listaIdSolInfoError != null && !listaIdSolInfoError.isEmpty()){
				for(InformeInteveBean informeError : listaIdSolInfoError){
					TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = getTramiteSolInfoVehiculoVO(informeError.getIdSolInfo(), numExpediente);
					if(tramiteTrafSolInfoVehiculoVO != null){
						tramiteTrafSolInfoVehiculoVO.setEstado(new BigDecimal(EstadoTramiteSolicitudInformacion.Finalizado_Con_Error.getValorEnum()));
						tramiteTrafSolInfoVehiculoVO.setResultado(informeError.getResultado());
						tramiteTraficoSolInfoDao.actualizarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
					}
				}
			}
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = getTramiteTraficoSolInfoVO(numExpediente, Boolean.FALSE);
			String estadoNuevo = null;
			if((listaIdSolInfoError != null && !listaIdSolInfoError.isEmpty()) && (listaIdSolInfoOK != null && !listaIdSolInfoOK.isEmpty())){
				estadoNuevo = EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum();
			} else if(listaIdSolInfoError != null && !listaIdSolInfoError.isEmpty()){
				estadoNuevo = EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum();
			} else {
				estadoNuevo = EstadoTramiteTrafico.Finalizado_PDF.getValorEnum();
			}
			tramiteTrafSolInfoVO.setEstado(new BigDecimal(estadoNuevo));
			tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);
			guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()),
					estadoNuevo, idUsuario);
			if(listaIdSolInfoOK != null && !listaIdSolInfoOK.isEmpty()){
				ResultBean resulCredito = servicioCredito.descontarCreditos(TipoTramiteTrafico.Solicitud.getValorEnum(), 
					new BigDecimal(tramiteTrafSolInfoVO.getContrato().getIdContrato()), BigDecimal.valueOf(-listaIdSolInfoOK.size()), idUsuario, ConceptoCreditoFacturado.AVPO, numExpediente.toString());
				if(resulCredito != null && resulCredito.getError()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resulCredito.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los informes, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los informes.");
		}
		if(resultado.isError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean borrarSolicitudVehiculo(BigDecimal numExpediente, long idVehiculo) {
		ResultBean resultBean = new ResultBean();
		TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = tramiteTraficoSolInfoDao.getSolicitudInformacion(numExpediente, idVehiculo, false);
		if (tramiteTrafSolInfoVehiculoVO == null || tramiteTrafSolInfoVehiculoVO.getTramiteTrafico() == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("No se ha encontrado la solicitud para que se desea borrar");
		} else if (!comprobarEstadosPermitenBorrar(EstadoTramiteTrafico.convertir(tramiteTrafSolInfoVehiculoVO.getTramiteTrafico().getEstado()))) {
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("No se puede modificar un tramite duplicado, anulado, pendiente de respuesta de DGT o finalizado sin error");
		} else if (EstadoTramiteSolicitudInformacion.Recibido.getValorEnum().equals(tramiteTrafSolInfoVehiculoVO.getEstado())) {
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("No se pueden eliminar líneas de vehículo que ya han sido enviadas");
		} else {
			// Desasignar tasa
			ResultBean resultadoDesasignar = servicioTasa.desasignarTasaInforme(tramiteTrafSolInfoVehiculoVO);
			if (resultadoDesasignar.getError()) {
				resultBean.setError(Boolean.TRUE);
				resultBean.addMensajeALista("No se ha podido desasignar la tasa");
			} else if (!tramiteTraficoSolInfoDao.borrarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO)) {
				if (resultadoDesasignar.getError()) {
					resultBean.setError(Boolean.TRUE);
					resultBean.addMensajeALista("No se ha podido borrar la solicitud");
				}
			}
		}
		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(numExpediente));

		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getTramiteTraficoSolInfo(BigDecimal numExpediente) {
		ResultBean resultBean = new ResultBean();
		TramiteTrafSolInfoDto tramiteTrafSolInfoDto = getTramiteTraficoSolInfoDto(numExpediente);
		resultBean.addAttachment(TRAMITE_DETALLE, tramiteTrafSolInfoDto);
		return resultBean;
	}

	@Override
	@Transactional(readOnly = true)
	public TramiteTrafSolInfoDto getTramiteTraficoSolInfoDto(BigDecimal numExpediente) {
		TramiteTrafSolInfoDto tramiteTrafSolInfoDto = null;
		try {
			if (numExpediente != null) {
				TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(numExpediente, true);
				if (tramiteTrafSolInfoVO != null) {
					tramiteTrafSolInfoDto = conversor.transform(tramiteTrafSolInfoVO, TramiteTrafSolInfoDto.class);
					if (tramiteTrafSolInfoDto != null) {
						List<SolicitudInformeVehiculoDto> solicitudes = conversor.transform(tramiteTrafSolInfoVO.getSolicitudes(), SolicitudInformeVehiculoDto.class);
						tramiteTrafSolInfoDto.setSolicitudes(solicitudes);
						if (tramiteTrafSolInfoDto.getIntervinienteTraficos() != null) {
							Iterator<IntervinienteTraficoDto> iterator = tramiteTrafSolInfoDto.getIntervinienteTraficos().iterator();
							while (iterator.hasNext()) {
								IntervinienteTraficoDto interviniente = iterator.next();
								if (TipoInterviniente.Solicitante.getValorEnum().equals(interviniente.getTipoInterviniente())) {
									tramiteTrafSolInfoDto.setSolicitante(interviniente);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la solicitud de información, error: ",e, numExpediente.toString());
		}
		return tramiteTrafSolInfoDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean guardarSolicitudesInformacion(TramiteTrafSolInfoDto tramiteDto, BigDecimal idUsuario, boolean admin) {
		ResultBean resultBean = new ResultBean();
		for (SolicitudInformeVehiculoDto solicitudDto : tramiteDto.getSolicitudes()) {
			resultBean = guardarSolicitudInformacion(tramiteDto, solicitudDto, idUsuario, admin);
			if (resultBean != null && resultBean.getError()) {
				break;
			}
		}
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void actualizarTramiteConEvolucion(TramiteTrafSolInfoDto tramiteDto, EstadoTramiteTrafico estadoAnterior, BigDecimal idUsuario) {
		// Conversion DTO a VO tramite_trafico
		TramiteTrafSolInfoVO tramiteTrafSolInfoVO = conversor.transform(tramiteDto, TramiteTrafSolInfoVO.class);

		// Actualizacion de un tramite existente
		BigDecimal estadoAnteriorBD = estadoAnterior != null ? new BigDecimal(estadoAnterior.getValorEnum()) : null;
		guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), estadoAnteriorBD, tramiteTrafSolInfoVO.getEstado().toString(), idUsuario);

		tramiteTrafSolInfoVO.setFechaUltModif(new Date());
		log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
		tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);

		for (TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : tramiteTrafSolInfoVO.getSolicitudes()) {
			tramiteTrafSolInfoVehiculoVO.getId().setIdVehiculo(tramiteTrafSolInfoVehiculoVO.getVehiculo().getIdVehiculo());
			tramiteTrafSolInfoVehiculoVO.getId().setNumColegiado(tramiteDto.getNumColegiado());
			tramiteTrafSolInfoVehiculoVO.getId().setNumExpediente(tramiteDto.getNumExpediente());
			tramiteTraficoSolInfoDao.actualizarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean guardarSolicitudInformacion(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, boolean admin) {
		ResultBean resultBean = new ResultBean();
		try {
			// Conversion DTO a VO tramite_trafico
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = getTramiteTraficoVO(tramiteDto);

			// Comprobar si es modificable
			comprobarModificable(resultBean, tramiteTrafSolInfoVO);

			validarMotivo(resultBean, solicitudDto);

			if (!resultBean.getError()) {
				// Guardar trámite
				guardarTramite(resultBean, tramiteTrafSolInfoVO, idUsuario);
				tramiteDto.setNumExpediente(tramiteTrafSolInfoVO.getNumExpediente());
				UsuarioDto usuarioDto = new UsuarioDto();
				usuarioDto.setIdUsuario(idUsuario);
				if (!resultBean.getError() && incluirVehiculo(solicitudDto)) {
					// Se guardará en mayúsculas
					solicitudDto.getVehiculo().setMatricula(solicitudDto.getVehiculo().getMatricula() != null ? solicitudDto.getVehiculo().getMatricula().toUpperCase() : null);
					solicitudDto.getVehiculo().setBastidor(solicitudDto.getVehiculo().getBastidor() != null ? solicitudDto.getVehiculo().getBastidor().toUpperCase() : null);
					solicitudDto.getVehiculo().setNive(solicitudDto.getVehiculo().getNive() != null ? solicitudDto.getVehiculo().getNive().toUpperCase() : null);

					// Conversion DTO a VO tramite_traf_sol_info
					TramiteTrafSolInfoVehiculoVO tramiteSolInfo = getTramiteTrafSolInfoVehiculoVO(solicitudDto, tramiteTrafSolInfoVO);

					// Asignar tasa
					asignarTasaExpediente(resultBean, tramiteSolInfo);

					if (!resultBean.getError()) {
						// Guardar Vehiculo
						guardarVehiculo(resultBean, tramiteSolInfo, usuarioDto, admin);
					}

					if (!resultBean.getError()) {
						// Guardar solicitud
						guardarSolicitud(resultBean, tramiteSolInfo);
					}
				}
				if (!resultBean.getError() && tramiteDto.getSolicitante() != null && tramiteDto.getSolicitante().getPersona() != null && tramiteDto.getSolicitante().getPersona().getNif() != null
						&& !tramiteDto.getSolicitante().getPersona().getNif().isEmpty()) {
					comprobarGuardarSolicitante(resultBean, tramiteDto, tramiteTrafSolInfoVO, usuarioDto);
				}
			}
		} catch (ParseException e) {
			log.error("", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.addError("No se pudo realizar el guardado, si el error persiste póngase en contacto con el administrador del sistema.");
		}
		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(NUM_EXPEDIENTE, tramiteDto.getNumExpediente());
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean reiniciarTramite(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultBean = new ResultBean();

		TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(numExpediente, false);

		if (tramiteTrafSolInfoVO == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.addError("Ha ocurrido un error al recuperar el trámite");
		}

		if (!resultBean.getError()) {
			// Realizo el cambio de estado y actualizo la evolucion
			guardarTramite(resultBean, tramiteTrafSolInfoVO, idUsuario);

			if (!resultBean.getError()) {
				List<TramiteTrafSolInfoVehiculoVO> lista = tramiteTraficoSolInfoDao.getSolicitudes(numExpediente);
				if (lista != null) {
					BigDecimal estadoIni = new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum());
					for (TramiteTrafSolInfoVehiculoVO trafSolInfoVO : lista) {
						trafSolInfoVO.setEstado(estadoIni);
						trafSolInfoVO.setReferenciaAtem(null);
						trafSolInfoVO.setResultado(null);
						tramiteTraficoSolInfoDao.actualizarSolInfoVehiculo(trafSolInfoVO);
					}
				}
				// Crear acciones
				servicioAccionTramite.crearCerrarAccionTramite(idUsuario, numExpediente, TipoAccion.ReinicioAVPO.getValorEnum(), null);
			}
		}

		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(numExpediente));
		return resultBean;
	}

	@Override
	public boolean existeFicheroInforme(BigDecimal numExpediente) {
		FileResultBean result = recuperaFicheroInforme(numExpediente);
		return result != null && result.getFiles() != null && !result.getFiles().isEmpty();
	}

	@Override
	public File getFicheroInforme(BigDecimal numExpediente) {
		FileResultBean result = recuperaFicheroInforme(numExpediente);
		return result != null && result.getFiles() != null && !result.getFiles().isEmpty() ? result.getFiles().get(0) : null;
	}

	public FileResultBean recuperaFicheroInforme(BigDecimal numExpediente) {
		FileResultBean result = null;
		if (numExpediente != null) {
			try {
				result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null, Utilidades.transformExpedienteFecha(numExpediente), numExpediente
						.toString());
			} catch (OegamExcepcion e) {
				log.error("Ocurrio un error al recuperar el fichero de informes", e, numExpediente.toString());
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean obtenerInformeAVPO(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, String numColegiado, BigDecimal idContrato, boolean admin) {

		// primero guarda por si hay algún cambio
		ResultBean resultBean = guardarSolicitudInformacion(tramiteDto, solicitudDto, idUsuario, admin);

		if (!resultBean.getError()) {
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteDto.getNumExpediente(), true);

			ResultBean resultadoInforme = servicioInformeAvpo.obtenerInformeTramite(tramiteTrafSolInfoVO.getSolicitudes(), idUsuario, numColegiado, idContrato);

			if (resultadoInforme.getError()) {
				// Si viene mensaje, es el empleado para cerrar la acción
				if (resultadoInforme.getMensaje() != null && !resultadoInforme.getMensaje().isEmpty()) {
					// cerrar accion tramite
					servicioAccionTramite.cerrarAccionTramite(idUsuario, tramiteDto.getNumExpediente(), TipoAccion.SolicitudAVPO.getValorEnum(), resultadoInforme.getMensaje());
					resultBean.setError(Boolean.TRUE);
					resultBean.setListaMensajes(resultadoInforme.getListaMensajes());
				}
			} else {
				int recibidos = 0;
				// Actualizar el estado del trámite
				BigDecimal recibido = new BigDecimal(EstadoTramiteSolicitudInformacion.Recibido.getValorEnum());
				for (TramiteTrafSolInfoVehiculoVO tramiteSolInfoVO : tramiteTrafSolInfoVO.getSolicitudes()) {
					if (recibido.equals(tramiteSolInfoVO.getEstado())) {
						recibidos++;
					}
				}
				EstadoTramiteTrafico estado;
				String respuesta;
				if (tramiteTrafSolInfoVO.getSolicitudes().size() == recibidos) {
					estado = EstadoTramiteTrafico.Finalizado_PDF;
					respuesta = "AVPO COMPLETADA";
				} else {
					estado = EstadoTramiteTrafico.Finalizado_Parcialmente;
					if (recibidos > 0) {
						respuesta = "AVPO NO SE HA COMPLETADO .COMPLETADA PARCIALMENTE";
					} else {
						respuesta = "AVPO NO SE HA COMPLETADO";
					}
				}

				// Actualizacion del trámite
				guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), tramiteTrafSolInfoVO.getEstado(), EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);

				tramiteTrafSolInfoVO.setFechaUltModif(new Date());
				tramiteTrafSolInfoVO.setEstado(new BigDecimal(estado.getValorEnum()));
				log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
				tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);

				// Cerrar la acción
				servicioAccionTramite.cerrarAccionTramite(idUsuario, tramiteDto.getNumExpediente(), TipoAccion.SolicitudAVPO.getValorEnum(), respuesta);
			}
			if (resultBean.getError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(tramiteDto.getNumExpediente()));
		}
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean obtenerInformeInteve(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin) {

		// primero guarda por si hay algún cambio
		ResultBean resultBean = guardarSolicitudInformacion(tramiteDto, solicitudDto, idUsuario, admin);

		if (!resultBean.getError()) {
			// Crear la accion
			try {
				servicioAccionTramite.crearAccionTramite(idUsuario, tramiteDto.getNumExpediente(), TipoAccion.SOLICITUDINTEVE.getValorEnum(), null, null);
			} catch (OegamExcepcion e) {
				log.error("Error al crear la accion de solicitar informacion por Inteve", e);
			}

			// Actualizacion del tramite
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteDto.getNumExpediente(), false);
			guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), tramiteTrafSolInfoVO.getEstado(), EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);

			tramiteTrafSolInfoVO.setFechaUltModif(new Date());
			tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
			tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);

			// Encolar tramite
			/*try {
				servicioCola.crearSolicitud(ProcesosEnum.INTEVE.getNombreEnum(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Solicitud.getValorEnum(), tramiteDto
						.getNumExpediente().toString(), idUsuario, null, idContrato);
			} catch (OegamExcepcion e) {
				log.error("Ocurrio un error al encolar la solicitud Inteve", e);
				resultBean.setError(Boolean.TRUE);
				resultBean.addMensajeALista("Ha ocurrido un error al crear la solicitud: " + e.getMensajeError1());
			}*/
		}

		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(tramiteDto.getNumExpediente()));
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean obtenerInformeInteveNuevo(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin) {
		// primero guarda por si hay algún cambio
		ResultBean resultBean = guardarSolicitudInformacion(tramiteDto, solicitudDto, idUsuario, admin);
		if (!resultBean.getError()) {
			// Crear la accion
			try {
				servicioAccionTramite.crearAccionTramite(idUsuario, tramiteDto.getNumExpediente(), TipoAccion.SOLICITUDINTEVE3.getValorEnum(), null, null);
			} catch (OegamExcepcion e) {
				log.error("Error al crear la accion de solicitar informacion por Inteve", e);
			}

			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteDto.getNumExpediente(), false);

			int cantidadCreditos = 0;
			BigDecimal pendiente = new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum());
			for (TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : tramiteTrafSolInfoVO.getSolicitudes()) {
				if (pendiente.equals(tramiteTrafSolInfoVehiculoVO.getEstado())) {
					cantidadCreditos++;
				}
			}
			// Validar creditos disponibles
			ResultBean resultadoCreditos = servicioCredito.validarCreditos(TipoTramiteTrafico.Solicitud.getValorEnum(), idContrato, BigDecimal.valueOf(cantidadCreditos));
			if (resultadoCreditos.getError()){
				resultBean.setError(true);
				resultBean.addMensajeALista("No tienen creditos suficientes para realizar la consulta de Inteve.");
			}

			// Actualizacion del tramite si se han descontado los creditos
			if (!resultBean.getError()) {
				guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), tramiteTrafSolInfoVO.getEstado(), EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);

				tramiteTrafSolInfoVO.setFechaUltModif(new Date());
				tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
				log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
				tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);

				// Encolar tramite
				try {
					servicioCola.crearSolicitud(ProcesosEnum.INTEVE3.getNombreEnum(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Solicitud.getValorEnum(), tramiteDto
							.getNumExpediente().toString(), idUsuario, null, idContrato);
				} catch (OegamExcepcion e) {
					log.error("Ocurrio un error al encolar la solicitud Inteve", e);
					resultBean.setError(Boolean.TRUE);
					resultBean.addMensajeALista("Ha ocurrido un error al crear la solicitud: " + e.getMensajeError1());
				}
			}
		}

		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(tramiteDto.getNumExpediente()));
		return resultBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultBean obtenerInformeAtem(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin) {

		// primero guarda por si hay algún cambio
		ResultBean resultBean = guardarSolicitudInformacion(tramiteDto, solicitudDto, idUsuario, admin);

		if (!resultBean.getError()) {
			// Crear la accion
			try {
				servicioAccionTramite.crearAccionTramite(idUsuario, tramiteDto.getNumExpediente(), TipoAccion.ATEM.getValorEnum(), null, null);
			} catch (OegamExcepcion e) {
				log.error("Error al crear la accion de solicitar informacion por Atem", e);
			}

			// Actualizacion del tramite
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteDto.getNumExpediente(), false);
			guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), tramiteTrafSolInfoVO.getEstado(), EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);

			tramiteTrafSolInfoVO.setFechaUltModif(new Date());
			tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
			tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);

			// Encolar tramite
			try {
				servicioCola.crearSolicitud(ProcesosEnum.ATEM.getNombreEnum(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Solicitud.getValorEnum(), tramiteDto
						.getNumExpediente().toString(), idUsuario, null, idContrato);
			} catch (OegamExcepcion e) {
				log.error("Ocurrio un error al encolar la solicitud Atem", e);
				resultBean.setError(Boolean.TRUE);
				resultBean.addMensajeALista("Ha ocurrido un error al crear la solicitud: " + e.getMensajeError1());
			}
		}

		if (resultBean.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultBean.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(tramiteDto.getNumExpediente()));
		return resultBean;
	}

	/**
	 * Comprueba que se deba guardar el solicitante
	 * 
	 * @param resultBean
	 * @param tramiteDto
	 * @param tramiteTraficoVO
	 * @param usuario
	 */
	private void comprobarGuardarSolicitante(ResultBean resultBean, TramiteTrafSolInfoDto tramiteDto, TramiteTraficoVO tramiteTraficoVO, UsuarioDto usuario) {
		String nifSolicitante = null;
		IntervinienteTraficoVO intervinienteSolicitante = null;
		if (tramiteDto.getSolicitante() != null && tramiteDto.getSolicitante().getPersona() != null && tramiteDto.getSolicitante().getPersona().getNif() != null) {
			tramiteDto.getSolicitante().setNifInterviniente(tramiteDto.getSolicitante().getPersona().getNif().toUpperCase());
			tramiteDto.getSolicitante().setNumColegiado(tramiteDto.getNumColegiado());
			nifSolicitante = tramiteDto.getSolicitante().getNifInterviniente();
		}
		if (tramiteTraficoVO.getIntervinienteTraficos() == null) {
			tramiteTraficoVO.setIntervinienteTraficos(new ArrayList<IntervinienteTraficoVO>());
		}
		for (IntervinienteTraficoVO interviniente : tramiteTraficoVO.getIntervinienteTraficos()) {
			if (!interviniente.getId().getNif().equals(nifSolicitante)) {
				servicioIntervinienteTrafico.eliminar(interviniente);
			} else {
				// Es actualizacion de un intervininiente trafico existente
				conversor.transform(tramiteDto.getSolicitante(), interviniente);
				intervinienteSolicitante = interviniente;
			}
		}
		if (nifSolicitante != null && intervinienteSolicitante == null) {
			// Es nuevo interviniente trafico
			intervinienteSolicitante = conversor.transform(tramiteDto.getSolicitante(), IntervinienteTraficoVO.class);
			intervinienteSolicitante.getId().setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
		}
 
		if (intervinienteSolicitante != null) {
			guardarSolicitante(resultBean, intervinienteSolicitante, tramiteTraficoVO, usuario);
		}
	}

	/**
	 * Guarda el interviniente de tipo solicitante y actualiza el resultbean
	 * 
	 * @param respuesta
	 * @param interviniente
	 * @param tramiteTraficoVO
	 * @param usuario
	 */
	private void guardarSolicitante(ResultBean respuesta, IntervinienteTraficoVO interviniente, TramiteTraficoVO tramiteTraficoVO, UsuarioDto usuario) {
		if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null && interviniente.getPersona().getId().getNif() != null
				&& !interviniente.getPersona().getId().getNif().isEmpty()) {
			interviniente.getId().setNif(interviniente.getPersona().getId().getNif().toUpperCase());
			interviniente.getId().setNumExpediente(tramiteTraficoVO.getNumExpediente());
			interviniente.getPersona().getId().setNumColegiado(tramiteTraficoVO.getNumColegiado());
			// Estado para saber que esta activo
			interviniente.getPersona().setEstado(Long.valueOf(1));

			// Guardar la persona antes de guardar el interviniente
			ResultBean resultPersona = servicioPersona.guardarActualizar(interviniente.getPersona(), tramiteTraficoVO.getNumExpediente(), TipoTramiteTrafico.Solicitud.getValorEnum(), usuario,
					ServicioPersona.CONVERSION_PERSONA_SOLICITANTE);

			if (!resultPersona.getError()) {
				// Guardar interviniente
				ResultBean result = servicioIntervinienteTrafico.guardarActualizar(interviniente, null);
				for (String mensaje : result.getListaMensajes()) {
					respuesta.addMensajeALista(mensaje);
				}
			} else {
				respuesta.addMensajeALista(resultPersona.getMensaje());
			}
		}
	}

	/**
	 * Transforma el DTO en el VO de tramite_trafico. Si tiene numExpediente, el VO es el existente en BBDD actualizado con el DTO de pantalla
	 * @param tramiteDto
	 * @return
	 * @throws ParseException
	 */
	private TramiteTrafSolInfoVO getTramiteTraficoVO(TramiteTrafSolInfoDto tramiteDto) throws ParseException {
		TramiteTrafSolInfoVO tramiteTrafSolInfoVO;
		if(tramiteDto.getRefPropia() != null) {
			tramiteDto.setRefPropia(tramiteDto.getRefPropia().toUpperCase());
		}
		if (tramiteDto.getNumExpediente() != null) {
			tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(tramiteDto.getNumExpediente(), false);
			tramiteTrafSolInfoVO.setRefPropia(tramiteDto.getRefPropia());
			tramiteTrafSolInfoVO.setAnotaciones(tramiteDto.getAnotaciones());
			tramiteTrafSolInfoVO.setFechaPresentacion(tramiteDto.getFechaPresentacion() != null ? tramiteDto.getFechaPresentacion().getFecha() : null);
		} else {
			tramiteTrafSolInfoVO = conversor.transform(tramiteDto, TramiteTrafSolInfoVO.class);
		}
		return tramiteTrafSolInfoVO;
	}

	/**
	 * Transforma el DTO en el VO de tramite_traf_sol_info
	 * @param solicitudDto
	 * @param tramiteTrafSolInfoVO
	 * @return
	 */
	private TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoVehiculoVO(SolicitudInformeVehiculoDto solicitudDto, TramiteTrafSolInfoVO tramiteTrafSolInfoVO) {
		TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = conversor.transform(solicitudDto, TramiteTrafSolInfoVehiculoVO.class);
		tramiteTrafSolInfoVehiculoVO.setTramiteTrafico(tramiteTrafSolInfoVO);
		tramiteTrafSolInfoVehiculoVO.getId().setNumExpediente(tramiteTrafSolInfoVO.getNumExpediente());
		tramiteTrafSolInfoVehiculoVO.getId().setNumColegiado(tramiteTrafSolInfoVO.getNumColegiado());
		tramiteTrafSolInfoVehiculoVO.getVehiculo().setNumColegiado(tramiteTrafSolInfoVO.getNumColegiado());
		return tramiteTrafSolInfoVehiculoVO;
	}

	/**
	 * Comprueba los datos minimos para incluir una nueva solicitud al tramite (tasa y (matricula o bastidor))
	 * @param solicitudDto
	 * @return
	 */
	private boolean incluirVehiculo(SolicitudInformeVehiculoDto solicitudDto) {
		if (solicitudDto == null || solicitudDto.getVehiculo() == null) {
			return false;
		}
		if ((solicitudDto.getVehiculo().getMatricula() == null || solicitudDto.getVehiculo().getMatricula().isEmpty())
				&& (solicitudDto.getVehiculo().getBastidor() == null || solicitudDto.getVehiculo().getBastidor().isEmpty())
				&& (solicitudDto.getVehiculo().getNive() == null || solicitudDto.getVehiculo().getNive().isEmpty())) {
			return false;
		} else if (solicitudDto.getTasa() == null || solicitudDto.getTasa().getCodigoTasa() == null || solicitudDto.getTasa().getCodigoTasa().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Asigna la tasa al tramite de solicitud de información
	 * @param resultBean
	 * @param tramiteSolInfo
	 * @return
	 */
	private void asignarTasaExpediente(ResultBean resultBean, TramiteTrafSolInfoVehiculoVO tramiteSolInfo) {
		if (tramiteSolInfo.getTasa() != null && !tramiteSolInfo.getTasa().getCodigoTasa().isEmpty() && !"-1".equals(tramiteSolInfo.getTasa().getCodigoTasa())) {
			if (servicioTasa.sePuedeAsignarTasa(tramiteSolInfo.getTasa().getCodigoTasa(), tramiteSolInfo.getId().getNumExpediente())) {
				ResultBean result = servicioTasa.asignarTasa(tramiteSolInfo.getTasa().getCodigoTasa(), tramiteSolInfo.getId().getNumExpediente());
				if (result.getError()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("No se puede asignar tasa");
				}
			} else if (tramiteSolInfo.getTasa() != null && tramiteSolInfo.getTasa().getCodigoTasa() != null) {
				resultBean.setError(true);
				resultBean.addMensajeALista("No se puede asignar tasa, tasa en uso");
			}
		}
	}

	/**
	 * Guarda el vehiculo, completando la informacion del ResultBean
	 * @param resultBean
	 * @param tramiteSolInfo
	 * @param usuario
	 */
	private void guardarVehiculo(ResultBean resultBean, TramiteTrafSolInfoVehiculoVO tramiteSolInfo, UsuarioDto usuario, boolean admin) {
		ResultBean respuestaVehiculo = servicioVehiculo.guardarVehiculo(tramiteSolInfo.getVehiculo(), tramiteSolInfo.getId().getNumExpediente(), TipoTramiteTrafico.Solicitud.getValorEnum(), usuario,
				null, ServicioVehiculo.CONVERSION_VEHICULO_SOL_INFO, false, admin);
		if (!respuestaVehiculo.getError()) {
			VehiculoVO vehiculo = (VehiculoVO) respuestaVehiculo.getAttachment(ServicioVehiculo.VEHICULO);
			tramiteSolInfo.setVehiculo(vehiculo);
			tramiteSolInfo.getId().setIdVehiculo(vehiculo.getIdVehiculo());
		} else {
			resultBean.setError(Boolean.TRUE);
			for (String mensaje : respuestaVehiculo.getListaMensajes()) {
				resultBean.addMensajeALista(mensaje);
			}
		}
	}

	/**
	 * Guarda en tramite_traf_sol_info, actualizando el resultbean
	 * @param resultBean
	 * @param tramiteTrafSolInfoVehiculoVO
	 * @param usuario
	 */
	private void guardarSolicitud(ResultBean resultBean, TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO) {
		try {
			tramiteTrafSolInfoVehiculoVO.setEstado(new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum()));
			tramiteTraficoSolInfoDao.guardarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
		} catch (Exception e) {
			log.error("Error al guardar el trámite de solicitud de informacion: " + tramiteTrafSolInfoVehiculoVO.getTramiteTrafico().getNumExpediente() + ". Mensaje: " + e.getMessage());
			resultBean.setMensaje(e.getMessage());
			resultBean.setError(true);
		}
	}

	/**
	 * Guarda el tramite_trafico, actualizando el resultBean si hay error
	 * @param resultBean
	 * @param tramiteTrafSolInfoVO
	 * @param idUsuario
	 * @return
	 */
	private void guardarTramite(ResultBean resultBean, TramiteTrafSolInfoVO tramiteTrafSolInfoVO, BigDecimal idUsuario) {
		try {
			if (tramiteTrafSolInfoVO.getNumExpediente() == null) {
				// Nuevo tramite
				BigDecimal numExpediente = servicioTramiteTrafico.generarNumExpediente(tramiteTrafSolInfoVO.getNumColegiado());
				tramiteTrafSolInfoVO.setNumExpediente(numExpediente);
				tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();
				tramiteTrafSolInfoVO.setFechaUltModif(fecha.getFechaHora());
				tramiteTrafSolInfoVO.setFechaAlta(fecha.getFechaHora());
				log.info("Creación trámite de solicitud de informacion: " + numExpediente.toString());
				tramiteTraficoSolInfoDao.guardar(tramiteTrafSolInfoVO);
				guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), null, EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);
			} else {
				// Actualizacion de un tramite existente
				guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), tramiteTrafSolInfoVO.getEstado(), EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);

				tramiteTrafSolInfoVO.setFechaUltModif(new Date());
				tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				log.info("Actualización trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente());
				tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de solicitud de informacion: " + tramiteTrafSolInfoVO.getNumExpediente() + ". Mensaje: " + e.getMessage());
			resultBean.setMensaje(e.getMessage());
			resultBean.setError(true);
		}
	}

	/**
	 * Guarda evolucio_tramite_trafico
	 * @param numExpediente
	 * @param estadoAnterior
	 * @param estadoNuevo
	 * @param idUsuario
	 */
	private void guardarEvolucionTramite(BigDecimal numExpediente, BigDecimal estadoAnterior, String estadoNuevo, BigDecimal idUsuario) {
		if (!estadoNuevo.equals(estadoAnterior)) {
			EvolucionTramiteTraficoDto evolucion = new EvolucionTramiteTraficoDto();
			if (estadoAnterior != null) {
				evolucion.setEstadoAnterior(estadoAnterior);
			} else {
				evolucion.setEstadoAnterior(BigDecimal.ZERO);
			}
			evolucion.setEstadoNuevo(new BigDecimal(estadoNuevo));
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setIdUsuario(idUsuario);
			evolucion.setUsuarioDto(usuarioDto);
			evolucion.setNumExpediente(numExpediente);
			servicioEvolucionTramite.guardar(evolucion);
		}
	}

	/**
	 * Recupera el DTO completo de tramiteTraficoSolInfo
	 * @param numExpediente
	 * @return
	 */
	private TramiteTrafSolInfoDto getTramiteTraficoCompleto(BigDecimal numExpediente) {
		TramiteTrafSolInfoDto tramiteTrafSolInfoDto = null;
		if (numExpediente != null) {
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfo(numExpediente, true);
			if (tramiteTrafSolInfoVO != null) {
				tramiteTrafSolInfoDto = conversor.transform(tramiteTrafSolInfoVO, TramiteTrafSolInfoDto.class);
				if (tramiteTrafSolInfoDto != null) {
					List<SolicitudInformeVehiculoDto> solicitudes = conversor.transform(tramiteTrafSolInfoVO.getSolicitudes(), SolicitudInformeVehiculoDto.class);
					tramiteTrafSolInfoDto.setSolicitudes(solicitudes);
					if (tramiteTrafSolInfoDto.getIntervinienteTraficos() != null) {
						Iterator<IntervinienteTraficoDto> iterator = tramiteTrafSolInfoDto.getIntervinienteTraficos().iterator();
						while (iterator.hasNext()) {
							IntervinienteTraficoDto interviniente = iterator.next();
							if (TipoInterviniente.Solicitante.getValorEnum().equals(interviniente.getTipoInterviniente())) {
								tramiteTrafSolInfoDto.setSolicitante(interviniente);
							}
						}
					}
				}
			}
		}
		return tramiteTrafSolInfoDto;
	}

	/**
	 * Comprueba si el tramite se encuentra en un estado modificable
	 * @param resultBean
	 * @param tramiteTraficoVO
	 * @return
	 */
	private ResultBean comprobarModificable(ResultBean resultBean, TramiteTraficoVO tramiteTraficoVO) {
		EstadoTramiteTrafico[] estadosPermitidos = { EstadoTramiteTrafico.Iniciado, EstadoTramiteTrafico.No_Tramitable, EstadoTramiteTrafico.Tramitable_Incidencias, EstadoTramiteTrafico.Tramitable,
				EstadoTramiteTrafico.Validado_Telematicamente, EstadoTramiteTrafico.Validado_PDF, EstadoTramiteTrafico.Finalizado_Con_Error, EstadoTramiteTrafico.Tramitable_Jefatura,
				EstadoTramiteTrafico.Finalizado_Parcialmente, EstadoTramiteTrafico.Finalizado_PDF };

		if (tramiteTraficoVO.getNumExpediente() != null && !isInEstados(EstadoTramiteTrafico.convertir(tramiteTraficoVO.getEstado()), estadosPermitidos)) {
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("El tramite no se encuentra en un estado modificable");
		}
		return resultBean;
	}

	private ResultBean validarMotivo(ResultBean resultBean, SolicitudInformeVehiculoDto solicitudDto) {
		if (solicitudDto.getVehiculo() != null
				&& (((solicitudDto.getVehiculo().getMatricula() != null && !solicitudDto.getVehiculo().getMatricula().isEmpty()) || (solicitudDto.getVehiculo().getBastidor() != null && !solicitudDto
						.getVehiculo().getBastidor().isEmpty()))) && (solicitudDto.getTasa().getCodigoTasa() != null && !solicitudDto.getTasa().getCodigoTasa().isEmpty())) {
			String nuevoInteve = gestorPropiedades.valorPropertie("dgt.aplicacion.inteve.nuevo.habilitar");
			if (nuevoInteve != null && nuevoInteve.equals("SI")) {
				if (solicitudDto.getMotivoInteve() == null || solicitudDto.getMotivoInteve().isEmpty()) {
					resultBean.addMensajeALista("Es obligatorio seleccionar un motivo");
					resultBean.setError(true);
				}
			}
		}
		return resultBean;
	}

	/**
	 * Comprueba si el tramite se encuentra en un estado en el que se pueden borrar lineas
	 * @param estado
	 * @return
	 */
	private boolean comprobarEstadosPermitenBorrar(EstadoTramiteTrafico estado) {
		EstadoTramiteTrafico[] estadosPermitidos = { EstadoTramiteTrafico.Iniciado, EstadoTramiteTrafico.No_Tramitable, EstadoTramiteTrafico.Tramitable_Incidencias, EstadoTramiteTrafico.Tramitable,
				EstadoTramiteTrafico.Validado_Telematicamente, EstadoTramiteTrafico.Validado_PDF, EstadoTramiteTrafico.Finalizado_Con_Error, EstadoTramiteTrafico.Tramitable_Jefatura,
				EstadoTramiteTrafico.Finalizado_Parcialmente };
		return isInEstados(estado, estadosPermitidos);
	}

	/**
	 * Comprueba si el estado pasado por parametro está entre los pasados
	 * @param estado
	 * @param conjuntoEstados
	 * @return
	 */
	private boolean isInEstados(EstadoTramiteTrafico estado, EstadoTramiteTrafico[] conjuntoEstados) {
		if (conjuntoEstados != null) {
			for (EstadoTramiteTrafico e : conjuntoEstados) {
				if (e.equals(estado)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public SolicitudInformeVehiculoDto getTramiteTraficoSolInfoPorCodTasa(String codigoTasa) {
		try {
			if (codigoTasa != null && !codigoTasa.isEmpty()) {
				TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = tramiteTraficoSolInfoDao.getTramiteTrafSolInfoPorCodTasa(codigoTasa, true);
				if (tramiteTrafSolInfoVehiculoVO != null) {
					return conversor.transform(tramiteTrafSolInfoVehiculoVO, SolicitudInformeVehiculoDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de solicitud por su tasa, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isSolicitudesOtrosColegiados(String matricula, String bastidor, String nive, String numColegiado) {
		boolean result = false;
		if (numColegiado != null) {
			String ultimoSolicitante = tramiteTraficoSolInfoDao.getUltimoSolicitante(matricula, bastidor, nive);
			if (ultimoSolicitante!= null && !ultimoSolicitante.equals(numColegiado)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarFacturacion (TramiteTrafFacturacionDto datosFacturacion, BigDecimal idUsuario) {
		ResultBean result;
		TramiteTrafSolInfoDto tramiteTrafSolInfoDto = getTramiteTraficoCompleto(datosFacturacion.getNumExpediente());
		if (tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null && !tramiteTrafSolInfoDto.getSolicitudes().isEmpty()) {
			datosFacturacion.setTipoTramite(TipoTramiteTrafico.Solicitud.getValorEnum());
			if (datosFacturacion.getGenerado() == null) {
				datosFacturacion.setGenerado(false);
			}
			List<TramiteTrafFacturacionDto> listaFacturacion = new ArrayList<TramiteTrafFacturacionDto>();
			for (SolicitudInformeVehiculoDto solicitud : tramiteTrafSolInfoDto.getSolicitudes()) {
				// Copia
				TramiteTrafFacturacionDto facturaSol = conversor.transform(datosFacturacion, TramiteTrafFacturacionDto.class);
				facturaSol.setBastidor(solicitud.getVehiculo().getBastidor());
				facturaSol.setMatricula(solicitud.getVehiculo().getMatricula());
				facturaSol.setCodTasa(solicitud.getTasa().getCodigoTasa());
				listaFacturacion.add(facturaSol);
			}

			result  = servicioFacturacion.guardarMultiple(listaFacturacion, idUsuario, datosFacturacion.getPersona(), datosFacturacion.getDireccion());
			result.addAttachment(TRAMITE_DETALLE, getTramiteTraficoCompleto(datosFacturacion.getNumExpediente()));
		} else {
			result = new ResultBean(Boolean.TRUE, "No se guardado facturacion");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean actualizarTramiteSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(tramiteTrafSolInfoVehiculoVO != null){
				tramiteTraficoSolInfoDao.actualizarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido actualizar la solicitud de información del vehículo porque se encuentra vacia.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la solicitud de informacion del vehiculo, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar la solicitud de información del vehículo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarTramiteVOConEvolucion(TramiteTrafSolInfoVO tramiteTrafSolInfoVO, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(tramiteTrafSolInfoVO != null){
				BigDecimal estadoAnterior = tramiteTrafSolInfoVO.getEstado();
				tramiteTrafSolInfoVO.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
				tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);
				guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), estadoAnterior, estadoNuevo.getValorEnum(), idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido actualizar la solicitud de información porque se encuentra vacia.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la solicitud de informacion, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar la solicitud de información.");
		}
		return resultado;
	}

	/********************************************Action y jsp nuevos****************************************************************/
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoTramSolInfoBean getTramSolInfoPantalla(BigDecimal numExpediente) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		try {
			if(numExpediente != null){
				TramiteTrafSolInfoVO tramiteTrafSolInfoVO = getTramiteTraficoSolInfoVO(numExpediente, Boolean.TRUE);
				if (tramiteTrafSolInfoVO != null) {
					TramiteTrafSolInfoDto tramiteTrafSolInfo = conversor.transform(tramiteTrafSolInfoVO, TramiteTrafSolInfoDto.class);
					if (tramiteTrafSolInfo != null) {
						List<SolicitudInformeVehiculoDto> solicitudes = conversor.transform(tramiteTrafSolInfoVO.getSolicitudes(), SolicitudInformeVehiculoDto.class);
						tramiteTrafSolInfo.setSolicitudes(solicitudes);
					}
					resultado.setTramiteTrafSolInfo(tramiteTrafSolInfo);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No existe un tramite de solicitud de información para el expediente: " + numExpediente + ".");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede obtener el detalle del tramite de solicitud de información con el numero de expediente vacío.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de solicitud de información, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de obtener el tramite de solicitud de información.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public TramiteTrafSolInfoVO getTramiteTraficoSolInfoVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		if (numExpediente != null) {
			return tramiteTraficoSolInfoDao.getTramiteTrafSolInfoNuevo(numExpediente, tramiteCompleto);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoTramSolInfoBean guardarTramSolInfo(TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		try {
			resultado = validarDatosMinimosGuardado(tramiteTrafSolInfo);
			if(!resultado.getError()){
				TramiteTrafSolInfoVO tramiteTrafSolInfoVO = conversor.transform(tramiteTrafSolInfo, TramiteTrafSolInfoVO.class);
				if(tramiteTrafSolInfoVO != null){
					Date fecha = new Date();
					if(!resultado.getError()){
						tramiteTrafSolInfoVO.setFechaUltModif(fecha);
						BigDecimal estadoAnterior = tramiteTrafSolInfoVO.getEstado();
						tramiteTrafSolInfoVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
						if(tramiteTrafSolInfoVO.getNumExpediente() != null){
							resultado = actualizarTramiteSolInfo(tramiteTrafSolInfoVO, tramiteTrafSolInfo.getSolInfoVehiculo(), idUsuario, fecha);
						} else {
							resultado = altaNuevoTramiteSolInfo(tramiteTrafSolInfoVO, tramiteTrafSolInfo.getSolInfoVehiculo(), idUsuario, fecha);
						}
						if(!resultado.getError()){
							guardarEvolucionTramite(tramiteTrafSolInfoVO.getNumExpediente(), estadoAnterior, EstadoTramiteTrafico.Iniciado.getValorEnum(), idUsuario);
							resultado.setNumExpediente(tramiteTrafSolInfoVO.getNumExpediente());
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Ha sucedido un error a la hora de convertir el tramite de pantalla.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramite de solicitud de información, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de guardar el tramite de solicitud de información.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean actualizarTramiteSolInfo(TramiteTrafSolInfoVO tramiteTrafSolInfoVO, SolicitudInformeVehiculoDto solicitudInformeVehiculoDto, BigDecimal idUsuario, Date fecha) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoVO);
		if(solicitudInformeVehiculoDto != null && ((solicitudInformeVehiculoDto.getMatricula() != null && !solicitudInformeVehiculoDto.getMatricula().isEmpty())
				|| (solicitudInformeVehiculoDto.getBastidor() != null && !solicitudInformeVehiculoDto.getBastidor().isEmpty()))){
			String codTasaAnt = "";
			TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoBBDD  = tramiteTraficoSolInfoDao.getTramiteTrafSolInfoVehiculoPorId(solicitudInformeVehiculoDto.getIdTramiteSolInfo());
			if(tramiteTrafSolInfoVehiculoBBDD != null){
				if(tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa() != null && !tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa().isEmpty()){
					codTasaAnt = tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa();
				}
			} else {
				tramiteTrafSolInfoVehiculoBBDD = new TramiteTrafSolInfoVehiculoVO();
				solicitudInformeVehiculoDto.getTramiteTrafico().setNumExpediente(tramiteTrafSolInfoVO.getNumExpediente());
				solicitudInformeVehiculoDto.getTramiteTrafico().setNumColegiado(tramiteTrafSolInfoVO.getNumColegiado());
				solicitudInformeVehiculoDto.setEstado(EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum());
			}
			conversor.transform(solicitudInformeVehiculoDto, tramiteTrafSolInfoVehiculoBBDD);
			resultado = gestionarAsignarDesasignarTasas(codTasaAnt,tramiteTrafSolInfoVehiculoBBDD);
			if(!resultado.getError()){
				tramiteTraficoSolInfoDao.guardarOActualizarTramSolInfoVehiculo(tramiteTrafSolInfoVehiculoBBDD);
			}
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean gestionarAsignarDesasignarTasas(String codTasaAnt, TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoBBDD) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		ResultBean resulTasa = new ResultBean(Boolean.FALSE);
		if(codTasaAnt == null && tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa() != null && tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa().isEmpty()){
			resulTasa= servicioTasa.asignarTasa(tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa(), tramiteTrafSolInfoVehiculoBBDD.getId().getNumExpediente());
			if(resulTasa.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Ha sucedido un error a la hora de asignar la tasa al trámite.");
				return resultado;
			}
		} else if(!codTasaAnt.equals(tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa())){
			resulTasa = servicioTasa.desasignarTasaExpediente(codTasaAnt, tramiteTrafSolInfoVehiculoBBDD.getId().getNumExpediente(), TipoTasa.CuatroUno.getValorEnum());
			if(resulTasa.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede modificar el trámite porque no se puede desasignar la tasa anterior.");
				return resultado;
			}
			resulTasa= servicioTasa.asignarTasa(tramiteTrafSolInfoVehiculoBBDD.getCodigoTasa(), tramiteTrafSolInfoVehiculoBBDD.getId().getNumExpediente());
			if(resulTasa.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Ha sucedido un error a la hora de asignar la tasa al trámite.");
				return resultado;
			}
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean altaNuevoTramiteSolInfo(TramiteTrafSolInfoVO tramiteTrafSolInfoVO, SolicitudInformeVehiculoDto solicitudInformeVehiculoDto, BigDecimal idUsuario, Date fecha) throws Exception {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		tramiteTrafSolInfoVO.setNumExpediente(servicioTramiteTrafico.generarNumExpediente(tramiteTrafSolInfoVO.getNumColegiado()));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramiteTrafSolInfoVO.setUsuario(usuario);
		tramiteTrafSolInfoVO.setFechaAlta(fecha);
		tramiteTraficoSolInfoDao.guardar(tramiteTrafSolInfoVO);
		if(solicitudInformeVehiculoDto != null){
			TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = conversor.transform(solicitudInformeVehiculoDto, TramiteTrafSolInfoVehiculoVO.class);
			ResultBean resultTasa = servicioTasa.asignarTasa(solicitudInformeVehiculoDto.getTasa().getCodigoTasa(), tramiteTrafSolInfoVO.getNumExpediente());
			if(!resultTasa.getError()){
				TramiteTrafSolInfoVehiculoPK id = new TramiteTrafSolInfoVehiculoPK();
				id.setNumExpediente(tramiteTrafSolInfoVO.getNumExpediente());
				tramiteTrafSolInfoVehiculoVO.setId(id);
				tramiteTraficoSolInfoDao.guardarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(resultTasa.getMensaje());
			}
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean validarDatosMinimosGuardado(TramiteTrafSolInfoDto tramiteTrafSolInfo) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		if(tramiteTrafSolInfo == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Debe de rellenar algún dato del trámite para poder realizar su guardado.");
		} else if(tramiteTrafSolInfo.getContrato() == null || tramiteTrafSolInfo.getContrato().getIdContrato() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Debe de indicar el contrato para poder guardar el trámite.");
		} else if(tramiteTrafSolInfo.getSolInfoVehiculo() != null){
			if((tramiteTrafSolInfo.getSolInfoVehiculo().getMatricula() == null || tramiteTrafSolInfo.getSolInfoVehiculo().getMatricula().isEmpty())
				&& (tramiteTrafSolInfo.getSolInfoVehiculo().getBastidor() == null || tramiteTrafSolInfo.getSolInfoVehiculo().getBastidor().isEmpty())){
				if((tramiteTrafSolInfo.getSolInfoVehiculo().getTasa() != null && tramiteTrafSolInfo.getSolInfoVehiculo().getTasa().getCodigoTasa() != null 
					&& !tramiteTrafSolInfo.getSolInfoVehiculo().getTasa().getCodigoTasa().isEmpty()) || (tramiteTrafSolInfo.getSolInfoVehiculo().getMotivoInteve() != null
					&& !tramiteTrafSolInfo.getSolInfoVehiculo().getMotivoInteve().isEmpty()) || (tramiteTrafSolInfo.getSolInfoVehiculo().getTipoInforme() != null &&
					!tramiteTrafSolInfo.getSolInfoVehiculo().getTipoInforme().isEmpty())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Para poder guardar el vehículo debe de indicar la matricula o el bastidor.");
				}
			} else {
				if(tramiteTrafSolInfo.getSolInfoVehiculo().getTasa() == null || tramiteTrafSolInfo.getSolInfoVehiculo().getTasa().getCodigoTasa() == null 
					|| tramiteTrafSolInfo.getSolInfoVehiculo().getTasa().getCodigoTasa().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Para poder guardar el vehículo debe de indicar la tasa del informe.");
				} else if(tramiteTrafSolInfo.getSolInfoVehiculo().getMotivoInteve() == null || tramiteTrafSolInfo.getSolInfoVehiculo().getMotivoInteve().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Para poder guardar el vehículo debe de indicar el motivo del informe.");
				} else if(tramiteTrafSolInfo.getSolInfoVehiculo().getTipoInforme() == null || tramiteTrafSolInfo.getSolInfoVehiculo().getTipoInforme().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Para poder guardar el vehículo debe de indicar el tipo de informe.");
				}
			}
		} else if(tramiteTrafSolInfo.getNumColegiado() == null || tramiteTrafSolInfo.getNumColegiado().isEmpty()){
			ContratoDto contratoDto = servicioContrato.getContratoDto(tramiteTrafSolInfo.getContrato().getIdContrato());
			if(contratoDto != null && contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getNumColegiado() != null 
				&& !contratoDto.getColegiadoDto().getNumColegiado().isEmpty()){
				tramiteTrafSolInfo.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existen datos del colegiado para poder dar de alta el trámite.");
			}
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public SolicitudInformeVehiculoDto getTramiteSolInfoVehiculo(Long idTramiteSolInfo, BigDecimal numExpediente) {
		try {
			if(idTramiteSolInfo != null || numExpediente != null){
				TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = getTramiteSolInfoVehiculoVO(idTramiteSolInfo,numExpediente);
				if(tramiteTrafSolInfoVehiculoVO != null){
					return conversor.transform(tramiteTrafSolInfoVehiculoVO, SolicitudInformeVehiculoDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de solicitud de informacion del vehiculo, error: ",e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public TramiteTrafSolInfoVehiculoVO getTramiteSolInfoVehiculoVO(Long idTramiteSolInfo, BigDecimal numExpediente) {
		try {
			if(idTramiteSolInfo != null || numExpediente != null){
				return tramiteTraficoSolInfoDao.getTramiteTrafSolInfoVehiculo(idTramiteSolInfo, numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite de solicitud de informacion del vehiculo, error: ",e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoTramSolInfoBean eliminarSolicitudes(String codSeleccionados, TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				if(tramiteTrafSolInfo != null && tramiteTrafSolInfo.getNumExpediente() != null){
					TramiteTrafSolInfoVO tramiteTrafSolInfoBBDD = tramiteTraficoSolInfoDao.getTramiteTrafSolInfoNuevo(tramiteTrafSolInfo.getNumExpediente(), Boolean.FALSE);
					if(tramiteTrafSolInfoBBDD != null){
						String[] idsTramitesSolInfoVehiculos = codSeleccionados.split("-");
						for(String idTramiteSolInfoVeh : idsTramitesSolInfoVehiculos){
							TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO = getTramiteSolInfoVehiculoVO(Long.parseLong(idTramiteSolInfoVeh), null);
							resultado = validarEliminarTramSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO,tramiteTrafSolInfoBBDD,tienePermisoAdmin);
							if(!resultado.getError()){
								ResultBean resultTasa = servicioTasa.desasignarTasaExpediente(tramiteTrafSolInfoVehiculoVO.getCodigoTasa(), tramiteTrafSolInfoVehiculoVO.getId().getNumExpediente(), TipoTasa.CuatroUno.getValorEnum());
								if(!resultTasa.getError()){
									tramiteTraficoSolInfoDao.borrarSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
								} else {
									resultado.setError(Boolean.TRUE);
									resultado.setMensajeError(resultTasa.getMensaje());
								}
							}
							if(resultado.getError()){
								break;
							}
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("No se pueden eliminar las solicitudes seleccionadas porque existen datos del tramite.");
					}
				} else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se pueden eliminar las solicitudes seleccionadas porque no se puede consultar los datos del tramite.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Debe de seleccionar alguna solicitud para poder proceder a su eliminación.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar las solicitudes de información seleccionadas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de eliminar las solicitudes de información seleccionadas.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean validarEliminarTramSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO, TramiteTrafSolInfoVO tramiteTrafSolInfoBBDD, Boolean tienePermisoAdmin) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		if(tramiteTrafSolInfoVehiculoVO == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No existen datos en la base de datos para una de las solicitudes que desea eliminar.");
		}else{
			String mensaje = "";
			if(tramiteTrafSolInfoVehiculoVO.getMatricula() != null && !tramiteTrafSolInfoVehiculoVO.getMatricula().isEmpty()){
				mensaje = "matricula " + tramiteTrafSolInfoVehiculoVO.getMatricula();
			} else if(tramiteTrafSolInfoVehiculoVO.getBastidor() != null && !tramiteTrafSolInfoVehiculoVO.getBastidor().isEmpty()){
				mensaje = "bastidor " + tramiteTrafSolInfoVehiculoVO.getBastidor();
			} 
			if(tramiteTrafSolInfoVehiculoVO.getId().getNumExpediente().compareTo(tramiteTrafSolInfoBBDD.getNumExpediente()) != 0){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede eliminar la solicitud con " + mensaje + " porque no es del expediente del tramite.");
			} else if(!tienePermisoAdmin){
				if(tramiteTrafSolInfoVehiculoVO.getTramiteTrafico() != null && tramiteTrafSolInfoVehiculoVO.getTramiteTrafico().getContrato() != null && tramiteTrafSolInfoVehiculoVO.getTramiteTrafico().getContrato().getIdContrato() != null){
					if(tramiteTrafSolInfoVehiculoVO.getTramiteTrafico().getContrato().getIdContrato().compareTo(tramiteTrafSolInfoBBDD.getContrato().getIdContrato().longValue()) != 0){
						resultado.setError(Boolean.TRUE);
						resultado.setMensajeError("No se puede eliminar la solicitud con " + mensaje + " porque la solicitud no pertenece al contrato del colegiado.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se puede eliminar la solicitud con " + mensaje + " porque no se ha podido verificar se la solicitud pertenece al contrato del colegiado.");
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoTramSolInfoBean generarXmlApp(TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		try {
			if(tramiteTrafSolInfo != null && tramiteTrafSolInfo.getNumExpediente() != null){
				TramiteTrafSolInfoVO tramiteTrafSolInfoBBDD = tramiteTraficoSolInfoDao.getTramiteTrafSolInfoNuevo(tramiteTrafSolInfo.getNumExpediente(), Boolean.TRUE);
				resultado = validarDatosGeneracionXml(tramiteTrafSolInfoBBDD);
				if(!resultado.getError()){
					Date fechaUltModif = new Date();
					tramiteTrafSolInfoBBDD.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Respuesta_APP.getValorEnum()));
					tramiteTrafSolInfoBBDD.setFechaUltModif(fechaUltModif);
					List<TramiteTrafSolInfoVehiculoVO> listaTramitesSolInfoVeh = obtenerListaTramitesGenerarXml(tramiteTrafSolInfoBBDD.getSolicitudes());
					resultado = generarXml(listaTramitesSolInfoVeh, tramiteTrafSolInfoBBDD);
					if(!resultado.getError()){
						for(TramiteTrafSolInfoVehiculoVO trafSolInfoVehiculoVO : listaTramitesSolInfoVeh){
							tramiteTraficoSolInfoDao.guardarOActualizarTramSolInfoVehiculo(trafSolInfoVehiculoVO);
						}
						tramiteTraficoSolInfoDao.actualizar(tramiteTrafSolInfoBBDD);
						guardarEvolucionTramite(tramiteTrafSolInfoBBDD.getNumExpediente(), new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()),
								tramiteTrafSolInfoBBDD.getEstado().toString(),idUsuario);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede generar el XML porque no se encuentran datos del trámite.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el XML, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el XML.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoTramSolInfoBean generarXml(List<TramiteTrafSolInfoVehiculoVO> listaTramitesSolInfoVeh,	TramiteTrafSolInfoVO tramiteTrafSolInfoBBDD) {
		return new ResultadoTramSolInfoBean(Boolean.FALSE);
	}

	private List<TramiteTrafSolInfoVehiculoVO> obtenerListaTramitesGenerarXml(List<TramiteTrafSolInfoVehiculoVO> solicitudes) {
		List<TramiteTrafSolInfoVehiculoVO> listaTramites = new ArrayList<>();
		for(TramiteTrafSolInfoVehiculoVO solInfoVehiculoVO : solicitudes){
			if(EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum().equals(solInfoVehiculoVO.getEstado().toString())){
				solInfoVehiculoVO.setEstado(new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum()));
				listaTramites.add(solInfoVehiculoVO);
			}
		}
		return listaTramites;
	}

	private ResultadoTramSolInfoBean validarDatosGeneracionXml(TramiteTrafSolInfoVO tramiteTrafSolInfoBBDD) {
		ResultadoTramSolInfoBean resultado = new ResultadoTramSolInfoBean(Boolean.FALSE);
		if(tramiteTrafSolInfoBBDD == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No existen datos en del tramite en la base de datos.");
		} else if(!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafSolInfoBBDD.getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("El tramite debe de estar en estado Iniciado para poder generar el xml.");
		} else if(tramiteTrafSolInfoBBDD.getSolicitudes() == null || tramiteTrafSolInfoBBDD.getSolicitudes().isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede generar el xml porque no tiene solicitudes de información asociadas.");
		} else {
			Boolean existeSolInfoIniciada = Boolean.FALSE;
			for(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : tramiteTrafSolInfoBBDD.getSolicitudes()){
				if(EstadoTramiteSolicitudInformacion.Iniciado.getValorEnum().equals(tramiteTrafSolInfoVehiculoVO.getEstado().toString())){
					existeSolInfoIniciada = Boolean.TRUE;
					break;
				}
			}
			if(!existeSolInfoIniciada){
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede generar el xml porque no tiene solicitudes de información asociadas en estado Iniciado.");
			}
		}
		return resultado;
	}

}