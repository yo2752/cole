package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteServiceLocator;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWSBindingStub;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebServiceJustificanteProf;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.model.webservice.handler.SoapJustificanteProfesionalFilesHandler;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceJustificanteProfImpl implements ServicioWebServiceJustificanteProf {

	private static final long serialVersionUID = -1935963357300677088L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceJustificanteProfImpl.class);

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	public ResultadoJustificanteProfesional procesarEmisionJustificanteProf(ColaDto solicitud) {
		ResultadoJustificanteProfesional resultadoWS = new ResultadoJustificanteProfesional(Boolean.FALSE);
		try {
			JustificanteProfVO justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorIDInternoVO(solicitud.getIdTramite().longValue());
			if (justificanteProfVO != null) {
				ColegioDto colegioDto = servicioJustificanteProfesional.getColegioContrato(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato());
				if (colegioDto != null) {
					String xml = recogerXml(solicitud.getXmlEnviar(), justificanteProfVO.getNumExpediente());
					if (xml != null && !xml.isEmpty()) {
						JustificanteRespuestaSOAP respuestaWS = getStubJustificante(justificanteProfVO.getNumExpediente()).emitirJustificante(gestorPropiedades.valorPropertie(
								SERVICIO_JUSTIFICANTE_PROFESIONAL_DOI_PLATAFORMA), utiles.doBase64Encode(xml.getBytes(UTF_8)));
						log.info("-------" + ProcesosEnum.JUSTIFICANTE_PROFESIONAL.toString() + " Recibida Respuesta WS ------");
						if (respuestaWS != null) {
							resultadoWS = gestionarResultadoWS(respuestaWS, justificanteProfVO.getIdJustificanteInterno(), justificanteProfVO.getNumExpediente());
						} else {
							resultadoWS = new ResultadoJustificanteProfesional(true, "Ha sucedido un error a la hora realizar la peticion del justificante Profesional.");
						}
					} else {
						resultadoWS = new ResultadoJustificanteProfesional(true, "No existen datos del xml de envio.");
					}
				} else {
					resultadoWS = new ResultadoJustificanteProfesional(true, "No existen datos del colegio asociado al justificante.");
				}
			} else {
				resultadoWS = new ResultadoJustificanteProfesional(true, "No existen datos del justificante.");
			}
		} catch (Throwable e) {
			resultadoWS = new ResultadoJustificanteProfesional((Exception) e);
		}
		return resultadoWS;
	}

	@Override
	public ResultadoJustificanteProfesional procesarVerificacionJustificanteProf(ColaDto solicitud) {
		ResultadoJustificanteProfesional resultadoWS = new ResultadoJustificanteProfesional(Boolean.FALSE);
		try {
			JustificanteProfVO justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorIDInternoVO(solicitud.getIdTramite().longValue());
			if (justificanteProfVO.getCodigoVerificacion() == null || justificanteProfVO.getCodigoVerificacion().isEmpty()) {
				resultadoWS = new ResultadoJustificanteProfesional(true, "No se puede recuperar la información del trámite necesaria para invocar al webservice.");
			} else {
				JustificanteRespuestaSOAP respuestaWS = getStubJustificante(justificanteProfVO.getNumExpediente()).verificarJustificante(justificanteProfVO.getCodigoVerificacion());
				actualizaResultado(solicitud.getIdTramite().longValue(), resultadoWS, respuestaWS);
				actualizarTramiteJustificanteProfesional(solicitud, justificanteProfVO, resultadoWS, solicitud.getIdUsuario());
			}
		} catch (Throwable e) {
			resultadoWS = new ResultadoJustificanteProfesional((Exception) e);
		}
		return resultadoWS;
	}

	private void actualizaResultado(Long idJustificanteInterno, ResultadoJustificanteProfesional justificanteProfResult, JustificanteRespuestaSOAP respuestaSoap) {
		if (justificanteProfResult != null) {
			if (respuestaSoap == null) {
				justificanteProfResult.setError(true);
				justificanteProfResult.setMensajeError(ERROR_GENERICO);
			} else {
				if (respuestaSoap.getAvisos() != null) {
					String avisos = "";
					for (JustificanteCodigoRetorno aviso : respuestaSoap.getAvisos()) {
						avisos += avisos + "-" + aviso.getDescripcion();
					}
					justificanteProfResult.setMensajeError(avisos);
				}
				justificanteProfResult.setNumExpediente(respuestaSoap.getNumeroJustificante());
				if (respuestaSoap.getJustificante() != null && respuestaSoap.getJustificante().length > 0) {
					justificanteProfResult.setJustificante(Base64.decodeBase64(respuestaSoap.getJustificante()));
				} else {
					justificanteProfResult.setError(true);
					justificanteProfResult.setMensajeError("No se ha obtenido pdf con el justificante profesional para el justificante con id " + (idJustificanteInterno != null ? idJustificanteInterno
							.toString() : null));
				}
			}
		}
	}

	private void actualizarTramiteJustificanteProfesional(ColaDto cola, JustificanteProfVO justificanteProfVO, ResultadoJustificanteProfesional resultado, BigDecimal idUsuario) {
		if (!resultado.isError() && resultado.getNumExpediente() != null && !resultado.getNumExpediente().isEmpty()) {
			justificanteProfVO.setVerificado("SI");
			justificanteProfVO.setIdJustificante(new BigDecimal(resultado.getNumExpediente()));
			if (justificanteProfVO.getFechaInicio() == null) {
				justificanteProfVO.setFechaInicio(Calendar.getInstance().getTime());
			}

			if (log.isInfoEnabled()) {
				log.info("Se actualiza el estado del justificante profesional con identificador interno " + justificanteProfVO.getIdJustificanteInterno() + " a finalizado");
			}
			servicioJustificanteProfesionalImprimir.guardarDocumento(justificanteProfVO, resultado.getNumExpediente(), resultado.getJustificante());
		} else {
			justificanteProfVO.setVerificado("NO");
			if (log.isInfoEnabled()) {
				log.info("Se actualiza el estado del justificante profesional con identificador interno " + justificanteProfVO.getIdJustificanteInterno() + " a finalizado con error");
			}
		}
		BigDecimal estado = new BigDecimal(EstadoJustificante.Ok.getValorEnum());
		ResultBean resultadoGuardar = servicioJustificanteProfesional.guardarJustificanteProfesional(justificanteProfVO, justificanteProfVO.getNumExpediente(), idUsuario, justificanteProfVO
				.getEstado(), estado, resultado.getMensajeError());
		if (resultadoGuardar.getError()) {
			resultado.setError(true);
			if (resultadoGuardar.getMensaje() != null && !resultadoGuardar.getMensaje().isEmpty()) {
				resultado.setMensajeError(resultadoGuardar.getMensaje());
			}
		} else {
			crearNotificacion(cola, justificanteProfVO, estado);
		}
	}

	private void crearNotificacion(ColaDto cola, JustificanteProfVO justificanteProfVO, BigDecimal estadoNuevo) {
		if (justificanteProfVO != null) {
			NotificacionDto notifDto = new NotificacionDto();
			notifDto.setEstadoAnt(justificanteProfVO.getEstado());
			notifDto.setEstadoNue(estadoNuevo);
			notifDto.setDescripcion(NOTIFICACION);
			notifDto.setTipoTramite(TIPO_TRAMITE_NOTIFICACION);
			notifDto.setIdTramite(BigDecimal.valueOf(justificanteProfVO.getIdJustificanteInterno()));
			notifDto.setIdUsuario(cola.getIdUsuario().longValue());
			servicioNotificacion.crearNotificacion(notifDto);
		}
	}

	private ResultadoJustificanteProfesional gestionarResultadoWS(JustificanteRespuestaSOAP respuestaWS, Long idJustificanteInterno, BigDecimal numExpediente) {
		ResultadoJustificanteProfesional resultado = null;
		if (respuestaWS.getAvisos() != null && respuestaWS.getAvisos().length > 0) {
			String mensajeError = null;
			for (JustificanteCodigoRetorno pproofError : respuestaWS.getAvisos()) {
				if ("PROOFI00115".equals(pproofError.getCodigo()) || "PROOFI00A04".equals(pproofError.getCodigo())) {
					return new ResultadoJustificanteProfesional(new Exception(pproofError.getCodigo() + " - " + pproofError.getDescripcion()));
				} else {
					mensajeError = pproofError.getCodigo() + " - " + pproofError.getDescripcion() + "; ";
				}
			}
			resultado = new ResultadoJustificanteProfesional(true, mensajeError);
		} else {
			if (respuestaWS.getNumeroJustificante() == null || respuestaWS.getNumeroJustificante().isEmpty()) {
				resultado = new ResultadoJustificanteProfesional(new Exception("El WS no ha devuelto id de justificante"));
			} else if (respuestaWS.getJustificante() == null) {
				resultado = new ResultadoJustificanteProfesional(new Exception("El WS no ha devuelto justificante pdf"));
			} else {
				ResultBean resultJustfProf = servicioJustificanteProfesional.guardarJustifProfWS(idJustificanteInterno, numExpediente, new BigDecimal(respuestaWS.getNumeroJustificante()), respuestaWS
						.getJustificante());
				if (resultJustfProf.getError()) {
					resultado = new ResultadoJustificanteProfesional(new Exception(resultJustfProf.getMensaje()));
				} else {
					resultado = new ResultadoJustificanteProfesional(false);
					resultado.setRespuesta("Ejecucion correcta");
				}
			}
		}
		return resultado;
	}

	private JustificanteWSBindingStub getStubJustificante(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		JustificanteWSBindingStub stubJustificanteProf = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_JUSTIFICANTES);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_JUSTIFICANTES_PRO));
		JustificanteWS justificanteWS = null;
		if (miUrl != null) {
			JustificanteServiceLocator justificanteServiceLocator = new JustificanteServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(), justificanteServiceLocator.getJustificanteServiceWSDDServiceName());

			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = justificanteServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapJustificanteProfesionalFilesHandler.class);

			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapJustificanteProfesionalFilesHandler.PROPERTY_KEY_ID, numExpediente.toString());
			logHandlerInfo.setHandlerConfig(filesConfig);
			list.add(logHandlerInfo);

			justificanteWS = justificanteServiceLocator.getJustificanteService();
			stubJustificanteProf = (JustificanteWSBindingStub) justificanteWS;
			stubJustificanteProf.setTimeout(Integer.parseInt(timeOut));
		}
		return stubJustificanteProf;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.ENVIO, fecha, xmlEnviar,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String(by, UTF_8);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return xml;
	}

	@Override
	public void cambiarEstadoJustificanteProfSolicitud(BigDecimal idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		if (idJustificanteInterno != null) {
			servicioJustificanteProfesional.cambiarEstadoConEvolucion(idJustificanteInterno.longValue(), estadoNuevo, idUsuario);
		}
	}

	@Override
	public void tratarDevolverCredito(BigDecimal idJustificanteInterno, BigDecimal idUsuario) {
		if (idJustificanteInterno != null) {
			servicioJustificanteProfesional.devolverCreditos(idJustificanteInterno.longValue(), idUsuario);
		}
	}
}
