package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.AtributosFoliado;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Documento;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Foliado;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.IdEtapa;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.IdProceso;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ObjectFactory;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDocumentoLicencia;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicEnviarDocuRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.ResultadoEnvioDocumentacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.corpme.xml.XmlCORPMEFactory;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
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
public class ServicioLicEnviarDocuRestWSImpl implements ServicioLicEnviarDocuRestWS {

	private static final long serialVersionUID = -3568430145807874231L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicEnviarDocuRestWSImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	ServicioLcDocumentoLicencia servicioLcDocumentoLicencia;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioLcTramite servicioLcTramite;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultBean enviarDocumentacion(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado, ArrayList<FicheroInfo> documentacion) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);

			if (tramite != null && StringUtils.isBlank(tramite.getIdSolicitud())) {
				String idSolicitud = servicioLcTramite.generarIdentificadorCam();
				if (idSolicitud == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha generado el identificador de solicitud");
					return resultado;
				} else {
					tramite.setIdSolicitud(idSolicitud.toString());
				}
			}

			Foliado foliado = obtenerPeticion(tramite, documentacion);

			String xml = toXML(foliado);

			if (!validar(xml)) {
				log.error("No se ha validado correctamente el XML contra el XSD");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha validado correctamente el XML contra el XSD");
				tramite.setRespuestaDoc("No se ha validado correctamente el XML contra el XSD");
				servicioLcTramite.guardarOActualizar(tramite);
				return resultado;
			}

			resultado = firmar(xml, aliasColegiado);
			if (resultado != null && !resultado.getError()) {
				String xmlFirmado = (String) resultado.getAttachment("xmlFirmado");
				String nombreDocumento = "foliado_" + numExpediente;
				resultado = guardarDocumentos(xmlFirmado, numExpediente, nombreDocumento, ConstantesGestorFicheros.ENVIO);
				if (resultado != null && !resultado.getError()) {
					resultado = servicioCola.crearSolicitud(ProcesosEnum.LIC_ENVIAR_DOCUMENTACION.getNombreEnum(), nombreDocumento, gestorPropiedades.valorPropertie(NOMBRE_HOST),
							TipoTramiteTrafico.Licencias_CAM.getValorEnum(), tramite.getNumExpediente().toString(), new BigDecimal(idUsuario), null, new BigDecimal(idContrato));
					if (resultado != null && !resultado.getError()) {
						tramite.setRespuestaDoc("Solicitud creada correctamente");
					}
				} else {
					resultado.setMensaje("Error al guardar el XML en disco");
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setMensaje("Error al firmar el XML");
				resultado.setError(Boolean.TRUE);
			}
			servicioLcTramite.guardarOActualizar(tramite);
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de licencias CAM, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar la documentacion de licencias CAM");
		} catch (OegamExcepcion oe) {
			log.error("El trámite ya se encuentra en la cola, por lo que no se duplicara, error:", oe);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite ya se encuentra en la cola, por lo que no se duplicara");
		}
		return resultado;
	}

	@Override
	public ResultBean enviarDocumentacionRest(BigDecimal numExpediente, String nombreXml) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			File ficheroXML = obtenerXML(numExpediente, nombreXml);
			if (ficheroXML != null) {
				ArrayList<File> documentacion = new ArrayList<File>();
				documentacion.add(ficheroXML);

				List<File> listaFicheros = obtenerFicherosAEnviar(numExpediente);
				if (listaFicheros != null && !listaFicheros.isEmpty()) {
					ContratoVO contrato = servicioContrato.getContrato(tramite.getIdContrato());
					List<File> listaFicherosFirmados = firmarDocumentacion(listaFicheros, contrato.getColegiado().getAlias());
					if (listaFicherosFirmados != null && !listaFicherosFirmados.isEmpty()) {
						documentacion.addAll(listaFicheros);
					}
				}

				if (documentacion != null && !documentacion.isEmpty()) {
					File ficheroZip = empaquetarZip(numExpediente.toString(), documentacion);
					guardarZip(ficheroZip, numExpediente);
					resultado = llamadaRest(tramite, ficheroZip);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ficheros para incluir en el ZIP en el Envío de Documentación para CAM.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe el xml de envio para poder realizar el envío de solicitud.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar el envío de solicitud para las Licencias CAM: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el envío de solicitud para las Licencias CAM");
		}
		return resultado;
	}

	private File empaquetarZip(String nombreDelZip, ArrayList<File> ficheros) throws IOException {
		if (ficheros.size() <= 1) {
			return null;
		}
		File destino = new File(nombreDelZip + ".zip");
		FileOutputStream out = new FileOutputStream(destino);
		ZipOutputStream zip = new ZipOutputStream(out);
		for (File file : ficheros) {
			FileInputStream is = new FileInputStream(file);
			ZipEntry zipEntry = null;
			if (file.getName().contains("foliado")) {
				zipEntry = new ZipEntry("foliado.xml");
			} else {
				zipEntry = new ZipEntry(file.getName());
			}
			zip.putNextEntry(zipEntry);
			byte[] buffer = new byte[2048];
			int byteCount;
			while (-1 != (byteCount = is.read(buffer))) {
				zip.write(buffer, 0, byteCount);
			}
			zip.closeEntry();
			is.close();
		}
		zip.close();
		return destino;
	}

	@Override
	@Transactional
	public void finalizarEnvioDocumentacion(BigDecimal numExpediente, String respuesta, BigDecimal idUsuario) {
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			tramite.setRespuestaDoc(respuesta);
			servicioLcTramite.guardarOActualizar(tramite);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora finalizar el proceso de envío documentación de Licencias CAM: " + numExpediente + ", error: ", e);
		}
	}

	private Foliado obtenerPeticion(LcTramiteVO tramite, ArrayList<FicheroInfo> documentacion) {
		ObjectFactory objFactory = new ObjectFactory();

		Foliado foliado = objFactory.createFoliado();

		foliado.setAtributos(rellenarAtributos(objFactory, tramite.getIdSolicitud()));
		foliado.setTotalDocumentos(documentacion.size());
		rellenarDocumentos(objFactory, foliado, documentacion);

		return foliado;
	}

	private AtributosFoliado rellenarAtributos(ObjectFactory objFactory, String idSolicitud) {
		AtributosFoliado atributosFoliado = objFactory.createAtributosFoliado();
		atributosFoliado.setIdProceso(IdProceso.LIC);

		String codigoAgente = gestorPropiedades.valorPropertie("licencias.cam.codigo.agente");
		atributosFoliado.setIdEclu(Integer.parseInt(codigoAgente));

		atributosFoliado.setIdEnvio(idSolicitud);
		atributosFoliado.setIdEtapa(IdEtapa.EMCC);
		return atributosFoliado;
	}

	private void rellenarDocumentos(ObjectFactory objFactory, Foliado foliado, List<FicheroInfo> documentacion) {
		for (FicheroInfo documento : documentacion) {
			try {
				Documento doc = objFactory.createDocumento();
				doc.setNombre(documento.getNombre());
				doc.setHuella(generarHuella(gestorDocumentos.transformFiletoByte(documento.getFichero())));
				doc.setTipo(servicioLcDocumentoLicencia.obtenerTipoDocumento(documento.getNombre()));
				// Se puede enviar cualquier tipo de firma
				doc.setTipoFirma(new BigInteger("1"));
				foliado.getDocumento().add(doc);
			} catch (Exception e) {
				log.error("Error al generar la huella para las licencias CAM del documento: " + documento.getNombre(), e);
			}
		}
	}

	private ResultBean firmar(String xml, String aliasColegiado) {
		ResultBean resultado = new ResultBean(false);
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] txtFirmado = utilesViafirma.firmarXMLLicenciasCam(xml.getBytes(), aliasColegiado);
			if (txtFirmado != null) {
				String xmlFirmado = new String(txtFirmado);
				resultado.addAttachment("xmlFirmado", xmlFirmado);
			} else {
				resultado.setError(true);
			}
		} catch (Exception e) {
			resultado.setError(true);
		}
		return resultado;
	}

	private List<File> firmarDocumentacion(List<File> listaFicheros, String aliasColegiado) {
		List<File> listaFicherosFirmados = new ArrayList<File>();
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			String pathTemp = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + "CAM" + System.currentTimeMillis();
			for (File fichero : listaFicheros) {
				String extension = fichero.getName().split("\\.")[1];
				File ficheroFirmado = null;
				if (StringUtils.isNotBlank(extension)) {
					if ("XML".equals(extension.toUpperCase())) {
						byte[] firmar = gestorDocumentos.transformFiletoByte(fichero);
						byte[] xmlFirmado = utilesViafirma.firmarXMLLicenciasCam(firmar, aliasColegiado);
						ficheroFirmado = gestorDocumentos.obtenerFileToByte(pathTemp, xmlFirmado);

					} else if ("PDF".equals(extension.toUpperCase())) {
						byte[] firmar = gestorDocumentos.transformFiletoByte(fichero);
						byte[] pdfFirmado = utilesViafirma.firmarPdfLicenciasCam(firmar, aliasColegiado);
						ficheroFirmado = gestorDocumentos.obtenerFileToByte(pathTemp, pdfFirmado);
					} else {
						byte[] firmar = gestorDocumentos.transformFiletoByte(fichero);
						// TODO: se elimina el tipo de fichero y se comprueba que firma
						byte[] docFirmado = utilesViafirma.firmarDocumentoLicenciasCam(firmar, aliasColegiado);
						ficheroFirmado = gestorDocumentos.obtenerFileToByte(pathTemp, docFirmado);
					}

					if (ficheroFirmado != null) {
						listaFicherosFirmados.add(ficheroFirmado);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return listaFicherosFirmados;
	}

	private ResultBean llamadaRest(LcTramiteVO tramite, File ficheroZip) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado = licenciasCamRest.enviarDocumentacion(ficheroZip, tramite.getIdSolicitud());
			ResultadoEnvioDocumentacion response = (ResultadoEnvioDocumentacion) resultado.getAttachment("RESPONSE");
			resultado = gestionarRespuesta(response, tramite);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada REST de Envío de Documentación de Licencias Cam, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la llamada REST de Envío de Documentación de Licencias Cam");

		}
		return resultado;
	}

	private ResultBean gestionarRespuesta(ResultadoEnvioDocumentacion response, LcTramiteVO tramite) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			tramite.setRespuestaDoc(response.getCodigoError().getDescripcion());
			tramite.setFechaEnvioDoc(new Date());
			servicioLcTramite.guardarOActualizar(tramite);

			String xml = toXMLResponse(response);
			String nombreDocumento = "EnviarDocumentacion_" + tramite.getNumExpediente();
			guardarDocumentos(xml, tramite.getNumExpediente(), nombreDocumento, ConstantesGestorFicheros.RESPUESTA);
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar los datos de la response");
		}
		return resultado;
	}

	private String toXMLResponse(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(ResultadoEnvioDocumentacion.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}

	private File obtenerXML(BigDecimal numExpediente, String nombreXml) {
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.LICENCIAS_CAM, ConstantesGestorFicheros.ENVIO, fechaBusqueda, nombreXml,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				File renombrar = new File(ficheroAenviar.getFile().getParent(), "foliado.xml");
				// File renombrar = new File("foliado.xml");
				ficheroAenviar.getFile().renameTo(renombrar);
				return ficheroAenviar.getFile();
			}
		} catch (Throwable e) {
			log.error("Error al buscar el xml para licencias, error: ", e, numExpediente.toString());
		}
		return null;
	}

	private List<File> obtenerFicherosAEnviar(BigDecimal numExpediente) {
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.LICENCIAS_CAM, ConstantesGestorFicheros.DOCUMENTOS_LICENCIAS, fechaBusqueda,
					numExpediente.toString());
			if (ficheroAenviar != null && !ficheroAenviar.getFiles().isEmpty()) {
				return ficheroAenviar.getFiles();
			}
		} catch (Throwable e) {
			log.error("Error al buscar los documentos subidos el xml para licencias, error: ", e, numExpediente.toString());
		}
		return null;
	}

	private ResultBean guardarDocumentos(String xmlFirmado, BigDecimal numExpediente, String nombreDocumento, String subTipo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
			ficheroBean.setSubTipo(subTipo);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombreDocumento);
			ficheroBean.setFicheroByte(xmlFirmado.getBytes("ISO-8859-1"));
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el xml firmado de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultBean guardarZip(File zip, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FileInputStream fis = new FileInputStream(zip);
			byte[] array = IOUtils.toByteArray(fis);
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.ENVIO);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_ZIP);
			ficheroBean.setNombreDocumento(numExpediente.toString());
			ficheroBean.setFicheroByte(array);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el xml firmado de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.licenciasCam.jaxb");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
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
			ByteArrayInputStream bais = new ByteArrayInputStream(stringXml.getBytes("ISO-8859-1"));
			unmarshaller.unmarshal(bais);
			return true;
		} catch (Exception ex) {
			log.error(ex);
			return false;
		}
	}

	private String generarHuella(byte[] fichero) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(fichero);
			byte[] mb = digest.digest();
			if (mb != null) {
				return Hex.encodeHex(mb).toString();
			}
		} catch (Throwable e) {

		}
		return "";
	}
}
