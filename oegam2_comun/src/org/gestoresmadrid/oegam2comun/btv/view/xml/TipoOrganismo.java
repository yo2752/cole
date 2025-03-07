package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "tipoOrganismo")
@XmlEnum
public enum TipoOrganismo {
	
	  DGT;

	    public String value() {
	        return name();
	    }

	    public static TipoOrganismo fromValue(String v) {
	        return valueOf(v);
	    }

}
