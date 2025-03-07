package org.gestoresmadrid.oegam2comun.sega.model.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceCheckCtitSega;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import net.gestores.sega.checkctit.CTITConsultaServiceLocator;
import net.gestores.sega.checkctit.CTITConsultaWS;
import net.gestores.sega.checkctit.CTITConsultaWSBindingStub;
import net.gestores.sega.checkctit.ws.SoapCheckCtitSegaWSHandler;
import net.gestores.sega.ctit.tipos.CheckArgument;
import net.gestores.sega.ctit.tipos.CheckReturn;
import net.gestores.sega.ctit.tipos.CheckStatus;
import net.gestores.sega.ctit.tipos.CtitError;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceCheckCtitSegaImpl implements ServicioWebServiceCheckCtitSega{

	private static final long serialVersionUID = 5938455614961726471L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceCheckCtitSegaImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;
	
	@Autowired
	ServicioMensajesProcesos servicioMensajesProcesos;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoCtitBean tramitarCheckCtit(ColaBean solicitud) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			String xml = recogerXml(solicitud.getXmlEnviar(), solicitud.getIdTramite());
			if(xml != null && !xml.isEmpty()){
				TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(solicitud.getIdTramite(), Boolean.TRUE);
				if(tramiteTrafTranDto != null){
					resultado = llamadaWS(xml, tramiteTrafTranDto, solicitud.getIdUsuario());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No existe datos para el expediente: " + solicitud.getIdTramite());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existe el xml de envio para poder realizar el checkCtit del tramite.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tramitar el checkCtit_Sega para el tramite: " + solicitud.getIdTramite() + ", error: ",e);
			resultado.setExcepcion(new Exception(e.getMessage()));
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}
	
	private ResultadoCtitBean llamadaWS(String xml, TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		try {
			log.info(TEXTO_CHECK_LOG + " ENTRA EN BLACK BOX");
			CheckArgument request = completarRequest(xml, tramiteTrafTranDto);
			if(request != null){
				CheckReturn respuesta = getStubCheckCtitSega(tramiteTrafTranDto.getNumExpediente()).checkCTIT(request);
				log.info("------" + TEXTO_CHECK_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
				log.info(TEXTO_CHECK_LOG + " customDossierNumber Request:  " + request.getCustomDossierNumber());
				log.info(TEXTO_CHECK_LOG + " dossierNumber Response: " + respuesta.getDossierNumber());
				resultadoWS = gestionarRespuesta(respuesta, tramiteTrafTranDto,idUsuario);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de CHECK_CTIT_SEGA.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de CHECK_CTTIT_SEGA, error: ",e, tramiteTrafTranDto.getNumExpediente().toString());
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoCtitBean gestionarRespuesta(CheckReturn respuesta, TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		if(respuesta == null){
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de CHECK_CTIT_SEGA y está se encuentra vacia"));
		} else if(CheckStatus._ERROR.equals(respuesta.getResult().getValue())){
			String mensajeError = "";
			for(CtitError ctitError : respuesta.getErrorCodes()){
				mensajeError += ctitError.getKey() + " - " + ctitError.getMessage();
			}
			if(mensajeError.contains("generico") || mensajeError.contains("timeout") || mensajeError.contains("CTITE") || compruebaErroresRecuperables(respuesta.getErrorCodes())){
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setEsRecuperable(Boolean.TRUE);
			} else {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.No_Tramitable);
				resultadoWS.setMensajeError(mensajeError.isEmpty() ? "NO TRAMITABLE" : mensajeError);
			}
		} else {
			if (CheckStatus._TRAMITABLE.equalsIgnoreCase(respuesta.getResult().getValue())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "TRAMITABLE"));
			} else if (CheckStatus._INFORME_REQUERIDO.equalsIgnoreCase(respuesta.getResult().getValue())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Incidencias);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "EMBARGO O PRECINTO ANOTADO. SE REQUIERE INFORME."));
			} else if (CheckStatus._NO_TRAMITABLE.equalsIgnoreCase(respuesta.getResult().getValue())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.No_Tramitable);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "NO TRAMITABLE"));
			} else if (CheckStatus._JEFATURA.equalsIgnoreCase(respuesta.getResult().getValue())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Jefatura);
				resultadoWS.setMensajeError(rellenarRespuesta(respuesta, "TRAMITABLE EN JEFATURA"));
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setExcepcion(new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			}
		}
		return resultadoWS;
	}
	
	private String rellenarRespuesta(CheckReturn respuesta, String mensaje) {
		String mensajeFinal = getMensajeError(respuesta.getErrorCodes());
		if(mensajeFinal == null || mensajeFinal.isEmpty()){
			mensajeFinal = mensaje;
		}
		return mensajeFinal;
	}

	private Boolean compruebaErroresRecuperables(CtitError[] errorCodes) {
		Boolean recuperable = true;
		for (CtitError error : errorCodes) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getKey(), error.getMessage());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}
	
	private String getMensajeError(CtitError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		if(listadoErrores != null){
			for (CtitError error : listadoErrores) {
				if(error.getKey() != null && !error.getKey().isEmpty() && 
						error.getMessage() != null && !error.getMessage().isEmpty()){
					mensajeError.append(error.getKey());
					mensajeError.append(" - ");
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

	private CheckArgument completarRequest(String xml, TramiteTrafTranDto tramiteTrafTranDto) throws UnsupportedEncodingException {
		CheckArgument request = new CheckArgument();
		request.setAgencyFiscalId(tramiteTrafTranDto.getContrato().getColegiadoDto().getUsuario().getNif());
		request.setCustomDossierNumber(tramiteTrafTranDto.getNumExpediente().toString());
		request.setExternalSystemFiscalId(tramiteTrafTranDto.getContrato().getColegioDto().getCif());
		request.setXmlB64(utiles.doBase64Encode(xml.getBytes(UTF_8)));
		return request;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CHECKCTIT, 
					ConstantesGestorFicheros.CTITENVIO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml  = new StringBuffer("<![CDATA[");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroAenviar.getFile()), "UTF-8"));
				int value = 0;
				// reads to the end of the stream
				while ((value = br.read()) != -1) {
					// converts int to character
					xml.append((char) value);
				}
				br.close();
				xml.append("]]>");			
			}
		} catch (Throwable e) {
			log.error("Error al recuperar el xml, error: ", e, numExpediente.toString());
		}
		return xml.toString();
	}
	
	private CTITConsultaWS getStubCheckCtitSega(BigDecimal numExpediente) throws ServiceException, MalformedURLException{
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		
		CTITConsultaWSBindingStub stub = null;
		CTITConsultaServiceLocator ctitConsultaServiceLocator = new CTITConsultaServiceLocator();
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),ctitConsultaServiceLocator.getCTITConsultaServiceWSDDServiceName());
		
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = ctitConsultaServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getFilesHandler(numExpediente));
		stub = (CTITConsultaWSBindingStub) ctitConsultaServiceLocator.getCTITConsultaService(miURL);
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}
	
	private HandlerInfo getFilesHandler(BigDecimal numExpediente) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapCheckCtitSegaWSHandler.PROPERTY_KEY_ID, numExpediente);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapCheckCtitSegaWSHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}
	
	@Override
	public ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExpediente, Boolean.TRUE);
			if(tramiteTrafTranDto != null){
				Date fechaActual = utilesFecha.getFechaActualDesfaseBBDD();
				tramiteTrafTranDto.setResCheckCtit(respuesta);
				tramiteTrafTranDto.setFechaUltModif(new Fecha(fechaActual));
				servicioTramiteTraficoTransmision.actualizarTramiteTransmision(tramiteTrafTranDto);
				servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.convertir(tramiteTrafTranDto.getEstado()), estadoNuevo, 
						Boolean.TRUE, NOTIFICACION, idUsuario);
			} else {
				log.error("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No existen datos en la BBDD para el tramite de transmision: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente+ ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de actualizar los datos del tramite:" + numExpediente+ ", error: "+e.getMessage());
		}
		return resultado;
	}
}
