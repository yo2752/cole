//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.10 at 02:06:02 PM CEST 
//


package es.map.scsp.esquemas.datosespecificos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosEspecificosConsultaDeudaIVTMReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosEspecificosConsultaDeudaIVTMReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.map.es/scsp/esquemas/datosespecificos}docautorizado"/>
 *         &lt;element ref="{http://www.map.es/scsp/esquemas/datosespecificos}matricula"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosEspecificosConsultaDeudaIVTMReq", propOrder = {
    "docautorizado",
    "matricula"
})
public class DatosEspecificosConsultaDeudaIVTMReq {

    @XmlElement(required = true)
    protected String docautorizado;
    @XmlElement(required = true)
    protected String matricula;

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
     * Gets the value of the matricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Sets the value of the matricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricula(String value) {
        this.matricula = value;
    }

}
