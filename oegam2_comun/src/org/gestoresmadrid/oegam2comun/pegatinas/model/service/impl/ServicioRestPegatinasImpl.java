package org.gestoresmadrid.oegam2comun.pegatinas.model.service.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaBajaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionImpresas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionInvalidos;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaRecepcionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.HandlerErrorRest;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioRestPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.JefaturasPegatinas;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioRestPegatinasImpl implements ServicioRestPegatinas {

	private static final long serialVersionUID = -2451647137417959589L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRestPegatinasImpl.class);
	
	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public RespuestaPeticionStock pedirStockPegatinas(String tipoSolicitud, String datosSolicitud){
		
		RespuestaPeticionStock response=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setErrorHandler(new HandlerErrorRest());
			HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantesPegatinas.ACCEPT, ConstantesPegatinas.APPLICATION_XML);
			headers.setContentType(MediaType.APPLICATION_XML);
			HttpEntity<String> request = new HttpEntity<String>(montarPeticion(tipoSolicitud, datosSolicitud), headers);
			log.info("Peticion enviada: " + tipoSolicitud + datosSolicitud);
			response = restTemplate.postForObject(ConstantesPegatinas.URL_PETICION_STOCK, request,RespuestaPeticionStock.class);
			
			JAXBContext context = JAXBContext.newInstance(RespuestaPeticionStock.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			String xmlString = sw.toString();
			byte[] b = xmlString.getBytes(StandardCharsets.UTF_8);
			guardarXml(b, "Response_pedirStock");
			
		}catch (Exception e){
			log.error(ServicioRestPegatinas.PEGATINAS_ERROR_LOG + e);
			response = new RespuestaPeticionStock();
			response.setException(e);
		}
		
		return response;
	}

	@Override
	public RespuestaBajaPeticionStock solicitarBajaStock(String tipoSolicitud, String datosSolicitud) {
		
		RespuestaBajaPeticionStock response=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setErrorHandler(new HandlerErrorRest());
			HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantesPegatinas.ACCEPT, ConstantesPegatinas.APPLICATION_XML);
			headers.setContentType(MediaType.APPLICATION_XML);
			HttpEntity<String> request = new HttpEntity<String>(montarPeticion(tipoSolicitud, datosSolicitud), headers);
			log.info("Peticion enviada: " + tipoSolicitud + datosSolicitud);
			response = restTemplate.postForObject(ConstantesPegatinas.URL_BAJA_PETICION_STOCK, request,	RespuestaBajaPeticionStock.class);
			
			JAXBContext context = JAXBContext.newInstance(RespuestaBajaPeticionStock.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			String xmlString = sw.toString();
			byte[] b = xmlString.getBytes(StandardCharsets.UTF_8);
			guardarXml(b, "Response_bajaStock");
			
		}catch (Exception e){
			log.error(ServicioRestPegatinas.PEGATINAS_ERROR_LOG + e);
			response = new RespuestaBajaPeticionStock();
			response.setException(e);
		}

		return response;
	}

	@Override
	public RespuestaRecepcionStock confirmarRecepcionStock(String tipoSolicitud, String datosSolicitud) {
		
		RespuestaRecepcionStock response=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setErrorHandler(new HandlerErrorRest());
			HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantesPegatinas.ACCEPT, ConstantesPegatinas.APPLICATION_XML);
			headers.setContentType(MediaType.APPLICATION_XML);
			HttpEntity<String> request = new HttpEntity<String>(montarPeticion(tipoSolicitud, datosSolicitud), headers);
			log.info("Peticion enviada: " + tipoSolicitud + datosSolicitud);
			response = restTemplate.postForObject(ConstantesPegatinas.URL_RECEPCION_STOCK, request,	RespuestaRecepcionStock.class);
			
			JAXBContext context = JAXBContext.newInstance(RespuestaRecepcionStock.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			String xmlString = sw.toString();
			byte[] b = xmlString.getBytes(StandardCharsets.UTF_8);
			guardarXml(b, "Response_recepcionStock");
			
		}catch (Exception e){
			log.error(ServicioRestPegatinas.PEGATINAS_ERROR_LOG + e);
			response = new RespuestaRecepcionStock();
			response.setException(e);
		}

		return response;
	}

	
	@Override
	public RespuestaNotificacionImpresas notificarImpresion(String tipoSolicitud, List<PegatinasNotificaVO> lista, String jefatura) {
		
		RespuestaNotificacionImpresas response = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setErrorHandler(new HandlerErrorRest());
			HttpHeaders headers = new HttpHeaders();
			headers.set(ConstantesPegatinas.ACCEPT, ConstantesPegatinas.APPLICATION_XML);
			headers.setContentType(MediaType.APPLICATION_XML);
			HttpEntity<String> request = new HttpEntity<String>(montarPeticionNotificacion(tipoSolicitud, lista, jefatura), headers);
			log.info("Peticion enviada: " + tipoSolicitud );
			response=restTemplate.postForObject(ConstantesPegatinas.URL_NOTIFICAR_IMPRESAS, request, RespuestaNotificacionImpresas.class);
			
			JAXBContext context = JAXBContext.newInstance(RespuestaNotificacionImpresas.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			String xmlString = sw.toString();
			byte[] b = xmlString.getBytes(StandardCharsets.UTF_8);
			guardarXml(b, "Response_notificarImpresion");
			
		}catch (Exception e){
			log.error(ServicioRestPegatinas.PEGATINAS_ERROR_LOG + e);
			response = new RespuestaNotificacionImpresas();
			response.setException(e);
		}
		
		return response;
	}

	@Override
	public RespuestaNotificacionInvalidos notificarInvalidos(String tipoSolicitud,  List<PegatinasNotificaVO> lista, String jefatura) {
		
		RespuestaNotificacionInvalidos response = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			restTemplate.setErrorHandler(new HandlerErrorRest());
			headers.set(ConstantesPegatinas.ACCEPT, ConstantesPegatinas.APPLICATION_XML);
			headers.setContentType(MediaType.APPLICATION_XML);
			HttpEntity<String> request = new HttpEntity<String>(montarPeticionNotificacion(tipoSolicitud, lista, jefatura), headers);
			log.info("Peticion enviada: " + tipoSolicitud );
			response = restTemplate.postForObject(ConstantesPegatinas.URL_NOTIFICAR_INVALIDOS, request, RespuestaNotificacionInvalidos.class);
			
			JAXBContext context = JAXBContext.newInstance(RespuestaNotificacionInvalidos.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(response, sw);
			String xmlString = sw.toString();
			byte[] b = xmlString.getBytes(StandardCharsets.UTF_8);
			guardarXml(b, "Response_notificarInvalidos");
			
		}catch (Exception e){
			log.error(ServicioRestPegatinas.PEGATINAS_ERROR_LOG + e);
			response = new RespuestaNotificacionInvalidos();
			response.setException(e);
		}
		
		return response;
	}
	

	private String montarPeticion(String tipoSolicitud, String datosSolicitud) {
		String peticion = "";
		if ("pedirStock".equals(tipoSolicitud)) {
			String[] campos = datosSolicitud.split(",");
			JefaturasPegatinas jefaturaPeg = JefaturasPegatinas.valueOf(campos[2]);
			peticion = "<peticion_stock><cantidad>" + campos[0] + "</cantidad><tipo>" + campos[1] + "</tipo><sede><jefatura>"
					+ jefaturaPeg.getJefatura() + "</jefatura><sucursal>" + jefaturaPeg.getSucursal() + "</sucursal></sede></peticion_stock>";
			guardarXml(peticion.getBytes(StandardCharsets.UTF_8), "Request_pedirStock");
		}else if ("bajaStock".equals(tipoSolicitud)) {
			String[] campos = datosSolicitud.split(",");
			JefaturasPegatinas jefaturaPeg = JefaturasPegatinas.valueOf(campos[1]);
			peticion = "<anular_peticion_stock><identificador_peticion>" + campos[0] + "</identificador_peticion><sede><jefatura>"
					+ jefaturaPeg.getJefatura() + "</jefatura><sucursal>" + jefaturaPeg.getSucursal() + "</sucursal></sede></anular_peticion_stock>";
			guardarXml(peticion.getBytes(StandardCharsets.UTF_8), "Request_bajaStock");
		}else if ("recepcionStock".equals(tipoSolicitud)){
			String[] campos = datosSolicitud.split(",");
			JefaturasPegatinas jefaturaPeg = JefaturasPegatinas.valueOf(campos[1]);
			peticion = "<recepcion_stock><identificador_peticion>" + campos[0] + "</identificador_peticion><sede><jefatura>"
					+ jefaturaPeg.getJefatura() + "</jefatura><sucursal>" + jefaturaPeg.getSucursal() + "</sucursal></sede></recepcion_stock>";
			guardarXml(peticion.getBytes(StandardCharsets.UTF_8), "Request_recepcionStock");
		}

		return peticion;
	}
	
	private String montarPeticionNotificacion(String tipoSolicitud, List<PegatinasNotificaVO> matriculas, String jefatura){
		String peticion = null;
		int cantidad = matriculas.size();
		JefaturasPegatinas jefaturaPeg = JefaturasPegatinas.valueOf(jefatura);
		if("notificarImpresion".equals(tipoSolicitud)){
			peticion = "<notificacion_impresas><cantidad>" + cantidad + "</cantidad><sede><jefatura>" + jefaturaPeg.getJefatura() + "</jefatura><sucursal>"
					+ jefaturaPeg.getSucursal() + "</sucursal></sede>" +  "<matriculas>" + montarMatriculas(matriculas) + "</matriculas></notificacion_impresas>";
			guardarXml(peticion.getBytes(StandardCharsets.UTF_8), "Request_notificarImpresion");
		}else if ("notificarInvalidos".equals(tipoSolicitud)){
			peticion = "<notificacion_invalidos><cantidad>" + cantidad + "</cantidad><sede><jefatura>" + jefaturaPeg.getJefatura() + "</jefatura><sucursal>"
					+ jefaturaPeg.getSucursal() + "</sucursal></sede><distintivos>" + montarDistintivo(matriculas) + "</distintivos></notificacion_invalidos>";
			guardarXml(peticion.getBytes(StandardCharsets.UTF_8), "Request_notificarInvalidos");
		}
		
		return peticion;
	}
	
	
	
	private String montarDistintivo(List<PegatinasNotificaVO> distintivo) {
		String distintivos =null;
		for (int i = 0; i < distintivo.size(); i++) {
			if(null==distintivos){
				distintivos = "<distintivo><identificador>" + distintivo.get(i).getIdentificador()+"</identificador><motivo>"
						  +distintivo.get(i).getMotivo()+"</motivo></distintivo>";
			}else{
				distintivos = distintivos + "<distintivo><identificador>" + distintivo.get(i).getIdentificador()+"</identificador><motivo>"
						  +distintivo.get(i).getMotivo()+"</motivo></distintivo>";
			}
			
			
		}
		return distintivos;
	}

	private String montarMatriculas(List<PegatinasNotificaVO> matriculas){
		
		String matriculas2 = null;
		
		for (int i = 0; i < matriculas.size(); i++) {
			
			if (matriculas2==null){
				matriculas2 = "<matricula>" + matriculas.get(i).getMatricula().trim() +"</matricula>";					
			}else{
				matriculas2 = matriculas2 + "<matricula>" + matriculas.get(i).getMatricula().trim() +"</matricula>";
			}

			
		}
		return matriculas2;
	}
	
	private void guardarXml(byte[] b, String tipo) {
		try{
			
			FicheroBean fichero = new FicheroBean();
			
			fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
			fichero.setSubTipo(ConstantesGestorFicheros.WS_PEGATINA);
			fichero.setSobreescribir(false);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setNombreDocumento(tipo+"_"+utilesFecha.getFechaHoy().replace("/", ""));
			
			fichero.setFicheroByte(b);
			gestorDocumentos.guardarByte(fichero);
		
		} catch (OegamExcepcion e) {
			log.error(e);
		}
		
	}

}
