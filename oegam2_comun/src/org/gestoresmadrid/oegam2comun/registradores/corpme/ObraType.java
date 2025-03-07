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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObraType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObraType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Titulos">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Titulo" type="{}Titulo_ObraType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Sinopsis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Productores">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Productor" type="{}Identificacion_SujetoType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Fecha_Establecimiento" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\d{2}/\d{2}/\d{4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Fecha_Primera_Divulgacion" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\d{2}/\d{2}/\d{4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Pais_Primera_Divulgacion" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\c{2}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Lengua" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Intervinientes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Interpretes">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Interprete" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="Nombre" type="{}Identificacion_PersonaType"/>
 *                                       &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Directores">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Director" type="{}Identificacion_PersonaType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Duracion" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObraType", propOrder = {

})
public class ObraType {

    @XmlElement(name = "Titulos", required = true)
    protected ObraType.Titulos titulos;
    @XmlElement(name = "Sinopsis", required = true)
    protected String sinopsis;
    @XmlElement(name = "Productores", required = true)
    protected ObraType.Productores productores;
    @XmlElement(name = "Fecha_Establecimiento")
    protected String fechaEstablecimiento;
    @XmlElement(name = "Fecha_Primera_Divulgacion")
    protected String fechaPrimeraDivulgacion;
    @XmlElement(name = "Pais_Primera_Divulgacion")
    protected String paisPrimeraDivulgacion;
    @XmlElement(name = "Lengua")
    protected String lengua;
    @XmlElement(name = "Intervinientes", required = true)
    protected ObraType.Intervinientes intervinientes;
    @XmlElement(name = "Duracion", required = true)
    protected BigInteger duracion;

    /**
     * Gets the value of the titulos property.
     * 
     * @return
     *     possible object is
     *     {@link ObraType.Titulos }
     *     
     */
    public ObraType.Titulos getTitulos() {
        return titulos;
    }

    /**
     * Sets the value of the titulos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObraType.Titulos }
     *     
     */
    public void setTitulos(ObraType.Titulos value) {
        this.titulos = value;
    }

    /**
     * Gets the value of the sinopsis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Sets the value of the sinopsis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSinopsis(String value) {
        this.sinopsis = value;
    }

    /**
     * Gets the value of the productores property.
     * 
     * @return
     *     possible object is
     *     {@link ObraType.Productores }
     *     
     */
    public ObraType.Productores getProductores() {
        return productores;
    }

    /**
     * Sets the value of the productores property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObraType.Productores }
     *     
     */
    public void setProductores(ObraType.Productores value) {
        this.productores = value;
    }

    /**
     * Gets the value of the fechaEstablecimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaEstablecimiento() {
        return fechaEstablecimiento;
    }

    /**
     * Sets the value of the fechaEstablecimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaEstablecimiento(String value) {
        this.fechaEstablecimiento = value;
    }

    /**
     * Gets the value of the fechaPrimeraDivulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaPrimeraDivulgacion() {
        return fechaPrimeraDivulgacion;
    }

    /**
     * Sets the value of the fechaPrimeraDivulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaPrimeraDivulgacion(String value) {
        this.fechaPrimeraDivulgacion = value;
    }

    /**
     * Gets the value of the paisPrimeraDivulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisPrimeraDivulgacion() {
        return paisPrimeraDivulgacion;
    }

    /**
     * Sets the value of the paisPrimeraDivulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisPrimeraDivulgacion(String value) {
        this.paisPrimeraDivulgacion = value;
    }

    /**
     * Gets the value of the lengua property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLengua() {
        return lengua;
    }

    /**
     * Sets the value of the lengua property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLengua(String value) {
        this.lengua = value;
    }

    /**
     * Gets the value of the intervinientes property.
     * 
     * @return
     *     possible object is
     *     {@link ObraType.Intervinientes }
     *     
     */
    public ObraType.Intervinientes getIntervinientes() {
        return intervinientes;
    }

    /**
     * Sets the value of the intervinientes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObraType.Intervinientes }
     *     
     */
    public void setIntervinientes(ObraType.Intervinientes value) {
        this.intervinientes = value;
    }

    /**
     * Gets the value of the duracion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuracion() {
        return duracion;
    }

    /**
     * Sets the value of the duracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuracion(BigInteger value) {
        this.duracion = value;
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
     *         &lt;element name="Interpretes">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Interprete" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="Nombre" type="{}Identificacion_PersonaType"/>
     *                             &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Directores">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Director" type="{}Identificacion_PersonaType" maxOccurs="unbounded"/>
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
    @XmlType(name = "", propOrder = {
        "interpretes",
        "directores"
    })
    public static class Intervinientes {

        @XmlElement(name = "Interpretes", required = true)
        protected ObraType.Intervinientes.Interpretes interpretes;
        @XmlElement(name = "Directores", required = true)
        protected ObraType.Intervinientes.Directores directores;

        /**
         * Gets the value of the interpretes property.
         * 
         * @return
         *     possible object is
         *     {@link ObraType.Intervinientes.Interpretes }
         *     
         */
        public ObraType.Intervinientes.Interpretes getInterpretes() {
            return interpretes;
        }

