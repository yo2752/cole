//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 11:05:31 AM CEST 
//


package org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for combustibleEmisiones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="combustibleEmisiones">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autonomiaElectrica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="categoriaElectrica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoEco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consumo" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="ecoInnovacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nivelEmisiones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propulsion" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="reduccionEco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAlimentacion" type="{}tipoDescrito" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "combustibleEmisiones", propOrder = {
    "autonomiaElectrica",
    "categoriaElectrica",
    "codigoEco",
    "codos",
    "consumo",
    "ecoInnovacion",
    "nivelEmisiones",
    "propulsion",
    "reduccionEco",
    "tipoAlimentacion"
})
public class CombustibleEmisiones {

    protected String autonomiaElectrica;
    protected String categoriaElectrica;
    protected String codigoEco;
    protected String codos;
    protected Float consumo;
    protected String ecoInnovacion;
    protected String nivelEmisiones;
    protected TipoDescrito propulsion;
    protected String reduccionEco;
    protected TipoDescrito tipoAlimentacion;

    /**
     * Gets the value of the autonomiaElectrica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutonomiaElectrica() {
        return autonomiaElectrica;
    }

    /**
     * Sets the value of the autonomiaElectrica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutonomiaElectrica(String value) {
        this.autonomiaElectrica = value;
    }

    /**
     * Gets the value of the categoriaElectrica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoriaElectrica() {
        return categoriaElectrica;
    }

    /**
     * Sets the value of the categoriaElectrica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoriaElectrica(String value) {
        this.categoriaElectrica = value;
    }

    /**
     * Gets the value of the codigoEco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEco() {
        return codigoEco;
    }

    /**
     * Sets the value of the codigoEco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEco(String value) {
        this.codigoEco = value;
    }

    /**
     * Gets the value of the codos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodos() {
        return codos;
    }

    /**
     * Sets the value of the codos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodos(String value) {
        this.codos = value;
    }

    /**
     * Gets the value of the consumo property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getConsumo() {
        return consumo;
    }

    /**
     * Sets the value of the consumo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setConsumo(Float value) {
        this.consumo = value;
    }

    /**
     * Gets the value of the ecoInnovacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEcoInnovacion() {
        return ecoInnovacion;
    }

    /**
     * Sets the value of the ecoInnovacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEcoInnovacion(String value) {
        this.ecoInnovacion = value;
    }

    /**
     * Gets the value of the nivelEmisiones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNivelEmisiones() {
        return nivelEmisiones;
    }

    /**
     * Sets the value of the nivelEmisiones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNivelEmisiones(String value) {
        this.nivelEmisiones = value;
    }

    /**
     * Gets the value of the propulsion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getPropulsion() {
        return propulsion;
    }

    /**
     * Sets the value of the propulsion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setPropulsion(TipoDescrito value) {
        this.propulsion = value;
    }

    /**
     * Gets the value of the reduccionEco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReduccionEco() {
        return reduccionEco;
    }

    /**
     * Sets the value of the reduccionEco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReduccionEco(String value) {
        this.reduccionEco = value;
    }

    /**
     * Gets the value of the tipoAlimentacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipoAlimentacion() {
        return tipoAlimentacion;
    }

    /**
     * Sets the value of the tipoAlimentacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipoAlimentacion(TipoDescrito value) {
        this.tipoAlimentacion = value;
    }

}
