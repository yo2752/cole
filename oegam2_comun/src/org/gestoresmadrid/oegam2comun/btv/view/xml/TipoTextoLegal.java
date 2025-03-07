package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "tipoTextoLegal")
@XmlEnum
public enum TipoTextoLegal {

	@XmlEnumValue("Este Colegio de Gestores Administrativos "
    		+ "ha verificado que la solicitud de baja "
    		+ "definitiva por Exportaci\u00f3n o Tr\u00e1nsito "
    		+ "Comunitario presentada cumple con todas las "
    		+ "obligaciones "
    		+ "establecidas en el Reglamento General "
    		+ "de Veh\u00edculos y resto de la normativa "
    		+ "aplicable, as\u00ed como las derivadas de la "
    		+ "aplicaci\u00f3n de las Instrucciones de la "
    		+ "Direcci\u00f3n General de tr\u00e1fico vigentes en "
    		+ "el momento de la solicitud.")
    TEXTO_LEGAL("Este Colegio de Gestores Administrativos "
    		+ "ha verificado que la solicitud de baja "
    		+ "definitiva por Exportaci\u00f3n o Tr\u00e1nsito "
    		+ "Comunitario presentada cumple con todas las "
    		+ "obligaciones "
    		+ "establecidas en el Reglamento General "
    		+ "de Veh\u00edculos y resto de la normativa "
    		+ "aplicable, as\u00ed como las derivadas de la "
    		+ "aplicaci\u00f3n de las Instrucciones de la "
    		+ "Direcci\u00f3n General de tr\u00e1fico vigentes en "
    		+ "el momento de la solicitud.");
	
	public String getValue() {
				return value;
			}

	private final String value;
	
    TipoTextoLegal(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoTextoLegal fromValue(String v) {
        for (TipoTextoLegal c: TipoTextoLegal.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
