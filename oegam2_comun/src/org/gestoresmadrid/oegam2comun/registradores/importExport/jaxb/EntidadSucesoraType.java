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
 * <p>Java class for Entidad_SucesoraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Entidad_SucesoraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Codigo_Identificacion_Fiscal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Razon_Social" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Datos_Registrales_Mercantil" type="{}Datos_Registrales_MercantilType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entidad_SucesoraType", propOrder = {
    "codigoIdentificacionFiscal",
    "razonSocial",
    "datosRegistralesMercantil"
})
public class EntidadSucesoraType {

    @XmlElement(name = "Codigo_Identificacion_Fiscal")
    protected String codigoIdentificacionFiscal;
    @XmlElement(name = "Razon_Social")
    protected String razonSocial;
    @XmlElement(name = "Datos_Registrales_Mercantil")
    protected DatosRegistralesMercantilType datosRegistralesMercantil;

    /**
     * Gets the value of the codigoIdentificacionFiscal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoIdentificacionFiscal() {
        return codigoIdentificacionFiscal;
    }

    /**
     * Sets the value of the codigoIdentificacionFiscal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoIdentificacionFiscal(String value) {
        this.codigoIdentificacionFiscal = value;
    }

    /**
     * Gets the value of the razonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Sets the value of the razonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    /**
     * Gets the value of the datosRegistralesMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link DatosRegistralesMercantilType }
     *     
     */
    public DatosRegistralesMercantilType getDatosRegistralesMercantil() {
        return datosRegistralesMercantil;
    }

    /**
     * Sets the value of the datosRegistralesMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosRegistralesMercantilType }
     *     
     */
    public void setDatosRegistralesMercantil(DatosRegistralesMercantilType value) {
        this.datosRegistralesMercantil = value;
    }

}
