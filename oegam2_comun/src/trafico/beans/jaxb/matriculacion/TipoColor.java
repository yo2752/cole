
package trafico.beans.jaxb.matriculacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoColor.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoColor">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="2"/>
 *     &lt;enumeration value="AM"/>
 *     &lt;enumeration value="AZ"/>
 *     &lt;enumeration value="BE"/>
 *     &lt;enumeration value="BL"/>
 *     &lt;enumeration value="GR"/>
 *     &lt;enumeration value="MA"/>
 *     &lt;enumeration value="NA"/>
 *     &lt;enumeration value="NE"/>
 *     &lt;enumeration value="RO"/>
 *     &lt;enumeration value="RS"/>
 *     &lt;enumeration value="VE"/>
 *     &lt;enumeration value="VI"/>
 *     &lt;enumeration value="ND"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoColor")
@XmlEnum
public enum TipoColor {

    AM,
    AZ,
    BE,
    BL,
    GR,
    MA,
    NA,
    NE,
    RO,
    RS,
    VE,
    VI,
    ND;

    public String value() {
        return name();
    }

    public static TipoColor fromValue(String v) {
        return valueOf(v);
    }

}
