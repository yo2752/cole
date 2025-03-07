//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.02 at 11:42:58 AM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.rm.rbm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Objeto_LeasingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Objeto_LeasingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Des_Bien_Mueble" type="{}Des_Bien_MuebleType"/>
 *         &lt;element name="Precio_Adquisicion" type="{}Precio_AdquisicionType"/>
 *         &lt;element name="Proveedor" type="{}ProveedorType"/>
 *         &lt;element name="Impuesto_Matriculacion" type="{}ImporteType" minOccurs="0"/>
 *         &lt;element name="Lugar_Utilizacion" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Domicilio" type="{}Domicilio_INEType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Objeto_LeasingType", propOrder = {
    "desBienMueble",
    "precioAdquisicion",
    "proveedor",
    "impuestoMatriculacion",
    "lugarUtilizacion"
})
public class ObjetoLeasingType {

    @XmlElement(name = "Des_Bien_Mueble", required = true)
    protected DesBienMuebleType desBienMueble;
    @XmlElement(name = "Precio_Adquisicion", required = true)
    protected PrecioAdquisicionType precioAdquisicion;
    @XmlElement(name = "Proveedor", required = true)
    protected ProveedorType proveedor;
    @XmlElement(name = "Impuesto_Matriculacion")
    protected String impuestoMatriculacion;
    @XmlElement(name = "Lugar_Utilizacion")
    protected ObjetoLeasingType.LugarUtilizacion lugarUtilizacion;

    /**
     * Gets the value of the desBienMueble property.
     * 
     * @return
     *     possible object is
     *     {@link DesBienMuebleType }
     *     
     */
    public DesBienMuebleType getDesBienMueble() {
        return desBienMueble;
    }

    /**
     * Sets the value of the desBienMueble property.
     * 
     * @param value
     *     allowed object is
     *     {@link DesBienMuebleType }
     *     
     */
    public void setDesBienMueble(DesBienMuebleType value) {
        this.desBienMueble = value;
    }

    /**
     * Gets the value of the precioAdquisicion property.
     * 
     * @return
     *     possible object is
     *     {@link PrecioAdquisicionType }
     *     
     */
    public PrecioAdquisicionType getPrecioAdquisicion() {
        return precioAdquisicion;
    }

    /**
     * Sets the value of the precioAdquisicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrecioAdquisicionType }
     *     
     */
    public void setPrecioAdquisicion(PrecioAdquisicionType value) {
        this.precioAdquisicion = value;
    }

    /**
     * Gets the value of the proveedor property.
     * 
     * @return
     *     possible object is
     *     {@link ProveedorType }
     *     
     */
    public ProveedorType getProveedor() {
        return proveedor;
    }

    /**
     * Sets the value of the proveedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProveedorType }
     *     
     */
    public void setProveedor(ProveedorType value) {
        this.proveedor = value;
    }

    /**
     * Gets the value of the impuestoMatriculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpuestoMatriculacion() {
        return impuestoMatriculacion;
    }

    /**
     * Sets the value of the impuestoMatriculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpuestoMatriculacion(String value) {
        this.impuestoMatriculacion = value;
    }

    /**
     * Gets the value of the lugarUtilizacion property.
     * 
     * @return
     *     possible object is
     *     {@link ObjetoLeasingType.LugarUtilizacion }
     *     
     */
    public ObjetoLeasingType.LugarUtilizacion getLugarUtilizacion() {
        return lugarUtilizacion;
    }

    /**
     * Sets the value of the lugarUtilizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjetoLeasingType.LugarUtilizacion }
     *     
     */
    public void setLugarUtilizacion(ObjetoLeasingType.LugarUtilizacion value) {
        this.lugarUtilizacion = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Domicilio" type="{}Domicilio_INEType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "domicilio"
    })
    public static class LugarUtilizacion {

        @XmlElement(name = "Domicilio", required = true)
        protected DomicilioINEType domicilio;

        /**
         * Gets the value of the domicilio property.
         * 
         * @return
         *     possible object is
         *     {@link DomicilioINEType }
         *     
         */
        public DomicilioINEType getDomicilio() {
            return domicilio;
        }

        /**
         * Sets the value of the domicilio property.
         * 
         * @param value
         *     allowed object is
         *     {@link DomicilioINEType }
         *     
         */
        public void setDomicilio(DomicilioINEType value) {
            this.domicilio = value;
        }

    }

}
