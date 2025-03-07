package org.gestoresmadrid.oegam2comun.procesos.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceNotificacionCtit;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gescogroup.blackbox.CtitNotificationAdvise;
import com.gescogroup.blackbox.CtitNotificationError;
import com.gescogroup.blackbox.CtitNotificationImpediment;
import com.gescogroup.blackbox.CtitsoapNotificationResponse;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.utiles.XmlTransTelematicaFactory;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceNotificacionCtitImpl implements ServicioWebServiceNotificacionCtit {

	private static final long serialVersionUID = -3384818378411669298L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceNotificacionCtitImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	DGTTransmision dgtTransmision;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoCtitBean tramitarPeticion(ColaDto solicitud) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			String xml = crearXml(solicitud.getIdTramite(), solicitud.getXmlEnviar());
			if (xml != null && !xml.isEmpty()) {
				resultado = llamadaWS(xml, solicitud.getIdTramite(), solicitud.getIdUsuario());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existe el XML de envío para poder realizar la NotificacionCtit del trámite.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la NotificacionCtit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
		}
		return resultado;
	}

	private ResultadoCtitBean llamadaWS(String xml, BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		try {
			DatosCTITDto datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(numExpediente);
			if (datosCTITDto != null) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Procesando petición");
				CtitsoapNotificationResponse respuestaWS = dgtTransmision.notificationCTITTransmision(xml, datosCTITDto);
				log.info("Proceso " + ConstantesProcesos.PROCESO_NOTIFICATIONCTIT + " -- Peticion Procesada");

				resultadoWS = gestionarRespuesta(respuestaWS, numExpediente, idUsuario);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de NOTIFICACION_CTTIT.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de NOTIFICACION_CTIT");
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoCtitBean gestionarRespuesta(CtitsoapNotificationResponse respuesta, BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		if (respuesta == null) {
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de NOTIFICACION_CTIT y está se encuentra vacia"));
		} else if (ConstantesProcesos.ERROR.equals(respuesta.getResult())) {
			resultadoWS.setMensajeError(gestionarMensajes(respuesta));
			if (resultadoWS.getMensajeError().contains("generico") || resultadoWS.getMensajeError().contains("timeout") || resultadoWS.getMensajeError().contains("CTITE")
					|| compruebaErroresRecuperables(respuesta.getErrorCodes())) {
				resultadoWS.setEsRecuperable(Boolean.TRUE);
			} else if (respuesta.getLicense() != null && !respuesta.getLicense().isEmpty()) {
				guardarPTC(numExpediente, respuesta.getLicense());
			}
			resultadoWS.setError(Boolean.TRUE);
		} else {
			guardarInforme(numExpediente, respuesta.getReport());
			guardarPTC(numExpediente, respuesta.getLicense());
			if (ConstantesProcesos.OK.equals(respuesta.getResult())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Telematicamente);
				resultadoWS.setMensajeError("TRÁMITE OK");
			} else if (ConstantesProcesos.TRAMITABLE_CON_INCIDENCIAS.equals(respuesta.getResult())) {
				log.info("Proceso " + ProcesosEnum.FULL_CTIT_SEGA.getNombreEnum() + " -- " + "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
				resultadoWS.setMensajeError(gestionarMensajes(respuesta));
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Incidencias);
			} else if (ConstantesProcesos.NO_TRAMITABLE.equals(respuesta.getResult())) {
				if (respuesta.getReport() != null && !respuesta.getReport().isEmpty()) {
					resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Telematicamente);
					resultadoWS.setMensajeError("CORRECTO");
				} else {
					resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Con_Error);
					resultadoWS.setError(Boolean.TRUE);
					resultadoWS.setMensajeError(gestionarMensajes(respuesta));
				}
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setExcepcion(new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			}
		}
		return resultadoWS;
	}

	private boolean compruebaErroresRecuperables(CtitNotificationError[] listadoErrores) {
		boolean recuperable = true;

		for (CtitNotificationError error : listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private void listarErroresCTIT(CtitNotificationError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + listadoErrores[i].getKey());
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION + listadoErrores[i].getMessage());
		}
	}

	private String gestionarMensajes(CtitsoapNotificationResponse respuestaWS) {
		String respuestaError = "";
		CtitNotificationError[] listadoErrores = respuestaWS.getErrorCodes();
		if (listadoErrores != null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}

		CtitNotificationImpediment[] listadoImpedimentos = respuestaWS.getImpedimentCodes();
		if (listadoImpedimentos != null) {
			if (!"".equals(respuestaError)) {
				respuestaError += " - ";
			}
			respuestaError += getImpedimentos(listadoImpedimentos);
		}

		CtitNotificationAdvise[] listadoAvisos = respuestaWS.getAdviseCodes();
		if (listadoAvisos != null) {
			if (!"".equals(respuestaError)) {
				respuestaError += " - ";
			}
			respuestaError += getAvisos(listadoAvisos);
		}
		return respuestaError;
	}

	private String getAvisos(CtitNotificationAdvise[] listadoAvisos) {
		StringBuffer aviso = new StringBuffer();
		for (int i = 0; i < listadoAvisos.length; i++) {
			aviso.append(listadoAvisos[i].getKey());
			aviso.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoAvisos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			aviso.append(error);
		}
		return aviso.toString();
	}

	private String getImpedimentos(CtitNotificationImpediment[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer();
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoImpedimentos[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString();
	}

	private String getMensajeError(CtitNotificationError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			String error = utiles.quitarCaracteresExtranios(listadoErrores[i].getMessage());
			if (error.length() > 80) {
				String resAux = "";
				for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
					if (tam != Math.floor(error.length() / 80)) {
						resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
					} else {
						resAux += error.substring(80 * tam) + " - ";
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString();
	}

	private void guardarInforme(BigDecimal numExpediente, String informe) {
		if (informe != null && !informe.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(informe, UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
				fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreDocumento(numExpediente.toString());
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar el informe para el tramite: " + numExpediente + ", error: ", e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae informe.");
		}
	}

	private void guardarPTC(BigDecimal numExpediente, String ptc) {
		if (ptc != null && !ptc.isEmpty()) {
			try {
				byte[] ptcBytes = utiles.doBase64Decode(ptc, UTF_8);
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.CTIT);
				fichero.setSubTipo(ConstantesGestorFicheros.PTC);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setNombreDocumento(numExpediente.toString());
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de guardar el PTC para el tramite: " + numExpediente + ", error: ", e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae permiso temporal de circulacion");
		}
	}

	private String crearXml(BigDecimal numExpediente, String xmlEnviar) {
		String peticionXML = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.CTITENVIO, fechaBusqueda, xmlEnviar, null);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				XmlTransTelematicaFactory xmlTransTelematicaFactory = new XmlTransTelematicaFactory();
				peticionXML = xmlTransTelematicaFactory.xmlFileToString(ficheroAenviar.getFile());
			}
		} catch (Throwable e) {
			log.error("Error al recuperar el xml, error: ", e, numExpediente.toString());
		}
		return peticionXML;
	}

	@Override
	public ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExpediente, Boolean.TRUE);
			if (tramiteTrafTranDto != null) {
				Date fechaActual = utilesFecha.getFechaActualDesfaseBBDD();
				tramiteTrafTranDto.setRespuesta(respuesta);
				tramiteTrafTranDto.setFechaUltModif(new Fecha(fechaActual));
				servicioTramiteTraficoTransmision.actualizarTramiteTransmision(tramiteTrafTranDto);
				servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramiteTrafTranDto.getEstado()), estadoNuevo, Boolean.TRUE, NOTIFICACION, idUsuario);
			} else {
				log.error("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente + ", error: " + e.getMessage());
		}
		return resultado;
	}

	@Override
	public ResultadoCtitBean descontarCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), idContrato, new BigDecimal(1), new BigDecimal(1), ConceptoCreditoFacturado.TBT,
					numExpediente.toString());
			if (resultBean.getError()) {
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato.toString());
				log.error("ID_USUARIO: " + idUsuario);
				String mensajes = "";
				for (String mensajeError : resultBean.getListaMensajes()) {
					log.error(mensajeError);
					mensajes += mensajeError;
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(mensajes);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente + ",error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente);
		}
		return resultado;
	}
}