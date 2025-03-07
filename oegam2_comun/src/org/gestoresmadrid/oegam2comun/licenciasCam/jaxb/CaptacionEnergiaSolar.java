//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:49 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for captacion_energia_solar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="captacion_energia_solar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="captacion_energia_solar" type="{http://licencias/mensajes}string2type"/>
 *         &lt;element name="equipos_uso_termico" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="num_equipos_uso_termico" type="{http://licencias/mensajes}int3type" minOccurs="0"/>
 *         &lt;element name="superficie_captacion_equipos_uso_termico" type="{http://licencias/mensajes}decimal102type" minOccurs="0"/>
 *         &lt;element name="equipos_fotovoltaicos" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="num_equipos_fotovoltaicos" type="{http://licencias/mensajes}int3type" minOccurs="0"/>
 *         &lt;element name="superficie_captacion_equipos_fotovoltaicos" type="{http://licencias/mensajes}decimal102type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "captacion_energia_solar", propOrder = {
    "captacionEnergiaSolar",
    "equiposUsoTermico",
    "numEquiposUsoTermico",
    "superficieCaptacionEquiposUsoTermico",
    "equiposFotovoltaicos",
    "numEquiposFotovoltaicos",
    "superficieCaptacionEquiposFotovoltaicos"
})
public class CaptacionEnergiaSolar {

    @XmlElement(name = "captacion_energia_solar", required = true)
    protected String captacionEnergiaSolar;
    @XmlElement(name = "equipos_uso_termico")
    protected String equiposUsoTermico;
    @XmlElement(name = "num_equipos_uso_termico")
    protected BigInteger numEquiposUsoTermico;
    @XmlElement(name = "superficie_captacion_equipos_uso_termico")
    protected BigDecimal superficieCaptacionEquiposUsoTermico;
    @XmlElement(name = "equipos_fotovoltaicos")
    protected String equiposFotovoltaicos;
    @XmlElement(name = "num_equipos_fotovoltaicos")
    protected BigInteger numEquiposFotovoltaicos;
    @XmlElement(name = "superficie_captacion_equipos_fotovoltaicos")
    protected BigDecimal superficieCaptacionEquiposFotovoltaicos;

    /**
     * Gets the value of the captacionEnergiaSolar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaptacionEnergiaSolar() {
        return captacionEnergiaSolar;
    }

    /**
     * Sets the value of the captacionEnergiaSolar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaptacionEnergiaSolar(String value) {
        this.captacionEnergiaSolar = value;
    }

    /**
     * Gets the value of the equiposUsoTermico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposUsoTermico() {
        return equiposUsoTermico;
    }

    /**
     * Sets the value of the equiposUsoTermico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposUsoTermico(String value) {
        this.equiposUsoTermico = value;
    }

    /**
     * Gets the value of the numEquiposUsoTermico property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumEquiposUsoTermico() {
        return numEquiposUsoTermico;
    }

    /**
     * Sets the value of the numEquiposUsoTermico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumEquiposUsoTermico(BigInteger value) {
        this.numEquiposUsoTermico = value;
    }

    /**
     * Gets the value of the superficieCaptacionEquiposUsoTermico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSuperficieCaptacionEquiposUsoTermico() {
        return superficieCaptacionEquiposUsoTermico;
    }

    /**
     * Sets the value of the superficieCaptacionEquiposUsoTermico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSuperficieCaptacionEquiposUsoTermico(BigDecimal value) {
        this.superficieCaptacionEquiposUsoTermico = value;
    }

    /**
     * Gets the value of the equiposFotovoltaicos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposFotovoltaicos() {
        return equiposFotovoltaicos;
    }

    /**
     * Sets the value of the equiposFotovoltaicos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposFotovoltaicos(String value) {
        this.equiposFotovoltaicos = value;
    }

    /**
     * Gets the value of the numEquiposFotovoltaicos property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumEquiposFotovoltaicos() {
        return numEquiposFotovoltaicos;
    }

    /**
     * Sets the value of the numEquiposFotovoltaicos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumEquiposFotovoltaicos(BigInteger value) {
        this.numEquiposFotovoltaicos = value;
    }

    /**
     * Gets the value of the superficieCaptacionEquiposFotovoltaicos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSuperficieCaptacionEquiposFotovoltaicos() {
        return superficieCaptacionEquiposFotovoltaicos;
    }

    /**
     * Sets the value of the superficieCaptacionEquiposFotovoltaicos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSuperficieCaptacionEquiposFotovoltaicos(BigDecimal value) {
        this.superficieCaptacionEquiposFotovoltaicos = value;
    }

}
