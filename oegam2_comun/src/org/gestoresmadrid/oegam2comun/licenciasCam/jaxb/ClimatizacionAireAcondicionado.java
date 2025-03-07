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
 * <p>Java class for climatizacion_aire_acondicionado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="climatizacion_aire_acondicionado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="climatizacion_aire_acondicionado" type="{http://licencias/mensajes}string2type"/>
 *         &lt;element name="instalaciones_general_edificio" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="potencia_calorifica_inst_gen" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="potencia_frigorifica_inst_gen" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="equipo_centralizado_local" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="potencia_calorifica_eq_centr" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="potencia_frigorifica_eq_centr" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="hay_equipos_autonomos" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="equipos_autonomos" type="{http://licencias/mensajes}int4type" minOccurs="0"/>
 *         &lt;element name="potencia_calorifica_eq_autonomos" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="potencia_frigorifica_eq_autonomos" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="hay_enfriadores_evaporativos" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="enfriadores_evaporativos" type="{http://licencias/mensajes}int4type" minOccurs="0"/>
 *         &lt;element name="potencia_calorifica_enfriadores" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="potencia_frigorifica_enfriadores" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="condensacion_aire_salida_fachada" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="caudal_salida_fachada" type="{http://licencias/mensajes}decimal42type" minOccurs="0"/>
 *         &lt;element name="condensacion_aire_salida_chimenea" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="caudal_salida_cubierta" type="{http://licencias/mensajes}decimal42type" minOccurs="0"/>
 *         &lt;element name="condensador_situado_cubierta" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="caudal_condensador_cubierta" type="{http://licencias/mensajes}decimal42type" minOccurs="0"/>
 *         &lt;element name="torre_refrigeracion" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="num_torres_refrigeracion" type="{http://licencias/mensajes}int2type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "climatizacion_aire_acondicionado", propOrder = {
    "climatizacionAireAcondicionado",
    "instalacionesGeneralEdificio",
    "potenciaCalorificaInstGen",
    "potenciaFrigorificaInstGen",
    "equipoCentralizadoLocal",
    "potenciaCalorificaEqCentr",
    "potenciaFrigorificaEqCentr",
    "hayEquiposAutonomos",
    "equiposAutonomos",
    "potenciaCalorificaEqAutonomos",
    "potenciaFrigorificaEqAutonomos",
    "hayEnfriadoresEvaporativos",
    "enfriadoresEvaporativos",
    "potenciaCalorificaEnfriadores",
    "potenciaFrigorificaEnfriadores",
    "condensacionAireSalidaFachada",
    "caudalSalidaFachada",
    "condensacionAireSalidaChimenea",
    "caudalSalidaCubierta",
    "condensadorSituadoCubierta",
    "caudalCondensadorCubierta",
    "torreRefrigeracion",
    "numTorresRefrigeracion"
})
public class ClimatizacionAireAcondicionado {

    @XmlElement(name = "climatizacion_aire_acondicionado", required = true)
    protected String climatizacionAireAcondicionado;
    @XmlElement(name = "instalaciones_general_edificio")
    protected String instalacionesGeneralEdificio;
    @XmlElement(name = "potencia_calorifica_inst_gen")
    protected BigDecimal potenciaCalorificaInstGen;
    @XmlElement(name = "potencia_frigorifica_inst_gen")
    protected BigDecimal potenciaFrigorificaInstGen;
    @XmlElement(name = "equipo_centralizado_local")
    protected String equipoCentralizadoLocal;
    @XmlElement(name = "potencia_calorifica_eq_centr")
    protected BigDecimal potenciaCalorificaEqCentr;
    @XmlElement(name = "potencia_frigorifica_eq_centr")
    protected BigDecimal potenciaFrigorificaEqCentr;
    @XmlElement(name = "hay_equipos_autonomos")
    protected String hayEquiposAutonomos;
    @XmlElement(name = "equipos_autonomos")
    protected BigInteger equiposAutonomos;
    @XmlElement(name = "potencia_calorifica_eq_autonomos")
    protected BigDecimal potenciaCalorificaEqAutonomos;
    @XmlElement(name = "potencia_frigorifica_eq_autonomos")
    protected BigDecimal potenciaFrigorificaEqAutonomos;
    @XmlElement(name = "hay_enfriadores_evaporativos")
    protected String hayEnfriadoresEvaporativos;
    @XmlElement(name = "enfriadores_evaporativos")
    protected BigInteger enfriadoresEvaporativos;
    @XmlElement(name = "potencia_calorifica_enfriadores")
    protected BigDecimal potenciaCalorificaEnfriadores;
    @XmlElement(name = "potencia_frigorifica_enfriadores")
    protected BigDecimal potenciaFrigorificaEnfriadores;
    @XmlElement(name = "condensacion_aire_salida_fachada")
    protected String condensacionAireSalidaFachada;
    @XmlElement(name = "caudal_salida_fachada")
    protected BigDecimal caudalSalidaFachada;
    @XmlElement(name = "condensacion_aire_salida_chimenea")
    protected String condensacionAireSalidaChimenea;
    @XmlElement(name = "caudal_salida_cubierta")
    protected BigDecimal caudalSalidaCubierta;
    @XmlElement(name = "condensador_situado_cubierta")
    protected String condensadorSituadoCubierta;
    @XmlElement(name = "caudal_condensador_cubierta")
    protected BigDecimal caudalCondensadorCubierta;
    @XmlElement(name = "torre_refrigeracion")
    protected String torreRefrigeracion;
    @XmlElement(name = "num_torres_refrigeracion")
    protected BigInteger numTorresRefrigeracion;

