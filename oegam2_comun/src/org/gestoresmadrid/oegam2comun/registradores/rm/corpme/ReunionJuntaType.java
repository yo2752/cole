//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.15 at 04:00:13 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.rm.corpme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Reunion_JuntaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reunion_JuntaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LugaryFecha_Celebracion" type="{}LugaryFechaType"/>
 *         &lt;element name="Tipo" type="{}Tipo_JuntaType"/>
 *         &lt;element name="Caracter" type="{}Caracter_JuntaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reunion_JuntaType", propOrder = {
    "lugaryFechaCelebracion",
    "tipo",
    "caracter"
})
public class ReunionJuntaType {

    @XmlElement(name = "LugaryFecha_Celebracion", required = true)
    protected LugaryFechaType lugaryFechaCelebracion;
    @XmlElement(name = "Tipo", required = true)
    protected String tipo;
    @XmlElement(name = "Caracter", required = true)
    protected String caracter;

    /**
     * Gets the value of the lugaryFechaCelebracion property.
     * 
     * @return
     *     possible object is
     *     {@link LugaryFechaType }
     *     
     */
    public LugaryFechaType getLugaryFechaCelebracion() {
        return lugaryFechaCelebracion;
    }

    /**
     * Sets the value of the lugaryFechaCelebracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link LugaryFechaType }
     *     
     */
    public void setLugaryFechaCelebracion(LugaryFechaType value) {
        this.lugaryFechaCelebracion = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the caracter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaracter() {
        return caracter;
    }

    /**
     * Sets the value of the caracter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaracter(String value) {
        this.caracter = value;
    }

}
