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
 * <p>Java class for detalleVehiculo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detalleVehiculo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bastidor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marca" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paisProcedencia" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="servicio" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="servicioComplementario" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="tipoIndustria" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="tipoVehiculo" type="{}tipoDescrito" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detalleVehiculo", propOrder = {
    "bastidor",
    "marca",
    "modelo",
    "nive",
    "paisProcedencia",
    "servicio",
    "servicioComplementario",
    "tipoIndustria",
    "tipoVehiculo"
})
public class DetalleVehiculo {

    protected String bastidor;
    protected TipoDescrito marca;
    protected String modelo;
    protected String nive;
    protected TipoDescrito paisProcedencia;
    protected TipoDescrito servicio;
    protected TipoDescrito servicioComplementario;
    protected TipoDescrito tipoIndustria;
    protected TipoDescrito tipoVehiculo;

    /**
     * Gets the value of the bastidor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBastidor() {
        return bastidor;
    }

    /**
     * Sets the value of the bastidor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBastidor(String value) {
        this.bastidor = value;
    }

    /**
     * Gets the value of the marca property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getMarca() {
        return marca;
    }

    /**
     * Sets the value of the marca property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setMarca(TipoDescrito value) {
        this.marca = value;
    }

    /**
     * Gets the value of the modelo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Sets the value of the modelo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelo(String value) {
        this.modelo = value;
    }

    /**
     * Gets the value of the nive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNive() {
        return nive;
    }

    /**
     * Sets the value of the nive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNive(String value) {
        this.nive = value;
    }

    /**
     * Gets the value of the paisProcedencia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getPaisProcedencia() {
        return paisProcedencia;
    }

    /**
     * Sets the value of the paisProcedencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setPaisProcedencia(TipoDescrito value) {
        this.paisProcedencia = value;
    }

    /**
     * Gets the value of the servicio property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getServicio() {
        return servicio;
    }

    /**
     * Sets the value of the servicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setServicio(TipoDescrito value) {
        this.servicio = value;
    }

    /**
     * Gets the value of the servicioComplementario property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getServicioComplementario() {
        return servicioComplementario;
    }

    /**
     * Sets the value of the servicioComplementario property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setServicioComplementario(TipoDescrito value) {
        this.servicioComplementario = value;
    }

    /**
     * Gets the value of the tipoIndustria property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipoIndustria() {
        return tipoIndustria;
    }

    /**
     * Sets the value of the tipoIndustria property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipoIndustria(TipoDescrito value) {
        this.tipoIndustria = value;
    }

    /**
     * Gets the value of the tipoVehiculo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Sets the value of the tipoVehiculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipoVehiculo(TipoDescrito value) {
        this.tipoVehiculo = value;
    }

}
