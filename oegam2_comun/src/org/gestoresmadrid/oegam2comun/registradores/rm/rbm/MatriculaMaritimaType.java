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
 * <p>Java class for Matricula_MaritimaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Matricula_MaritimaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Capitania_Maritima" type="{}Tipificacion_Capitania_MaritimaType"/>
 *           &lt;element name="Provincia_Maritima" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="Distrito_Maritimo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Num_Lista" type="{}Tipificacion_Lista_MaritimaType"/>
 *         &lt;element name="Folio_Inscripcion" type="{}NumericoType"/>
 *         &lt;element name="Anio_Inscripcion" type="{}NumericoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Matricula_MaritimaType", propOrder = {
    "capitaniaMaritima",
    "provinciaMaritima",
    "distritoMaritimo",
    "numLista",
    "folioInscripcion",
    "anioInscripcion"
})
public class MatriculaMaritimaType {

    @XmlElement(name = "Capitania_Maritima")
    protected String capitaniaMaritima;
    @XmlElement(name = "Provincia_Maritima")
    protected String provinciaMaritima;
    @XmlElement(name = "Distrito_Maritimo")
    protected String distritoMaritimo;
    @XmlElement(name = "Num_Lista", required = true)
    protected String numLista;
    @XmlElement(name = "Folio_Inscripcion", required = true)
    protected String folioInscripcion;
    @XmlElement(name = "A\u00f1o_Inscripcion", required = true)
    protected String anioInscripcion;

    /**
     * Gets the value of the capitaniaMaritima property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitaniaMaritima() {
        return capitaniaMaritima;
    }

    /**
     * Sets the value of the capitaniaMaritima property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitaniaMaritima(String value) {
        this.capitaniaMaritima = value;
    }

    /**
     * Gets the value of the provinciaMaritima property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaMaritima() {
        return provinciaMaritima;
    }

    /**
     * Sets the value of the provinciaMaritima property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaMaritima(String value) {
        this.provinciaMaritima = value;
    }

    /**
     * Gets the value of the distritoMaritimo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistritoMaritimo() {
        return distritoMaritimo;
    }

    /**
     * Sets the value of the distritoMaritimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistritoMaritimo(String value) {
        this.distritoMaritimo = value;
    }

    /**
     * Gets the value of the numLista property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumLista() {
        return numLista;
    }

    /**
     * Sets the value of the numLista property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumLista(String value) {
        this.numLista = value;
    }

    /**
     * Gets the value of the folioInscripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioInscripcion() {
        return folioInscripcion;
    }

    /**
     * Sets the value of the folioInscripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioInscripcion(String value) {
        this.folioInscripcion = value;
    }

    /**
     * Gets the value of the anioInscripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnioInscripcion() {
        return anioInscripcion;
    }

    /**
     * Sets the value of the anioInscripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnioInscripcion(String value) {
        this.anioInscripcion = value;
    }

}
