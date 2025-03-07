package org.gestoresmadrid.oegam2comun.sega.model.service.impl;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceBtv;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoBTVBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import net.gestores.sega.btvtramita.BTVTramitaLocator;
import net.gestores.sega.btvtramita.BTVWS;
import net.gestores.sega.btvtramita.BTVWSBindingStub;
import net.gestores.sega.btvtramita.BtvCodigoDescripcion;
import net.gestores.sega.btvtramita.BtvtramitaArgument;
import net.gestores.sega.btvtramita.BtvtramitaReturn;
import net.gestores.sega.btvtramita.ws.SoapTramitarBtvSegaWSHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceBtvImpl implements ServicioWebServiceBtv{

	private static final long serialVersionUID = 8723750037774513875L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceBtvImpl.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	public ResultadoBTVBean procesarSolicitudBTV(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoBTVBean resultado = null;
		try {
			String xml = recogerXml(xmlEnviar,numExpediente);
			if(xml != null && !xml.isEmpty()){
				TramiteTrafBajaVO tramiteTrafBajaVO = servicioTramiteTraficoBaja.getTramiteBajaVO(numExpediente, true);
				if(tramiteTrafBajaVO != null){
					resultado = llamadaWS(tramiteTrafBajaVO,xml);
				} else {
					resultado = new ResultadoBTVBean(true, "No existe ningún tranite con el número de expediente: " + numExpediente);
				}
			} else {
				resultado = new ResultadoBTVBean(true, "No existe xml de envio para esta solicitud.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud de BTV, error: ", e, numExpediente.toString());
			resultado = new ResultadoBTVBean(true, e);
		}
		return resultado;
	}
	
	private ResultadoBTVBean llamadaWS(TramiteTrafBajaVO tramiteTrafVO, String xml) {
		ResultadoBTVBean resultado = null;
		try {
			BtvtramitaArgument btvsoapPeticion = generarPeticionSoapTramitarBtv(xml,tramiteTrafVO);
			if(btvsoapPeticion != null){
				BtvtramitaReturn btvsoapTramitaRespuesta = getStubTramitarBtv(tramiteTrafVO.getNumExpediente()).tramitarBTV(btvsoapPeticion);
				log.info("-------" + TEXTO_WS_TRAMITAR_BTV + " Recibida Respuesta WS ------" );
				if(btvsoapTramitaRespuesta != null){
					resultado = gestionarResultadoWS(btvsoapTramitaRespuesta,tramiteTrafVO.getNumExpediente());
				}else{
					resultado = new ResultadoBTVBean(true, "Ha sucedido un error a la hora realizar la peticion de tramitar de BTV.");
				}
			}else{
				resultado = new ResultadoBTVBean(true, "Ha sucedido un error a la hora realizar la peticion de tramitar de BTV.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de tramitarBTV, error: ",e, tramiteTrafVO.getNumExpediente().toString());
			resultado = new ResultadoBTVBean(true, e);
		}
		return resultado;
	}

	private ResultadoBTVBean gestionarResultadoWS(BtvtramitaReturn btvsoapTramitaRespuesta, BigDecimal numExpediente) {
		ResultadoBTVBean resultado = null;
		String mensaje = "";
		if("OK".equals(btvsoapTramitaRespuesta.getResultado_descripcion())){
			resultado = guardarInformeBajas(btvsoapTramitaRespuesta, numExpediente);
			if(!resultado.isError()){
				if(btvsoapTramitaRespuesta.getAvisos() != null){
					mensaje = resultado.getRespuesta() + "---Avisos:";
					for(BtvCodigoDescripcion avisos : btvsoapTramitaRespuesta.getAvisos()){
						if(mensaje.isEmpty()){
							mensaje = avisos.getCodigo() + ": " + avisos.getDescripcion();
						}else{
							mensaje += ", " + avisos.getCodigo() + ": " + avisos.getDescripcion();
						}
					}
					resultado.setRespuesta(mensaje);
				}
			}
		}else if("NO TRAMITABLE".equals(btvsoapTramitaRespuesta.getResultado_descripcion())){
			if(btvsoapTramitaRespuesta.getImpedimentos() != null){
				mensaje += "---Impedimentos:";
				for(BtvCodigoDescripcion impedimento : btvsoapTramitaRespuesta.getImpedimentos()){
					if(mensaje.isEmpty()){
						mensaje = impedimento.getCodigo() + ": " + impedimento.getDescripcion();
					}else{
						mensaje += ", " + impedimento.getCodigo() + ": " + impedimento.getDescripcion();
					}
				}
			}
			if(btvsoapTramitaRespuesta.getAvisos() != null){
				mensaje += "---Avisos:";
				for(BtvCodigoDescripcion avisos : btvsoapTramitaRespuesta.getAvisos()){
					if(mensaje.isEmpty()){
						mensaje = avisos.getCodigo() + ": " + avisos.getDescripcion();
					}else{
						mensaje += ", " + avisos.getCodigo() + ": " + avisos.getDescripcion();
					}
				}
			}
			resultado = new ResultadoBTVBean(false, mensaje);
			resultado.setNoTramitable(true);
			resultado.setRespuesta(mensaje);
		}else if(ConstantesProcesos.ERROR.equals(btvsoapTramitaRespuesta.getResultado_descripcion())){
			for(BtvCodigoDescripcion error: btvsoapTramitaRespuesta.getErrores()){
				if(mensaje.isEmpty()){
					mensaje = error.getCodigo() + ": " + error.getDescripcion();
				}else{
					mensaje += ", " + error.getCodigo() + ": " + error.getDescripcion();
				}
			}
			resultado = new ResultadoBTVBean(true, mensaje);
			resultado.setRespuesta(mensaje);
		}
		return resultado;
	}

	private ResultadoBTVBean guardarInformeBajas(BtvtramitaReturn btvsoapTramitaRespuesta, BigDecimal numExpediente) {
		ResultadoBTVBean resultado = null;
		String respuesta = null;
		String mensaje = null;
		boolean error = false;
		if(btvsoapTramitaRespuesta.getInforme() != null && !btvsoapTramitaRespuesta.getInforme().isEmpty()){
			byte[] informe = null;
			try {
				informe = utiles.doBase64Decode(btvsoapTramitaRespuesta.getInforme(), ServicioWebServiceBtv.UTF_8);
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de tratar el informe de baja, error: ",e, numExpediente.toString());
				mensaje += ". Ha sucedido un error a la hora de recuperar el informe de baja y no se ha custodiado correctamente.";
				respuesta = "No se ha podido recuperar el informe de baja";
			}
			if(informe != null){
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.TRAMITAR_BTV);
				fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
				fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				fichero.setFicheroByte(informe);
				fichero.setNombreDocumento(TipoImpreso.InformeBajaTelematica.getNombreEnum() + "_" + numExpediente);
				try {
					gestorDocumentos.guardarByte(fichero);
					respuesta = "Baja tramitada telematicamente";
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de guardar el informe de baja, error: ",e, numExpediente.toString());
					mensaje += ". Ha sucedido un error a la hora de guardar el informe de baja y no se ha custodiado correctamente.";
					respuesta = "No se ha podido recuperar el informe de baja";
					error = true;
				}
			}else{
				error = true;
			}
		} else {
			log.error("Para el expediente: " + numExpediente + ", no se ha podido guardar el informe de baja porque no lo hemos recibido de DGT.");
		}
		resultado = new ResultadoBTVBean(error,mensaje);
		resultado.setRespuesta(respuesta);
		return resultado;
	}

	private BTVWSBindingStub getStubTramitarBtv(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		BTVWSBindingStub stubTramitarBtv = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceBtv.TIMEOUT_PROP_TRAMITAR_BTV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceBtv.WEBSERVICE_TRAMITAR_BTV));
		BTVWS tramitarBtvService = null;
		if(miUrl != null){
			BTVTramitaLocator btvTramitarLocator = new BTVTramitaLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),btvTramitarLocator.getBTVTramitaWSDDServiceName());
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = btvTramitarLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapTramitarBtvSegaWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapTramitarBtvSegaWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);
			
			list.add(logHandlerInfo);
			
			tramitarBtvService = btvTramitarLocator.getBTVTramita(miUrl);
			stubTramitarBtv = (BTVWSBindingStub) tramitarBtvService;
			stubTramitarBtv.setTimeout(Integer.parseInt(timeOut));
		}
		return stubTramitarBtv;
	}

	private BtvtramitaArgument generarPeticionSoapTramitarBtv(String xml, TramiteTraficoVO tramiteTrafVO) {
		BtvtramitaArgument btvsoapPeticion = null;
		if(tramiteTrafVO.getContrato().getColegiado() != null && tramiteTrafVO.getContrato().getColegiado().getUsuario() != null 
				&& tramiteTrafVO.getContrato().getColegio() != null){
			btvsoapPeticion = new BtvtramitaArgument();
			btvsoapPeticion.setDoi_gestor(tramiteTrafVO.getContrato().getColegiado().getUsuario().getNif());
			btvsoapPeticion.setDoi_gestoria(tramiteTrafVO.getContrato().getColegiado().getUsuario().getNif());
			btvsoapPeticion.setDoi_plataforma(tramiteTrafVO.getContrato().getColegio().getCif());
			btvsoapPeticion.setNum_expediente_propio(tramiteTrafVO.getNumExpediente().toString());
			btvsoapPeticion.setHistorico(false);
			try {
				btvsoapPeticion.setXmlB64(utiles.doBase64Encode(xml.getBytes(UTF_8)));
			} catch (UnsupportedEncodingException e) {
				log.error("Ha sucedido un error a la hora de pasar a base64 el xml de envio, error: ",e, tramiteTrafVO.getNumExpediente().toString());
				return null;
			}
		}
		return btvsoapPeticion;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TRAMITAR_BTV, ConstantesGestorFicheros.XML_ENVIO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String (by, UTF_8);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return xml;
	}

	@Override
	public void cambiarEstado(ColaBean cola, EstadoTramiteTrafico estadoTramite, String respuesta) {
		TramiteTrafBajaDto tramiteTrafBajaDto = servicioTramiteTraficoBaja.getTramiteBaja(cola.getIdTramite(), false);
		servicioTramiteTraficoBaja.cambiarEstadoTramite(cola.getIdTramite(),estadoTramite, EstadoTramiteTrafico.convertir(tramiteTrafBajaDto.getEstado()),false, cola.getIdUsuario(),respuesta,false,true);
	}
	
	@Override
	public void tratarDevolverCredito(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		servicioTramiteTraficoBaja.tratarDevolverCreditos(numExpediente,idUsuario,idContrato,ConceptoCreditoFacturado.TBT);
		
	}
}
