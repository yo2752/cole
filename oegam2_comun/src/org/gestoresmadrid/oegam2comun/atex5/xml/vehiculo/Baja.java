//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 11:05:31 AM CEST 
//


package org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for baja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baja">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="causaBaja" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="fechaFin" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;list itemType="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="fechaInicio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jefatura" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="sucursal" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="tipoBaja" type="{}tipoDescrito" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baja", propOrder = {
    "causaBaja",
    "fechaFin",
    "fechaInicio",
    "jefatura",
    "sucursal",
    "tipoBaja"
})
public class Baja {

    protected TipoDescrito causaBaja;
    @XmlElementRef(name = "fechaFin", type = JAXBElement.class, required = false)
    protected List<JAXBElement<List<XMLGregorianCalendar>>> fechaFin;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaInicio;
    protected TipoDescrito jefatura;
    protected TipoDescrito sucursal;
    protected TipoDescrito tipoBaja;

    /**
     * Gets the value of the causaBaja property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getCausaBaja() {
        return causaBaja;
    }

    /**
     * Sets the value of the causaBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setCausaBaja(TipoDescrito value) {
        this.causaBaja = value;
    }

    /**
     * Gets the value of the fechaFin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fechaFin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFechaFin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link XMLGregorianCalendar }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<XMLGregorianCalendar>>> getFechaFin() {
        if (fechaFin == null) {
            fechaFin = new ArrayList<JAXBElement<List<XMLGregorianCalendar>>>();
        }
        return this.fechaFin;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaInicio(XMLGregorianCalendar value) {
        this.fechaInicio = value;
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
     * Gets the value of the tipoBaja property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDescrito }
     *     
     */
    public TipoDescrito getTipoBaja() {
        return tipoBaja;
    }

    /**
     * Sets the value of the tipoBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDescrito }
     *     
     */
    public void setTipoBaja(TipoDescrito value) {
        this.tipoBaja = value;
    }

}
