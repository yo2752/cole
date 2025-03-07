//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:49 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for equipos_inst_relevantes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="equipos_inst_relevantes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hay_instalaciones_relevantes" type="{http://licencias/mensajes}string1type"/>
 *         &lt;element name="equipos_inst_radioactivas" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="equipos_rayos_uva" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="equipos_rayos_laser" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="potencia_equipos_rayos_laser" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="equipos_audiovisuales" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "equipos_inst_relevantes", propOrder = {
    "hayInstalacionesRelevantes",
    "equiposInstRadioactivas",
    "equiposRayosUva",
    "equiposRayosLaser",
    "potenciaEquiposRayosLaser",
    "equiposAudiovisuales"
})
@XmlSeeAlso({
    EquiposInstRelevantesOrdinario.class
})
public class EquiposInstRelevantes {

    @XmlElement(name = "hay_instalaciones_relevantes", required = true)
    protected String hayInstalacionesRelevantes;
    @XmlElement(name = "equipos_inst_radioactivas")
    protected String equiposInstRadioactivas;
    @XmlElement(name = "equipos_rayos_uva")
    protected String equiposRayosUva;
    @XmlElement(name = "equipos_rayos_laser")
    protected String equiposRayosLaser;
    @XmlElement(name = "potencia_equipos_rayos_laser")
    protected BigDecimal potenciaEquiposRayosLaser;
    @XmlElement(name = "equipos_audiovisuales")
    protected String equiposAudiovisuales;

    /**
     * Gets the value of the hayInstalacionesRelevantes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHayInstalacionesRelevantes() {
        return hayInstalacionesRelevantes;
    }

    /**
     * Sets the value of the hayInstalacionesRelevantes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHayInstalacionesRelevantes(String value) {
        this.hayInstalacionesRelevantes = value;
    }

    /**
     * Gets the value of the equiposInstRadioactivas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposInstRadioactivas() {
        return equiposInstRadioactivas;
    }

    /**
     * Sets the value of the equiposInstRadioactivas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposInstRadioactivas(String value) {
        this.equiposInstRadioactivas = value;
    }

    /**
     * Gets the value of the equiposRayosUva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposRayosUva() {
        return equiposRayosUva;
    }

    /**
     * Sets the value of the equiposRayosUva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposRayosUva(String value) {
        this.equiposRayosUva = value;
    }

    /**
     * Gets the value of the equiposRayosLaser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposRayosLaser() {
        return equiposRayosLaser;
    }

    /**
     * Sets the value of the equiposRayosLaser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposRayosLaser(String value) {
        this.equiposRayosLaser = value;
    }

    /**
     * Gets the value of the potenciaEquiposRayosLaser property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaEquiposRayosLaser() {
        return potenciaEquiposRayosLaser;
    }

    /**
     * Sets the value of the potenciaEquiposRayosLaser property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaEquiposRayosLaser(BigDecimal value) {
        this.potenciaEquiposRayosLaser = value;
    }

    /**
     * Gets the value of the equiposAudiovisuales property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquiposAudiovisuales() {
        return equiposAudiovisuales;
    }

    /**
     * Sets the value of the equiposAudiovisuales property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquiposAudiovisuales(String value) {
        this.equiposAudiovisuales = value;
    }

}