    /**
     * Gets the value of the climatizacionAireAcondicionado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClimatizacionAireAcondicionado() {
        return climatizacionAireAcondicionado;
    }

    /**
     * Sets the value of the climatizacionAireAcondicionado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClimatizacionAireAcondicionado(String value) {
        this.climatizacionAireAcondicionado = value;
    }

    /**
     * Gets the value of the instalacionesGeneralEdificio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstalacionesGeneralEdificio() {
        return instalacionesGeneralEdificio;
    }

    /**
     * Sets the value of the instalacionesGeneralEdificio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstalacionesGeneralEdificio(String value) {
        this.instalacionesGeneralEdificio = value;
    }

    /**
     * Gets the value of the potenciaCalorificaInstGen property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaCalorificaInstGen() {
        return potenciaCalorificaInstGen;
    }

    /**
     * Sets the value of the potenciaCalorificaInstGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaCalorificaInstGen(BigDecimal value) {
        this.potenciaCalorificaInstGen = value;
    }

    /**
     * Gets the value of the potenciaFrigorificaInstGen property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaFrigorificaInstGen() {
        return potenciaFrigorificaInstGen;
    }

    /**
     * Sets the value of the potenciaFrigorificaInstGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaFrigorificaInstGen(BigDecimal value) {
        this.potenciaFrigorificaInstGen = value;
    }

    /**
     * Gets the value of the equipoCentralizadoLocal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipoCentralizadoLocal() {
        return equipoCentralizadoLocal;
    }

    /**
     * Sets the value of the equipoCentralizadoLocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipoCentralizadoLocal(String value) {
        this.equipoCentralizadoLocal = value;
    }

    /**
     * Gets the value of the potenciaCalorificaEqCentr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaCalorificaEqCentr() {
        return potenciaCalorificaEqCentr;
    }

    /**
     * Sets the value of the potenciaCalorificaEqCentr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaCalorificaEqCentr(BigDecimal value) {
        this.potenciaCalorificaEqCentr = value;
    }

    /**
     * Gets the value of the potenciaFrigorificaEqCentr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaFrigorificaEqCentr() {
        return potenciaFrigorificaEqCentr;
    }

    /**
     * Sets the value of the potenciaFrigorificaEqCentr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaFrigorificaEqCentr(BigDecimal value) {
        this.potenciaFrigorificaEqCentr = value;
    }

    /**
     * Gets the value of the hayEquiposAutonomos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHayEquiposAutonomos() {
        return hayEquiposAutonomos;
    }

    /**
     * Sets the value of the hayEquiposAutonomos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHayEquiposAutonomos(String value) {
        this.hayEquiposAutonomos = value;
    }

    /**
     * Gets the value of the equiposAutonomos property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEquiposAutonomos() {
        return equiposAutonomos;
    }

    /**
     * Sets the value of the equiposAutonomos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEquiposAutonomos(BigInteger value) {
        this.equiposAutonomos = value;
    }

    /**
     * Gets the value of the potenciaCalorificaEqAutonomos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaCalorificaEqAutonomos() {
        return potenciaCalorificaEqAutonomos;
    }

    /**
     * Sets the value of the potenciaCalorificaEqAutonomos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaCalorificaEqAutonomos(BigDecimal value) {
        this.potenciaCalorificaEqAutonomos = value;
    }

    /**
     * Gets the value of the potenciaFrigorificaEqAutonomos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaFrigorificaEqAutonomos() {
        return potenciaFrigorificaEqAutonomos;
    }

    /**
     * Sets the value of the potenciaFrigorificaEqAutonomos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaFrigorificaEqAutonomos(BigDecimal value) {
        this.potenciaFrigorificaEqAutonomos = value;
    }

    /**
     * Gets the value of the hayEnfriadoresEvaporativos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHayEnfriadoresEvaporativos() {
        return hayEnfriadoresEvaporativos;
    }

    /**
     * Sets the value of the hayEnfriadoresEvaporativos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHayEnfriadoresEvaporativos(String value) {
        this.hayEnfriadoresEvaporativos = value;
    }

    /**
     * Gets the value of the enfriadoresEvaporativos property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEnfriadoresEvaporativos() {
        return enfriadoresEvaporativos;
    }

    /**
     * Sets the value of the enfriadoresEvaporativos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEnfriadoresEvaporativos(BigInteger value) {
        this.enfriadoresEvaporativos = value;
    }

    /**
     * Gets the value of the potenciaCalorificaEnfriadores property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaCalorificaEnfriadores() {
        return potenciaCalorificaEnfriadores;
    }

    /**
     * Sets the value of the potenciaCalorificaEnfriadores property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaCalorificaEnfriadores(BigDecimal value) {
        this.potenciaCalorificaEnfriadores = value;
    }

    /**
     * Gets the value of the potenciaFrigorificaEnfriadores property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaFrigorificaEnfriadores() {
        return potenciaFrigorificaEnfriadores;
    }

    /**
     * Sets the value of the potenciaFrigorificaEnfriadores property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaFrigorificaEnfriadores(BigDecimal value) {
        this.potenciaFrigorificaEnfriadores = value;
    }

    /**
     * Gets the value of the condensacionAireSalidaFachada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondensacionAireSalidaFachada() {
        return condensacionAireSalidaFachada;
    }

    /**
     * Sets the value of the condensacionAireSalidaFachada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondensacionAireSalidaFachada(String value) {
        this.condensacionAireSalidaFachada = value;
    }

    /**
     * Gets the value of the caudalSalidaFachada property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCaudalSalidaFachada() {
        return caudalSalidaFachada;
    }

    /**
     * Sets the value of the caudalSalidaFachada property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCaudalSalidaFachada(BigDecimal value) {
        this.caudalSalidaFachada = value;
    }

    /**
     * Gets the value of the condensacionAireSalidaChimenea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondensacionAireSalidaChimenea() {
        return condensacionAireSalidaChimenea;
    }

    /**
     * Sets the value of the condensacionAireSalidaChimenea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondensacionAireSalidaChimenea(String value) {
        this.condensacionAireSalidaChimenea = value;
    }

    /**
     * Gets the value of the caudalSalidaCubierta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCaudalSalidaCubierta() {
        return caudalSalidaCubierta;
    }

    /**
     * Sets the value of the caudalSalidaCubierta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCaudalSalidaCubierta(BigDecimal value) {
        this.caudalSalidaCubierta = value;
    }

    /**
     * Gets the value of the condensadorSituadoCubierta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondensadorSituadoCubierta() {
        return condensadorSituadoCubierta;
    }

    /**
     * Sets the value of the condensadorSituadoCubierta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondensadorSituadoCubierta(String value) {
        this.condensadorSituadoCubierta = value;
    }

    /**
     * Gets the value of the caudalCondensadorCubierta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCaudalCondensadorCubierta() {
        return caudalCondensadorCubierta;
    }

    /**
     * Sets the value of the caudalCondensadorCubierta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCaudalCondensadorCubierta(BigDecimal value) {
        this.caudalCondensadorCubierta = value;
    }

    /**
     * Gets the value of the torreRefrigeracion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTorreRefrigeracion() {
        return torreRefrigeracion;
    }

    /**
     * Sets the value of the torreRefrigeracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTorreRefrigeracion(String value) {
        this.torreRefrigeracion = value;
    }

    /**
     * Gets the value of the numTorresRefrigeracion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumTorresRefrigeracion() {
        return numTorresRefrigeracion;
    }

    /**
     * Sets the value of the numTorresRefrigeracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumTorresRefrigeracion(BigInteger value) {
        this.numTorresRefrigeracion = value;
    }

}
