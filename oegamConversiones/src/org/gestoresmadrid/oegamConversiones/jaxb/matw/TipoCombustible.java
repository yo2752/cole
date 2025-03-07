//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.05 at 02:44:47 PM CET 
//


package org.gestoresmadrid.oegamConversiones.jaxb.matw;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoCombustible.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoCombustible">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="GLP"/>
 *     &lt;enumeration value="GNC"/>
 *     &lt;enumeration value="GNL"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="BM"/>
 *     &lt;enumeration value="ET"/>
 *     &lt;enumeration value="BD"/>
 *     &lt;enumeration value="O"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoCombustible")
@XmlEnum
public enum TipoCombustible {

	G("G"),
    D("D"),
    E("E"),
    GLP("GLP"),
    GNC("GNC"),
    GNL("GNL"),
    H("H"),
    BM("BM"),
    ET("ET"),
    BD("BD"),
    @XmlEnumValue("G/E")
    GE("G/E"),
    @XmlEnumValue("G/GLP")
    GGLP("G/GLP"),
    @XmlEnumValue("GLP/G")
    GLPG("GLP/G"),
    @XmlEnumValue("G/GNC")
    GGNC("G/GNC"),
	@XmlEnumValue("BD/D")
    BDD("BD/D"),
	@XmlEnumValue("D/BD")
    DBD("D/BD"),
    O("O");
    
    private final String value;
    
    TipoCombustible(String v) {
        value = v;
    }

    public String value() {
        return name();
    }

	public static TipoCombustible fromValue(String v) {
		for (TipoCombustible c : TipoCombustible.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
