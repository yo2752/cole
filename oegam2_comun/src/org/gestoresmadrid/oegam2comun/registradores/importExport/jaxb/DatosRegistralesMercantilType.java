//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.29 at 01:11:02 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Datos_Registrales_MercantilType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Datos_Registrales_MercantilType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Codigo_R_Mercantil" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}Tipificacion_RegistroType">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Tomo_R_Mercantil" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Folio_R_Mercantil" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Inscripcion_R_Mercantil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datos_Registrales_MercantilType", propOrder = {
    "codigoRMercantil",
    "tomoRMercantil",
    "folioRMercantil",
    "inscripcionRMercantil"
})
public class DatosRegistralesMercantilType {

    @XmlElement(name = "Codigo_R_Mercantil")
    protected String codigoRMercantil;
    @XmlElement(name = "Tomo_R_Mercantil")
    protected String tomoRMercantil;
    @XmlElement(name = "Folio_R_Mercantil")
    protected String folioRMercantil;
    @XmlElement(name = "Inscripcion_R_Mercantil")
    protected String inscripcionRMercantil;

    /**
     * Gets the value of the codigoRMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoRMercantil() {
        return codigoRMercantil;
    }

    /**
     * Sets the value of the codigoRMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoRMercantil(String value) {
        this.codigoRMercantil = value;
    }

    /**
     * Gets the value of the tomoRMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTomoRMercantil() {
        return tomoRMercantil;
    }

    /**
     * Sets the value of the tomoRMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTomoRMercantil(String value) {
        this.tomoRMercantil = value;
    }

    /**
     * Gets the value of the folioRMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioRMercantil() {
        return folioRMercantil;
    }

    /**
     * Sets the value of the folioRMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioRMercantil(String value) {
        this.folioRMercantil = value;
    }

    /**
     * Gets the value of the inscripcionRMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscripcionRMercantil() {
        return inscripcionRMercantil;
    }

    /**
     * Sets the value of the inscripcionRMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscripcionRMercantil(String value) {
        this.inscripcionRMercantil = value;
    }

}
