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
 * <p>Java class for AcuerdosType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcuerdosType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nombramientos" type="{}NombramientosType" minOccurs="0"/>
 *         &lt;element name="Ceses" type="{}CesesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcuerdosType", propOrder = {
    "nombramientos",
    "ceses"
})
public class AcuerdosType {

    @XmlElement(name = "Nombramientos")
    protected NombramientosType nombramientos;
    @XmlElement(name = "Ceses")
    protected CesesType ceses;

    /**
     * Gets the value of the nombramientos property.
     * 
     * @return
     *     possible object is
     *     {@link NombramientosType }
     *     
     */
    public NombramientosType getNombramientos() {
        return nombramientos;
    }

    /**
     * Sets the value of the nombramientos property.
     * 
     * @param value
     *     allowed object is
     *     {@link NombramientosType }
     *     
     */
    public void setNombramientos(NombramientosType value) {
        this.nombramientos = value;
    }

    /**
     * Gets the value of the ceses property.
     * 
     * @return
     *     possible object is
     *     {@link CesesType }
     *     
     */
    public CesesType getCeses() {
        return ceses;
    }

    /**
     * Sets the value of the ceses property.
     * 
     * @param value
     *     allowed object is
     *     {@link CesesType }
     *     
     */
    public void setCeses(CesesType value) {
        this.ceses = value;
    }

}
