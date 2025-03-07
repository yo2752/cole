package org.gestoresmadrid.oegam2comun.presentacion05.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.presentacion05.service.ServicioPresentacion05;
import org.gestoresmadrid.oegam2comun.presentacion05.view.bean.Presentacion05Bean;
import org.gestoresmadrid.oegam2comun.presentacion05.view.bean.ResultadoPresentacion05Bean;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import general.utiles.UtilesCadenaCaracteres;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPresentacion05Impl implements ServicioPresentacion05 {

	private static final long serialVersionUID = -4280865743611971688L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPresentacion05Impl.class);

	@Autowired
	private ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	private ServicioComunVehiculo servicioVehiculo;

	@Autowired
	private UtilesFecha utilesFecha;

	@Override
	public ResultadoPresentacion05Bean presentacionJnlp(BigDecimal numExpediente) {
		ResultadoPresentacion05Bean resultado = new ResultadoPresentacion05Bean(Boolean.FALSE);
		try {
			TramiteTrafMatrVO tramiteTrafMatrVO = servicioComunTramiteTrafico.getTramiteMatriculacionVO(numExpediente, Boolean.TRUE);
			if (tramiteTrafMatrVO != null) {
				ResultadoPresentacion05Bean resultValidacion = validacionesPresentacion05(tramiteTrafMatrVO);
				if (!resultValidacion.getError()) {
					Presentacion05Bean presentacion05Bean = rellenarDatos(tramiteTrafMatrVO);
					if (!resultado.getError()) {
						resultado.setPresentacion05Bean(presentacion05Bean);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Hubo un problema al realizar la navegacion web.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setListaMensajes(resultValidacion.getListaMensajes());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido recuperar el tramite o el tramite no es de matriculacion, numero expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error al presentar el 05, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al presentar el 05.");
		}
		return resultado;
	}

	private ResultadoPresentacion05Bean validacionesPresentacion05(TramiteTrafMatrVO tramiteTrafMatrVO) {
		ResultadoPresentacion05Bean resultado = new ResultadoPresentacion05Bean(Boolean.FALSE);
		if (!EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafMatrVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("El trámite tiene que estar en estado Iniciado para poder Tramitar la solicitud 05.");
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafMatrVO.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafMatrVO
				.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("Para poder Tramitar la solicitud 05, el tipo de tramite solo puede ser de Matriculacion o Transmision Electronica.");
		} else if (tramiteTrafMatrVO.getContrato().getColegiado().getUsuario().getNif() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("El NIF del colegiado es obligatorio para poder Tramitar la solicitud 05.");
		} else if (tramiteTrafMatrVO.getContrato().getColegiado().getUsuario().getApellidosNombre() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("Los apellidos del colegiado son obligatorios para poder Tramitar la solicitud 05.");
		} else if (tramiteTrafMatrVO.getFechaPresentacion() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("La fecha de presentación es obligatorio para poder Tramitar la solicitud 05.");
		} else if (tramiteTrafMatrVO.getVehiculo() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addListaMensajes("No existe vehiculo guardado. Es necesario tener un vehiculo con el bastidor obligatorio para poder tramitar la solicitud 05.");
		} else if (tramiteTrafMatrVO.getVehiculo() != null) {
			if (tramiteTrafMatrVO.getVehiculo().getBastidor() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addListaMensajes("El bastidor es obligatorio para poder Tramitar la solicitud 05.");
			}
		}
		for (IntervinienteTraficoVO tramiteVo : tramiteTrafMatrVO.getIntervinienteTraficosAsList()) {
			if (TipoInterviniente.Titular.getValorEnum().equals(tramiteVo.getTipoIntervinienteVO().getTipoInterviniente())) {
				if (TipoPersona.Fisica.getValorEnum().equals(tramiteVo.getPersona().getTipoPersona())) {
					if (tramiteVo.getPersona().getApellido1RazonSocial() == null || tramiteVo.getPersona().getApellido2() == null) {
						resultado.setError(Boolean.TRUE);
						resultado.addListaMensajes("Los apellidos del titular son obligatorios para poder Tramitar la solicitud 05.");
					}
				} else if (TipoPersona.Juridica.getValorEnum().equals(tramiteVo.getPersona().getTipoPersona())) {
					if (tramiteVo.getPersona().getApellido1RazonSocial() == null) {
						resultado.setError(Boolean.TRUE);
						resultado.addListaMensajes("Para personas Juridicas, la razón social del titular es obligatorio para poder Tramitar la solicitud 05.");
					}
				}
				if (tramiteVo.getPersona().getId().getNif() == null) {
					resultado.setError(Boolean.TRUE);
					resultado.addListaMensajes("El NIF del titular es obligatorio para poder Tramitar la solicitud 05.");
				}
			}
		}
		return resultado;
	}

	private Presentacion05Bean rellenarDatos(TramiteTrafMatrVO tramiteTrafMatrVO) {
		Presentacion05Bean presentacion05Bean = new Presentacion05Bean();
		if (tramiteTrafMatrVO.getVehiculo() != null) {
			presentacion05Bean.setBastidor(tramiteTrafMatrVO.getVehiculo().getBastidor());
			String marca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatrVO.getVehiculo().getCodigoMarca(), Boolean.TRUE);
			presentacion05Bean.setMarca(marca);
			presentacion05Bean.setModelo(tramiteTrafMatrVO.getVehiculo().getModelo());
			presentacion05Bean.setCilindrada(tramiteTrafMatrVO.getVehiculo().getCilindrada());
			presentacion05Bean.setCriterioConstruccion(tramiteTrafMatrVO.getVehiculo().getCilindrada());
			presentacion05Bean.setCriterioUtilizacion(tramiteTrafMatrVO.getVehiculo().getIdCriterioUtilizacion());
			presentacion05Bean.setCriterioConstruccion(tramiteTrafMatrVO.getVehiculo().getIdCriterioConstruccion());
			presentacion05Bean.setClasificacion(tramiteTrafMatrVO.getVehiculo().getIdDirectivaCee());
			presentacion05Bean.setCombustible(tramiteTrafMatrVO.getVehiculo().getIdCarburante());

			if ("SI".equals(tramiteTrafMatrVO.getVehiculo().getVehiUsado()) || "S".equals(tramiteTrafMatrVO.getVehiculo().getVehiUsado())) {
				presentacion05Bean.setUsado("SI");
				presentacion05Bean.setProcedencia(tramiteTrafMatrVO.getVehiculo().getIdProcedencia());
				presentacion05Bean.setFechaPrimMatriculacion(utilesFecha.formatoFecha(tramiteTrafMatrVO.getVehiculo().getFechaPrimMatri()));
			} else {
				presentacion05Bean.setFechaMatriculacion(utilesFecha.formatoFecha(tramiteTrafMatrVO.getVehiculo().getFechaMatriculacion()));
			}
			presentacion05Bean.setCo2(tramiteTrafMatrVO.getVehiculo().getCo2());
			presentacion05Bean.setCodigoITV(tramiteTrafMatrVO.getVehiculo().getCoditv());
			presentacion05Bean.setTipoITV(tramiteTrafMatrVO.getVehiculo().getTipoItv());
			presentacion05Bean.setTipoTarjetaITV(tramiteTrafMatrVO.getVehiculo().getIdTipoTarjetaItv());

			if (tramiteTrafMatrVO.getVehiculo().getPersona() != null) {
				presentacion05Bean.setNifIntegrador(tramiteTrafMatrVO.getVehiculo().getNifIntegrador());
				String nombreApellidos = tramiteTrafMatrVO.getVehiculo().getPersona().getApellido1RazonSocial();
				if (StringUtils.isNotBlank(tramiteTrafMatrVO.getVehiculo().getPersona().getApellido2())) {
					nombreApellidos += " " + tramiteTrafMatrVO.getVehiculo().getPersona().getApellido2();
				}
				if (StringUtils.isNotBlank(tramiteTrafMatrVO.getVehiculo().getPersona().getNombre())) {
					nombreApellidos += " " + tramiteTrafMatrVO.getVehiculo().getPersona().getNombre();
				}
				presentacion05Bean.setApellidosNombreIntegrador(UtilesCadenaCaracteres.reemplazarAcentoss(nombreApellidos));
			}

		}
		presentacion05Bean.setNifGestor(getNifColegiado(tramiteTrafMatrVO));
		presentacion05Bean.setApellidosNombreGestor(getApeRazonSocialColegiado(tramiteTrafMatrVO));
		Date date = new Date();
		presentacion05Bean.setEjercicioServicio(String.valueOf(date.getYear()));

		presentacion05Bean.setObservaciones(tramiteTrafMatrVO.getObservaciones576());
		presentacion05Bean.setReduccion05(tramiteTrafMatrVO.getIdReduccion05());
		presentacion05Bean.setNumExpediente(tramiteTrafMatrVO.getNumExpediente().toString());
		return presentacion05Bean;
	}

	private String getApeRazonSocialColegiado(TramiteTrafMatrVO tramiteTrafMatrVO) {
		String apeRazonSocialColegiado = "";
		if (tramiteTrafMatrVO.getContrato() != null && tramiteTrafMatrVO.getContrato().getColegiado() != null && tramiteTrafMatrVO.getContrato().getColegiado().getUsuario() != null
				&& tramiteTrafMatrVO.getContrato().getColegiado().getUsuario().getApellidosNombre() != null) {
			String[] apellRSocial = tramiteTrafMatrVO.getContrato().getColegiado().getUsuario().getApellidosNombre().split(",");
			apeRazonSocialColegiado = apellRSocial[0];
		}
		if (StringUtils.isNotBlank(apeRazonSocialColegiado) && apeRazonSocialColegiado.length() > 30) {
			apeRazonSocialColegiado = apeRazonSocialColegiado.substring(0, 30);
		}

		return UtilesCadenaCaracteres.reemplazarAcentoss(apeRazonSocialColegiado);
	}

	private String getNifColegiado(TramiteTrafMatrVO tramiteTrafMatrVO) {
		String nifColegiado = "";
		if (tramiteTrafMatrVO.getContrato() != null && tramiteTrafMatrVO.getContrato().getColegiado() != null && tramiteTrafMatrVO.getContrato().getColegiado().getUsuario() != null) {
			nifColegiado = tramiteTrafMatrVO.getContrato().getColegiado().getUsuario().getNif();
		}
		return nifColegiado;
	}
}
