//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.05 at 01:31:28 PM CET 
//


package org.gestoresmadrid.oegamConversiones.jaxb.duplicado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}PROVINCIA" minOccurs="0"/>
 *         &lt;element ref="{}MUNICIPIO" minOccurs="0"/>
 *         &lt;element ref="{}PUEBLO" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_POSTAL" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_VIA" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_VIA" minOccurs="0"/>
 *         &lt;element ref="{}NUMERO" minOccurs="0"/>
 *         &lt;element ref="{}LETRA" minOccurs="0"/>
 *         &lt;element ref="{}ESCALERA" minOccurs="0"/>
 *         &lt;element ref="{}PISO" minOccurs="0"/>
 *         &lt;element ref="{}PUERTA" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE" minOccurs="0"/>
 *         &lt;element ref="{}KM" minOccurs="0"/>
 *         &lt;element ref="{}HM" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "DIRECCION")
public class DIRECCION {

    @XmlElement(name = "PROVINCIA")
    protected String provincia;
    @XmlElement(name = "MUNICIPIO")
    protected String municipio;
    @XmlElement(name = "PUEBLO")
    protected String pueblo;
    @XmlElement(name = "CODIGO_POSTAL")
    protected String codigopostal;
    @XmlElement(name = "TIPO_VIA")
    protected String tipovia;
    @XmlElement(name = "NOMBRE_VIA")
    protected String nombrevia;
    @XmlElement(name = "NUMERO")
    protected String numero;
    @XmlElement(name = "LETRA")
    protected String letra;
    @XmlElement(name = "ESCALERA")
    protected String escalera;
    @XmlElement(name = "PISO")
    protected String piso;
    @XmlElement(name = "PUERTA")
    protected String puerta;
    @XmlElement(name = "BLOQUE")
    protected String bloque;
    @XmlElement(name = "KM")
    protected String km;
    @XmlElement(name = "HM")
    protected String hm;

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIO() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIO(String value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the pueblo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLO() {
        return pueblo;
    }

    /**
     * Sets the value of the pueblo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLO(String value) {
        this.pueblo = value;
    }

    /**
     * Gets the value of the codigopostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOPOSTAL() {
        return codigopostal;
    }

    /**
     * Sets the value of the codigopostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOPOSTAL(String value) {
        this.codigopostal = value;
    }

    /**
     * Gets the value of the tipovia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOVIA() {
        return tipovia;
    }

    /**
     * Sets the value of the tipovia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOVIA(String value) {
        this.tipovia = value;
    }

    /**
     * Gets the value of the nombrevia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIA() {
        return nombrevia;
    }

    /**
     * Sets the value of the nombrevia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIA(String value) {
        this.nombrevia = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERO() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERO(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the letra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRA() {
        return letra;
    }

    /**
     * Sets the value of the letra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRA(String value) {
        this.letra = value;
    }

    /**
     * Gets the value of the escalera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERA() {
        return escalera;
    }

    /**
     * Sets the value of the escalera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERA(String value) {
        this.escalera = value;
    }

    /**
     * Gets the value of the piso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISO() {
        return piso;
    }

    /**
     * Sets the value of the piso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISO(String value) {
        this.piso = value;
    }

    /**
     * Gets the value of the puerta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTA() {
        return puerta;
    }

    /**
     * Sets the value of the puerta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTA(String value) {
        this.puerta = value;
    }

    /**
     * Gets the value of the bloque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUE() {
        return bloque;
    }

    /**
     * Sets the value of the bloque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUE(String value) {
        this.bloque = value;
    }

    /**
     * Gets the value of the km property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKM() {
        return km;
    }

    /**
     * Sets the value of the km property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKM(String value) {
        this.km = value;
    }

    /**
     * Gets the value of the hm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHM() {
        return hm;
    }

    /**
     * Sets the value of the hm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHM(String value) {
        this.hm = value;
    }

}
