//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.16 at 10:50:21 AM CEST 
//


package trafico.beans.schemas.generated.electronicMate.es.dgt.vehiculos.tipos;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoMotivoExencion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoMotivoExencion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALQU"/>
 *     &lt;enumeration value="FAMN"/>
 *     &lt;enumeration value="MINU"/>
 *     &lt;enumeration value="TAXI"/>
 *     &lt;enumeration value="AESC"/>
 *     &lt;enumeration value="RESI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoMotivoExencion")
@XmlEnum
public enum TipoMotivoExencion {

    ALQU,
    FAMN,
    MINU,
    TAXI,
    AESC,
    RESI;

    public String value() {
        return name();
    }

    public static TipoMotivoExencion fromValue(String v) {
        return valueOf(v);
    }

}