        /**
         * Sets the value of the interpretes property.
         * 
         * @param value
         *     allowed object is
         *     {@link ObraType.Intervinientes.Interpretes }
         *     
         */
        public void setInterpretes(ObraType.Intervinientes.Interpretes value) {
            this.interpretes = value;
        }

        /**
         * Gets the value of the directores property.
         * 
         * @return
         *     possible object is
         *     {@link ObraType.Intervinientes.Directores }
         *     
         */
        public ObraType.Intervinientes.Directores getDirectores() {
            return directores;
        }

        /**
         * Sets the value of the directores property.
         * 
         * @param value
         *     allowed object is
         *     {@link ObraType.Intervinientes.Directores }
         *     
         */
        public void setDirectores(ObraType.Intervinientes.Directores value) {
            this.directores = value;
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
         *         &lt;element name="Director" type="{}Identificacion_PersonaType" maxOccurs="unbounded"/>
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
            "director"
        })
        public static class Directores {

            @XmlElement(name = "Director", required = true)
            protected List<IdentificacionPersonaType> director;

            /**
             * Gets the value of the director property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the director property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDirector().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link IdentificacionPersonaType }
             * 
             * 
             */
            public List<IdentificacionPersonaType> getDirector() {
                if (director == null) {
                    director = new ArrayList<IdentificacionPersonaType>();
                }
                return this.director;
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
         *         &lt;element name="Interprete" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="Nombre" type="{}Identificacion_PersonaType"/>
         *                   &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/all>
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
        @XmlType(name = "", propOrder = {
            "interprete"
        })
        public static class Interpretes {

            @XmlElement(name = "Interprete", required = true)
            protected List<ObraType.Intervinientes.Interpretes.Interprete> interprete;

            /**
             * Gets the value of the interprete property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the interprete property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getInterprete().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ObraType.Intervinientes.Interpretes.Interprete }
             * 
             * 
             */
            public List<ObraType.Intervinientes.Interpretes.Interprete> getInterprete() {
                if (interprete == null) {
                    interprete = new ArrayList<ObraType.Intervinientes.Interpretes.Interprete>();
                }
                return this.interprete;
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
             *       &lt;all>
             *         &lt;element name="Nombre" type="{}Identificacion_PersonaType"/>
             *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/all>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {

            })
            public static class Interprete {

                @XmlElement(name = "Nombre", required = true)
                protected IdentificacionPersonaType nombre;
                @XmlElement(name = "Tipo", required = true)
                protected String tipo;

                /**
                 * Gets the value of the nombre property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link IdentificacionPersonaType }
                 *     
                 */
                public IdentificacionPersonaType getNombre() {
                    return nombre;
                }

                /**
                 * Sets the value of the nombre property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link IdentificacionPersonaType }
                 *     
                 */
                public void setNombre(IdentificacionPersonaType value) {
                    this.nombre = value;
                }

                /**
                 * Gets the value of the tipo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipo() {
                    return tipo;
                }

                /**
                 * Sets the value of the tipo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipo(String value) {
                    this.tipo = value;
                }

            }

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
     *         &lt;element name="Productor" type="{}Identificacion_SujetoType" maxOccurs="unbounded"/>
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
        "productor"
    })
    public static class Productores {

        @XmlElement(name = "Productor", required = true)
        protected List<IdentificacionSujetoType> productor;

        /**
         * Gets the value of the productor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdentificacionSujetoType }
         * 
         * 
         */
        public List<IdentificacionSujetoType> getProductor() {
            if (productor == null) {
                productor = new ArrayList<IdentificacionSujetoType>();
            }
            return this.productor;
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
     *         &lt;element name="Titulo" type="{}Titulo_ObraType" maxOccurs="unbounded"/>
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
        "titulo"
    })
    public static class Titulos {

        @XmlElement(name = "Titulo", required = true)
        protected List<TituloObraType> titulo;

        /**
         * Gets the value of the titulo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the titulo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTitulo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TituloObraType }
         * 
         * 
         */
        public List<TituloObraType> getTitulo() {
            if (titulo == null) {
                titulo = new ArrayList<TituloObraType>();
            }
            return this.titulo;
        }

    }

}
