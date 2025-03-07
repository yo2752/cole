package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


	@XmlType(name = "descripcion")
	@XmlEnum
	public enum DescripcionDestino {
		
		//@XmlEnumValue("DGT - Veh�culos")
	
		//DESCRIPCION("DGT - Veh�culos");
		@XmlEnumValue("DGT - Veh\u00EDculos")
		
		DESCRIPCION("DGT - Veh\u00EDculos");
		
		private final String value;
		
		DescripcionDestino(String v) {
			value = v;
		}
		
		public String value() {
			return value;
		}
		
		public static DescripcionDestino fromValue(String v) {
			for (DescripcionDestino c: DescripcionDestino.values()) {
			    if (c.value.equals(v)) {
			        return c;
			    }
			}
			throw new IllegalArgumentException(v);
		}
	
	}
