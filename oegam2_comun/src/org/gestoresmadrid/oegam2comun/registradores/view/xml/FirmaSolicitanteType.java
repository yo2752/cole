//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.27 at 10:57:32 AM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Firma_SolicitanteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Firma_SolicitanteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Lugar_Firma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fecha_Firma" type="{}FechaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Firma_SolicitanteType", propOrder = {
    "lugarFirma",
    "fechaFirma"
})
public class FirmaSolicitanteType {

    @XmlElement(name = "Lugar_Firma", required = true)
    protected String lugarFirma;
    @XmlElement(name = "Fecha_Firma", required = true)
    protected String fechaFirma;

    /**
     * Gets the value of the lugarFirma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarFirma() {
        return lugarFirma;
    }

    /**
     * Sets the value of the lugarFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarFirma(String value) {
        this.lugarFirma = value;
    }

    /**
     * Gets the value of the fechaFirma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFirma() {
        return fechaFirma;
    }

    /**
     * Sets the value of the fechaFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFirma(String value) {
        this.fechaFirma = value;
    }

}
