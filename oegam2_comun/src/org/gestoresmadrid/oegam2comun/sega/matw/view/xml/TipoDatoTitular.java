//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.09 at 05:24:21 PM CEST 
//


package org.gestoresmadrid.oegam2comun.sega.matw.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoDatoTitular complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipoDatoTitular">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Datos_Nombre" type="{http://www.dgt.es/vehiculos/tipos}tipoDatoNombre"/>
 *         &lt;element name="Documento_Identidad" type="{http://www.dgt.es/vehiculos/tipos}TipoDocumentoIdentificacion"/>
 *         &lt;element name="Fecha_Nacimiento" type="{http://www.dgt.es/vehiculos/tipos}tipoFechaAAAAMMDD" minOccurs="0"/>
 *         &lt;element name="Sexo" type="{http://www.dgt.es/vehiculos/tipos}alfa1" minOccurs="0"/>
 *         &lt;element name="Documento_Identidad_Representante" type="{http://www.dgt.es/vehiculos/tipos}TipoDocumentoIdentificacion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoDatoTitular", propOrder = {
    "datosNombre",
    "documentoIdentidad",
    "fechaNacimiento",
    "sexo",
    "documentoIdentidadRepresentante"
})
public class TipoDatoTitular {

    @XmlElement(name = "Datos_Nombre", required = true)
    protected TipoDatoNombre datosNombre;
    @XmlElement(name = "Documento_Identidad", required = true)
    protected TipoDocumentoIdentificacion documentoIdentidad;
    @XmlElement(name = "Fecha_Nacimiento")
    protected String fechaNacimiento;
    @XmlElement(name = "Sexo")
    protected String sexo;
    @XmlElement(name = "Documento_Identidad_Representante")
    protected TipoDocumentoIdentificacion documentoIdentidadRepresentante;

    /**
     * Gets the value of the datosNombre property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDatoNombre }
     *     
     */
    public TipoDatoNombre getDatosNombre() {
        return datosNombre;
    }

    /**
     * Sets the value of the datosNombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDatoNombre }
     *     
     */
    public void setDatosNombre(TipoDatoNombre value) {
        this.datosNombre = value;
    }

    /**
     * Gets the value of the documentoIdentidad property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoIdentificacion }
     *     
     */
    public TipoDocumentoIdentificacion getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    /**
     * Sets the value of the documentoIdentidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoIdentificacion }
     *     
     */
    public void setDocumentoIdentidad(TipoDocumentoIdentificacion value) {
        this.documentoIdentidad = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimiento(String value) {
        this.fechaNacimiento = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the documentoIdentidadRepresentante property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoIdentificacion }
     *     
     */
    public TipoDocumentoIdentificacion getDocumentoIdentidadRepresentante() {
        return documentoIdentidadRepresentante;
    }

    /**
     * Sets the value of the documentoIdentidadRepresentante property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoIdentificacion }
     *     
     */
    public void setDocumentoIdentidadRepresentante(TipoDocumentoIdentificacion value) {
        this.documentoIdentidadRepresentante = value;
    }

}
