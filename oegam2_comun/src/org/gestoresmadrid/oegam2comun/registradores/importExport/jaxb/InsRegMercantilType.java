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
 * <p>Java class for Ins_Reg_MercantilType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ins_Reg_MercantilType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cod_Reg_Mercantil" type="{}Tipificacion_RegistroType" minOccurs="0"/>
 *         &lt;element name="Seccion_Mercantil" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Num_Hoja" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Num_Hoja_Dup" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Num_Sub_Hoja" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="Tomo_Sociedad" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Folio_Sociedad" type="{}NumericoType" minOccurs="0"/>
 *         &lt;element name="Ins_Sociedad" type="{}NumericoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ins_Reg_MercantilType", propOrder = {
    "codRegMercantil",
    "seccionMercantil",
    "numHoja",
    "numHojaDup",
    "numSubHoja",
    "tomoSociedad",
    "folioSociedad",
    "insSociedad"
})
public class InsRegMercantilType {

    @XmlElement(name = "Cod_Reg_Mercantil")
    protected String codRegMercantil;
    @XmlElement(name = "Seccion_Mercantil")
    protected String seccionMercantil;
    @XmlElement(name = "Num_Hoja")
    protected String numHoja;
    @XmlElement(name = "Num_Hoja_Dup")
    protected String numHojaDup;
    @XmlElement(name = "Num_Sub_Hoja")
    protected Object numSubHoja;
    @XmlElement(name = "Tomo_Sociedad")
    protected String tomoSociedad;
    @XmlElement(name = "Folio_Sociedad")
    protected String folioSociedad;
    @XmlElement(name = "Ins_Sociedad")
    protected String insSociedad;

    /**
     * Gets the value of the codRegMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRegMercantil() {
        return codRegMercantil;
    }

    /**
     * Sets the value of the codRegMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRegMercantil(String value) {
        this.codRegMercantil = value;
    }

    /**
     * Gets the value of the seccionMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeccionMercantil() {
        return seccionMercantil;
    }

    /**
     * Sets the value of the seccionMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeccionMercantil(String value) {
        this.seccionMercantil = value;
    }

    /**
     * Gets the value of the numHoja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumHoja() {
        return numHoja;
    }

    /**
     * Sets the value of the numHoja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumHoja(String value) {
        this.numHoja = value;
    }

    /**
     * Gets the value of the numHojaDup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumHojaDup() {
        return numHojaDup;
    }

    /**
     * Sets the value of the numHojaDup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumHojaDup(String value) {
        this.numHojaDup = value;
    }

    /**
     * Gets the value of the numSubHoja property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNumSubHoja() {
        return numSubHoja;
    }

    /**
     * Sets the value of the numSubHoja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNumSubHoja(Object value) {
        this.numSubHoja = value;
    }

    /**
     * Gets the value of the tomoSociedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTomoSociedad() {
        return tomoSociedad;
    }

    /**
     * Sets the value of the tomoSociedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTomoSociedad(String value) {
        this.tomoSociedad = value;
    }

    /**
     * Gets the value of the folioSociedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioSociedad() {
        return folioSociedad;
    }

    /**
     * Sets the value of the folioSociedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioSociedad(String value) {
        this.folioSociedad = value;
    }

    /**
     * Gets the value of the insSociedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsSociedad() {
        return insSociedad;
    }

    /**
     * Sets the value of the insSociedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsSociedad(String value) {
        this.insSociedad = value;
    }

}
