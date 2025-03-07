//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.01 at 05:01:41 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.corpme;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Numero_Entrada_LiquidadoraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Numero_Entrada_LiquidadoraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoImpuesto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;pattern value="1"/>
 *               &lt;pattern value="2"/>
 *               &lt;pattern value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Anyo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NumeroExpediente" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NumeroPresentacion" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="NumeroComplementaria" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Numero_Entrada_LiquidadoraType", propOrder = {
    "tipoImpuesto",
    "anyo",
    "numeroExpediente",
    "numeroPresentacion",
    "numeroComplementaria"
})
public class NumeroEntradaLiquidadoraType {

    @XmlElement(name = "TipoImpuesto", required = true)
    protected BigInteger tipoImpuesto;
    @XmlElement(name = "Anyo", required = true)
    protected BigInteger anyo;
    @XmlElement(name = "NumeroExpediente", required = true)
    protected BigInteger numeroExpediente;
    @XmlElement(name = "NumeroPresentacion", required = true)
    protected BigInteger numeroPresentacion;
    @XmlElement(name = "NumeroComplementaria", required = true)
    protected BigInteger numeroComplementaria;

    /**
     * Gets the value of the tipoImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTipoImpuesto() {
        return tipoImpuesto;
    }

    /**
     * Sets the value of the tipoImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTipoImpuesto(BigInteger value) {
        this.tipoImpuesto = value;
    }

    /**
     * Gets the value of the anyo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAnyo() {
        return anyo;
    }

    /**
     * Sets the value of the anyo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnyo(BigInteger value) {
        this.anyo = value;
    }

    /**
     * Gets the value of the numeroExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * Sets the value of the numeroExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroExpediente(BigInteger value) {
        this.numeroExpediente = value;
    }

    /**
     * Gets the value of the numeroPresentacion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroPresentacion() {
        return numeroPresentacion;
    }

    /**
     * Sets the value of the numeroPresentacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroPresentacion(BigInteger value) {
        this.numeroPresentacion = value;
    }

    /**
     * Gets the value of the numeroComplementaria property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroComplementaria() {
        return numeroComplementaria;
    }

    /**
     * Sets the value of the numeroComplementaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroComplementaria(BigInteger value) {
        this.numeroComplementaria = value;
    }

}
