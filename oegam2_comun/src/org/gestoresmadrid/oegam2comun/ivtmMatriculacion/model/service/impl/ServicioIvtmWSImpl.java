package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.transform.dom.DOMResult;

import org.apache.axis.message.MessageElement;
import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmWS;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ResultBeanAltaIVTM;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ResultBeanConsultaIVTM;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.iam.sbae.sbintopexterna.InteroperabilidadExterna;
import es.iam.sbae.sbintopexterna.InteroperabilidadExternaPortBindingStub;
import es.iam.sbae.sbintopexterna.InteroperabilidadExternaServiceLocator;
import es.iam.sbae.sbintopexterna.PeticionExterna;
import es.iam.sbae.sbintopexterna.RespuestaPeticion;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.EnumeracionEstadoElaboracion;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.Firmas;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.ObjectFactory;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoContenido;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoDocumental;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoDocumento;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoEstadoElaboracion;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoFirmasElectronicas;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoFirmasElectronicas.ContenidoFirma;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoFirmasElectronicas.ContenidoFirma.FirmaConCertificado;
import es.iam.sbae.sbintopexterna.jaxb.documentoEni.TipoMetadatos;
import es.iam.sbae.sbintopexterna.jaxb.tipos.AltaProyectoAutoliquidacionIVTM;
import es.iam.sbae.sbintopexterna.jaxb.tipos.AltaProyectoAutoliquidacionIVTMRequestType;
import es.iam.sbae.sbintopexterna.jaxb.tipos.ConsultaDeudasIVTM;
import es.iam.sbae.sbintopexterna.jaxb.tipos.ConsultaDeudasIVTMRequestType;
import es.iam.sbae.sbintopexterna.jaxb.tipos.ModificacionProyectoAutoliquidacionIVTM;
import es.iam.sbae.sbintopexterna.jaxb.tipos.ModificacionProyectoAutoliquidacionIVTMRequestType;
import es.iam.sbae.sbintopexterna.jaxb.tipos.XmlTiposIvtmFactory;
import es.iam.sbae.sbintopexterna.ws.FileHandler;
import es.iam.sbae.sbintopexterna.ws.SecurityHandler;
import es.map.scsp.esquemas.datosespecificos.DomicilioTipo;
import es.map.scsp.esquemas.datosespecificos.SujetoType;
import es.map.scsp.esquemas.datosespecificos.VehiculoType;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioIvtmWSImpl implements ServicioIvtmWS {

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioIvtmWSImpl.class);
	private static final String UTF_8 = "UTF-8";

	@Autowired
	ServicioIvtmMatriculacion servicioIvtm;

	@Autowired
	IvtmMatriculacionDao ivtmDao;

	@Autowired
	ServicioColegiado servicioColegiado;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioMatriculacion;

	@Autowired
	Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public ResultBeanAltaIVTM procesarSolicitudAlta(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario,
			BigDecimal idContrato) {

		ResultBeanAltaIVTM resultado = null;
		InteroperabilidadExternaPortBindingStub stub = null;
		PeticionExterna peticion = null;
		RespuestaPeticion resp = null;
		String alias = servicioColegiado.getColegiadoPorIdUsuario(idUsuario).getAlias();
		try {
			stub = getStub(alias, numExpediente);
			peticion = getPeticionAlta(numExpediente);

			if (resp.getRespuestaServicio() != null) { // TODO: Puede dar NullPointerException
				resultado = new ResultBeanAltaIVTM();
				resultado.setMensajeError(resp.getResultadoServicio().getMensajeResultado());
				resultado.setRespuesta(resp.getResultadoServicio().getMensajeResultado());
			} else {
				resultado = new ResultBeanAltaIVTM(true, "Sin respuesta");
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de tramitarBTV, error: ", e, numExpediente.toString());
			resultado = new ResultBeanAltaIVTM(true, e);
		}

		return resultado;
	}

	@Override
	public ResultBeanConsultaIVTM procesarSolicitudConsulta(BigDecimal numExpediente, String xmlEnviar,
			BigDecimal idUsuario, BigDecimal idContrato) {

		ResultBeanConsultaIVTM resultado = null;
		InteroperabilidadExternaPortBindingStub stub = null;
		PeticionExterna peticion = null;
		RespuestaPeticion resp = null;
		String alias = null;
		try {
			alias = servicioColegiado.getColegiadoPorIdUsuario(idUsuario).getAlias();
			stub = getStub(alias, numExpediente);
			peticion = getPeticionConsulta(numExpediente);

			resp = stub.realizaPeticion(peticion);

			if (resp != null) {
				resultado = new ResultBeanConsultaIVTM();
				resultado.setMensajeError(resp.getResultadoServicio().getMensajeResultado());
				resultado.setRespuesta(resp.getResultadoServicio().getMensajeResultado());
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de tramitarBTV, error: ", e, numExpediente.toString());
			resultado = new ResultBeanConsultaIVTM(true, e);
		}

		return resultado;
	}

	private PeticionExterna getPeticionConsulta(BigDecimal numExpediente) throws Exception {

		PeticionExterna peticion = new PeticionExterna();

		ObjectFactory objectFactory = new ObjectFactory();

		TipoDocumento tipoDocumento = objectFactory.createTipoDocumento();
		/*-- Body request -- */

		/* -- <enifile:contenido> */
		TipoContenido tipoContenido = objectFactory.createTipoContenido();

		es.iam.sbae.sbintopexterna.jaxb.tipos.ObjectFactory objectFactoryTipos = new es.iam.sbae.sbintopexterna.jaxb.tipos.ObjectFactory();

		ConsultaDeudasIVTM consulta = objectFactoryTipos.createConsultaDeudasIVTM();
		ConsultaDeudasIVTMRequestType consultaElemento = objectFactoryTipos.createConsultaDeudasIVTMRequestType();
		consultaElemento.setMatricula("M2967TN"); /* Coger la matricula del numExpediente */
		consultaElemento.setDocautorizado("00821563A");
		consulta.setConsultaDeudasIVTMRequest(consultaElemento);

		XmlTiposIvtmFactory xmlTiposIvtmFactory = new XmlTiposIvtmFactory();

		tipoContenido.setDatosXML(xmlTiposIvtmFactory.toXML(consulta));
		tipoContenido.setNombreFormato("XML");

		tipoDocumento.setContenido(tipoContenido);

		/* -- <enidocmeta:metadatos> */
		TipoMetadatos tipoMetadatos = objectFactory.createTipoMetadatos();
		tipoMetadatos.setVersionNTI("1.0");
		tipoMetadatos.setIdentificador("00004461");
		tipoMetadatos.setOrigenCiudadanoAdministracion(false);
		tipoMetadatos.setTipoDocumental(TipoDocumental.TD_06);
		TipoEstadoElaboracion tipoEstadoElaboracion = objectFactory.createTipoEstadoElaboracion();
		tipoEstadoElaboracion.setValorEstadoElaboracion(EnumeracionEstadoElaboracion.EE_01);
		tipoMetadatos.setEstadoElaboracion(tipoEstadoElaboracion);

		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

		tipoMetadatos.setFechaCaptura(xgcal);
		tipoMetadatos.getOrgano().add("L01280796");

		tipoDocumento.setMetadatos(tipoMetadatos);

		ContenidoFirma contenidoFirma = objectFactory.createTipoFirmasElectronicasContenidoFirma();
		FirmaConCertificado firmaConCertificado = objectFactory.createTipoFirmasElectronicasContenidoFirmaFirmaConCertificado();
		firmaConCertificado.setReferenciaFirma("#ID_CONT_01");
		contenidoFirma.setFirmaConCertificado(firmaConCertificado);

		TipoFirmasElectronicas tipoFirmasElectronicas = objectFactory.createTipoFirmasElectronicas();
		tipoFirmasElectronicas.setTipoFirma("TF03");
		tipoFirmasElectronicas.setContenidoFirma(contenidoFirma);

		Firmas firmas = objectFactory.createFirmas();
		firmas.getFirma().add(tipoFirmasElectronicas);

		tipoDocumento.setFirmas(firmas);

		tipoDocumento.setId("ID_CONT_01");

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXB.marshal(objectFactory.createDocumento(tipoDocumento), new DOMResult(document));

		MessageElement[] messageElement = new MessageElement[1];
		messageElement[0] = new MessageElement(document.getDocumentElement());

		peticion.set_any(messageElement);

		return peticion;
	}

	@Override
	public ResultBeanConsultaIVTM procesarSolicitudModificacion(BigDecimal numExpediente, String xmlEnviar,
			BigDecimal idUsuario, BigDecimal idContrato) {

		ResultBeanConsultaIVTM resultado = null;
		InteroperabilidadExternaPortBindingStub stub = null;
		PeticionExterna peticion = null;
		RespuestaPeticion resp = null;
		String alias = null;
		try {
			alias = servicioColegiado.getColegiadoPorIdUsuario(idUsuario).getAlias();
			stub = getStub(alias, numExpediente);
			peticion = getPeticionModificacion(numExpediente);
			resp = stub.realizaPeticion(peticion);

			if (resp != null) {
				resultado = new ResultBeanConsultaIVTM();
				resultado.setMensajeError(resp.getResultadoServicio().getMensajeResultado());
				resultado.setRespuesta(resp.getResultadoServicio().getMensajeResultado());
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de tramitarBTV, error: ", e, numExpediente.toString());
			resultado = new ResultBeanConsultaIVTM(true, e);
		}

		return resultado;
	}

	/**
	 * Se añaden a cliente los handlers necesarios para firmar y para guardar
	 * las peticiones.
	 * 
	 * @param alias
	 * @param numExpediente
	 * @return
	 */
	private InteroperabilidadExternaPortBindingStub getStub(String alias, BigDecimal numExpediente) {
		InteroperabilidadExternaPortBindingStub stub = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioIvtmWS.TIMEOUT_PROP_CONSULTA_IVTM);
		URL miUrl;
		InteroperabilidadExterna tramitar = null;
		try {
			miUrl = new URL(ServicioIvtmWS.WEBSERVICE_URL_IVTM);
			if (miUrl != null) {
				InteroperabilidadExternaServiceLocator locator = new InteroperabilidadExternaServiceLocator();
				javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),
						locator.getInteroperabilidadExternaPortWSDDServiceName());

				@SuppressWarnings("unchecked")
				List<HandlerInfo> list = locator.getHandlerRegistry().getHandlerChain(portName);
				list.add(getSignerHandler(alias));
				list.add(getFilesHandler(numExpediente));

				tramitar = locator.getInteroperabilidadExternaPort(miUrl);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		stub = (InteroperabilidadExternaPortBindingStub) tramitar;
		stub.setTimeout(Integer.parseInt(timeOut));

		return stub;
	}

	private PeticionExterna getPeticionAlta(BigDecimal numExpediente) throws JAXBException {
		PeticionExterna peticion = null;
		MessageElement[] messageElement = null;
		try {
			IvtmMatriculacionDto dto = servicioIvtm.getIvtmPorExpedienteDto(numExpediente);
			TramiteTrafMatrDto traficoDTO = servicioMatriculacion.getTramiteMatriculacion(numExpediente, Boolean.TRUE,
					Boolean.TRUE);

			peticion = new PeticionExterna();
			AltaProyectoAutoliquidacionIVTM alta = new AltaProyectoAutoliquidacionIVTM();
			AltaProyectoAutoliquidacionIVTMRequestType altaElemento = new AltaProyectoAutoliquidacionIVTMRequestType();

			if (dto != null && traficoDTO != null) {

				SujetoType sujeto = rellenarSujeto(traficoDTO);
				VehiculoType vehiculo = rellenarVehiculo(traficoDTO, dto);
				altaElemento.setDocautorizado(utilesColegiado.getNifColegiadoDelContrato(numExpediente));
				alta.setAltaProyectoAutoliquidacionIVTMRequest(altaElemento);

				String xmlString = null;

				JAXBContext context = JAXBContext.newInstance(AltaProyectoAutoliquidacionIVTM.class);
				Marshaller m1 = context.createMarshaller();

				m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
				m1.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
				StringWriter sw = new StringWriter();
				m1.marshal(alta, sw);
				xmlString = sw.toString();
				messageElement = new MessageElement[1];

				InputStream source = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
				InputSource is = new InputSource(source);
				is.setEncoding(UTF_8);
				Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				Element element = (Element) XMLDoc.getDocumentElement();
				messageElement[0] = new MessageElement(element);
			} else {
				return peticion;
			}

		} catch (SAXException | IOException | ParserConfigurationException | OegamExcepcion | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// peticion.set_any(messageElement);
		return peticion;
	}

	private PeticionExterna getPeticionModificacion(BigDecimal numExpediente) throws JAXBException {
		PeticionExterna peticion = null;
		MessageElement[] messageElement = null;
		try {
			IvtmMatriculacionDto dto = servicioIvtm.getIvtmPorExpedienteDto(numExpediente);
			TramiteTrafMatrDto traficoDTO = servicioMatriculacion.getTramiteMatriculacion(numExpediente, Boolean.TRUE,
					Boolean.TRUE);

			peticion = new PeticionExterna();
			ModificacionProyectoAutoliquidacionIVTM mod = new ModificacionProyectoAutoliquidacionIVTM();
			ModificacionProyectoAutoliquidacionIVTMRequestType modElemento = new ModificacionProyectoAutoliquidacionIVTMRequestType();
			if (dto != null && traficoDTO != null) {

				SujetoType sujeto = rellenarSujeto(traficoDTO);
				VehiculoType vehiculo = rellenarVehiculo(traficoDTO, dto);
				modElemento.setDocautorizado(utilesColegiado.getNifColegiadoDelContrato(numExpediente));
				mod.setModificacionProyectoAutoliquidacionIVTMRequest(modElemento);

				String xmlString = null;

				JAXBContext context = JAXBContext.newInstance(AltaProyectoAutoliquidacionIVTM.class);
				Marshaller m1 = context.createMarshaller();

				m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
				m1.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
				StringWriter sw = new StringWriter();
				m1.marshal(mod, sw);
				xmlString = sw.toString();
				messageElement = new MessageElement[1];

				InputStream source = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
				InputSource is = new InputSource(source);
				is.setEncoding(UTF_8);

				Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				Element element = (Element) XMLDoc.getDocumentElement();
				messageElement[0] = new MessageElement(element);
			} else {
				return peticion;
			}

		} catch (SAXException | IOException | ParserConfigurationException | OegamExcepcion | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// peticion.set_any(messageElement);
		return peticion;
	}

//	@SuppressWarnings("unused")
//	private void procesarRespuestaAlta(RespuestaPeticion respuesta, BigDecimal numExpediente) {
//		AltaProyectoAutoliquidacionIVTMResponse result = null;
//		try {
//			IvtmMatriculacionDto dto = servicioIvtm.getIvtmPorExpedienteDto(numExpediente);
//			TramiteTrafMatrDto traficoDTO = servicioMatriculacion.getTramiteMatriculacion(numExpediente, Boolean.TRUE,
//					Boolean.TRUE);
//
//			if (respuesta.getRespuestaServicio() != null) {
//				String resp = respuesta.getRespuestaServicio();
//
//				InputSource is = new InputSource();
//				is.setCharacterStream(new StringReader(resp));
//				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//
//				Document doc = builder.parse(is);
//
//				NodeList nodes = doc.getElementsByTagName("AltaProyectoAutoliquidacionIVTMResponse");
//				for (int i = 0; i < nodes.getLength(); i++) {
//					result = (AltaProyectoAutoliquidacionIVTMResponse) nodes.item(i);
//				}
//				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Recibida respuesta correcta. Procesando respuesta");
//				// dto.setNrc(datosEspecificosRespuesta.getNumautoliquidacion());
//				// dto.setCodGestor(datosEspecificosRespuesta.getCodgestor());
//				// dto.setDigitoControl(datosEspecificosRespuesta.getDigitos());
//				// dto.setEmisor(datosEspecificosRespuesta.getEmisor());
//				// if(datosEspecificosRespuesta.getImporte()!= null &&
//				// !datosEspecificosRespuesta.getImporte().equals("")){
//				// ivtmMatriculacion.setImporte(new
//				// BigDecimal(datosEspecificosRespuesta.getImporte()));
//				// }
//				// if(datosEspecificosRespuesta.getImporteanual()!=null &&
//				// !datosEspecificosRespuesta.getImporteanual().equals("")){
//				// ivtmMatriculacion.setImporteAnual(new
//				// BigDecimal(datosEspecificosRespuesta.getImporte()));
//				// }
//				dto.setMensaje("OK");
//				dto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Ok.getValorEnum()));
//			} else {
//				log.info("Respuesta Consulta IVTM -- Error Recibido: " + dto.getMensaje());
//				dto.setMensaje(respuesta.getResultadoServicio().getMensajeResultado());
//				dto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error.getValorEnum()));
//				traficoDTO.setEstado(EstadoIVTM.Ivtm_Error.getValorEnum());
//			}
//
//			servicioMatriculacion.guardarOActualizar(traficoDTO);
//			servicioIvtm.guardarIvtm(dto);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} catch (OegamExcepcion e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Instancia y configura un handler para realizar la firma de las peticiones
	 * soap
	 * 
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getSignerHandler(String alias) {
		Map<String, Object> config = new HashMap<>();
		config.put(SecurityHandler.ALIAS_KEY, alias);
		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(SecurityHandler.class);
		signerHandlerInfo.setHandlerConfig(config);
		return signerHandlerInfo;
	}

	/**
	 * Instancia y configura un handler para guardar en fichero una copia de las
	 * peticiones y respuestas SOAP
	 * 
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getFilesHandler(BigDecimal numExpediente) {
		// Configuración de la compra que desencadena la petición
		Map<String, Object> filesConfig = new HashMap<>();
		filesConfig.put(FileHandler.PROPERTY_KEY_ID, numExpediente);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(FileHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}

	/**
	 * Rellenamos el vehículo para realizar la petición
	 * 
	 * @return VehiculoType
	 */
	private VehiculoType rellenarVehiculo(TramiteTrafMatrDto traficoDTO, IvtmMatriculacionDto dto) {
		VehiculoType vehiculo = new VehiculoType();
		vehiculo.setCo2(traficoDTO.getVehiculoDto().getCo2());
		vehiculo.setCubicaje(traficoDTO.getVehiculoDto().getCilindrada());
		vehiculo.setMarca(traficoDTO.getVehiculoDto().getMarcaSinEditar());
		vehiculo.setModelo(traficoDTO.getVehiculoDto().getModelo());
		vehiculo.setNumbastidor(traficoDTO.getVehiculoDto().getBastidor());
		vehiculo.setPlazas(traficoDTO.getVehiculoDto().getPlazas().toString());
		vehiculo.setPotencia(traficoDTO.getVehiculoDto().getPotenciaNeta().toString());
		vehiculo.setTipocarburante(traficoDTO.getVehiculoDto().getTipoAlimentacion());
		vehiculo.setBodmedamb(dto.getBonmedam().toString());
		vehiculo.setPorcentajebodmedamb(dto.getPorcentajebonmedam().toString());
		return vehiculo;
	}

	private SujetoType rellenarSujeto(TramiteTrafMatrDto traficoDTO) {
		SujetoType sujeto = new SujetoType();
		sujeto.setApellido1(traficoDTO.getTitular().getPersona().getApellido1RazonSocial());
		sujeto.setApellido2(traficoDTO.getTitular().getPersona().getApellido2());
		sujeto.setNombre(traficoDTO.getTitular().getPersona().getNombre());
		sujeto.setNumdocumento(traficoDTO.getTitular().getPersona().getNif());
		sujeto.setTipodocumento(traficoDTO.getTitular().getPersona().getTipoDocumento());

		// Rellenamos el domicilio del sujeto
		DomicilioTipo domi = new DomicilioTipo();
		domi.setClasevial(traficoDTO.getTitular().getDireccion().getIdTipoVia());
		domi.setNombrevial(traficoDTO.getTitular().getDireccion().getNombreVia());
		// domi.setPiso(traficoDTO.getTitular().getPersona().getDireccionDto().getPlanta());
		// domi.setPuerta(traficoDTO.getTitular().getPersona().getDireccionDto().getPuerta());
		domi.setPoblacion(traficoDTO.getTitular().getDireccion().getPueblo());
		domi.setProvincia(traficoDTO.getTitular().getDireccion().getNombreProvincia());
		domi.setCodpostal(traficoDTO.getTitular().getDireccion().getCodPostalCorreos());

		sujeto.setDomicilio(domi);

		return sujeto;
	}

}