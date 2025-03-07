package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


	@XmlType(name = "codigo")
	@XmlEnum
	public enum CodigoDestino {
		
		@XmlEnumValue("101001")
	
		CODIGO("101001");
		private final String value;
		
		CodigoDestino(String v) {
			value = v;
		}
		
		public String value() {
			return value;
		}
		
		public static CodigoDestino fromValue(String v) {
			for (CodigoDestino c: CodigoDestino.values()) {
			    if (c.value.equals(v)) {
			        return c;
			    }
			}
			throw new IllegalArgumentException(v);
		}
	
	}
