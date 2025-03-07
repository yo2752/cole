package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "codigo")
@XmlEnum
public enum CodigoAsunto {
	
	BTETC;

	    public String value() {
	        return name();
	    }

	    public static CodigoAsunto fromValue(String v) {
	        return valueOf(v);
	    }

}