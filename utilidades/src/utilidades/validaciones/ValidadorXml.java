/**
 * 
 */
package utilidades.validaciones;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * @author ext_fjcl
 *
 */
public class ValidadorXml {

	private static SchemaFactory schemaFactory;
	
	static{
		schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}
	
	public static void validarXML(String pathXsd, File xml) throws IOException, SAXException {
		Schema schema = schemaFactory.newSchema(new StreamSource(pathXsd));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));
	}
	
}
