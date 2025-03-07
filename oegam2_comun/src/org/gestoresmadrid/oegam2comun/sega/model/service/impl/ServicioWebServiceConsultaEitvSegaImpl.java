package org.gestoresmadrid.oegam2comun.sega.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ConsultaTarjeta;
import org.gestoresmadrid.oegam2comun.sega.eitv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceConsultaEitvSega;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoConsultaEitvBean;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.gestores.sega.eitv.EITVQueryServiceLocator;
import net.gestores.sega.eitv.EITVQueryWS;
import net.gestores.sega.eitv.EITVQueryWSBindingStub;
import net.gestores.sega.eitv.EitvArgument;
import net.gestores.sega.eitv.EitvError;
import net.gestores.sega.eitv.EitvReturn;
import net.gestores.sega.eitv.ws.SoapEITVPreMatriculadosSegaWSHandler;
import net.gestores.sega.eitv.ws.SoapEITVSegaWSHandler;
import trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos.Vehiculo;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioWebServiceConsultaEitvSegaImpl implements ServicioWebServiceConsultaEitvSega{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConsultaEitvSegaImpl.class);
	
	private static final long serialVersionUID = 5938455614961726471L;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	ServicioVehiculo servicioVehiculo;
	
	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	private ObjectFactory objectFactory;

	private UtilesViafirma utilesViafirma;
	
	@Override
	public ResultadoConsultaEitvBean generarConsultaEitv(ColaBean solicitud) {
		ResultadoConsultaEitvBean respuesta = null;
		try {
			if(solicitud.getXmlEnviar().contains(ServicioConsultaEitv.CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR)){
				respuesta = generarConsultaEitvPreMatriculados(solicitud);
			}else{
				String xml = recogerXmlEitv(solicitud.getXmlEnviar());
				if(xml != null){	
					ConsultaTarjeta consultaTarjetaEitv = (ConsultaTarjeta)getBeanToXml(xml, ServicioWebServiceConsultaEitvSega.NAME_CONTEXT);
					if(consultaTarjetaEitv != null){
						TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(solicitud.getIdTramite(), true);
						respuesta = llamadaWS(consultaTarjetaEitv, xml, tramiteTraficoVO, solicitud.getIdUsuario(), false);
					}else{
						respuesta = new ResultadoConsultaEitvBean();
						respuesta.setException(new Exception("Ha sucedido un error a la hora de crear el xml con los datos firmados"));
					}
				}else{
					respuesta = new ResultadoConsultaEitvBean(true,"No existen el xml de consulta de la tarjeta Eitv, con nombre: " + solicitud.getXmlEnviar());
					respuesta.setException(new Exception("No existen el xml de consulta de la tarjeta Eitv"));
				}
			}
		} catch (JAXBException e) {
			log.error(e);
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(new Exception(e));
		} catch (FileNotFoundException e) {
			log.error(e);
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(new Exception(e));
		}
		return respuesta;
	}
	
	private ResultadoConsultaEitvBean generarConsultaEitvPreMatriculados(ColaBean colaBean) throws FileNotFoundException, JAXBException {
		ResultadoConsultaEitvBean respuesta = null;
		String xml = recogerXmlEitvPreMatriculados(colaBean.getXmlEnviar());
		if(xml != null){
			ConsultaTarjeta consultaTarjetaEitv;
			consultaTarjetaEitv = (ConsultaTarjeta)getBeanToXml(xml, ServicioWebServiceConsultaEitvSega.NAME_CONTEXT);
			if(consultaTarjetaEitv != null){
				VehiculoPrematriculadoVO vehiculoPreVO = servicioVehiculosPrematriculados.buscarVehiculoPrematriculado(colaBean.getIdTramite().longValue());
				respuesta = llamadaWSPreMatriculados(consultaTarjetaEitv, xml, vehiculoPreVO, colaBean.getIdUsuario(), colaBean.getIdContrato());
			}
		}else{
			respuesta = new ResultadoConsultaEitvBean(true,"No existen el xml de consulta de la tarjeta Eitv, con nombre: " + colaBean.getXmlEnviar());
			respuesta.setException(new Exception("No existen el xml de consulta de la tarjeta Eitv"));
		}
		return respuesta;
	}
	
	@Override
	public ResultBean firmarConsultaTarjetaEitvSega(ConsultaTarjeta consultaTarjetaEitvSega, TramiteTraficoVO tramiteTraficoVO) {
		ResultBean resultado = new ResultBean(false);
		String xmlFirmado = null;
		try {
			String aliasColegiado = tramiteTraficoVO.getContrato().getColegiado().getAlias();
			String xmlConsultaTarjeta = getXmlConsultaTarjetaSega(consultaTarjetaEitvSega);
			String xml =  generarXmlDatosFirmados(xmlConsultaTarjeta);
			String idFirma= getUtilesViafirma().firmarPruebaCertificadoCaducidad(xml,aliasColegiado);
			if(idFirma != null){
				idFirma = getUtilesViafirma().firmarConsultaTarjetaEitv(xml.getBytes(),aliasColegiado);
				if(idFirma != null){
					byte[] txtFirmado = getUtilesViafirma().getBytesDocumentoFirmado(idFirma);
					String firmaB64 = new String(txtFirmado);
					consultaTarjetaEitvSega.setFirmaGestor(firmaB64.getBytes(ServicioWebServiceConsultaEitvSega.UTF_8));
					xmlFirmado = getXmlConsultaTarjetaSega(consultaTarjetaEitvSega);
					resultado.addAttachment("xmlFirmado", xmlFirmado);
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("Ha sucedido un error a la hora de firmar el xml.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("El certificado del colegiado está caducado.");
			}
		} catch (JAXBException e) {
			log.error("Error a la hora de crear el xml de los datos firmados, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Error a la hora de crear el xml de los datos firmados.");
		} catch (UnsupportedEncodingException e) {
			log.error("Error a la hora de crear el xml de los datos firmados, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Error a la hora de crear el xml de los datos firmados.");
		}
		return resultado;
	}
	
	@Override
	public String getXmlConsultaTarjetaSega(ConsultaTarjeta consultaTarjetaEitvSega) throws JAXBException, UnsupportedEncodingException {
		StringWriter writer = new StringWriter();
		Marshaller m = getContext(ServicioWebServiceConsultaEitvSega.NAME_CONTEXT).createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, ServicioWebServiceConsultaEitvSega.XML_ENCODING);
		m.marshal(consultaTarjetaEitvSega, writer);
		writer.flush();
		return writer.toString();
	}
	
	
	
	@Override
	public ResultadoConsultaEitvBean llamadaWS(ConsultaTarjeta consultaTarjeta, String xml, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) {
		log.info(" ENTRA EN BLACK BOX");
		ResultadoConsultaEitvBean respuestaConsultaEitv = null;
		String respuestaError = "";
		try {
			EitvArgument req = completaRequest(consultaTarjeta, xml, tramiteTraficoVO);
			// Llamada WS
			EitvReturn respuestaEitv =  getStubEitv(false).getDataEITV(req);
			log.info("------ " + ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " dossierNumber Respuesta: " + respuestaEitv.getDossierNumber());	
			
			EitvError[] bbErrores = respuestaEitv.getErrorCodes();
		
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RESPUESTA SIN ERRORES");
				// Se gestiona la respuesta del WS
				respuestaConsultaEitv = gestionRespuestaWS(respuestaEitv, consultaTarjeta, idUsuario, tramiteTraficoVO, admin);
				respuestaError=ConstantesMensajes.CONSULTA_EITV_CORRECTA;
				
			} else {
				log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RESPUESTA CON ERRORES");
				if(bbErrores != null) {
					listarErroresMateITV(bbErrores);
					respuestaError += getMensajeError(bbErrores);
					log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " Errores: "+ respuestaError); 
				}				
				respuestaConsultaEitv = new ResultadoConsultaEitvBean(true, respuestaError);
				
			}
			servicioTramiteTraficoMatriculacion.actualizarRespuestaMateEitv(respuestaError,tramiteTraficoVO.getNumExpediente());
		}catch (UnsupportedEncodingException eU){
			log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + eU);
			respuestaConsultaEitv = new ResultadoConsultaEitvBean();
			respuestaConsultaEitv.setException(new Exception(eU));
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + e);
			respuestaConsultaEitv = new ResultadoConsultaEitvBean();
			respuestaConsultaEitv.setException(e);
		}
		log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " SALE DE EITV");
		return respuestaConsultaEitv;
	}
	
	@Override
	@Transactional
	public ResultadoConsultaEitvBean llamadaWSPreMatriculados(ConsultaTarjeta consultaTarjetaEitv, String xmlFirmado, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idUsuario, BigDecimal idContrato) {
		log.info(" ENTRA EN BLACK BOX");
		ResultadoConsultaEitvBean respuesta = null;
		try {
			EitvArgument req = completaRequestVehiculoP(consultaTarjetaEitv, xmlFirmado, vehiculoPreVO, idContrato);
			// Llamada WS
			EitvReturn respuestaEitv =  getStubEitv(true).getDataEITV(req);
			log.info(" ");
			log.info("------ " + ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " dossierNumber Respuesta: " + respuestaEitv.getDossierNumber());	
			
			EitvError[] bbErrores = respuestaEitv.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RESPUESTA SIN ERRORES");
				// Se gestiona la respuesta del WS
				respuesta = gestionRespuestaPreMatriculadosWS(respuestaEitv, consultaTarjetaEitv, idUsuario, vehiculoPreVO, idUsuario);
			} else {
				log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " RESPUESTA CON ERRORES");
				String respuestaError = "";
				if(bbErrores != null) {
					listarErroresMateITV(bbErrores);
					respuestaError += getMensajeError(bbErrores);
					log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " Errores: "+ respuestaError); 
				}		
				servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(false, respuestaError,vehiculoPreVO.getId());
				respuesta = new ResultadoConsultaEitvBean(true, respuestaError);
			}		
		}catch (UnsupportedEncodingException eU){
			log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + eU);
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(new Exception(eU));
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + e);
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(e);
		}
		log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " SALE DE EITV");
		return respuesta;
	}
	
	public String recogerXmlEitv(String xmlEnviar) throws FileNotFoundException {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			String[] xmlEnv = xmlEnviar.split("_");
			Fecha fecha = Utilidades.transformExpedienteFecha(xmlEnv[1]);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml = xmlFiletoString(ficheroAenviar.getFile());
			}
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e);
		} catch (JAXBException e) {
			log.error("error al convertir el documento, error: ", e);
		}
		return xml;		
	}
	
	public String recogerXmlEitvPreMatriculados(String xmlEnviar) throws FileNotFoundException {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = extraerFechaXmlEnviar(xmlEnviar);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV_PREMATRICULADO, fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				xml =  xmlFiletoString(ficheroAenviar.getFile());
			}
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e);
		} catch (JAXBException e) {
			log.error("error al convertir el documento, error: ", e);
		}
		return xml;		
	}

	private Fecha extraerFechaXmlEnviar(String xmlEnviar) {
		String sFecha = xmlEnviar.substring(xmlEnviar.length() - 8, xmlEnviar.length());
		Fecha fecha = new Fecha();
		fecha.setDia(sFecha.substring(0,2));
		fecha.setMes(sFecha.substring(2,4));
		fecha.setAnio(sFecha.substring(4,8));
		return fecha;
	}

	@Override
	public Object getBeanToXml(String xml, String nameContext) throws JAXBException {
		Object obj = null;
		Unmarshaller um = getContext(nameContext).createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		try {
			obj = um.unmarshal(bais);
		} catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		try {
			bais.close();
		} catch(IOException ioe) {
			//Error al cerrar el flujo de bytes
		}
		return obj;
	}
	public JAXBContext getContext(String nameContext) throws JAXBException {
		return JAXBContext.newInstance(nameContext);
	}

	@Override
	public String xmlFiletoString(File ficheroAenviar) throws JAXBException {
		byte[] by = null;
		FileInputStream fis;
		String peticionXML = null;
		try {
			fis = new FileInputStream(ficheroAenviar);
			by = IOUtils.toByteArray(fis);
			peticionXML = new String (by, ServicioWebServiceConsultaEitvSega.UTF_8);
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return peticionXML;
	}
	
	
	private EitvArgument completaRequest(ConsultaTarjeta consultaTarjeta, String xmlFirmado, TramiteTraficoVO tramiteTraficoVO) throws UnsupportedEncodingException{		
		EitvArgument req = new EitvArgument();
		String nive = consultaTarjeta.getDatosFirmados().getNIVE();
		if(nive != null && !nive.equals(" ")){
			req.setNive(consultaTarjeta.getDatosFirmados().getNIVE().toUpperCase());
		}else{
			req.setNive("");
		}
		req.setVin(consultaTarjeta.getDatosFirmados().getVIN()); 
		req.setAgentFiscalId(consultaTarjeta.getDatosFirmados().getAGENTDOI());
		req.setAgencyFiscalId(consultaTarjeta.getDatosFirmados().getAGENCYDOI());
		req.setCustomDossierNumber(tramiteTraficoVO.getNumExpediente().toString());
		req.setExternalSystemFiscalId(tramiteTraficoVO.getContrato().getColegio().getCif());		
		req.setXmlB64(utiles.doBase64Encode(xmlFirmado.getBytes(UTF_8)));
		return req;
	}
	
	private EitvArgument completaRequestVehiculoP(ConsultaTarjeta consultaTarjeta, String xmlFirmado, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idContrato) throws UnsupportedEncodingException {
		EitvArgument req = new EitvArgument();
		req.setNive(consultaTarjeta.getDatosFirmados().getNIVE() == null ? "0" : consultaTarjeta.getDatosFirmados().getNIVE().toUpperCase());
		req.setVin(consultaTarjeta.getDatosFirmados().getVIN()); 
		req.setAgentFiscalId(consultaTarjeta.getDatosFirmados().getAGENTDOI());
		req.setAgencyFiscalId(consultaTarjeta.getDatosFirmados().getAGENCYDOI());
		req.setCustomDossierNumber(vehiculoPreVO.getId().toString());
		if(idContrato != null){
			ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
			if(contratoVO != null && contratoVO.getColegio() != null){
				req.setExternalSystemFiscalId(contratoVO.getColegio().getCif());
			}
		}
		req.setXmlB64(utiles.doBase64Encode(xmlFirmado.getBytes(UTF_8)));
		return req;
	}
	
	public EITVQueryWSBindingStub getStubEitv(boolean preMatriculado)	throws MalformedURLException, ServiceException {
		EITVQueryWSBindingStub stubEitv = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_EITV);  
		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_CONSULTA_TARJETA_EITV));
		EITVQueryWS eitvQueryService = null;
		
		if (null != miURL) {
		
			EITVQueryServiceLocator eitvQueryServiceLocator = new EITVQueryServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(),eitvQueryServiceLocator.getEITVQueryServiceWSDDServiceName());
		
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = eitvQueryServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			if(preMatriculado){
				logHandlerInfo.setHandlerClass(SoapEITVSegaWSHandler.class);
			}else{
				logHandlerInfo.setHandlerClass(SoapEITVPreMatriculadosSegaWSHandler.class);
			}
			list.add(logHandlerInfo);

			eitvQueryService = eitvQueryServiceLocator.getEITVQueryService(miURL);
			log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG + " miURL: " + miURL.toString());
		}
	
		stubEitv = (EITVQueryWSBindingStub) eitvQueryService;
		log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG +" generado stubEitv.");

		stubEitv.setTimeout(Integer.parseInt(timeOut));
		log.info(ServicioWebServiceConsultaEitvSega.TEXTO_CONSULTAEITV_LOG +" timeout: "	+ ServicioWebServiceConsultaEitvSega.TIMEOUT_MATEGE );
		
		return stubEitv;
	}
	
	public ResultadoConsultaEitvBean gestionRespuestaWS(EitvReturn respuestaWS, ConsultaTarjeta consultaTarjeta, BigDecimal idUsuario, TramiteTraficoVO tramiteTraficoVO, boolean admin) {
		ResultadoConsultaEitvBean respuesta = null;
		ResultBean result = null;
		try {
			// Guardar el xml de respuesta
			result = guardarXmlRespuesta(respuestaWS, tramiteTraficoVO.getNumExpediente().toString());
			if(result == null){
				// Comprobar si ha sido correcta la respuesta del WS si es asi guardar los datos del vehiculo que vienen en el xml
				ResultBean resultado = guardarDatosVehiculoXml(respuestaWS.getXmldata(), tramiteTraficoVO, idUsuario, admin);
				if(resultado != null && resultado.getError()){
					respuesta = new ResultadoConsultaEitvBean();
					respuesta.setError(true);
					respuesta.setMensajesError(resultado.getListaMensajes());	
					return respuesta;
				}
				String nombreFichero = TipoImpreso.ConsultaEITV.toString() + "_" + tramiteTraficoVO.getNumExpediente();
				//Una vez recibida la respuesta del WS obtener el pdf y guardarlo
				respuesta = guardarPDFConsultaEitv(respuestaWS, nombreFichero);
			}else{
				respuesta = new ResultadoConsultaEitvBean();
				respuesta.setException(new Exception(result.getListaMensajes().get(0)));
			}
		} catch (Exception e) {
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(e);
		}
		return respuesta;
	}
	
	private ResultadoConsultaEitvBean gestionRespuestaPreMatriculadosWS(EitvReturn respuestaWS, ConsultaTarjeta consultaTarjeta, BigDecimal idUsuario, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idUsuario2) {
		ResultadoConsultaEitvBean respuesta = null;
		ResultBean result = null;
		try {
			servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(true, ServicioWebServiceConsultaEitvSega.MENSAJE_RESPUESTA_OK, vehiculoPreVO.getId());
			result = servicioVehiculosPrematriculados.guardarXmlRespuestaDatosTecnicos(respuestaWS.getXmldata(),vehiculoPreVO.getId(), vehiculoPreVO.getFechaAlta());
			if(result != null){
				result = servicioVehiculosPrematriculados.guardarPdfRespuestaDatosTecnicos(respuestaWS.getFicha(), vehiculoPreVO);
			}
			if(result != null && result.getError()){
				respuesta = new ResultadoConsultaEitvBean();
				respuesta.setError(true);
				respuesta.setMensajesError(result.getListaMensajes());	
				return respuesta;
			}
		} catch (Exception e) {
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(e);
		}
		return respuesta;
	}
	private ResultBean guardarXmlRespuesta(EitvReturn respuestaWS, String numExpediente) {		
		ResultBean resultado = null;
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.RESPUESTAEITV);
		fichero.setNombreDocumento(ConstantesGestorFicheros.CONSULTA_EITV_NUEVO + numExpediente);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
		fichero.setFecha(fecha);
		
		try {
			byte[] xmlData = utiles.doBase64Decode(respuestaWS.getXmldata(), UTF_8);
			fichero.setFicheroByte(xmlData);
			File file = gestorDocumentos.guardarFichero(fichero);
			if(file == null){
				resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
			}
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento",e, numExpediente);
			resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
		} catch (Exception e) {
			log.error("Error al guardar el documento,error al decodificar",e, numExpediente);
			resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
		}	
		return resultado;
	}
	
	private ResultBean guardarDatosVehiculoXml(String xmlB64, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) throws Exception {
		String xmlData = new String(utiles.doBase64Decode(xmlB64, Claves.ENCODING_UTF8));
		Log.info("Datos del vehiculo convertidos");
		Vehiculo vehiculo = (Vehiculo)getBeanToXml(xmlData, NAME_CONTEXT_VEHICULO);
		ResultBean result = null;
		if(vehiculo != null){
			result = guardarDatosVehiculoConsultaEITV(vehiculo, tramiteTraficoVO, idUsuario, admin);
		}else{
			result = new ResultBean(true, "Ha surgido un error a la hora de crear el Vehiculo a partir del xml");
		}
		return result;
	}

	private ResultBean guardarDatosVehiculoConsultaEITV(Vehiculo vehiculoEitv, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) {
		ResultBean result = new ResultBean();
		VehiculoVO vehiculoVO = new VehiculoVO();
		
		vehiculoVO.setIdVehiculo(tramiteTraficoVO.getVehiculo().getIdVehiculo());
		vehiculoVO.setNumColegiado(tramiteTraficoVO.getNumColegiado());
		
		// BLOQUE DATOS DEL VEHÍCULO
		setDatosVehiculoConversion(vehiculoEitv, vehiculoVO);
		
		// BLOQUE DATOS TÉCNICOS
		setDatosTecnicosConversion(vehiculoEitv, vehiculoVO);
		
		// BLOQUE CARACTERÍSTICAS
		setCaracteristicasConversion(vehiculoEitv, vehiculoVO);
		
		// BLOQUE DATOS DE LA INSPECCIÓN TÉCNICA (ITV)
		setDatosItvConversion(vehiculoEitv, vehiculoVO);
		
		chequeaDirectivaCEE(vehiculoVO);
		
		Date fechaPresentacion = null;
		if (tramiteTraficoVO.getFechaPresentacion() != null) {
			fechaPresentacion = tramiteTraficoVO.getFechaPresentacion();
		}
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setIdUsuario(idUsuario);
		
		result = servicioVehiculo.guardarVehiculo(vehiculoVO, tramiteTraficoVO.getNumExpediente(), ServicioVehiculo.TIPO_TRAMITE_MATE_EITV, 
				usuarioDto, fechaPresentacion, ServicioVehiculo.CONVERSION_VEHICULO_MATE_EITV, false, admin);
		
		return result;
	}
	
	private void chequeaDirectivaCEE(VehiculoVO vehiculoVO) {
		if (vehiculoVO != null && vehiculoVO.getIdDirectivaCee() != null 
					&& !vehiculoVO.getIdDirectivaCee().equals("")){
			
			String directivaCEEaux = vehiculoVO.getIdDirectivaCee().toUpperCase();
			if ("QUAD".equals(directivaCEEaux) || "ATV".equals(directivaCEEaux) ) {
				directivaCEEaux = directivaCEEaux.toLowerCase();
			}
			if (directivaCEEaux.startsWith("L")){
				directivaCEEaux = directivaCEEaux.replace("E", "e");
			}
			vehiculoVO.setIdDirectivaCee(directivaCEEaux);	
		}
	}

	private void setDatosItvConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		// Tipo ITV
		vehiculoVO.setTipoItv(vehiculoEitv.getCaracteristicas().getTipo()!=null &&
				!vehiculoEitv.getCaracteristicas().getTipo().equals("") ? vehiculoEitv.getCaracteristicas().getTipo() : null);
				
		// Tipo Tarjeta ITV
		vehiculoVO.setIdTipoTarjetaItv(vehiculoEitv.getGeneral().getTipotarjeta()!=null
				&& ! vehiculoEitv.getGeneral().getTipotarjeta().equals("") ? vehiculoEitv.getGeneral().getTipotarjeta() : null);
		
	}

	private void setCaracteristicasConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		
		// Categoría / Homologación EU
		vehiculoVO.setIdDirectivaCee(vehiculoEitv.getCaracteristicas().getCategoria()!=null
				&& !vehiculoEitv.getCaracteristicas().getCategoria().equals("") ? vehiculoEitv.getCaracteristicas().getCategoria() : null);
		
		// Carrocería
		vehiculoVO.setCarroceria(vehiculoEitv.getCaracteristicas().getTipocarroc()!=null
				&& ! vehiculoEitv.getCaracteristicas().getTipocarroc().equals("") ? vehiculoEitv.getCaracteristicas().getTipocarroc().substring(0, 2) : null);
		
		// Cilindrada
		vehiculoVO.setCilindrada(vehiculoEitv.getCaracteristicas().getCilindrada()!=null
				&& !vehiculoEitv.getCaracteristicas().getCilindrada().equals("") ? vehiculoEitv.getCaracteristicas().getCilindrada() : null);
		
		// CO2
		vehiculoVO.setCo2(vehiculoEitv.getCaracteristicas().getEco2Cmixto()!=null
				&& !vehiculoEitv.getCaracteristicas().getEco2Cmixto().equals("") ? vehiculoEitv.getCaracteristicas().getEco2Cmixto() : null);
		
		// Código ECO
		
		//vehiculoBean.setCodigoEco(codigoEco);
		
		// Consumo
		
		//vehiculoBean.setConsumo(consumo);
		
		// Distancia Entre Ejes
		vehiculoVO.setDistanciaEjes(vehiculoEitv.getCaracteristicas().getDistanejes()!=null
				&& !vehiculoEitv.getCaracteristicas().getDistanejes().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getDistanejes()) : BigDecimal.ZERO);
		
		// ECO Innovacion
		//vehiculoBean.setEcoInnovacion(ecoInnovacion);
		
		// Potencia Fiscal
		vehiculoVO.setPotenciaFiscal(vehiculoEitv.getCaracteristicas().getPotencfiscal()!=null
				&& !vehiculoEitv.getCaracteristicas().getPotencfiscal().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getPotencfiscal()): BigDecimal.ZERO);
		
		// MOM
		vehiculoVO.setMom(vehiculoEitv.getCaracteristicas().getMasamarcha()!=null
				&& !vehiculoEitv.getCaracteristicas().getMasamarcha().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getMasamarcha()) : null);
		
		// MMA
		vehiculoVO.setPesoMma(vehiculoEitv.getCaracteristicas().getMma()!=null
				&& !vehiculoEitv.getCaracteristicas().getMma().equals("") ? vehiculoEitv.getCaracteristicas().getMma() : null);
		
		// MTA / MMA
		vehiculoVO.setMtmaItv(vehiculoEitv.getCaracteristicas().getMmta()!=null
				&& !vehiculoEitv.getCaracteristicas().getMmta().equals("") ? vehiculoEitv.getCaracteristicas().getMmta() : null);
		
		// Nivel de emisiones
		if (vehiculoEitv.getCaracteristicas().getNemisioneseuro()!=null && vehiculoEitv.getCaracteristicas().getNemisioneseuro().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getNemisioneseuro().length() > 15) {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro().substring(0, 14));
			} else {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro());
			}
		} else {
			vehiculoVO.setNivelEmisiones(null);
		}
		
		// Número de ruedas
		vehiculoVO.setnRuedas(vehiculoEitv.getCaracteristicas().getNumruedas()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumruedas().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumruedas()) : null);
		
		// Número máximo de plazas
		vehiculoVO.setPlazas(vehiculoEitv.getCaracteristicas().getNumasientos()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumasientos().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumasientos()) : null);
		
		// Número de plazas pie
		vehiculoVO.setnPlazasPie(vehiculoEitv.getCaracteristicas().getNumplazaspie()!=null
				&& !vehiculoEitv.getCaracteristicas().getNumplazaspie().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getNumplazaspie()) : null);
		
		// Potencia Neta
		if (vehiculoEitv.getCaracteristicas().getPotenciamax()!=null && !vehiculoEitv.getCaracteristicas().getPotenciamax().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getPotenciamax().contains("/")) {
				String potenciaMax[] = vehiculoEitv.getCaracteristicas().getPotenciamax().split("/");
				vehiculoVO.setPotenciaNeta(new BigDecimal(potenciaMax[0]));
			} else {
				vehiculoVO.setPotenciaNeta(new BigDecimal(vehiculoEitv.getCaracteristicas().getPotenciamax()));
			}
		} else {
			vehiculoVO.setPotenciaNeta(null);
		}
		
		// Potencia / Peso
		BigDecimal potenciaPeso = vehiculoEitv.getCaracteristicas().getPotenciapeso()!=null
				&& !vehiculoEitv.getCaracteristicas().getPotenciapeso().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getPotenciapeso()) : BigDecimal.ZERO;
		vehiculoVO.setPotenciaPeso(potenciaPeso);
		
		// Reducción ECO
		//vehiculoBean.setReduccionEco(reduccionEco);
		
		// Tara
		if (vehiculoEitv.getCaracteristicas().getTara() != null && vehiculoEitv.getCaracteristicas().getTara().length() > 7) {
			String tara = vehiculoEitv.getCaracteristicas().getTara().substring(vehiculoEitv.getCaracteristicas().getTara().length() - 7);
			vehiculoVO.setTara(tara);
		} else {
			vehiculoVO.setTara(vehiculoEitv.getCaracteristicas().getTara() != null && !vehiculoEitv.getCaracteristicas().getTara().equals("") ? vehiculoEitv.getCaracteristicas().getTara() : null);
		}
		
		// Tipo Alimentación
		if (vehiculoEitv.getCaracteristicas().getMonobiflex()!=null && !vehiculoEitv.getCaracteristicas().getMonobiflex().equals("")) {
			if ("MONOCOMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase()) || "MONO COMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase()) || "MONOCARBURANT".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase())) {
				vehiculoVO.setTipoAlimentacion("M");
			} else  if ("BICOMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase()) || "BI COMBUSTIBLE".equals(vehiculoEitv.getCaracteristicas().getMonobiflex().toUpperCase())) {
				vehiculoVO.setTipoAlimentacion("B");
			} else {
				vehiculoVO.setTipoAlimentacion(vehiculoEitv.getCaracteristicas().getMonobiflex());
			} 
		}
		
		// Tipo Carburante
		vehiculoVO.setIdCarburante(vehiculoEitv.getCaracteristicas().getCarburante()!=null
				&& !vehiculoEitv.getCaracteristicas().getCarburante().equals("") ? vehiculoEitv.getCaracteristicas().getCarburante() : null);
		
		// Vía Anterior
		vehiculoVO.setViaAnterior(vehiculoEitv.getCaracteristicas().getViaeje1()!=null
				&& !vehiculoEitv.getCaracteristicas().getViaeje1().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getViaeje1()) : null);
			
		// Vía Posterior
		vehiculoVO.setViaPosterior(vehiculoEitv.getCaracteristicas().getViaeje2()!=null
				&& !vehiculoEitv.getCaracteristicas().getViaeje2().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas().getViaeje2()) : null);
		
	}

	private void setDatosTecnicosConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		String clasificacionITV = vehiculoEitv.getFabricante().getClasificacion()!=null
				&& !vehiculoEitv.getFabricante().getClasificacion().equals("") ? vehiculoEitv.getFabricante().getClasificacion() : null;
		String criterioConstruccion = clasificacionITV!=null ? clasificacionITV.substring(0, 2) : null;
		String criterioUtilizacion = clasificacionITV!=null ? clasificacionITV.substring(2) : null;
		
		// Criterio de Construcción
		vehiculoVO.setIdCriterioConstruccion(criterioConstruccion);
		
		// Criterio de Utilización
		vehiculoVO.setIdCriterioUtilizacion(criterioUtilizacion);
		
		// Clasificación ITV
		vehiculoVO.setClasificacionItv(clasificacionITV);
	
		// Color
		vehiculoVO.setIdColor(vehiculoEitv.getCaracteristicas().getColor()!=null
				&& !vehiculoEitv.getCaracteristicas().getColor().equals("") ? vehiculoEitv.getCaracteristicas().getColor() : null);
		
		// Fabricante
		vehiculoVO.setFabricante(vehiculoEitv.getGeneral().getNomfabricante()!=null
				&& !vehiculoEitv.getGeneral().getNomfabricante().equals("") ? vehiculoEitv.getGeneral().getNomfabricante() : null);
		
		// Número de serie
		vehiculoVO.setnSerie(vehiculoEitv.getGeneral().getSerieindustria()!=null
				&& !vehiculoEitv.getGeneral().getSerieindustria().equals("") ? vehiculoEitv.getGeneral().getSerieindustria() : null);
		
		// Número de homologación
		
		vehiculoVO.setnHomologacion(vehiculoEitv.getCertificacion().getContraseña()!=null
				&& !vehiculoEitv.getCertificacion().getContraseña().equals("") ? vehiculoEitv.getCertificacion().getContraseña() : null);
		
		// Servicio
		/*vehiculoVO.setIdServicio(vehiculoEitv.getComunidad().getServicio()!=null
				&& !vehiculoEitv.getComunidad().getServicio().equals("") ? vehiculoEitv.getComunidad().getServicio() : null);*/
		
		// Variante
		
		vehiculoVO.setVariante(vehiculoEitv.getCaracteristicas().getVariante()!=null
				&& !vehiculoEitv.getCaracteristicas().getVariante().equals("") ? vehiculoEitv.getCaracteristicas().getVariante() : null);
		
		// Versión
		vehiculoVO.setVersion(vehiculoEitv.getCaracteristicas().getVersion()!=null
				&& !vehiculoEitv.getCaracteristicas().getVersion().equals("") ? vehiculoEitv.getCaracteristicas().getVersion() : null);
	}

	private void setDatosVehiculoConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		//Bastidor
		vehiculoVO.setBastidor(vehiculoEitv.getFabricante().getNumerovin());
		
		//NIVE
		if (vehiculoEitv.getGeneral().getNive() != null) {
			vehiculoVO.setNive(vehiculoEitv.getGeneral().getNive().toUpperCase());
		}
		
		//Código ITV
		vehiculoVO.setCoditv((vehiculoEitv.getFabricante().getCodigoitv()!=null
				&& !vehiculoEitv.getFabricante().getCodigoitv().equals("") ? vehiculoEitv.getFabricante().getCodigoitv() : null));
		
		// Marca
		// se busca cod marca
		if(vehiculoEitv.getCaracteristicas().getMarca() != null){
			MarcaDgtVO marcaDgtVO = servicioMarcaDgt.getMarcaDgt(null, vehiculoEitv.getCaracteristicas().getMarca(), true);
			if(marcaDgtVO != null){
				vehiculoVO.setCodigoMarca(marcaDgtVO.getCodigoMarca().toString());
			}
		}
		// Modelo
		vehiculoVO.setModelo(vehiculoEitv.getCaracteristicas().getDenominacion()!=null
				&& !vehiculoEitv.getCaracteristicas().getDenominacion().equals("") ? vehiculoEitv.getCaracteristicas().getDenominacion():null);
		
		// Matrícula (sólo si viene, porque el vehículo ya esté matriculado)
		vehiculoVO.setMatricula(vehiculoEitv.getTrafico().getMatricula());
	}
	
	
	public void listarErroresMateITV(EitvError[] listadoErrores){
		log.error("Listando errores MateITV");
		for (int i = 0; i < listadoErrores.length; i++){
			log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO      + " " + listadoErrores[i].getKey()); 
			
			if (listadoErrores[i].getMessage()== null) {
				log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + "No devuelve descripcion del error");
			}
			else {
			    log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
			}
		}
	}
	
	
	public String getMensajeError(EitvError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer(); 
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - " );
			if ("EITV07002".equals(listadoErrores[i].getKey()) && (listadoErrores[i].getMessage()==null)||"None".equals(listadoErrores[i].getMessage()) ){
				listadoErrores[i].setMessage("No se ha encontrado la tarjeta EITV");
				mensajeError.append(listadoErrores[i].getMessage());
			} else if (listadoErrores[i].getMessage()==null) {
				listadoErrores[i].setMessage("Tipo de error: " + listadoErrores[i].getKey());
				mensajeError.append(listadoErrores[i].getMessage());
			} else {
				String error = listadoErrores[i].getMessage().replaceAll("'", "");
				error = error.replaceAll("\"", ""); 
				if(error.length()>80) {
					String resAux = "";
					for(int tam=0; tam<=Math.floor(error.length()/80); tam++) {
						if(tam!=Math.floor(error.length()/80)){
							resAux += error.substring(80*tam, 80*tam+80)+" - ";
						} else {
							resAux += error.substring(80*tam)+" - ";
						}
					}
					error = resAux;
				}
				mensajeError.append(error);
			}
		}
		return mensajeError.toString(); 
	}
	private String generarXmlDatosFirmados(String xmlConsulta) throws JAXBException, UnsupportedEncodingException {
		String nodoDatosFirmados =  obtenerNodoDatosFirmados(xmlConsulta);
		nodoDatosFirmados = utiles.doBase64Encode(nodoDatosFirmados.getBytes(ServicioWebServiceConsultaEitvSega.UTF_8));
		nodoDatosFirmados = nodoDatosFirmados.replaceAll("\n", "");
		return encerrarAfirmaContent(nodoDatosFirmados);
	}

	private String obtenerNodoDatosFirmados(String xml) {
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		return xml.substring(inicio, fin);
	}
	
	 private static String encerrarAfirmaContent(String encodedAFirmar) {
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"" + MATEGE_NODO_FIRMAR_ID + "\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
			log.debug("XML a firmar:" + xmlAFirmar);
			return xmlAFirmar;
		}
	
	private ResultadoConsultaEitvBean guardarPDFConsultaEitv(EitvReturn respuestaWS, String nombreFichero) throws Exception {
		ResultadoConsultaEitvBean respuesta = null;
		try {
			String fichaEitv = respuestaWS.getFicha();
			byte[] ptcBytes = utiles.doBase64Decode(fichaEitv, UTF_8);
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
			fichero.setSubTipo(ConstantesGestorFicheros.PDF_CONSULTA_EITV);
			fichero.setSobreescribir(true);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
			fichero.setNombreDocumento(nombreFichero);
			fichero.setFicheroByte(ptcBytes);
			File ficheroResult = gestorDocumentos.guardarByte(fichero);
			if(ficheroResult == null){
				respuesta = new ResultadoConsultaEitvBean();
				respuesta.setException(new Exception("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta Eitv."));
				log.error("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta Eitv.");
			}
		} catch (OegamExcepcion e) {
			respuesta = new ResultadoConsultaEitvBean();
			respuesta.setException(new Exception(e));
			log.error("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta Eitv, error: ",e);
		}
		return respuesta;
	}
	
	public void cambiarEstadoTramiteProceso(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, EstadoTramiteTrafico.Pendiente_Consulta_EITV,
				EstadoTramiteTrafico.convertir(estadoNuevo), false, null, idUsuario);
		
	}
	
	public ObjectFactory getObjectFactory() {
		if(objectFactory == null){
			objectFactory = new ObjectFactory();
		}
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
	
	public UtilesViafirma getUtilesViafirma() {
		if(utilesViafirma == null){
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}

}
