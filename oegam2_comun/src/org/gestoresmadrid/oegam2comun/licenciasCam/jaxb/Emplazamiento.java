//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:49 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for emplazamiento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="emplazamiento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="direccion_emplazamiento_principal" type="{http://licencias/mensajes}direccion_corta"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emplazamiento", propOrder = {
    "direccionEmplazamientoPrincipal"
})
public class Emplazamiento {

    @XmlElement(name = "direccion_emplazamiento_principal", required = true)
    protected DireccionCorta direccionEmplazamientoPrincipal;

    /**
     * Gets the value of the direccionEmplazamientoPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link DireccionCorta }
     *     
     */
    public DireccionCorta getDireccionEmplazamientoPrincipal() {
        return direccionEmplazamientoPrincipal;
    }

    /**
     * Sets the value of the direccionEmplazamientoPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link DireccionCorta }
     *     
     */
    public void setDireccionEmplazamientoPrincipal(DireccionCorta value) {
        this.direccionEmplazamientoPrincipal = value;
    }

}
