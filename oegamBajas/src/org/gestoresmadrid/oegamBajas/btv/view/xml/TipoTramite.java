package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "tipoTramite")
@XmlEnum
public enum TipoTramite {
	
	@XmlEnumValue("4")
	TIPO_TRAMITE("4");
	
	private final String value;
	
	TipoTramite(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoTramite fromValue(String v) {
        for (TipoTramite c: TipoTramite.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}