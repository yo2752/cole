//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.04 at 01:50:00 PM CEST 
//


package org.gestoresmadrid.oegamConversiones.jaxb.consultaEitv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoCertificacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoCertificacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="autorizado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contrase�a" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lugarfirma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechafirma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmante" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sociedadinscrita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoCertificacion", propOrder = {

})
public class TipoCertificacion {

    @XmlElement(required = true)
    protected String autorizado;
    @XmlElement(required = true)
    protected String contraseña;
    @XmlElement(required = true)
    protected String lugarfirma;
    @XmlElement(required = true)
    protected String fechafirma;
    @XmlElement(required = true)
    protected String firmante;
    @XmlElement(required = true)
    protected String sociedadinscrita;

    /**
     * Gets the value of the autorizado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutorizado() {
        return autorizado;
    }

    /**
     * Sets the value of the autorizado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizado(String value) {
        this.autorizado = value;
    }

    /**
     * Gets the value of the contrase�a property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Sets the value of the contrase�a property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContraseña(String value) {
        this.contraseña = value;
    }

    /**
     * Gets the value of the lugarfirma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarfirma() {
        return lugarfirma;
    }

    /**
     * Sets the value of the lugarfirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarfirma(String value) {
        this.lugarfirma = value;
    }

    /**
     * Gets the value of the fechafirma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechafirma() {
        return fechafirma;
    }

    /**
     * Sets the value of the fechafirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechafirma(String value) {
        this.fechafirma = value;
    }

    /**
     * Gets the value of the firmante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmante() {
        return firmante;
    }

    /**
     * Sets the value of the firmante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmante(String value) {
        this.firmante = value;
    }

    /**
     * Gets the value of the sociedadinscrita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSociedadinscrita() {
        return sociedadinscrita;
    }

    /**
     * Sets the value of the sociedadinscrita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSociedadinscrita(String value) {
        this.sociedadinscrita = value;
    }

}
