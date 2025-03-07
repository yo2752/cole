//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.15 at 04:00:13 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.rm.corpme;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AsistentesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AsistentesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Asistente" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Identificacion" type="{}Identificacion_SujetoType"/>
 *                   &lt;element name="Cargo" type="{}CargoType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AsistentesType", propOrder = {
    "asistente"
})
public class AsistentesType {

    @XmlElement(name = "Asistente", required = true)
    protected List<AsistentesType.Asistente> asistente;

    /**
     * Gets the value of the asistente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asistente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsistente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AsistentesType.Asistente }
     * 
     * 
     */
    public List<AsistentesType.Asistente> getAsistente() {
        if (asistente == null) {
            asistente = new ArrayList<AsistentesType.Asistente>();
        }
        return this.asistente;
    }


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
     *         &lt;element name="Identificacion" type="{}Identificacion_SujetoType"/>
     *         &lt;element name="Cargo" type="{}CargoType"/>
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
        "identificacion",
        "cargo"
    })
    public static class Asistente {

        @XmlElement(name = "Identificacion", required = true)
        protected IdentificacionSujetoType identificacion;
        @XmlElement(name = "Cargo", required = true)
        protected String cargo;

        /**
         * Gets the value of the identificacion property.
         * 
         * @return
         *     possible object is
         *     {@link IdentificacionSujetoType }
         *     
         */
        public IdentificacionSujetoType getIdentificacion() {
            return identificacion;
        }

        /**
         * Sets the value of the identificacion property.
         * 
         * @param value
         *     allowed object is
         *     {@link IdentificacionSujetoType }
         *     
         */
        public void setIdentificacion(IdentificacionSujetoType value) {
            this.identificacion = value;
        }

        /**
         * Gets the value of the cargo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCargo() {
            return cargo;
        }

        /**
         * Sets the value of the cargo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCargo(String value) {
            this.cargo = value;
        }

    }

}
