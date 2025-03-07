//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.01 at 05:01:41 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.corpme;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Tipo_Documento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Firma_Electronica" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\c{1}"/>
 *               &lt;enumeration value="S"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ficheros">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Fichero" type="{}FicheroType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Numero_Ficheros" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoType", propOrder = {

})
public class DocumentoType {

    @XmlElement(name = "Tipo_Documento")
    protected String tipoDocumento;
    @XmlElement(name = "Descripcion")
    protected String descripcion;
    @XmlElement(name = "Firma_Electronica")
    protected String firmaElectronica;
    @XmlElement(name = "Ficheros", required = true)
    protected DocumentoType.Ficheros ficheros;

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the firmaElectronica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmaElectronica() {
        return firmaElectronica;
    }

    /**
     * Sets the value of the firmaElectronica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmaElectronica(String value) {
        this.firmaElectronica = value;
    }

    /**
     * Gets the value of the ficheros property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoType.Ficheros }
     *     
     */
    public DocumentoType.Ficheros getFicheros() {
        return ficheros;
    }

    /**
     * Sets the value of the ficheros property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoType.Ficheros }
     *     
     */
    public void setFicheros(DocumentoType.Ficheros value) {
        this.ficheros = value;
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
     *         &lt;element name="Fichero" type="{}FicheroType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *       &lt;attribute name="Numero_Ficheros" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fichero"
    })
    public static class Ficheros {

        @XmlElement(name = "Fichero", required = true)
        protected List<FicheroType> fichero;
        @XmlAttribute(name = "Numero_Ficheros", required = true)
        protected BigInteger numeroFicheros;

        /**
         * Gets the value of the fichero property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fichero property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFichero().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FicheroType }
         * 
         * 
         */
        public List<FicheroType> getFichero() {
            if (fichero == null) {
                fichero = new ArrayList<FicheroType>();
            }
            return this.fichero;
        }

        /**
         * Gets the value of the numeroFicheros property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNumeroFicheros() {
            return numeroFicheros;
        }

        /**
         * Sets the value of the numeroFicheros property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNumeroFicheros(BigInteger value) {
            this.numeroFicheros = value;
        }

    }

}
