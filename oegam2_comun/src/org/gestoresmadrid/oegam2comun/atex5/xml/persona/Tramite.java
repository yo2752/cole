//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 11:08:23 AM CEST 
//


package org.gestoresmadrid.oegam2comun.atex5.xml.persona;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for tramite complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tramite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="centroMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claseAfectacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="concesionObligada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jefatura" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="sucursal" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="tipo" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="tipoPermiso" type="{}tipoDescrito" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramite", propOrder = {
    "centroMedico",
    "claseAfectacion",
    "concesionObligada",
    "fecha",
    "jefatura",
    "sucursal",
    "tipo",
    "tipoPermiso"
})
public class Tramite {

    protected String centroMedico;
    protected String claseAfectacion;
    protected String concesionObligada;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected TipoDescrito jefatura;
    protected TipoDescrito sucursal;
    protected TipoDescrito tipo;
    protected TipoDescrito tipoPermiso;

    /**
     * Gets the value of the centroMedico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroMedico() {
        return centroMedico;
    }

    /**
     * Sets the value of the centroMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroMedico(String value) {
        this.centroMedico = value;
    }

    /**
     * Gets the value of the claseAfectacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseAfectacion() {
        return claseAfectacion;
    }

    /**
     * Sets the value of the claseAfectacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseAfectacion(String value) {
        this.claseAfectacion = value;
    }

    /**
     * Gets the value of the concesionObligada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcesionObligada() {
        return concesionObligada;
    }

    /**
     * Sets the value of the concesionObligada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcesionObligada(String value) {
        this.concesionObligada = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the jefatura property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getJefatura() {
        return jefatura;
    }

    /**
     * Sets the value of the jefatura property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setJefatura(TipoDescrito value) {
        this.jefatura = value;
    }

    /**
     * Gets the value of the sucursal property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getSucursal() {
        return sucursal;
    }

    /**
     * Sets the value of the sucursal property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setSucursal(TipoDescrito value) {
        this.sucursal = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipo(TipoDescrito value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the tipoPermiso property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipoPermiso() {
        return tipoPermiso;
    }

    /**
     * Sets the value of the tipoPermiso property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipoPermiso(TipoDescrito value) {
        this.tipoPermiso = value;
    }

}
