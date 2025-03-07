package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "descripcion")
@XmlEnum
public enum DescripcionAsunto {

	
	@XmlEnumValue("Solicitud de Baja Telem\u00E1tica por Exportaci\u00F3n o Tr\u00E1nsito Comunitario")
	DESCRIPCION("Solicitud de Baja Telem\u00E1tica por Exportaci\u00F3n o Tr\u00E1nsito Comunitario");
	
	private final String value;
	
	DescripcionAsunto(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DescripcionAsunto fromValue(String v) {
        for (DescripcionAsunto c: DescripcionAsunto.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}