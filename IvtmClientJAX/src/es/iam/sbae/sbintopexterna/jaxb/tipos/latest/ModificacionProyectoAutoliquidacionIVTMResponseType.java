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
 * <p>Java class for ModificacionProyectoAutoliquidacionIVTMResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModificacionProyectoAutoliquidacionIVTMResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numautoliquidacion" type="{http://ws.iam.munimadrid.es}numautoliquidacionTipo"/>
 *         &lt;element name="emisor" type="{http://ws.iam.munimadrid.es}emisorTipo"/>
 *         &lt;element name="importeanual" type="{http://ws.iam.munimadrid.es}importeTipo"/>
 *         &lt;element name="importe" type="{http://ws.iam.munimadrid.es}importeTipo"/>
 *         &lt;element name="digitos" type="{http://ws.iam.munimadrid.es}digitosTipo"/>
 *         &lt;element name="codgestor" type="{http://ws.iam.munimadrid.es}codgestorTipo"/>
 *         &lt;element name="errores" type="{http://ws.iam.munimadrid.es}erroresType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificacionProyectoAutoliquidacionIVTMResponseType", propOrder = {
    "numautoliquidacion",
    "emisor",
    "importeanual",
    "importe",
    "digitos",
    "codgestor",
    "errores"
})
public class ModificacionProyectoAutoliquidacionIVTMResponseType {

    @XmlElement(required = true)
    protected String numautoliquidacion;
    @XmlElement(required = true)
    protected String emisor;
    @XmlElement(required = true)
    protected String importeanual;
    @XmlElement(required = true)
    protected String importe;
    @XmlElement(required = true)
    protected String digitos;
    @XmlElement(required = true)
    protected String codgestor;
    protected ErroresType errores;

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
     * Gets the value of the emisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmisor() {
        return emisor;
    }

    /**
     * Sets the value of the emisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmisor(String value) {
        this.emisor = value;
    }

    /**
     * Gets the value of the importeanual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporteanual() {
        return importeanual;
    }

    /**
     * Sets the value of the importeanual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporteanual(String value) {
        this.importeanual = value;
    }

    /**
     * Gets the value of the importe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporte(String value) {
        this.importe = value;
    }

    /**
     * Gets the value of the digitos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigitos() {
        return digitos;
    }

    /**
     * Sets the value of the digitos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigitos(String value) {
        this.digitos = value;
    }

    /**
     * Gets the value of the codgestor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodgestor() {
        return codgestor;
    }

    /**
     * Sets the value of the codgestor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodgestor(String value) {
        this.codgestor = value;
    }

    /**
     * Gets the value of the errores property.
     * 
     * @return
     *     possible object is
     *     {@link ErroresType }
     *     
     */
    public ErroresType getErrores() {
        return errores;
    }

    /**
     * Sets the value of the errores property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroresType }
     *     
     */
    public void setErrores(ErroresType value) {
        this.errores = value;
    }

}
