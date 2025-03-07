package org.gestoresmadrid.oegam2comun.procesos.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.procesos.model.service.ServicioWebServiceCheckCtit;
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

import com.gescogroup.blackbox.CtitCheckError;
import com.gescogroup.blackbox.CtitsoapResponse;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.utiles.XmlCheckCTITFactory;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceCheckCtitImpl implements ServicioWebServiceCheckCtit {

	private static final long serialVersionUID = 2723934378550683658L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceCheckCtitImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

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
				resultado.setMensajeError("No existe el XML de envio para poder realizar el checkCtit del tramite.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tramitar el checkCtit_Sega para el tramite: " + solicitud.getIdTramite() + ", error: ", e);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoCtitBean llamadaWS(String xml, BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		try {
			DatosCTITDto datosCTITDto = servicioTramiteTraficoTransmision.datosCTIT(numExpediente);
			if (datosCTITDto != null) {
				log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Procesando petición para el trámite:" + numExpediente);
				CtitsoapResponse respuestaWS = dgtTransmision.checkCTITTransmision(xml, datosCTITDto);
				log.info("Proceso " + ConstantesProcesos.PROCESO_CHECKCTIT + " -- Peticion Procesada");

				resultadoWS = gestionarRespuesta(respuestaWS);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de CHECK_CTIT.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de CHECK_CTIT, error: ", e, numExpediente.toString());
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoCtitBean gestionarRespuesta(CtitsoapResponse respuesta) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		if (respuesta == null) {
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de CHECK_CTIT y esta se encuentra vacía"));
		} else if (ConstantesProcesos.ERROR.equals(respuesta.getResult())) {
			String mensajeError = "";
			for (CtitCheckError ctitError : respuesta.getErrorCodes()) {
				mensajeError += ctitError.getKey() + " - " + ctitError.getMessage();
			}
			if (mensajeError.contains("generico") || mensajeError.contains("timeout") || mensajeError.contains("CTITE") || compruebaErroresRecuperables(respuesta.getErrorCodes())) {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setEsRecuperable(Boolean.TRUE);
				resultadoWS.setMensajeError(mensajeError);
			} else {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.No_Tramitable);
				resultadoWS.setMensajeError(mensajeError.isEmpty() ? "NO TRAMITABLE" : mensajeError);
			}
		} else {
			if (ConstantesProcesos.TRAMITABLE.equalsIgnoreCase(respuesta.getResult())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "TRAMITABLE"));
			} else if (ConstantesProcesos.INFORME_REQUERIDO.equalsIgnoreCase(respuesta.getResult())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Incidencias);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "EMBARGO O PRECINTO ANOTADO. SE REQUIERE INFORME."));
			} else if (ConstantesProcesos.NO_TRAMITABLE.equalsIgnoreCase(respuesta.getResult())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.No_Tramitable);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "NO TRAMITABLE"));
			} else if (ConstantesProcesos.JEFATURA.equalsIgnoreCase(respuesta.getResult())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Jefatura);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "TRAMITABLE EN JEFATURA"));
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setExcepcion(new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			}
		}
		return resultadoWS;
	}

	private boolean compruebaErroresRecuperables(CtitCheckError[] listadoErrores) {
		boolean recuperable = true;
		for (CtitCheckError error : listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private String rellenarRespuesta(CtitsoapResponse respuesta, String mensaje) {
		String mensajeFinal = getMensajeError(respuesta.getErrorCodes());
		if (mensajeFinal == null || mensajeFinal.isEmpty()) {
			mensajeFinal = mensaje;
		}
		return mensajeFinal;
	}

	private String getMensajeError(CtitCheckError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		if (listadoErrores != null) {
			for (CtitCheckError error : listadoErrores) {
				if (error.getKey() != null && !error.getKey().isEmpty() && error.getMessage() != null && !error.getMessage().isEmpty()) {
					mensajeError.append(error.getKey());
					mensajeError.append(" - ");
					error.setMessage(utiles.quitarCaracteresExtranios(error.getMessage()));
					mensajeError.append(error.getMessage());
					if (listadoErrores.length > 1) {
						mensajeError.append(" - ");
					}
					log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + error.getKey());
					log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION + error.getMessage());
				}
			}
		}
		return mensajeError.toString();
	}

	private String crearXml(BigDecimal numExpediente, String xmlEnviar) {
		String peticionXML = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesProcesos.PROCESO_CHECKCTIT, ConstantesGestorFicheros.CTITENVIO, fechaBusqueda, xmlEnviar, null);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
				peticionXML = xmlCheckCTITFactory.xmlFileToString(ficheroAenviar.getFile());
			}
		} catch (Throwable e) {
			log.error("Error al recuperar el XML, error: ", e, numExpediente.toString());
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
				tramiteTrafTranDto.setResCheckCtit(respuesta);
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
			resultado.setMensajeError("Ha sucedido un error a la hora de actualizar los datos del trámite:" + numExpediente + ", error: " + e.getMessage());
		}
		return resultado;
	}
}