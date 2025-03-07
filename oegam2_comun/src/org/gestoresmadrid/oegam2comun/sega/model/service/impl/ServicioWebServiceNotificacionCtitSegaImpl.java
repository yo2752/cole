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

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceNotificacionCtitSega;
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
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.gestores.sega.ctit.CTITServiceLocator;
import net.gestores.sega.ctit.CTITWS;
import net.gestores.sega.ctit.CTITWSBindingStub;
import net.gestores.sega.ctit.tipos.CtitArgument;
import net.gestores.sega.ctit.tipos.CtitError;
import net.gestores.sega.ctit.tipos.CtitReturn;
import net.gestores.sega.ctit.tipos.CtitStatus;
import net.gestores.sega.ctit.tipos.CtitVehiclePurpose;
import net.gestores.sega.ctit.ws.SoapNotificacionCtitSegaWSHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

@Service
public class ServicioWebServiceNotificacionCtitSegaImpl implements ServicioWebServiceNotificacionCtitSega {

	private static final long serialVersionUID = 4058681248217251367L; 

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceNotificacionCtitSegaImpl.class);
	
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
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoCtitBean tramitarNotificacionCtit(ColaBean solicitud) {
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
				resultado.setMensajeError("No existe el xml de envio para poder realizar la NotificacionCtit del tramite.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la NotificacionCtit, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	private ResultadoCtitBean llamadaWS(String xml, TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		try {
			log.info("Proceso " + ProcesosEnum.NOTIFICACION_CTIT_SEGA.getNombreEnum() + " -- Procesando petición");
			CtitArgument request = completarRequest(xml, tramiteTrafTranDto);
			if(request != null){
				CtitReturn respuesta = getStubNotificacionCtitSega(tramiteTrafTranDto.getNumExpediente()).notificationCTIT(request);
				log.info("------" + ProcesosEnum.NOTIFICACION_CTIT_SEGA.getNombreEnum() + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
				log.info(ProcesosEnum.NOTIFICACION_CTIT_SEGA.getNombreEnum() + " customDossierNumber Request:  " + request.getCustomDossierNumber());
				log.info(ProcesosEnum.NOTIFICACION_CTIT_SEGA.getNombreEnum() + " dossierNumber Response: " + respuesta.getDossierNumber());
				resultadoWS = gestionarRespuesta(respuesta, tramiteTrafTranDto, idUsuario);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("Ha sucedido un error a la hora de completar la request de envio al WS de NOTIFICACION_CTTIT_SEGA.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada al WS de NOTIFICACION_CTTIT_SEGA, error: ",e, tramiteTrafTranDto.getNumExpediente().toString());
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoCtitBean gestionarRespuesta(CtitReturn respuesta, TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario) {
		ResultadoCtitBean resultadoWS = new ResultadoCtitBean(Boolean.FALSE);
		if(respuesta == null){
			resultadoWS.setError(Boolean.TRUE);
			resultadoWS.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de FULL_CTIT_SEGA y está se encuentra vacia"));
		} else if(CtitStatus._ERROR.equals(respuesta.getResult())){
			resultadoWS.setMensajeError(gestionarMensajes(respuesta));
			if(resultadoWS.getMensajeError().contains("generico") || resultadoWS.getMensajeError().contains("timeout") 
					|| resultadoWS.getMensajeError().contains("CTITE") || compruebaErroresRecuperables(respuesta.getErrorCodes())){				
				resultadoWS.setEsRecuperable(Boolean.TRUE);
			} else{
				if(respuesta.getLicense() != null && !respuesta.getLicense().isEmpty()){
					guardarPTC(tramiteTrafTranDto.getNumExpediente(),respuesta.getLicense());
				}
			}
			resultadoWS.setError(Boolean.TRUE);
		} else {
			guardarInforme(tramiteTrafTranDto.getNumExpediente(), respuesta.getReport());
			guardarPTC(tramiteTrafTranDto.getNumExpediente(), respuesta.getLicense());
			if (CtitStatus._OK.equals(respuesta.getResult().getValue())) {
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Finalizado_Telematicamente);
				resultadoWS.setMensajeError("TRÁMITE OK");
			} else if (CtitStatus._TRAMITABLE_CON_INCIDENCIAS.equals(respuesta.getResult().getValue())) {
				log.info("Proceso " + ProcesosEnum.FULL_CTIT_SEGA.getNombreEnum()+ " -- "
						+ "Respuesta recibida con resultado TRAMITABLE CON INCIDENCIAS");
				resultadoWS.setMensajeError(gestionarMensajes(respuesta));
				resultadoWS.setEstadoNuevoProceso(EstadoTramiteTrafico.Tramitable_Incidencias);
			} else if (CtitStatus._NO_TRAMITABLE.equals(respuesta.getResult().getValue())) {
				if(respuesta.getReport() != null && !respuesta.getReport().isEmpty()){
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
	
	private void listarErroresCTIT(CtitError[] listadoErrores){
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(" Error de webservice codigo: " + listadoErrores[i].getKey()); 
			log.error(" Error de webservice descripcion: " +listadoErrores[i].getMessage());
		}
	}
	
	private String gestionarMensajes(CtitReturn respuestaWS) {
		String respuestaError = "";
		CtitError[] listadoErrores = respuestaWS.getErrorCodes();		
		if(listadoErrores!=null) {
			listarErroresCTIT(listadoErrores);
			respuestaError += getMensajeError(listadoErrores);
		}
		
		CtitError[] listadoImpedimentos = respuestaWS.getImpedimentCodes();		
		if(listadoImpedimentos!=null) {
			if (!"".equals(respuestaError)){
				respuestaError += " - ";
			}
			respuestaError += getImpedimentos(listadoImpedimentos);
		}
		
		CtitError[] listadoAvisos = respuestaWS.getAdviseCodes();		
		if(listadoAvisos!=null) {
			if (!"".equals(respuestaError)){
				respuestaError += Claves.BR;
			}
			respuestaError += getAvisos(listadoAvisos);
		}
		return respuestaError;
	}
	
	private String getAvisos(CtitError[] listadoAvisos) {
		StringBuffer aviso = new StringBuffer(); 
		for (int i = 0; i < listadoAvisos.length; i++) {
			if (i>0)
				aviso.append(" - ");
			aviso.append(listadoAvisos[i].getKey());
			aviso.append(" - " );
			String error = listadoAvisos[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
					}
				}
				error = resAux;
			}
			aviso.append(error);
		}
		return aviso.toString(); 
	}

	private String  getImpedimentos(CtitError[] listadoImpedimentos) {
		StringBuffer impedimento = new StringBuffer(); 
		for (int i = 0; i < listadoImpedimentos.length; i++) {
			if (i>0)
				impedimento.append(" - ");
			impedimento.append(listadoImpedimentos[i].getKey());
			impedimento.append(" - " );
			String error = listadoImpedimentos[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
					}
				}
				error = resAux;
			}
			impedimento.append(error);
		}
		return impedimento.toString(); 
	}
	
	private String  getMensajeError(CtitError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			if (i>0)
				mensajeError.append(" - ");
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			String error = listadoErrores[i].getMessage().replaceAll("'", "");
			error = error.replaceAll("\"", ""); 
			if(error.length()>80){
				String resAux = "";
				for(int tam=0; tam<=Math.floor(error.length()/80); tam++){
					if(tam!=Math.floor(error.length()/80)){
						resAux += error.substring(80*tam, 80*tam+80)+Claves.BR;
					} else {
						resAux += error.substring(80*tam)+Claves.BR;
					}
				}
				error = resAux;
			}
			mensajeError.append(error);
		}
		return mensajeError.toString(); 
	}

	private void guardarInforme(BigDecimal numExpediente, String informe) {
		if(informe != null && !informe.isEmpty()){
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
				log.error("Ha sucedido un error a la hora de guardar el informe para el tramite: " + numExpediente + ", error: ",e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae informe.");
		}
	}
	
	private void guardarPTC(BigDecimal numExpediente, String ptc) {
		if(ptc != null && !ptc.isEmpty()){
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
				log.error("Ha sucedido un error a la hora de guardar el PTC para el tramite: " + numExpediente + ", error: ",e, numExpediente.toString());
			}
		} else {
			log.error("La respuesta del tramite " + numExpediente + " no trae permiso temporal de circulacion");
		}
	}
	
	private CTITWS getStubNotificacionCtitSega(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		URL miURL = new URL(gestorPropiedades.valorPropertie(URL_WS));
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT);
		
		CTITWSBindingStub stub = null;
		CTITServiceLocator  notifCtitServiceLocator = new CTITServiceLocator();
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),notifCtitServiceLocator.getCTITServiceWSDDServiceName());
		
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = notifCtitServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getFilesHandler(numExpediente));
		stub = (CTITWSBindingStub) notifCtitServiceLocator.getCTITService(miURL);
		stub.setTimeout(Integer.parseInt(timeOut));
		return stub;
	}
	
	private HandlerInfo getFilesHandler(BigDecimal numExpediente) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapNotificacionCtitSegaWSHandler.PROPERTY_KEY_ID, numExpediente);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapNotificacionCtitSegaWSHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}

	private CtitArgument completarRequest(String xml,TramiteTrafTranDto tramiteTrafTranDto) throws UnsupportedEncodingException {
		CtitArgument request = new CtitArgument();
		request.setAgencyFiscalId(tramiteTrafTranDto.getContrato().getColegiadoDto().getUsuario().getNif());
		request.setCustomDossierNumber(tramiteTrafTranDto.getNumExpediente().toString());
		request.setExternalSystemFiscalId(tramiteTrafTranDto.getContrato().getColegioDto().getCif());
		request.setXmlB64(utiles.doBase64Encode(xml.getBytes(UTF_8)));
		
		if(tramiteTrafTranDto.getVehiculoDto().getTara() != null && !tramiteTrafTranDto.getVehiculoDto().getTara().isEmpty()){
			request.setTara(Integer.parseInt(tramiteTrafTranDto.getVehiculoDto().getTara()));
		}
		if(tramiteTrafTranDto.getVehiculoDto().getPlazas() != null){
			request.setSeatPlaces(tramiteTrafTranDto.getVehiculoDto().getPlazas().intValue());
		}
		if(tramiteTrafTranDto.getVehiculoDto().getPesoMma() != null && !tramiteTrafTranDto.getVehiculoDto().getPesoMma().isEmpty()){
			request.setMma(Integer.parseInt(tramiteTrafTranDto.getVehiculoDto().getPesoMma()));
		}
		if(tramiteTrafTranDto.getVehiculoDto().getServicioTrafico() != null 
			&& tramiteTrafTranDto.getVehiculoDto().getServicioTrafico().getIdServicio() != null
			&& !tramiteTrafTranDto.getVehiculoDto().getServicioTrafico().getIdServicio().isEmpty()){
			request.setCurrentVehiclePurpose(CtitVehiclePurpose.fromString(tramiteTrafTranDto.getVehiculoDto().getServicioTrafico().getIdServicio()));
		}
		if(!TipoTransferencia.tipo2.getValorEnum().equals(tramiteTrafTranDto.getTipoTransferencia())){
			if(tramiteTrafTranDto.getTransmitente() != null && tramiteTrafTranDto.getTransmitente().getDireccion() != null 
				&& tramiteTrafTranDto.getTransmitente().getDireccion().getIdProvincia() != null 
				&& !tramiteTrafTranDto.getTransmitente().getDireccion().getIdProvincia().isEmpty()){
				request.setSellerINECode(tramiteTrafTranDto.getTransmitente().getDireccion().getIdProvincia());
			}
		} else {
			request.setSellerINECode("ND");
		}
		String codigoProvinciaPrimeraMatricula = "ND";
		if (tramiteTrafTranDto.getVehiculoDto() != null && 
			tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula() != null 
			&& new BigDecimal(-1).equals(tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula())) {
			
			if (tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula().intValue() < 10 && tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula().intValue() > 0) {
				codigoProvinciaPrimeraMatricula = "0" + tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula().toString();
			} else {
				codigoProvinciaPrimeraMatricula = tramiteTrafTranDto.getVehiculoDto().getProvinciaPrimeraMatricula().toString();
			}
		}
		request.setFirstMatriculationINECode(codigoProvinciaPrimeraMatricula);
		return request;
	}
	
	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, 
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
	
	@Override
	public ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExpediente, Boolean.TRUE);
			if(tramiteTrafTranDto != null){
				Date fechaActual = utilesFecha.getFechaActualDesfaseBBDD();
				tramiteTrafTranDto.setRespuesta(respuesta);
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
	
	@Override
	public ResultadoCtitBean descontarCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), idContrato, 
					new BigDecimal(1), new BigDecimal(1), ConceptoCreditoFacturado.TBT, numExpediente.toString());
			if(resultBean.getError()){
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato.toString());
				log.error("ID_USUARIO: " + idUsuario);
				String mensajes = "";
				for(String mensajeError : resultBean.getListaMensajes()){
					log.error(mensajeError);
					mensajes += mensajeError;
				}
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError(mensajes);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente + ",error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de descontar los creditos para el tramite: " + numExpediente);
		}
		return resultado;
	}
}
