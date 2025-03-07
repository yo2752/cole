//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 11:05:31 AM CEST 
//


package org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for transferencia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transferencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaTransferencia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idDocumentoAnterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jefatura" type="{}tipoDescrito" minOccurs="0"/>
 *         &lt;element name="sucursal" type="{}tipoDescrito" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transferencia", propOrder = {
    "fechaTransferencia",
    "idDocumentoAnterior",
    "jefatura",
    "sucursal"
})
public class Transferencia {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaTransferencia;
    protected String idDocumentoAnterior;
    protected TipoDescrito jefatura;
    protected TipoDescrito sucursal;

    /**
     * Gets the value of the fechaTransferencia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaTransferencia() {
        return fechaTransferencia;
    }

    /**
     * Sets the value of the fechaTransferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaTransferencia(XMLGregorianCalendar value) {
        this.fechaTransferencia = value;
    }

    /**
     * Gets the value of the idDocumentoAnterior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocumentoAnterior() {
        return idDocumentoAnterior;
    }

    /**
     * Sets the value of the idDocumentoAnterior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocumentoAnterior(String value) {
        this.idDocumentoAnterior = value;
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

}
