//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:31 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for id_etapa.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="id_etapa">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALSO"/>
 *     &lt;enumeration value="EMCC"/>
 *     &lt;enumeration value="VALI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "id_etapa", namespace = "http://licencias/peticionGenerica")
@XmlEnum
public enum IdEtapa {

    ALSO,
    EMCC,
    VALI;

    public String value() {
        return name();
    }

    public static IdEtapa fromValue(String v) {
        return valueOf(v);
    }

}
