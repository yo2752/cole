//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:49 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datos_portal_alta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datos_portal_alta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre_portal" type="{http://licencias/mensajes}string2type" minOccurs="0"/>
 *         &lt;element name="bajo_rasante" type="{http://licencias/mensajes}datos_superficie_portal" minOccurs="0"/>
 *         &lt;element name="sobre_rasante" type="{http://licencias/mensajes}datos_superficie_portal"/>
 *         &lt;element name="planta" type="{http://licencias/mensajes}datos_plantas_alta_lic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datos_portal_alta", propOrder = {
    "nombrePortal",
    "bajoRasante",
    "sobreRasante",
    "planta"
})
public class DatosPortalAlta {

    @XmlElement(name = "nombre_portal")
    protected String nombrePortal;
    @XmlElement(name = "bajo_rasante")
    protected DatosSuperficiePortal bajoRasante;
    @XmlElement(name = "sobre_rasante", required = true)
    protected DatosSuperficiePortal sobreRasante;
    protected List<DatosPlantasAltaLic> planta;

    /**
     * Gets the value of the nombrePortal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrePortal() {
        return nombrePortal;
    }

    /**
     * Sets the value of the nombrePortal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrePortal(String value) {
        this.nombrePortal = value;
    }

    /**
     * Gets the value of the bajoRasante property.
     * 
     * @return
     *     possible object is
     *     {@link DatosSuperficiePortal }
     *     
     */
    public DatosSuperficiePortal getBajoRasante() {
        return bajoRasante;
    }

    /**
     * Sets the value of the bajoRasante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosSuperficiePortal }
     *     
     */
    public void setBajoRasante(DatosSuperficiePortal value) {
        this.bajoRasante = value;
    }

    /**
     * Gets the value of the sobreRasante property.
     * 
     * @return
     *     possible object is
     *     {@link DatosSuperficiePortal }
     *     
     */
    public DatosSuperficiePortal getSobreRasante() {
        return sobreRasante;
    }

    /**
     * Sets the value of the sobreRasante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosSuperficiePortal }
     *     
     */
    public void setSobreRasante(DatosSuperficiePortal value) {
        this.sobreRasante = value;
    }

    /**
     * Gets the value of the planta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the planta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlanta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatosPlantasAltaLic }
     * 
     * 
     */
    public List<DatosPlantasAltaLic> getPlanta() {
        if (planta == null) {
            planta = new ArrayList<DatosPlantasAltaLic>();
        }
        return this.planta;
    }

}
