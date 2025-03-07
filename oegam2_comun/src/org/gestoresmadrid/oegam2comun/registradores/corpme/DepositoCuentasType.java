//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.01 at 05:01:41 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.corpme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Deposito_CuentasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Deposito_CuentasType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ejercicio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Clase_Cuentas" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Depositante" type="{}DepositanteType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Deposito_CuentasType", propOrder = {
    "ejercicio",
    "claseCuentas",
    "depositante"
})
public class DepositoCuentasType {

    @XmlElement(name = "Ejercicio", required = true)
    protected String ejercicio;
    @XmlElement(name = "Clase_Cuentas", required = true)
    protected String claseCuentas;
    @XmlElement(name = "Depositante", required = true)
    protected DepositanteType depositante;

    /**
     * Gets the value of the ejercicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEjercicio() {
        return ejercicio;
    }

    /**
     * Sets the value of the ejercicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEjercicio(String value) {
        this.ejercicio = value;
    }

    /**
     * Gets the value of the claseCuentas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseCuentas() {
        return claseCuentas;
    }

    /**
     * Sets the value of the claseCuentas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseCuentas(String value) {
        this.claseCuentas = value;
    }

    /**
     * Gets the value of the depositante property.
     * 
     * @return
     *     possible object is
     *     {@link DepositanteType }
     *     
     */
    public DepositanteType getDepositante() {
        return depositante;
    }

    /**
     * Sets the value of the depositante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositanteType }
     *     
     */
    public void setDepositante(DepositanteType value) {
        this.depositante = value;
    }

}
