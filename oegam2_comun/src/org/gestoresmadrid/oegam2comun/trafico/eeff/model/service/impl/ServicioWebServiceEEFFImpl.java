package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioWebServiceEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoWSEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SecurityClientHandlerEEFF;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSServiceLocator;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSSoapBindingStub;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.ws.SoapConsultaEEFFWSHandler;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.ws.SoapLiberarEEFFWSHandler;
import es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO;
import es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO;
import es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceEEFFImpl implements ServicioWebServiceEEFF{

	private static final long serialVersionUID = 5552071641878059162L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceEEFFImpl.class);
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioEEFFNuevo servicioEEFF;
	
	@Autowired
	ServicioColegiado servicioColegiado;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoWSEEFF liberar(ColaBean solicitud) {
		ResultadoWSEEFF resultado = new ResultadoWSEEFF(Boolean.FALSE);
		try {
			StringBuffer xml = recogerXml(solicitud.getXmlEnviar(),ConstantesGestorFicheros.EEFFLIBERACION, solicitud.getIdTramite());
			if(xml != null){
				LiberacionEEFFDto liberacionEEFFDto = servicioEEFF.getLiberacionEEFFDto(solicitud.getIdTramite());
				if (liberacionEEFFDto != null) {
					ColegiadoDto colegiadoDto = servicioColegiado.getColegiadoDto(liberacionEEFFDto.getNumColegiado());
					resultado = llamadaWSLiberacion(xml, liberacionEEFFDto, colegiadoDto);
				} else {
					resultado.setExcepcion(new Exception("No existe datos de liberacion para poder realizar la llamada."));
				}
			} else{
				resultado.setExcepcion(new Exception("No existe fichero de liberacion para poder realizar la llamada."));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la liberacion, error: ", e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	private ResultadoWSEEFF llamadaWSLiberacion(StringBuffer xml, LiberacionEEFFDto liberacionEEFFDto, ColegiadoDto colegiadoDto) {
		ResultadoWSEEFF resultadoWS = new ResultadoWSEEFF(Boolean.FALSE);
		try {
			RespuestaLiberarDTO respuestaLiberarDTO = getStubLiberarEEFF(liberacionEEFFDto.getNumExpediente(), colegiadoDto.getAlias()).liberarEITV("ES_es", xml.toString());
			if(respuestaLiberarDTO != null){
				resultadoWS = gestionarRespuestaLiberacion(respuestaLiberarDTO,liberacionEEFFDto);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("No se puede realizar la liberación porque ha sucedio un error en la conexion.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada al WS de liberacion, error: ",e);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoWSEEFF gestionarRespuestaLiberacion(RespuestaLiberarDTO respuestaLiberarDTO, LiberacionEEFFDto liberacionEEFFDto) {
		ResultadoWSEEFF resultadoWSEEFF = new ResultadoWSEEFF(Boolean.FALSE);
		String sRespuesta = null;
		if(respuestaLiberarDTO.getInfoErrores() != null && respuestaLiberarDTO.getInfoErrores().length > 0){
			for (InfoErrorDTO info : respuestaLiberarDTO.getInfoErrores()) {
				log.info(info.getCodigoError() + ":"+ info.getDescripcionError());
				sRespuesta += info.getCodigoError() + ":" + info.getDescripcionError() + " ";
			}
			resultadoWSEEFF.setError(Boolean.TRUE);
			liberacionEEFFDto.setRespuesta(sRespuesta);
		} else if (respuestaLiberarDTO.getNumRegistroEntrada() != null && !respuestaLiberarDTO.getNumRegistroEntrada().isEmpty()) {
			liberacionEEFFDto.setRealizado(Boolean.TRUE);
			liberacionEEFFDto.setFechaRealizacion(utilesFecha.getFechaHoraActualLEG());
			liberacionEEFFDto.setRespuesta("Estado de la liberacion " + respuestaLiberarDTO.getDatosSimpleEITV().getEstadoBastidor()
					+ " NumReg Entrada " + respuestaLiberarDTO.getNumRegistroEntrada() + ", NumReg Salida " + respuestaLiberarDTO.getNumRegistroSalida());
			sRespuesta = "OK";
		}
		ResultBean resultActLiberacion = servicioEEFF.guardarLiberacionProceso(liberacionEEFFDto);
		if(resultActLiberacion.getError()){
			resultadoWSEEFF.setError(Boolean.TRUE);
			resultadoWSEEFF.setMensajeError(resultActLiberacion.getMensaje());
		} else {
			resultadoWSEEFF.setRespuesta(sRespuesta);
		}
		return resultadoWSEEFF;
	}

	@SuppressWarnings("unchecked")
	private SolicitudOperacionesITVWS getStubLiberarEEFF(BigDecimal numExpediente, String alias) throws MalformedURLException, ServiceException {
		SolicitudOperacionesITVWSSoapBindingStub stubLiberarEEFF  = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_EEFF);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(EEFF_URL));
		SolicitudOperacionesITVWS liberarService = null;
		if(miUrl != null){
			SolicitudOperacionesITVWSServiceLocator liberarEEFFLocator = new SolicitudOperacionesITVWSServiceLocator();		
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),liberarEEFFLocator.getSolicitudOperacionesITVWSWSDDServiceName());
			
			List<HandlerInfo> list = liberarEEFFLocator.getHandlerRegistry().getHandlerChain(portName); 
			list.add(getSignerHandler(alias));
			list.add(getFilesHandlerLiberar(numExpediente));
		
			liberarService = liberarEEFFLocator.getSolicitudOperacionesITVWS(miUrl);
			stubLiberarEEFF = (SolicitudOperacionesITVWSSoapBindingStub) liberarService;
			stubLiberarEEFF.setTimeout(Integer.parseInt(timeOut));
		}
		return liberarService;
	}

	private HandlerInfo getFilesHandlerLiberar(BigDecimal numExpediente) {
		HandlerInfo logHandlerInfo = new HandlerInfo();
		logHandlerInfo.setHandlerClass(SoapLiberarEEFFWSHandler.class);
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapLiberarEEFFWSHandler.PROPERTY_KEY_ID, numExpediente);
		logHandlerInfo.setHandlerConfig(filesConfig);
		return logHandlerInfo;
	}

	private HandlerInfo getSignerHandler(String alias) {
		// Configuración del almacén de claves y certificado a usar
		Map<String, String> securityConfig = new HashMap<String, String>();
		securityConfig.put(SecurityClientHandlerEEFF.ALIAS_KEY, alias);
		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(SecurityClientHandlerEEFF.class);
		signerHandlerInfo.setHandlerConfig(securityConfig);
		return signerHandlerInfo;
	}

	private StringBuffer recogerXml(String xmlEnviar, String subTipo, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		StringBuffer xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EEFF, 
					subTipo, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
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
		return xml;
	}

	@Override
	public void cambiarEstadoLiberacion(BigDecimal numExpediente, BigDecimal idUsuario,EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt, String respuestaError) {
		servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, estadoNuevo, estadoAnt, false, respuestaError, idUsuario);
	}
	
	@Override
	public void cambiarEstadoConsulta(BigDecimal idTramite, BigDecimal idUsuario, EstadoConsultaEEFF estadoNuevo, EstadoConsultaEEFF estadoAnt, String respuesta) {
		servicioEEFF.cambiarEstadoConsultaEEFF(idTramite, estadoNuevo, idUsuario, Boolean.FALSE);
	}
	
	@Override
	public ResultadoWSEEFF consultar(ColaBean solicitud) {
		ResultadoWSEEFF resultado = new ResultadoWSEEFF(Boolean.FALSE);
		try {
			StringBuffer xml = recogerXml(solicitud.getXmlEnviar(),ConstantesGestorFicheros.EEFFCONSULTA, solicitud.getIdTramite());
			if(xml != null){
				ConsultaEEFFDto consultaEEFFDto = servicioEEFF.getConsultaEEFFDto(solicitud.getIdTramite(), Boolean.FALSE);
				if(consultaEEFFDto != null){
					ColegiadoDto colegiadoDto = servicioColegiado.getColegiadoDto(consultaEEFFDto.getNumColegiado());
					resultado = llamadaWSConsulta(xml, consultaEEFFDto,colegiadoDto);
				} else {
					resultado.setExcepcion(new Exception("No existe datos de la consulta de liberacion para poder realizar la llamada."));
				}
			} else{
				resultado.setExcepcion(new Exception("No existe fichero de consulta de liberacion para poder realizar la llamada."));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la consulta de liberacion, error: ", e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ResultadoWSEEFF llamadaWSConsulta(StringBuffer xml, ConsultaEEFFDto consultaEEFFDto, ColegiadoDto colegiadoDto) {
		ResultadoWSEEFF resultadoWS = new ResultadoWSEEFF(Boolean.FALSE);
		try {
			ConsultaEITVRespuestaDTO respuestaConsultaDTO = getStubConsultaEEFF(consultaEEFFDto.getNumExpediente(),colegiadoDto.getAlias()).consultarEITV("ES_es", xml.toString());
			if(respuestaConsultaDTO != null){
				resultadoWS = gestionarRespuestaConsulta(respuestaConsultaDTO,consultaEEFFDto);
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("No se puede realizar la consulta de liberacion porque ha sucedio un error en la conexion.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada al WS de consulta, error: ",e);
			resultadoWS.setExcepcion(e);
		}
		return resultadoWS;
	}

	private ResultadoWSEEFF gestionarRespuestaConsulta(ConsultaEITVRespuestaDTO respuestaConsultaDTO, ConsultaEEFFDto consultaEEFFDto) {
		ResultadoWSEEFF resultadoWSEEFF = new ResultadoWSEEFF(Boolean.FALSE);
		String sRespuesta = null;
		if(respuestaConsultaDTO.getInfoErrores() != null && respuestaConsultaDTO.getInfoErrores().length > 0){
			for (InfoErrorDTO info : respuestaConsultaDTO.getInfoErrores()) {
				log.info(info.getCodigoError() + ":"+ info.getDescripcionError());
				sRespuesta += info.getCodigoError() + ":" + info.getDescripcionError() + " ";
			}
			resultadoWSEEFF.setError(Boolean.TRUE);
			consultaEEFFDto.setRespuesta(sRespuesta);
		} else if (ESTADO_BASTIDOR_LIBERADO.equals(respuestaConsultaDTO.getDatossimpleeitv().getEstadoBastidor())) {
			consultaEEFFDto.setRealizado(Boolean.TRUE);
			consultaEEFFDto.setRespuesta(respuestaConsultaDTO.getDatossimpleeitv().getEstadoBastidor());
		}
		ResultBean resultActConsulta = servicioEEFF.guardarConsultaEEFFProceso(consultaEEFFDto);
		if(resultActConsulta.getError()){
			resultadoWSEEFF.setError(Boolean.TRUE);
			resultadoWSEEFF.setMensajeError(resultActConsulta.getMensaje());
		} else {
			resultadoWSEEFF.setRespuesta("OK");
		}
		return resultadoWSEEFF;
	}

	private SolicitudOperacionesITVWSSoapBindingStub getStubConsultaEEFF(BigDecimal numExpediente, String alias) throws ServiceException, MalformedURLException {
		SolicitudOperacionesITVWSSoapBindingStub stubConsultaEEFF  = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_EEFF);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(EEFF_URL));
		SolicitudOperacionesITVWS consultaService = null;
		if(miUrl != null){
			SolicitudOperacionesITVWSServiceLocator consultaEEFFLocator = new SolicitudOperacionesITVWSServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),consultaEEFFLocator.getSolicitudOperacionesITVWSWSDDServiceName());
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = consultaEEFFLocator.getHandlerRegistry().getHandlerChain(portName); 
			list.add(getSignerHandler(alias));
			list.add(getFilesHandlerConsulta(numExpediente));
			
			consultaService = consultaEEFFLocator.getSolicitudOperacionesITVWS(miUrl);
			stubConsultaEEFF = (SolicitudOperacionesITVWSSoapBindingStub) consultaService;
			stubConsultaEEFF.setTimeout(Integer.parseInt(timeOut));
		}
		return stubConsultaEEFF;
	}

	private HandlerInfo getFilesHandlerConsulta(BigDecimal numExpediente) {
		HandlerInfo logHandlerInfo = new HandlerInfo();
		logHandlerInfo.setHandlerClass(SoapLiberarEEFFWSHandler.class);
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapConsultaEEFFWSHandler.PROPERTY_KEY_ID, numExpediente);
		logHandlerInfo.setHandlerConfig(filesConfig);
		return logHandlerInfo;
	}

}
