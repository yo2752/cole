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
 * <p>Java class for VendedorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VendedorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Iden_Sujeto" type="{}Iden_SujetoType" minOccurs="0"/>
 *         &lt;element name="Domicilio" type="{}Domicilio_INEType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendedorType", propOrder = {
    "idenSujeto",
    "domicilio"
})
public class VendedorType {

    @XmlElement(name = "Iden_Sujeto")
    protected IdenSujetoType idenSujeto;
    @XmlElement(name = "Domicilio")
    protected DomicilioINEType domicilio;

    /**
     * Gets the value of the idenSujeto property.
     * 
     * @return
     *     possible object is
     *     {@link IdenSujetoType }
     *     
     */
    public IdenSujetoType getIdenSujeto() {
        return idenSujeto;
    }

    /**
     * Sets the value of the idenSujeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdenSujetoType }
     *     
     */
    public void setIdenSujeto(IdenSujetoType value) {
        this.idenSujeto = value;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link DomicilioINEType }
     *     
     */
    public DomicilioINEType getDomicilio() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomicilioINEType }
     *     
     */
    public void setDomicilio(DomicilioINEType value) {
        this.domicilio = value;
    }

}
