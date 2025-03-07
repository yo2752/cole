//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.10 at 12:05:23 PM CEST 
//


package es.map.scsp.esquemas.datosespecificos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosEspecificosModificacionProyectoIVTMEntrada complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosEspecificosModificacionProyectoIVTMEntrada">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docautorizado" type="{http://www.map.es/scsp/esquemas/datosespecificos}docautorizadoTipo"/>
 *         &lt;element name="numautoliquidacion" type="{http://www.map.es/scsp/esquemas/datosespecificos}numautoliquidacionTipo"/>
 *         &lt;element name="sujeto" type="{http://www.map.es/scsp/esquemas/datosespecificos}sujetoType" minOccurs="0"/>
 *         &lt;element name="osujeto" type="{http://www.map.es/scsp/esquemas/datosespecificos}sujetoType" minOccurs="0"/>
 *         &lt;element name="vehiculo" type="{http://www.map.es/scsp/esquemas/datosespecificos}vehiculoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosEspecificosModificacionProyectoIVTMEntrada", propOrder = {
    "docautorizado",
    "numautoliquidacion",
    "sujeto",
    "osujeto",
    "vehiculo"
})
public class DatosEspecificosModificacionProyectoIVTMEntrada {

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
