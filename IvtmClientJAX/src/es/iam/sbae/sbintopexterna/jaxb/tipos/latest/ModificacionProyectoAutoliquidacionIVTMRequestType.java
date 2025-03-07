//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.12 at 12:34:00 PM CEST 
//


package es.iam.sbae.sbintopexterna.jaxb.tipos.latest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModificacionProyectoAutoliquidacionIVTMRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModificacionProyectoAutoliquidacionIVTMRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docautorizado" type="{http://ws.iam.munimadrid.es}docautorizadoTipo"/>
 *         &lt;element name="numautoliquidacion" type="{http://ws.iam.munimadrid.es}numautoliquidacionTipo"/>
 *         &lt;element name="sujeto" type="{http://ws.iam.munimadrid.es}sujetoType" minOccurs="0"/>
 *         &lt;element name="osujeto" type="{http://ws.iam.munimadrid.es}sujetoType" minOccurs="0"/>
 *         &lt;element name="vehiculo" type="{http://ws.iam.munimadrid.es}vehiculoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificacionProyectoAutoliquidacionIVTMRequestType", propOrder = {
    "docautorizado",
    "numautoliquidacion",
    "sujeto",
    "osujeto",
    "vehiculo"
})
public class ModificacionProyectoAutoliquidacionIVTMRequestType {

    @XmlElement(required = true)
    protected String docautorizado;
    @XmlElement(required = true)
    protected String numautoliquidacion;
    protected SujetoType sujeto;
    protected SujetoType osujeto;
    @XmlElement(required = true)
    protected VehiculoType vehiculo;

    /**
     * Gets the value of the docautorizado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocautorizado() {
        return docautorizado;
    }

    /**
     * Sets the value of the docautorizado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocautorizado(String value) {
        this.docautorizado = value;
    }

    /**
     * Gets the value of the numautoliquidacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumautoliquidacion() {
        return numautoliquidacion;
    }

    /**
     * Sets the value of the numautoliquidacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumautoliquidacion(String value) {
        this.numautoliquidacion = value;
    }

    /**
     * Gets the value of the sujeto property.
     * 
     * @return
     *     possible object is
     *     {@link SujetoType }
     *     
     */
    public SujetoType getSujeto() {
        return sujeto;
    }

    /**
     * Sets the value of the sujeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SujetoType }
     *     
     */
    public void setSujeto(SujetoType value) {
        this.sujeto = value;
    }

    /**
     * Gets the value of the osujeto property.
     * 
     * @return
     *     possible object is
     *     {@link SujetoType }
     *     
     */
    public SujetoType getOsujeto() {
        return osujeto;
    }

    /**
     * Sets the value of the osujeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SujetoType }
     *     
     */
    public void setOsujeto(SujetoType value) {
        this.osujeto = value;
    }

    /**
     * Gets the value of the vehiculo property.
     * 
     * @return
     *     possible object is
     *     {@link VehiculoType }
     *     
     */
    public VehiculoType getVehiculo() {
        return vehiculo;
    }

    /**
     * Sets the value of the vehiculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VehiculoType }
     *     
     */
    public void setVehiculo(VehiculoType value) {
        this.vehiculo = value;
    }

}
