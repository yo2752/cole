//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.09 at 05:22:13 PM CEST 
//


package org.gestoresmadrid.oegam2comun.sega.btv.view.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoTextoLegal.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoTextoLegal">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Este Colegio de Gestores Administrativos ha verificado que la solicitud de baja definitiva por Exportaci�n o Tr�nsito Comunitario presentada cumple todas las obligaciones establecidas en el Reglamento General de Veh�culos y resto de normativa aplicable, as� como las derivadas de la aplicaci�n de las Instrucciones de la Direcci�n General de Tr�fico."/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
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
    ESTE_COLEGIO_DE_GESTORES_ADMINISTRATIVOS_HA_VERIFICADO_QUE_LA_SOLICITUD_DE_BAJA_DEFINITIVA_POR_EXPORTACI�N_O_TR�NSITO_COMUNITARIO_PRESENTADA_CUMPLE_TODAS_LAS_OBLIGACIONES_ESTABLECIDAS_EN_EL_REGLAMENTO_GENERAL_DE_VEH�CULOS_Y_RESTO_DE_NORMATIVA_APLICABLE_AS�_COMO_LAS_DERIVADAS_DE_LA_APLICACI�N_DE_LAS_INSTRUCCIONES_DE_LA_DIRECCI�N_GENERAL_DE_TR�FICO_VIGENTES_EN_EL_MOMENTO_DE_LA_SOLICITUD_ENTRE_ELLAS_LA_OBLIGACI�N_DE_LIQUIDAR_ANTE_EL_DEPARTAMENTO_DE_ADUANAS_LA_LEGAL_EXPORTACI�N_DEL_VEH�CULO_EN_EL_CASO_DE_BAJAS_DEFINITIVAS_POR_EXPORTACI�N_DUA("Este Colegio de Gestores Administrativos "
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
