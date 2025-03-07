package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.AtributosRegistro;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ObjectFactory;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicRegSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.RegistrarSolicitudRequest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.ResultadoRegistro;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.corpme.xml.XmlCORPMEFactory;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioLicRegSolicitudRestWSImpl implements ServicioLicRegSolicitudRestWS {

	private static final long serialVersionUID = 670688101064717720L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicRegSolicitudRestWSImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioLcTramite servicioLcTramite;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultBean registrarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);

			String codigoAgente = gestorPropiedades.valorPropertie("licencias.cam.codigo.agente");

			AtributosRegistro atributoRegistro = obtenerAtributoRegistro(codigoAgente, tramite.getIdSolicitud(), "LIC", "ANOT");

			String xml = toXML(atributoRegistro);

			if (validar(xml)) {
				log.error("No se ha validado correctamente el XML contra el XSD");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha validado correctamente el XML contra el XSD");
				return resultado;
			}

			resultado = firmar(xml, atributoRegistro, aliasColegiado);
			if (resultado != null && !resultado.getError()) {
				String xmlFirmado = (String) resultado.getAttachment("xmlFirmado");
				String nombreDocumento = "RegistrarSolicitud_" + numExpediente;
				resultado = guardarDocumentos(xmlFirmado, numExpediente, nombreDocumento, ConstantesGestorFicheros.ENVIO);
				if (resultado != null && !resultado.getError()) {
					resultado = servicioCola.crearSolicitud(ProcesosEnum.LIC_REGISTRAR_SOLICITUD.getNombreEnum(), nombreDocumento, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Licencias_CAM
							.getValorEnum(), tramite.getNumExpediente().toString(), new BigDecimal(idUsuario), null, new BigDecimal(idContrato));
					if (resultado != null && !resultado.getError()) {
						tramite.setRespuestaReg("Solicitud creada correctamente");
						servicioLcTramite.guardarOActualizar(tramite);
						servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Pendiente_Presentacion.getValorEnum()), new BigDecimal(idUsuario));
					}
				} else {
					resultado.setMensaje("Error al guardar el XML en disco");
				}
			} else {
				resultado.setMensaje("Error al firmar el XML");
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de licencias CAM, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al registrar la solicitud de licencias CAM");
		} catch (OegamExcepcion oe) {
			log.error("El trámite ya se encuentra en la cola, por lo que no se duplicara, error:", oe);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite ya se encuentra en la cola, por lo que no se duplicara");
		}
		return resultado;
	}

	@Override
	public ResultBean registrarSolicitudRest(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			String xmlBase64 = obtenerXMLBase64(numExpediente, nombreXml);
			if (xmlBase64 != null && !xmlBase64.isEmpty()) {
				resultado = llamadaRest(xmlBase64, tramite, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe el xml de envio para poder realizar el registro de solicitud.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar el registro de solicitud para las Licencias CAM: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el registro de solicitud para las Licencias CAM");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void finalizarRegistro(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario) {
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			tramite.setRespuestaReg(respuesta);
			servicioLcTramite.guardarOActualizar(tramite);
			servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), estado, idUsuario);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora finalizar el proceso de presentación de Licencias CAM: " + numExpediente + ", error: ", e);
		}
	}

	private AtributosRegistro obtenerAtributoRegistro(String idCanal, String idSolicitud, String idProceso, String idEtapa) {
		ObjectFactory objFactory = new ObjectFactory();

		AtributosRegistro atributosRegistro = objFactory.createAtributosRegistro();

		atributosRegistro.setIdEclu(idCanal);
		atributosRegistro.setIdSolicitud(idSolicitud);
		atributosRegistro.setIdProceso(idProceso);
		atributosRegistro.setIdEtapa(idEtapa);

		return atributosRegistro;
	}

	private ResultBean firmar(String xml, AtributosRegistro atributosRegistro, String aliasColegiado) {
		ResultBean resultado = new ResultBean(false);
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] txtFirmado = utilesViafirma.firmarXMLLicenciasCam(xml.getBytes(), aliasColegiado);
			if (txtFirmado != null) {
				String firmaB64 = new String(txtFirmado);
				atributosRegistro.setAny(firmaB64.getBytes(StandardCharsets.UTF_8));
				String xmlFirmado = toXML(atributosRegistro);
				resultado.addAttachment("xmlFirmado", xmlFirmado);
			} else {
				resultado.setError(true);
			}
		} catch (JAXBException e) {
			resultado.setError(true);
		}
		return resultado;
	}

	private ResultBean llamadaRest(String xmlBase64, LcTramiteVO tramite, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			RegistrarSolicitudRequest request = crearRequest(xmlBase64);
			resultado = licenciasCamRest.registrarSolicitud(request);
			ResultadoRegistro response = (ResultadoRegistro) resultado.getAttachment("RESPONSE");
			resultado = gestionarRespuesta(response, tramite, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada REST de Registro de Solicitud de Licencias Cam, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la llamada REST de Registro de Solicitud de Licencias Cam");

		}
		return resultado;
	}

	private ResultBean gestionarRespuesta(ResultadoRegistro response, LcTramiteVO tramite, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (response.getAtributosAnotacion() != null) {
				if (StringUtils.isNotBlank(response.getAtributosRegistro().getAnotacion())) {
					tramite.setAnotacion(response.getAtributosRegistro().getAnotacion());
				}
				if (StringUtils.isNotBlank(response.getAtributosRegistro().getCodInteresado())) {
					tramite.setCodigoInteresado(response.getAtributosRegistro().getCodInteresado());
				}
				if (StringUtils.isNotBlank(response.getAtributosRegistro().getFechaCreacion())) {
					Date fechaRegistro = utilesFecha.formatoFecha("yyyyMMdd", response.getAtributosRegistro().getFechaCreacion());
					if (StringUtils.isNotBlank(response.getAtributosRegistro().getHoraCreacion())) {
						try {
							fechaRegistro = utilesFecha.setHorasMinutosSegundosEnDate(fechaRegistro, response.getAtributosRegistro().getHoraCreacion());
						} catch (Exception e) {
							log.error("La hora de registro en CAM es incorrecta");
						}
					}
					tramite.setFechaPresentacion(fechaRegistro);
				}
			}

			if (response.getB64Solicitud() != null) {
				byte[] pdfSolicitud = Base64.decodeBase64(response.getB64Solicitud().getBytes(StandardCharsets.UTF_8));
				guardarDocumentosPDFSolicitud(pdfSolicitud, tramite.getNumExpediente(), "Presentacion_" + tramite.getNumExpediente());
			}

			if (response.getCodigoError() != null) {
				tramite.setRespuestaEnv(response.getCodigoError().getCodigoRespuesta());
				if ("COK_000".equals(response.getCodigoError().getCodigoRespuesta())) {
					servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Presentada.getValorEnum()), idUsuario);
					tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Presentada.getValorEnum()));
				} else {
					servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.No_Presentada.getValorEnum()), idUsuario);
					tramite.setEstado(new BigDecimal(EstadoLicenciasCam.No_Presentada.getValorEnum()));
				}
			} else {
				tramite.setRespuestaEnv("OK");
				servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Presentada.getValorEnum()), idUsuario);
				tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Presentada.getValorEnum()));
			}

			servicioLcTramite.guardarOActualizar(tramite);

			String xml = toXML(response);
			String nombreDocumento = "RegistrarSolicitud_" + tramite.getNumExpediente();
			guardarDocumentos(xml, tramite.getNumExpediente(), nombreDocumento, ConstantesGestorFicheros.RESPUESTA);
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar los datos de la response");
		}
		return resultado;
	}

	private RegistrarSolicitudRequest crearRequest(String xmlBase64) {
		RegistrarSolicitudRequest request = new RegistrarSolicitudRequest();
		request.setFicheroBase64(xmlBase64);
		return request;
	}

	private String obtenerXMLBase64(BigDecimal numExpediente, String nombreXml) {
		String xmlBase64 = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.LICENCIAS_CAM, ConstantesGestorFicheros.ENVIO, fechaBusqueda, nombreXml,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				byte[] xml = readFileToByteArray(ficheroAenviar.getFile());
				xmlBase64 = new String(Base64.encodeBase64(xml));
			}
		} catch (Throwable e) {
			log.error("Error al crear el xml para licencias, error: ", e, numExpediente.toString());
		}
		return xmlBase64;
	}

	private ResultBean guardarDocumentos(String xmlFirmado, BigDecimal numExpediente, String nombreDocumento, String subTipo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
			ficheroBean.setSubTipo(subTipo);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombreDocumento);
			ficheroBean.setFicheroByte(xmlFirmado.getBytes(StandardCharsets.UTF_8));
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el xml firmado de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultBean guardarDocumentosPDFSolicitud(byte[] pdfSolicitud, BigDecimal numExpediente, String nombreDocumento) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.DOCUMENTOS_SOLICITUD_LICENCIAS);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ficheroBean.setNombreDocumento(nombreDocumento);
			ficheroBean.setFicheroByte(pdfSolicitud);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el xml firmado de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}

	private String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.licenciasCam.jaxb");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}

	private boolean validar(String stringXml) throws SAXException, JAXBException, URISyntaxException {
		URL resource = XmlCORPMEFactory.class.getResource("/org/gestoresmadrid/oegam2comun/licenciasCam/jaxb/GestionLicencias.xsd");
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.licenciasCam.jaxb");
		try {
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(resource);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			ByteArrayInputStream bais = new ByteArrayInputStream(stringXml.getBytes(StandardCharsets.UTF_8));
			unmarshaller.unmarshal(bais);
			return true;
		} catch (Exception ex) {
			log.error(ex);
			return false;
		}
	}
}