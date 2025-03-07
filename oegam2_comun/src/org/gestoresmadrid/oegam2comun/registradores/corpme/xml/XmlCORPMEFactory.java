package org.gestoresmadrid.oegam2comun.registradores.corpme.xml;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


/*
 * Encapsula métodos comunes en la construcción y pruebas
 * de documentos xml mediante el api JAXB
 */
public class XmlCORPMEFactory {

	//private static final String XML_ENCODING_ISO = "ISO-8859-1";
	private static final String XML_ENCODING_UTF8 = "UTF-8";
	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	/**
	 * Método que obtiene el String XML que se monta a partir de un objeto al
	 * que se le realiza un marshall.
	 */
	public String toXML(Object xmlObject,JAXBContext context) throws Exception {
		StringWriter writer = new StringWriter();
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING_UTF8);
		m.marshal(xmlObject, writer);
		writer.flush();
		//String retorno = new String(writer.toString().getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8"));
		//byte[] bytesRetorno = new String(writer.toString().getBytes("UTF-8"),"UTF-8").getBytes("UTF-8");
		return writer.toString();
	}

	/**
	 * Construye la jerarquía de objetos java asociada a un string xml
	 */
	public Object fromXML(String xmlString,JAXBContext context) throws Exception {
		Object obj = null;
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream(xmlString
				.getBytes());
		obj = um.unmarshal(bais);
		bais.close();
		return obj;
	}

	/**
	 * Construye la jerarquía de objetos java asociada a un fichero xml
	 */
	public Object validarFile(File fichero,JAXBContext context) throws Exception {
		Object obj = null;
		Unmarshaller um = context.createUnmarshaller();
		obj = um.unmarshal(new FileInputStream(fichero));
		return obj;
	}

	/*
	 * Elimina de la cadena xml los saltos de línea y los espacios:
	 */
	public String niSaltosNiEspacios(String cadena) {
		cadena = cadena.replaceAll("[\n\r]", "");
		StringBuffer cadBuf = new StringBuffer();
		StringTokenizer stTexto = new StringTokenizer(cadena, ">");
		while (stTexto.hasMoreElements()) {
			cadBuf.append(stTexto.nextElement().toString().trim());
			cadBuf.append(">");
		}
		return cadBuf.toString();
	}

	/*
	 * Muestra por la salida por defecto el string xml generado a partir de la
	 * jeraquía de objetos jaxb. Si el boolean formateado se establece a true
	 * la cadena xml se construirá sin espacios en blanco y sin saltos de linea
	 */
	public void sacarPorPantalla(Object objeto,boolean formateado,JAXBContext context) throws Exception {
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING_UTF8);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formateado);
		m.marshal(objeto, System.out);
	}
	
	/*
	 * Graba el marshal del objeto en un fichero que se recibe como parámetro:
	 */
	public void grabarEnFichero(Object objeto,boolean formateado,JAXBContext context,File fich) throws Exception {
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING_UTF8);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formateado);
		m.marshal(objeto, fich);
	}

	
	
	
	
	/*
	 * Graba el contenido de un string xml en un fichero.
	 */
	public File toFile(String where, String cadXML) throws IOException {
		File ficheroXML = new File(where);
		//BufferedWriter out out = new BufferedWriter(new FileWriter(ficheroXML));
		CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(where),encoder));
		out.write(cadXML);
		out.close();
		return ficheroXML;
	}

	/*
	 * Método sobrescrito que recibe el fichero.
	 */
	public File toFile(File file, String cadXML) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write(cadXML);
		out.close();
		return file;
	}
	
	/*
	 * Validar con el xsd la jerarquía de objetos de jaxb. Debe recibir
	 * como parámetro el nombre del esquema contra el que validar, y el 
	 * string xml que se desea que se valide.	
	 */
	public boolean validar(String stringXml, JAXBContext context, String nombreEsquema) throws SAXException, JAXBException, URISyntaxException {
		// Nombres válidos de esquemas:
		// CORPME_eDoc
		// CORPME_RM
		// RBM_FINANCIACION
		URL resource = null;
		if (nombreEsquema.equals("CORPME_RM")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/CORPME_RM_CeseyNombramiento.xsd");
		} else if (nombreEsquema.equals("CORPME_eDoc")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_eDoc/CORPME_eDoc.xsd");
		} else if (nombreEsquema.equals("RBM_FINANCIACION")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Financiacion.xsd");
		} else if (nombreEsquema.equals("RBM_RENTING")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Renting.xsd");
		} else if (nombreEsquema.equals("RBM_LEASING")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Leasing.xsd");
		} else if (nombreEsquema.equals("RBM_CANCELACION")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Cancelacion_Limitaciones.xsd");
		}
		try {
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(resource);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			ByteArrayInputStream bais = new ByteArrayInputStream(stringXml.getBytes("UTF-8"));
			unmarshaller.unmarshal(bais);
			return true;
		} catch (Exception ex) {
			log.error(ex);
			return false;
		}
	}
	
	public ResultBean validarRbm(String stringXml, JAXBContext context, String nombreEsquema) throws SAXException, JAXBException, URISyntaxException {
		// Nombres válidos de esquemas:
		// CORPME_eDoc
		// CORPME_RM
		// RBM_FINANCIACION
		ResultBean result = new ResultBean();
		URL resource = null;
		if (nombreEsquema.equals("CORPME_RM")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/CORPME_RM_CeseyNombramiento.xsd");
		} else if (nombreEsquema.equals("CORPME_eDoc")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_eDoc/CORPME_eDoc.xsd");
		} else if (nombreEsquema.equals("RBM_FINANCIACION")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Financiacion.xsd");
		} else if (nombreEsquema.equals("RBM_RENTING")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Renting.xsd");
		} else if (nombreEsquema.equals("RBM_LEASING")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Contrato_Leasing.xsd");
		} else if (nombreEsquema.equals("RBM_CANCELACION")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Cancelacion_Limitaciones.xsd");
		} else if (nombreEsquema.equals("RBM_DESISTIMIENTO")) {
			resource = XmlCORPMEFactory.class.getResource("/registradores/esquemas/CORPME_RM/RBM_Protocolo_Desistimiento.xsd");
		}
		try {
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(resource);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			ByteArrayInputStream bais = new ByteArrayInputStream(stringXml.getBytes("UTF-8"));
			unmarshaller.unmarshal(bais);
			result.setError(false);
			result.setMensaje("XML validado correctamente");
			return result;
		} catch (Exception ex) {
			log.error(ex);
			result.setError(true);
			result.setMensaje("Error validando xml el dato "+ex.getCause().toString().substring(ex.getCause().toString().indexOf("{")+1, ex.getCause().toString().indexOf("}"))+" no es correcto");
			return result;
		}
	}
	/**
	 * 
	 * @param ficheroAenviar File de un xml
	 * @return El fichero xml en String
	 */
	
	public static String xmlFileToString(File ficheroAenviar) {
		byte[] by = null; 
		try {
			FileInputStream fis;
			fis = new FileInputStream(ficheroAenviar);
			by = IOUtils.toByteArray(fis);
			}
		catch (FileNotFoundException e1) 
			{
			log.error(e1);
			}
		catch (IOException e) 
			{
			log.error(e);
			}
		String  peticionXML = null;
		try {
			peticionXML = new String (by, "UTF8");
			}
		catch (UnsupportedEncodingException e1) 
			{
			log.error(e1);
			}
		return peticionXML;
	}
}
