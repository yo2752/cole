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
 * <p>Java class for Precio_AdquisicionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Precio_AdquisicionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Unidad_Cuenta" type="{}Tipificacion_Unidad_CuentaType" minOccurs="0"/>
 *         &lt;element name="Importe_Base" type="{}ImporteType" minOccurs="0"/>
 *         &lt;element name="Importe_Impuesto" type="{}ImporteType" minOccurs="0"/>
 *         &lt;element name="Importe_Total" type="{}ImporteType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Precio_AdquisicionType", propOrder = {
    "unidadCuenta",
    "importeBase",
    "importeImpuesto",
    "importeTotal"
})
public class PrecioAdquisicionType {

    @XmlElement(name = "Unidad_Cuenta")
    protected String unidadCuenta;
    @XmlElement(name = "Importe_Base")
    protected String importeBase;
    @XmlElement(name = "Importe_Impuesto")
    protected String importeImpuesto;
    @XmlElement(name = "Importe_Total")
    protected String importeTotal;

    /**
     * Gets the value of the unidadCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidadCuenta() {
        return unidadCuenta;
    }

    /**
     * Sets the value of the unidadCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidadCuenta(String value) {
        this.unidadCuenta = value;
    }

    /**
     * Gets the value of the importeBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteBase() {
        return importeBase;
    }

    /**
     * Sets the value of the importeBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteBase(String value) {
        this.importeBase = value;
    }

    /**
     * Gets the value of the importeImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteImpuesto() {
        return importeImpuesto;
    }

    /**
     * Sets the value of the importeImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteImpuesto(String value) {
        this.importeImpuesto = value;
    }

    /**
     * Gets the value of the importeTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteTotal() {
        return importeTotal;
    }

    /**
     * Sets the value of the importeTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteTotal(String value) {
        this.importeTotal = value;
    }

}
