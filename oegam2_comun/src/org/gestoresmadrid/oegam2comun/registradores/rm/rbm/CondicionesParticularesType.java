//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.02 at 11:42:58 AM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.rm.rbm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Condiciones_ParticularesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Condiciones_ParticularesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pactos" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Pacto" type="{}PactoType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Cesion" type="{}CesionType" minOccurs="0"/>
 *         &lt;element name="Clausulas_Particulares" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Clausula" type="{}ClausulaType" maxOccurs="unbounded"/>
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
@XmlType(name = "Condiciones_ParticularesType", propOrder = {
    "pactos",
    "cesion",
    "clausulasParticulares"
})
public class CondicionesParticularesType {

    @XmlElement(name = "Pactos")
    protected CondicionesParticularesType.Pactos pactos;
    @XmlElement(name = "Cesion")
    protected CesionType cesion;
    @XmlElement(name = "Clausulas_Particulares")
    protected CondicionesParticularesType.ClausulasParticulares clausulasParticulares;

    /**
     * Gets the value of the pactos property.
     * 
     * @return
     *     possible object is
     *     {@link CondicionesParticularesType.Pactos }
     *     
     */
    public CondicionesParticularesType.Pactos getPactos() {
        return pactos;
    }

    /**
     * Sets the value of the pactos property.
     * 
     * @param value
     *     allowed object is
     *     {@link CondicionesParticularesType.Pactos }
     *     
     */
    public void setPactos(CondicionesParticularesType.Pactos value) {
        this.pactos = value;
    }

    /**
     * Gets the value of the cesion property.
     * 
     * @return
     *     possible object is
     *     {@link CesionType }
     *     
     */
    public CesionType getCesion() {
        return cesion;
    }

    /**
     * Sets the value of the cesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CesionType }
     *     
     */
    public void setCesion(CesionType value) {
        this.cesion = value;
    }

    /**
     * Gets the value of the clausulasParticulares property.
     * 
     * @return
     *     possible object is
     *     {@link CondicionesParticularesType.ClausulasParticulares }
     *     
     */
    public CondicionesParticularesType.ClausulasParticulares getClausulasParticulares() {
        return clausulasParticulares;
    }

    /**
     * Sets the value of the clausulasParticulares property.
     * 
     * @param value
     *     allowed object is
     *     {@link CondicionesParticularesType.ClausulasParticulares }
     *     
     */
    public void setClausulasParticulares(CondicionesParticularesType.ClausulasParticulares value) {
        this.clausulasParticulares = value;
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
     *         &lt;element name="Clausula" type="{}ClausulaType" maxOccurs="unbounded"/>
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
        "clausula"
    })
    public static class ClausulasParticulares {

        @XmlElement(name = "Clausula", required = true)
        protected List<ClausulaType> clausula;

        /**
         * Gets the value of the clausula property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clausula property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClausula().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClausulaType }
         * 
         * 
         */
        public List<ClausulaType> getClausula() {
            if (clausula == null) {
                clausula = new ArrayList<ClausulaType>();
            }
            return this.clausula;
        }

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
     *         &lt;element name="Pacto" type="{}PactoType" maxOccurs="unbounded"/>
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
        "pacto"
    })
    public static class Pactos {

        @XmlElement(name = "Pacto", required = true)
        protected List<PactoType> pacto;

        /**
         * Gets the value of the pacto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pacto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPacto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PactoType }
         * 
         * 
         */
        public List<PactoType> getPacto() {
            if (pacto == null) {
                pacto = new ArrayList<PactoType>();
            }
            return this.pacto;
        }

    }

}
