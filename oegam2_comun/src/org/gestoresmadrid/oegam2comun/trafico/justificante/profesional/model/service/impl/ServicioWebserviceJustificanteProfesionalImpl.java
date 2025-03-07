package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteServiceLocator;
import org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS;
import org.gestoresmadrid.justificanteprofesional.ws.schema.DatosFirmados;
import org.gestoresmadrid.justificanteprofesional.ws.schema.DatosFirmados.DatosVehiculo;
import org.gestoresmadrid.justificanteprofesional.ws.schema.DatosFirmados.Titular;
import org.gestoresmadrid.justificanteprofesional.ws.schema.ObjectFactory;
import org.gestoresmadrid.justificanteprofesional.ws.schema.SolicitudJustificanteProfesional;
import org.gestoresmadrid.justificanteprofesional.ws.schema.TipoDatoNombre;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioWebserviceJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.webservice.handler.SoapJustificanteProfesionalFilesHandler;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.JustificanteProfResult;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import colas.constantes.ConstantesProcesos;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.UtilesWSMatw;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioWebserviceJustificanteProfesionalImpl implements ServicioWebserviceJustificanteProfesional {

	private static final long serialVersionUID = -8568740703602998411L;
	// Claves de propiedades
	private static final String SERVICIO_JUSTIFICANTE_PROFESIONAL_URL = "justificante.profesional.ws.url";
	private static final String SERVICIO_JUSTIFICANTE_PROFESIONAL_DOI_PLATAFORMA = "doi.plataforma";

	// Constantes del XML
	private static final String XML_ENCODING_UTF8 = "UTF-8";
	private static final String NAME_CONTEXT = "org.gestoresmadrid.justificanteprofesional.ws.schema";

	// Constantes
	private static final String TIPO_EMISION = "ISSUEPROPROOF";
	private static final String TIPO_DESCARGA = "DOWNLOADPROPROOF";
	private static final String TIPO_VERIFICACION = "VERIPROPROOF";
	private static final String ERROR_GENERICO = "Error no controlado en la ejecucion del servicio";
	private static final String ERROR_DATOS_INCOMPLETOS = "Faltan datos para realizar la llamada al servicio";
	private static final String TEXTO_LEGAL = "LOS DATOS CONSIGNADOS EN ESTE XML SE SUMINISTRAN PARA LA EMISIÓN DE UN JUSTIFICANTE PROFESIONAL";
	private static final String FORMATO_FECHA_AVISO = "yyyy/MM/dd";
	private static final String ESPACIO = " ";
	private static final String NUMERO_VIA = "Nº";
	private static final String RUTA_XSD = "/xsd/Justificante.xsd";

	private ILoggerOegam log = LoggerOegam.getLogger(ConstantesProcesos.LOG_APPENDER_JUSTIFICANTES);

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public JustificanteProfResult emisionJustificante(JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico) {
		JustificanteProfResult result = new JustificanteProfResult();
		if (justificanteProfDto != null) {
			if (log.isInfoEnabled()) {
				log.info("Emitir justificante profesional con id " + (justificanteProfDto.getIdJustificanteInterno() != null ? justificanteProfDto.getIdJustificanteInterno().toString() : null));
			}
			try {
				// Iniciar el cliente de WS
				JustificanteWS justificanteWS = initializePagoItv(justificanteProfDto.getIdJustificanteInterno(), TIPO_EMISION);

				// Generar el XML que se envia en la request
				byte[] xml = generarXMLEmisionJustificante(justificanteProfDto, tramiteTrafico);
				if (xml != null) {
					try {
						validarXMLcontraXSD(xml);

						// Invocación del servicio
						JustificanteRespuestaSOAP respuestaSoap = null;

						respuestaSoap = justificanteWS.emitirJustificante(gestorPropiedades.valorPropertie(SERVICIO_JUSTIFICANTE_PROFESIONAL_DOI_PLATAFORMA), new String(xml));
						// Parsear respuesta generica
						actualizaResultado(justificanteProfDto.getIdJustificanteInterno(), result, respuestaSoap);

					} catch (SAXException e) {
						// Ha fallado la validacion del XML, recuperamos el mensaje de error
						log.error("Error en la validacion del XML de justificantes", e);
						result.setError(true);
						result.addMensaje(parseSAXParseException(e));
					}
				}

				// Parsear respuesta particular de emision de justificante
				if (result.getNumeroJustificante() == null || result.getNumeroJustificante().isEmpty() || result.getJustificante() == null || result.getJustificante().length <= 0) {
					result.setError(true);
				}

			} catch (ServiceException e) {
				// Captura de la excepcion lanzada por initializePagoItv
				result.setError(true);
				result.setException(e);
				log.error("Error inicializando cliente de WS", e);
			} catch (RemoteException e) {
				// Captura de la excepcion lanzada por la llamada al WS de emitirJustificante
				result.setError(true);
				result.setException(e);
				log.error("Error en la llamada al WS de emision de justificantes", e);
			} catch (UnsupportedEncodingException e) {
				// Captura de la excepcion lanzada al generar el XML que se envia
				result.setException(e);
				log.error("Error formateando el XML", e);
			} catch (JAXBException e) {
				// Captura de la excepcion lanzada al generar el XML que se envia
				result.setException(e);
				log.error("Error generando el XML", e);
			}
		} else {
			// justificanteProfDto es nulo
			result.setError(true);
			result.addMensaje(ERROR_DATOS_INCOMPLETOS);
		}
		return result;
	}

	@Override
	public JustificanteProfResult descargarJustificante(JustificanteProfDto justificanteProfDto) {
		JustificanteProfResult result = new JustificanteProfResult();
		if (justificanteProfDto != null && justificanteProfDto.getIdJustificante() != null) {
			if (log.isInfoEnabled()) {
				log.info("Descarga justificante profesional con numero de justificante " + justificanteProfDto.getIdJustificante().toString());
			}
			try {
				// Iniciar el cliente de WS
				JustificanteWS justificanteWS = initializePagoItv(justificanteProfDto.getIdJustificanteInterno(), TIPO_DESCARGA);

				// Invocación del servicio
				JustificanteRespuestaSOAP respuestaSoap = justificanteWS.descargarJustificante(gestorPropiedades.valorPropertie(SERVICIO_JUSTIFICANTE_PROFESIONAL_DOI_PLATAFORMA), justificanteProfDto
						.getIdJustificante().toString());

				// Parsear respuesta generica
				actualizaResultado(justificanteProfDto.getIdJustificanteInterno(), result, respuestaSoap);

				// Parsear respuesta particular de descarga de justificante
				if (result.getJustificante() == null || result.getJustificante().length <= 0) {
					result.setError(true);
				}

			} catch (ServiceException e) {
				// Captura de la excepcion lanzada por initializePagoItv
				result.setError(true);
				result.setException(e);
				log.error("Error inicializando cliente de WS", e);
			} catch (RemoteException e) {
				// Captura de la excepcion lanzada por la llamada al WS de descargarJustificante
				result.setError(true);
				result.setException(e);
				log.error("Error en la llamada al WS de descargar justificante profesional", e);
			}
		} else {
			// justificanteProfDto es nulo o no tiene numero de justificante
			result.setError(true);
			result.addMensaje(ERROR_DATOS_INCOMPLETOS);
		}
		return result;
	}

	@Override
	public JustificanteProfResult verificarJustificante(JustificanteProfDto justificanteProfDto) {
		JustificanteProfResult result = new JustificanteProfResult();
		if (justificanteProfDto != null && justificanteProfDto.getCodigoVerificacion() != null) {
			if (log.isInfoEnabled()) {
				log.info("Verificar justificante profesional con CSV " + justificanteProfDto.getCodigoVerificacion());
			}
			try {
				// Iniciar el cliente de WS
				JustificanteWS justificanteWS = initializePagoItv(justificanteProfDto.getIdJustificanteInterno(), TIPO_VERIFICACION);

				// Invocación del servicio
				JustificanteRespuestaSOAP respuestaSoap = justificanteWS.verificarJustificante(justificanteProfDto.getCodigoVerificacion());

				// Parsear respuesta generica
				actualizaResultado(justificanteProfDto.getIdJustificanteInterno(), result, respuestaSoap);

				// Parsear respuesta particular de verificacion de justificante
				if (result.getNumeroJustificante() == null || result.getNumeroJustificante().isEmpty() || result.getJustificante() == null || result.getJustificante().length <= 0) {
					result.setError(true);
				}

			} catch (ServiceException e) {
				// Captura de la excepcion lanzada por initializePagoItv
				result.setError(true);
				result.setException(e);
				log.error("Error inicializando cliente de WS", e);
			} catch (RemoteException e) {
				// Captura de la excepcion lanzada por la llamada al WS de verificarJustificante
				result.setError(true);
				result.setException(e);
				log.error("Error en la llamada al WS de verificar justificantes profesionales", e);
			}
		} else {
			// justificanteProfDto es nulo
			result.setError(true);
			result.addMensaje(ERROR_DATOS_INCOMPLETOS);
		}
		return result;
	}

	/**
	 * Genera el XML que se envia al WS de emision de justificantes
	 * @param justificanteProfDto
	 * @param tramiteTrafico
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	private byte[] generarXMLEmisionJustificante(JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico) throws UnsupportedEncodingException, JAXBException {
		SolicitudJustificanteProfesional solicitudJustificanteProfesional = generarSolicitudJustificanteProfesional(justificanteProfDto, tramiteTrafico);
		byte[] xml = parseToXML(solicitudJustificanteProfesional, false);
		if (xml != null) {
			log.debug("Emision de justificante profesional. firma del xml por parte del colegiado");
			// Realizar firma en viafirma
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			
			//revisar, no se si esta bien
			if(utilesViafirma.firmarPruebaCertificadoCaducidad(new String(xml, "UTF-8"), tramiteTrafico.getContrato().getColegiadoDto().getAlias())==null){
				
				xml=null;
				
			}else{
				
				byte[] firma = utilesViafirma.firmarJustificanteProfesional(xml, tramiteTrafico.getContrato().getColegiadoDto().getAlias());
				solicitudJustificanteProfesional.setFirmaGestor(firma);
	
				if (EstadoJustificante.Autorizado_icogam.equals(EstadoJustificante.convertir(justificanteProfDto.getEstado()))) {
					log.debug("Emision de justificante profesional. firma del xml por parte del ICOGAM");
					byte[] firmaColegio = utilesViafirma.firmarJustificanteProfesional(xml, gestorPropiedades.valorPropertie(ConstantesPDF.ALIAS_COLEGIO));
					solicitudJustificanteProfesional.setFirmaColegio(firmaColegio);
				}
	
				xml = parseToXML(solicitudJustificanteProfesional, true);
				
			}
			
			

		}
		return xml;
	}

	/**
	 * Transformar Objeto a XML
	 * @param justificanteProfDto
	 * @param full true si se genera el XML completo, false si solo se genera el xml de datosFirmados
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private byte[] parseToXML(SolicitudJustificanteProfesional solicitudJustificanteProfesional, boolean full) throws UnsupportedEncodingException, JAXBException {
		byte[] xml = null;
		if (log.isDebugEnabled()) {
			log.debug("Emision de justificante profesional. Obtencion del XML " + (full ? "completo" : "de datos_firmados"));
		}

		ByteArrayOutputStream baos = null;
		try {
			// Generar XML
			JAXBContext context = JAXBContext.newInstance(NAME_CONTEXT);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING_UTF8);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			baos = new ByteArrayOutputStream();
			if (full) {
				m.marshal(solicitudJustificanteProfesional, baos);
				xml = Base64.encodeBase64(baos.toByteArray());
			} else {
				m.marshal(solicitudJustificanteProfesional.getDatosFirmados(), baos);
				String xmlString = baos.toString(XML_ENCODING_UTF8);
				int inicio = xmlString.indexOf("<Datos_Firmados>") + 16;
				int fin = xmlString.indexOf("</Datos_Firmados>");
				xmlString = new String(Base64.encodeBase64(xmlString.substring(inicio, fin).getBytes(XML_ENCODING_UTF8)));
				xmlString = "<AFIRMA><CONTENT Id=\"D0\">" + xmlString + "</CONTENT></AFIRMA>";
				xml = xmlString.getBytes(XML_ENCODING_UTF8);
			}
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					log.error("Error cerrando recursos", e);
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Emision de justificante profesional. XML obtenido: " + xml);
		}
		return xml;
	}

	/**
	 * Construir JAXB
	 * @param justificanteProfDto
	 * @return
	 */
	private SolicitudJustificanteProfesional generarSolicitudJustificanteProfesional(JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico) {
		log.debug("Emision de justificante profesional. Parseo de los dtos al objeto del XSD SolicitudJustificanteProfesional");
		PersonaDto presentador = servicioPersona.obtenerColegiadoCompleto(tramiteTrafico.getNumColegiado(), tramiteTrafico.getContrato().getIdContrato());
		IntervinienteTraficoDto titularAdquiriente = servicioJustificanteProfesional.getTitularAdquiriente(tramiteTrafico);
		;

		// Montar estructura
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudJustificanteProfesional solicitudJustificanteProfesional = objectFactory.createSolicitudJustificanteProfesional();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		DatosVehiculo datosVehiculo = objectFactory.createDatosFirmadosDatosVehiculo();
		TipoDatoNombre datosNombre = objectFactory.createTipoDatoNombre();
		Titular titular = objectFactory.createDatosFirmadosTitular();
		datosFirmados.setDatosVehiculo(datosVehiculo);
		datosFirmados.setTitular(titular);
		solicitudJustificanteProfesional.setDatosFirmados(datosFirmados);
		titular.setDatosNombre(datosNombre);

		// Campos simples de datos firmados
		datosFirmados.setDOIColegio(tramiteTrafico.getContrato().getColegioDto().getCif());
		datosFirmados.setDOIGestoria(tramiteTrafico.getContrato().getCif());
		datosFirmados.setDOIGestor(tramiteTrafico.getContrato().getColegiadoDto().getUsuario().getNif());
		datosFirmados.setNombreColegio(tramiteTrafico.getContrato().getColegioDto().getNombre());
		
		String nombreProvincia = servicioDireccion.obtenerNombreProvincia(tramiteTrafico.getContrato().getIdProvincia());
		
		datosFirmados.setProvinciaDespachoProfesional(nombreProvincia);
		datosFirmados.setProvinciaFecha(nombreProvincia);
		datosFirmados.setNumeroColegiadoGestor(tramiteTrafico.getNumColegiado());
		datosFirmados.setTextoLegal(TEXTO_LEGAL);
		datosFirmados.setObservaciones(justificanteProfDto.getObservaciones());

		// Montar nombre del gestor
		StringBuilder nombreGestor = new StringBuilder();
		if (presentador.getNombre() != null && !presentador.getNombre().isEmpty()) {
			nombreGestor.append(presentador.getNombre()).append(ESPACIO);
		}
		if (presentador.getApellido1RazonSocial() != null && !presentador.getApellido1RazonSocial().isEmpty()) {
			nombreGestor.append(presentador.getApellido1RazonSocial()).append(ESPACIO);
		}
		if (presentador.getApellido2() != null) {
			nombreGestor.append(presentador.getApellido2());
		}
		datosFirmados.setNombreGestor(nombreGestor.toString().trim());
		// Montar direccion de la gestoria;
		StringBuilder direccionGestoria = new StringBuilder();
		String nombreTipoVia = servicioDireccion.obtenerNombreTipoVia(tramiteTrafico.getContrato().getIdTipoVia());
		if (nombreTipoVia != null && !nombreTipoVia.isEmpty()) {
			direccionGestoria.append(nombreTipoVia).append(ESPACIO);
		}
		if (tramiteTrafico.getContrato().getVia() != null && !tramiteTrafico.getContrato().getVia().isEmpty()) {
			direccionGestoria.append(tramiteTrafico.getContrato().getVia()).append(ESPACIO);
		}
		if (tramiteTrafico.getContrato().getNumero() != null && !tramiteTrafico.getContrato().getNumero().isEmpty()) {
			direccionGestoria.append(NUMERO_VIA).append(ESPACIO).append(tramiteTrafico.getContrato().getNumero());
		}
		if ("2440".equals(tramiteTrafico.getNumColegiado())) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(tramiteTrafico.getContrato().getIdMunicipio(), tramiteTrafico.getContrato().getIdProvincia());
			direccionGestoria.append(" - ").append(nombreMunicipio);
		}
		datosFirmados.setDireccionGestor(direccionGestoria.toString().trim());

		// Datos del titular
		if (TipoPersona.Juridica.equals(TipoPersona.convertir(titularAdquiriente.getPersona().getTipoPersona()))) {
			datosNombre.setRazonSocial(titularAdquiriente.getPersona().getApellido1RazonSocial());
		} else {
			datosNombre.setNombre(titularAdquiriente.getPersona().getNombre());
			datosNombre.setPrimerApellido(titularAdquiriente.getPersona().getApellido1RazonSocial());
			datosNombre.setSegundoApellido(titularAdquiriente.getPersona().getApellido2());
		}

		// Comprobar si el titular o adquiriente pertenece a las fuerzas armadas
		if (titularAdquiriente.getPersona().getFa() == null || titularAdquiriente.getPersona().getFa().isEmpty()) {
			titular.setDOI(titularAdquiriente.getNifInterviniente());
		} else {
			titular.setFA(titularAdquiriente.getPersona().getFa());
		}
		// Municipio del titular o adquiriente
		if (titularAdquiriente.getDireccion().getMunicipio() != null && !titularAdquiriente.getDireccion().getMunicipio().isEmpty()) {
			titular.setMunicipio(titularAdquiriente.getDireccion().getMunicipio());
		} else {
			MunicipioDto municipioDto = servicioDireccion.getMunicipio(titularAdquiriente.getDireccion().getIdMunicipio(), titularAdquiriente.getDireccion().getIdProvincia());
			if (municipioDto != null) {
				titular.setMunicipio(municipioDto.getNombre());
			}
		}
		// Montar la direccion del titular o adquiriente
		StringBuilder direccionTitular = new StringBuilder();
		if (titularAdquiriente.getDireccion().getTipoViaDescripcion() != null && !titularAdquiriente.getDireccion().getTipoViaDescripcion().isEmpty()) {
			direccionTitular.append(titularAdquiriente.getDireccion().getTipoViaDescripcion()).append(ESPACIO);
		} else if (titularAdquiriente.getDireccion().getIdTipoVia() != null && !titularAdquiriente.getDireccion().getIdTipoVia().isEmpty()) {
			TipoViaDto tipoViaDto = servicioDireccion.getTipoVia(titularAdquiriente.getDireccion().getIdTipoVia());
			if (tipoViaDto != null && tipoViaDto.getNombre() != null && !tipoViaDto.getNombre().isEmpty()) {
				direccionTitular.append(tipoViaDto.getNombre()).append(ESPACIO);
			}
		}
		if (titularAdquiriente.getDireccion().getNombreVia() != null && !titularAdquiriente.getDireccion().getNombreVia().isEmpty()) {
			direccionTitular.append(titularAdquiriente.getDireccion().getNombreVia()).append(ESPACIO);
		}
		if (titularAdquiriente.getDireccion().getNumero() != null && !titularAdquiriente.getDireccion().getNumero().isEmpty()) {
			direccionTitular.append(NUMERO_VIA).append(ESPACIO).append(titularAdquiriente.getDireccion().getNumero());
		}
		titular.setDireccion(direccionTitular.toString().trim());

		// Datos del vehiculo
		datosVehiculo.setMatricula(tramiteTrafico.getVehiculoDto().getMatricula());
		MarcaDgtDto marca = servicioVehiculo.getMarcaDgt(tramiteTrafico.getVehiculoDto().getCodigoMarca(), null, null);
		datosVehiculo.setMarca(marca != null ? marca.getMarca() : null);
		datosVehiculo.setModelo(tramiteTrafico.getVehiculoDto().getModelo());
		datosVehiculo.setBastidor(tramiteTrafico.getVehiculoDto().getBastidor());

		return solicitudJustificanteProfesional;
	}

	/**
	 * Actualiza el resultado (justificanteProfResult) de los metodos de emision, descarga o verificacion de justificantes, con el resultado del WS (respuestaSoap)
	 * @param justificanteProfResult
	 * @param respuestaSoap
	 */
	private void actualizaResultado(Long idJustificanteInterno, JustificanteProfResult justificanteProfResult, JustificanteRespuestaSOAP respuestaSoap) {
		if (justificanteProfResult != null) {
			if (respuestaSoap == null) {
				justificanteProfResult.setError(true);
				justificanteProfResult.addMensaje(ERROR_GENERICO);
			} else {
				if (respuestaSoap.getAvisos() != null) {
					if (log.isInfoEnabled()) {
						log.info("Emisión del justificante con id  " + (idJustificanteInterno != null ? idJustificanteInterno.toString() : null) + " obtiene los siguientes avisos:");
					}
					SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_AVISO);
					for (JustificanteCodigoRetorno aviso : respuestaSoap.getAvisos()) {
						if (log.isInfoEnabled()) {
							StringBuilder sb = new StringBuilder(aviso.getCodigo()).append(" :: ").append(aviso.getDescripcion());
							if (aviso.getCreatedOn() != null) {
								sb.append(". Creado el ");
								sb.append(sdf.format(aviso.getCreatedOn().getTime()));
							}
							if (aviso.getModifiedOn() != null) {
								sb.append(". Modificado el ");
								sb.append(sdf.format(aviso.getModifiedOn().getTime()));
							}
							if (aviso.getDeletedOn() != null) {
								sb.append(". Borrado el ");
								sb.append(sdf.format(aviso.getDeletedOn().getTime()));
							}
							log.info(sb.toString());

						}
						justificanteProfResult.addMensaje(aviso.getDescripcion());
					}
				}

				justificanteProfResult.setNumeroJustificante(respuestaSoap.getNumeroJustificante());
				if (respuestaSoap.getJustificante() != null && respuestaSoap.getJustificante().length > 0) {
					justificanteProfResult.setJustificante(Base64.decodeBase64(respuestaSoap.getJustificante()));
				} else {
					if (log.isInfoEnabled()) {
						log.info("No se ha obtenido pdf con el justificante profesional para el justificante con id " + (idJustificanteInterno != null ? idJustificanteInterno.toString() : null));
					}
				}
			}
		}
	}

	/**
	 * Inicializa el cliente del WS
	 * @return
	 * @throws ServiceException
	 */
	private JustificanteWS initializePagoItv(Long identificador, String tipo) throws ServiceException {
		// Cargamos los almacenes de certificados que están en la máquina, en la carpeta datos/oegam/keystore.
		try {
			new UtilesWSMatw().cargarAlmacenesTrafico();
		} catch (OegamExcepcion e) {
			log.error("Error cargando los almacenetes de certificados", e);
		}

		// Recuperar URL de properties
		String address = gestorPropiedades.valorPropertie(SERVICIO_JUSTIFICANTE_PROFESIONAL_URL);
		if (log.isDebugEnabled()) {
			log.info("Inicializando cliente del WS de justificantes profesionales con la URL " + address);
		}

		JustificanteServiceLocator justificanteServiceLocator = new JustificanteServiceLocator();
		justificanteServiceLocator.setJustificanteServiceEndpointAddress(address);

		// Añadimos los handlers de logs
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(address, justificanteServiceLocator.getJustificanteServiceWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = justificanteServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getFilesHandler(identificador, tipo));

		return justificanteServiceLocator.getJustificanteService();
	}

	/**
	 * Instancia y configura un handler para guardar en fichero una copia de las peticiones y respuestas soap
	 * @return Nueva instancia de HandlerInfo
	 */
	private HandlerInfo getFilesHandler(Long identificador, String tipo) {
		// Configuración de la compra que desencadena la peticion
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapJustificanteProfesionalFilesHandler.PROPERTY_KEY_ID, identificador);
		filesConfig.put(SoapJustificanteProfesionalFilesHandler.PROPERTY_KEY_TIPO, tipo);

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapJustificanteProfesionalFilesHandler.class);
		filesHandlerInfo.setHandlerConfig(filesConfig);

		return filesHandlerInfo;
	}

	/**
	 * Valida el XML generado
	 * @param xml
	 * @throws SAXException
	 */
	private void validarXMLcontraXSD(byte[] xml) throws SAXException {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(Base64.decodeBase64(xml));
			URL url = getClass().getResource(RUTA_XSD);
			Schema schema =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(url);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(is));
		} catch (IOException e) {
			log.error("error validando el XML contra el XSD", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("error cerrando el ByteArrayInputStream", e);
				}
			}
		}
	}

	private String parseSAXParseException(SAXException spe) {
		if (spe == null) {
			return null;
		} else {
			if (spe.getMessage() != null && !spe.getMessage().isEmpty()) {
				String ERROR_LENGTH = "^(cvc-maxLength-valid: Value ')(.+)(' with length = ')(.+)(' is not facet-valid with respect to maxLength ')(.+)(' for type ')(.+)";
				Pattern pattern = Pattern.compile(ERROR_LENGTH);
				Matcher matcher = pattern.matcher(spe.getMessage());
				if (matcher.find()) {
					return new StringBuilder("La lóngitud de \"")
							.append(matcher.group(2))
							.append("\" excede el máximo permitido (")
							.append(matcher.group(6)).append(")").toString();
				}
			}
			return spe.getMessage();
		}
	}

}
