//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.09 at 12:09:58 PM CEST 
//


package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoriaElectrica.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="categoriaElectrica">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BEV"/>
 *     &lt;enumeration value="PHEV"/>
 *     &lt;enumeration value="REEV"/>
 *     &lt;enumeration value="HEV"/>
 *     &lt;enumeration value="FCEV"/>
 *     &lt;enumeration value="HICEV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "categoriaElectrica")
@XmlEnum
public enum CategoriaElectrica {

    BEV,
    PHEV,
    REEV,
    HEV,
    FCEV,
    HICEV;

    public String value() {
        return name();
    }

    public static CategoriaElectrica fromValue(String v) {
        return valueOf(v);
    }

}
