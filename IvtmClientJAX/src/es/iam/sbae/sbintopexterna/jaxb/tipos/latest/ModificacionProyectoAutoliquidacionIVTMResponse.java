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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ModificacionProyectoAutoliquidacionIVTMResponse" type="{http://ws.iam.munimadrid.es}ModificacionProyectoAutoliquidacionIVTMResponseType"/>
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
    "modificacionProyectoAutoliquidacionIVTMResponse"
})
@XmlRootElement(name = "ModificacionProyectoAutoliquidacionIVTMResponse")
public class ModificacionProyectoAutoliquidacionIVTMResponse {

    @XmlElement(name = "ModificacionProyectoAutoliquidacionIVTMResponse", required = true)
    protected ModificacionProyectoAutoliquidacionIVTMResponseType modificacionProyectoAutoliquidacionIVTMResponse;

    /**
     * Gets the value of the modificacionProyectoAutoliquidacionIVTMResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ModificacionProyectoAutoliquidacionIVTMResponseType }
     *     
     */
    public ModificacionProyectoAutoliquidacionIVTMResponseType getModificacionProyectoAutoliquidacionIVTMResponse() {
        return modificacionProyectoAutoliquidacionIVTMResponse;
    }

    /**
     * Sets the value of the modificacionProyectoAutoliquidacionIVTMResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModificacionProyectoAutoliquidacionIVTMResponseType }
     *     
     */
    public void setModificacionProyectoAutoliquidacionIVTMResponse(ModificacionProyectoAutoliquidacionIVTMResponseType value) {
        this.modificacionProyectoAutoliquidacionIVTMResponse = value;
    }

}
