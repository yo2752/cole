package org.gestoresmadrid.oegam2comun.trafico.webService.model.service.impl;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaBTV.view.dto.ResultadoConsultaBTV;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioWebServiceConsultaEitv;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.webService.model.service.ServicioWebServiceConsultaBtv;
import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gescogroup.blackbox.BTVConsultaLocator;
import com.gescogroup.blackbox.BTVWS;
import com.gescogroup.blackbox.BTVWSBindingStub;
import com.gescogroup.blackbox.BtvCodigoDescripcion;
import com.gescogroup.blackbox.BtvsoapConsultaRespuesta;
import com.gescogroup.blackbox.BtvsoapPeticion;
import com.gescogroup.blackbox.ws.SoapConsultaBtvWSHandler;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceConsultaBtvImpl implements ServicioWebServiceConsultaBtv{

	private static final long serialVersionUID = -1717168143244350466L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConsultaBtvImpl.class);
	
	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;
	
	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	public ResultadoConsultaBTV procesarSolicitudConsultaBTV(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario,BigDecimal idContrato) {
		ResultadoConsultaBTV resultado = null;
		try {
			String xml = recogerXml(xmlEnviar, numExpediente);
			if(xml != null && !xml.isEmpty()){
				TramiteTrafBajaVO tramiteTrafBajaVO = servicioTramiteTraficoBaja.getTramiteBajaVO(numExpediente, true);
				resultado = llamadaWS(tramiteTrafBajaVO,xml);
			}else{
				resultado = new ResultadoConsultaBTV(true, "Ha sucedido un error a la hora de obtener el xml con los datos del vehiculo para enviarlo a la consulta de BTV.");
				resultado.setRespuesta("Ha sucedido un error a la hora de obtener el xml con los datos del vehiculo para enviarlo a la consulta de BTV.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud de CheckBTV, error: ", e, numExpediente.toString());
			resultado = new ResultadoConsultaBTV(true, e);
		}
		return resultado;

	}
	
	public ResultadoConsultaBTV llamadaWS(TramiteTrafBajaVO tramiteTrafBajaVO, String xml) {
		ResultadoConsultaBTV resultado = null;
		try {
			BtvsoapPeticion soapPeticionBtv = generarPeticionSoapConsultatv(xml, tramiteTrafBajaVO);
			if(soapPeticionBtv != null){
				BtvsoapConsultaRespuesta btvConsultaRespuesta = getStubBtv(tramiteTrafBajaVO.getNumExpediente()).consultarBTV(soapPeticionBtv);
				log.info("-------" + ServicioWebServiceConsultaBtv.TEXTO_WS_CONSULTA_BTV + " Recibida Respuesta WS ------" );
				if(btvConsultaRespuesta != null){
					resultado = gestionarResultadoWS(btvConsultaRespuesta);
				}else{
					resultado = new ResultadoConsultaBTV(true, "Ha sucedido un error a la hora realizar la peticion de consulta de BTV.");
					resultado.setRespuesta("Ha sucedido un error a la hora realizar la peticion de consulta de BTV.");
				}
			}else{
				resultado = new ResultadoConsultaBTV(true, "Ha sucedido un error a la hora de generar la peticion de consulta de BTV.");
				resultado.setRespuesta("Ha sucedido un error a la hora realizar la peticion de consulta de BTV.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada al WS de CheckBTV: ", e, tramiteTrafBajaVO.getNumExpediente().toString());
			resultado = new ResultadoConsultaBTV(true, e);
		}
		return resultado;
	}

	private ResultadoConsultaBTV gestionarResultadoWS(BtvsoapConsultaRespuesta btvConsultaRespuesta) {
		ResultadoConsultaBTV resultado = null;
		 if (btvConsultaRespuesta.getResultado() != null){ 
		 	log.info("resultado checkBTV para " + btvConsultaRespuesta.getNum_expediente() + ": " + btvConsultaRespuesta.getResultado());
		 } 
		 if("".equals(btvConsultaRespuesta.getResultado()) || ConstantesProcesos.ERROR.toUpperCase().equals(btvConsultaRespuesta.getResultado())){
				resultado = comprobarErroresRecuperables(btvConsultaRespuesta.getErrores());
		 }else if(ConstantesProcesos.TRAMITABLE.toUpperCase().equals(btvConsultaRespuesta.getResultado().toUpperCase())){
			resultado = new ResultadoConsultaBTV(false);
			resultado.setEstadoNuevo(EstadoTramiteTrafico.Tramitable);
			resultado.setResultadoConsuta(ConstantesProcesos.TRAMITABLE);
			resultado.setRespuesta(ConstantesProcesos.TRAMITABLE);
		} else if(ConstantesProcesos.INFORME_REQUERIDO.toUpperCase().equals(btvConsultaRespuesta.getResultado().toUpperCase())){
			resultado = new ResultadoConsultaBTV(false);
			resultado.setEstadoNuevo(EstadoTramiteTrafico.Tramitable_Incidencias);
			resultado.setResultadoConsuta(ConstantesProcesos.INFORME_REQUERIDO);
			resultado.setRespuesta(ConstantesProcesos.INFORME_REQUERIDO + gestionarMensajeError(btvConsultaRespuesta));
		} else if(ConstantesProcesos.NO_TRAMITABLE_BTV.toUpperCase().equals(btvConsultaRespuesta.getResultado().toUpperCase())){
			resultado = new ResultadoConsultaBTV(false);
			resultado.setEstadoNuevo(EstadoTramiteTrafico.No_Tramitable);
			resultado.setResultadoConsuta(ConstantesProcesos.NO_TRAMITABLE_BTV);
			resultado.setRespuesta(ConstantesProcesos.NO_TRAMITABLE_BTV + gestionarMensajeError(btvConsultaRespuesta));
		} else if(ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO.toUpperCase().equals(btvConsultaRespuesta.getResultado().toUpperCase())){
			resultado = new ResultadoConsultaBTV(false);
				resultado.setEstadoNuevo(EstadoTramiteTrafico.Tramitable_Jefatura);
			resultado.setResultadoConsuta(ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO);
			resultado.setRespuesta(ConstantesProcesos.TRAMITABLE_JEFATURA_TRAFICO + gestionarMensajeError(btvConsultaRespuesta));
		} else {						
			resultado = new ResultadoConsultaBTV(true, new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
			resultado.setRespuesta(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
		}
		return resultado;
	}

	private String gestionarMensajeError(BtvsoapConsultaRespuesta btvConsultaRespuesta) {
		String mensaje = null;
		if( btvConsultaRespuesta.getErrores() != null && btvConsultaRespuesta.getErrores().length > 0){
			for(BtvCodigoDescripcion btvCodigoDescripcion : btvConsultaRespuesta.getErrores()){
				if(mensaje == null){
					mensaje = btvCodigoDescripcion.getDescripcion();
				} else {
					mensaje += ", " + btvCodigoDescripcion.getDescripcion();
				}
				if(mensaje.length() > 300){
					mensaje = mensaje.substring(0,265) + ".....";
				}
			}
		}
		if(mensaje == null){
			mensaje = "";
		}
		return mensaje;
	}

	private BTVWSBindingStub getStubBtv(BigDecimal numExpediente) throws MalformedURLException, ServiceException{
		BTVWSBindingStub stubBtv = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceConsultaBtv.TIMEOUT_PROP_BTV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceConsultaBtv.WEBSERVICE_CONSULTA_BTV));
		BTVWS btvService = null;
		if(miUrl != null){
			BTVConsultaLocator btvConsultaLocator = new BTVConsultaLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),btvConsultaLocator.getBTVConsultaWSDDServiceName());
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = btvConsultaLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapConsultaBtvWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapConsultaBtvWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);
			
			btvService = btvConsultaLocator.getBTVConsulta(miUrl);
			stubBtv = (BTVWSBindingStub) btvService;
			stubBtv.setTimeout(Integer.parseInt(timeOut));
		}
		return stubBtv;
	}
	
	private BtvsoapPeticion generarPeticionSoapConsultatv(String xml, TramiteTrafBajaVO tramiteTrafBajaVO) {
		BtvsoapPeticion soapPeticionBtv = null;
		if(tramiteTrafBajaVO.getContrato().getColegiado() != null  && tramiteTrafBajaVO.getContrato().getColegiado().getUsuario() != null
				&& tramiteTrafBajaVO.getContrato().getColegio() != null){
			soapPeticionBtv = new BtvsoapPeticion();
			soapPeticionBtv.setDoi_gestor(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
			soapPeticionBtv.setDoi_gestoria(tramiteTrafBajaVO.getContrato().getColegiado().getUsuario().getNif());
			soapPeticionBtv.setDoi_plataforma(tramiteTrafBajaVO.getContrato().getColegio().getCif());
			soapPeticionBtv.setNum_expediente_propio(tramiteTrafBajaVO.getNumExpediente().toString());
			try {
				soapPeticionBtv.setXmlB64(utiles.doBase64Encode(xml.getBytes(ServicioWebServiceConsultaBtv.UTF_8)));
			} catch (UnsupportedEncodingException e) {
				log.error("Ha sucedido un error a la hora de pasar a base64 el xml de envio, error: ",e, tramiteTrafBajaVO.getNumExpediente().toString());
				return null;
			}
		}
		return soapPeticionBtv;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CONSULTA_BTV, ConstantesGestorFicheros.XML_ENVIO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String (by, ServicioWebServiceConsultaEitv.UTF_8);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return xml;
	}

	//comprobar el listado de errores y dependiendo del tipo de error marcar como no tramitable o recuperable
	private ResultadoConsultaBTV comprobarErroresRecuperables(BtvCodigoDescripcion[] btvCodigoDescripcion){
		ResultadoConsultaBTV resultado = null;
		String mensaje = null;
		List<String> listaCodErrores = new ArrayList<>();
		// si no es recuperable se pone como no tramitable
		try{
			if(btvCodigoDescripcion != null){
				for(BtvCodigoDescripcion codDescripcion : btvCodigoDescripcion){
					if(codDescripcion.getDescripcion() != null && !codDescripcion.getDescripcion().isEmpty()){
						if(mensaje == null || mensaje.isEmpty()){
							mensaje = codDescripcion.getDescripcion();
						} else {
							mensaje += " - " + codDescripcion.getDescripcion();
						}
					} else {
						MensajesProcesosDto mensajesProcesosDto = servicioMensajesProcesos.getMensaje(codDescripcion.getCodigo());	
						String error = null;
						if(mensajesProcesosDto != null) { 
							error = mensajesProcesosDto.getDescripcion();
						} else {
							error = codDescripcion.getCodigo();
						}
						if(mensaje == null || mensaje.isEmpty()){
							mensaje = error;
						} else {
							mensaje += " - " + error;
						}
					}
					listaCodErrores.add(codDescripcion.getCodigo());
				}
			}
			if(mensaje != null && !mensaje.isEmpty()){
				resultado = new ResultadoConsultaBTV(false);
				resultado.setEstadoNuevo(EstadoTramiteTrafico.No_Tramitable);
				resultado.setResultadoConsuta(ConstantesProcesos.NO_TRAMITABLE_BTV);
				resultado.setRespuesta(ConstantesProcesos.NO_TRAMITABLE_BTV + mensaje);
			} else {
				resultado = new ResultadoConsultaBTV(true, new Exception(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA));
				resultado.setRespuesta(ConstantesProcesos.EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar los errores de la respuesta del WS");
			resultado = new ResultadoConsultaBTV(true,e);
			resultado.setRespuesta(e.toString());
		}
		return resultado;
	}
	
	
	@Override
	public void cambiarEstadoConsultaBtv(ColaDto cola, EstadoTramiteTrafico estadoNuevo, String respuesta) {
		TramiteTrafBajaDto tramiteTrafBajaDto = servicioTramiteTraficoBaja.getTramiteBaja(cola.getIdTramite(), false);
		if(tramiteTrafBajaDto != null){
			servicioTramiteTraficoBaja.cambiarEstadoTramite(cola.getIdTramite(), estadoNuevo, EstadoTramiteTrafico.convertir(tramiteTrafBajaDto.getEstado()), false, cola.getIdUsuario(), respuesta, Boolean.TRUE, Boolean.FALSE);
		}
	}
}
