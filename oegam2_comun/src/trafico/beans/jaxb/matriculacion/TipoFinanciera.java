//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.16 at 11:00:46 AM CEST 
//


package trafico.beans.jaxb.matriculacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoFinanciera.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoFinanciera">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EXENTO PR IEDMT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoFinanciera")
@XmlEnum
public enum TipoFinanciera {

    @XmlEnumValue("EXENTO PR IEDMT")
    EXENTO_PR_IEDMT("EXENTO PR IEDMT");
    private final String value;

    TipoFinanciera(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoFinanciera fromValue(String v) {
        for (TipoFinanciera c: TipoFinanciera.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
