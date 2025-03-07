package es.oegam.eeffWS.util;

import java.io.IOException;
import java.io.Writer;

import org.apache.axis.components.encoding.AbstractXMLEncoder;
import org.apache.axis.components.encoding.XMLEncoderFactory;
import org.apache.axis.i18n.Messages;

/**
 * Esta clase implementa el XMLEncoder que sustituye al de por defecto para
 * UTF-8. Este encoder tiene en cuenta si la cadena es de tipo CDATA para no
 * codificar los caracteres especiales
 * 
 * Para usar esta clase, se debe crear un fichero
 * org.apache.axis.components.encoding.XMLEncoder dentro de META-INF/services,
 * que contenga el nombre completo de esta clase (paquete+clase)
 */
public class CustomXMLEncoder extends AbstractXMLEncoder {
    protected static final String AMP = "&amp;";
    protected static final String QUOTE = "&quot;";
    protected static final String LESS = "&lt;";
    protected static final String GREATER = "&gt;";
    protected static final String LF = "\n";
    protected static final String CR = "\r";
    protected static final String TAB = "\t";

    /**
     * gets the encoding supported by this encoder
     * 
     * @return string
     */
    public String getEncoding() {
        return XMLEncoderFactory.ENCODING_UTF_8;
    }

    /**
     * write the encoded version of a given string
     * 
     * @param writer    writer to write this string to
     * @param xmlString string to be encoded
     */
    public void writeEncoded(Writer writer, String xmlString)
            throws IOException {
        if (xmlString == null) {
            return;
        }
        boolean noEscape = false;
        if (xmlString.startsWith("<![CDATA[") && xmlString.endsWith("]]>")) {
        	noEscape = true;
        }
        int length = xmlString.length();
        char character;
        for (int i = 0; i < length; i++) {
            character = xmlString.charAt( i );
            switch (character) {
                // we don't care about single quotes since axis will
                // use double quotes anyway
                case '&':
                    writer.write(AMP);
                    break;
                case '"':
                	writer.write(character);
//                    if(noEscape) {
//                		writer.write(character);
//                	} else {
//                		writer.write(QUOTE);
//                		
//                	}
                    break;
                case '<':
                	writer.write(character);
//                	if(noEscape) {
//                		writer.write(character);
//                	//	writer.write(LESS);
//                	} else {
//                	//	writer.write(LESS);
//                		writer.write(character);
//                	}
                    break;
                case '>':
                	writer.write(character);
//                	if(noEscape) {
//                		writer.write(character);
//                	//	writer.write(GREATER);
//                	} else {
//                	//	writer.write(GREATER);
//                		writer.write(character);
//                	}
                    break;
                case '\n':
                    writer.write(LF);
                    break;
                case '\r':
                    writer.write(CR);
                    break;
                case '\t':
                    writer.write(TAB);
                    break;
                default:
                    if (character < 0x20) {
                        throw new IllegalArgumentException(Messages.getMessage(
                                "invalidXmlCharacter00",
                                Integer.toHexString(character),
                                xmlString.substring(0, i)));
                    } else if (character > 0x7F) {
                        writer.write("&#x");
                        writer.write(Integer.toHexString(character).toUpperCase());
                        writer.write(";");
                    } else {
                        writer.write(character);
                    }
                    break;
            }
        }
    }
}
