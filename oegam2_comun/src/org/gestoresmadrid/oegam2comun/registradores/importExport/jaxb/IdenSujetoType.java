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
 * <p>Java class for Iden_SujetoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Iden_SujetoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tipo_Persona" type="{}Tipificacion_Tipo_PersonaType" minOccurs="0"/>
 *         &lt;element name="Nif_Cif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Apellido1_Razon_Social" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Apellido2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Iden_SujetoType", propOrder = {
    "tipoPersona",
    "nifCif",
    "nombre",
    "apellido1RazonSocial",
    "apellido2"
})
public class IdenSujetoType {

    @XmlElement(name = "Tipo_Persona")
    protected String tipoPersona;
    @XmlElement(name = "Nif_Cif")
    protected String nifCif;
    @XmlElement(name = "Nombre")
    protected String nombre;
    @XmlElement(name = "Apellido1_Razon_Social")
    protected String apellido1RazonSocial;
    @XmlElement(name = "Apellido2")
    protected String apellido2;

    /**
     * Gets the value of the tipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Sets the value of the tipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPersona(String value) {
        this.tipoPersona = value;
    }

    /**
     * Gets the value of the nifCif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNifCif() {
        return nifCif;
    }

    /**
     * Sets the value of the nifCif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNifCif(String value) {
        this.nifCif = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the apellido1RazonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido1RazonSocial() {
        return apellido1RazonSocial;
    }

    /**
     * Sets the value of the apellido1RazonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido1RazonSocial(String value) {
        this.apellido1RazonSocial = value;
    }

    /**
     * Gets the value of the apellido2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Sets the value of the apellido2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido2(String value) {
        this.apellido2 = value;
    }

}
