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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;choice minOccurs="0">
 *         &lt;element name="Aviso_Contenido" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="Notificacion" type="{}AvisoType"/>
 *                   &lt;element name="Comunicacion" type="{}AvisoType"/>
 *                   &lt;element name="Minuta" type="{}MinutaType"/>
 *                   &lt;element name="Factura" type="{}FacturaType"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="Tipo_Contenido" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;enumeration value="1"/>
 *                       &lt;enumeration value="2"/>
 *                       &lt;enumeration value="3"/>
 *                       &lt;enumeration value="4"/>
 *                       &lt;enumeration value="5"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="Identificador_Contenido" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="Id_Tramite_Origen">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Peticion_Contenido" type="{}Peticion_ContenidoType" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *       &lt;attribute name="Version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Tipo_Mensaje" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="ACR"/>
 *             &lt;enumeration value="PCR"/>
 *             &lt;enumeration value="PDDR"/>
 *             &lt;enumeration value="RNR"/>
 *             &lt;enumeration value="AC"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Tipo_Entidad_Destinatario">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="1"/>
 *             &lt;enumeration value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Codigo_Entidad_Destinatario" type="{}EntidadType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "avisoContenido",
    "peticionContenido"
})
@XmlRootElement(name = "Datos_GI_CORPME")
public class DatosGICORPME {

    @XmlElement(name = "Aviso_Contenido")
    protected List<DatosGICORPME.AvisoContenido> avisoContenido;
    @XmlElement(name = "Peticion_Contenido")
    protected List<PeticionContenidoType> peticionContenido;
    @XmlAttribute(name = "Version")
    protected String version;
    @XmlAttribute(name = "Tipo_Mensaje", required = true)
    protected String tipoMensaje;
    @XmlAttribute(name = "Tipo_Entidad_Destinatario")
    protected String tipoEntidadDestinatario;
    @XmlAttribute(name = "Codigo_Entidad_Destinatario")
    protected String codigoEntidadDestinatario;

    /**
     * Gets the value of the avisoContenido property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the avisoContenido property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvisoContenido().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatosGICORPME.AvisoContenido }
     * 
     * 
     */
    public List<DatosGICORPME.AvisoContenido> getAvisoContenido() {
        if (avisoContenido == null) {
            avisoContenido = new ArrayList<DatosGICORPME.AvisoContenido>();
        }
        return this.avisoContenido;
    }

    /**
     * Gets the value of the peticionContenido property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the peticionContenido property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeticionContenido().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PeticionContenidoType }
     * 
     * 
     */
    public List<PeticionContenidoType> getPeticionContenido() {
        if (peticionContenido == null) {
            peticionContenido = new ArrayList<PeticionContenidoType>();
        }
        return this.peticionContenido;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the tipoMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * Sets the value of the tipoMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMensaje(String value) {
        this.tipoMensaje = value;
    }

    /**
     * Gets the value of the tipoEntidadDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEntidadDestinatario() {
        return tipoEntidadDestinatario;
    }

    /**
     * Sets the value of the tipoEntidadDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEntidadDestinatario(String value) {
        this.tipoEntidadDestinatario = value;
    }

    /**
     * Gets the value of the codigoEntidadDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEntidadDestinatario() {
        return codigoEntidadDestinatario;
    }

    /**
     * Sets the value of the codigoEntidadDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEntidadDestinatario(String value) {
        this.codigoEntidadDestinatario = value;
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
     *       &lt;choice>
     *         &lt;element name="Notificacion" type="{}AvisoType"/>
     *         &lt;element name="Comunicacion" type="{}AvisoType"/>
     *         &lt;element name="Minuta" type="{}MinutaType"/>
     *         &lt;element name="Factura" type="{}FacturaType"/>
     *       &lt;/choice>
     *       &lt;attribute name="Tipo_Contenido" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;enumeration value="1"/>
     *             &lt;enumeration value="2"/>
     *             &lt;enumeration value="3"/>
     *             &lt;enumeration value="4"/>
     *             &lt;enumeration value="5"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="Identificador_Contenido" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="Id_Tramite_Origen">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;minLength value="1"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "notificacion",
        "comunicacion",
        "minuta",
        "factura"
    })
    public static class AvisoContenido {

        @XmlElement(name = "Notificacion")
        protected AvisoType notificacion;
        @XmlElement(name = "Comunicacion")
        protected AvisoType comunicacion;
        @XmlElement(name = "Minuta")
        protected MinutaType minuta;
        @XmlElement(name = "Factura")
        protected FacturaType factura;
        @XmlAttribute(name = "Tipo_Contenido", required = true)
        protected BigInteger tipoContenido;
        @XmlAttribute(name = "Identificador_Contenido", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String identificadorContenido;
        @XmlAttribute(name = "Id_Tramite_Origen")
        protected String idTramiteOrigen;

        /**
         * Gets the value of the notificacion property.
         * 
         * @return
         *     possible object is
         *     {@link AvisoType }
         *     
         */
        public AvisoType getNotificacion() {
            return notificacion;
        }

        /**
         * Sets the value of the notificacion property.
         * 
         * @param value
         *     allowed object is
         *     {@link AvisoType }
         *     
         */
        public void setNotificacion(AvisoType value) {
            this.notificacion = value;
        }

        /**
         * Gets the value of the comunicacion property.
         * 
         * @return
         *     possible object is
         *     {@link AvisoType }
         *     
         */
        public AvisoType getComunicacion() {
            return comunicacion;
        }

        /**
         * Sets the value of the comunicacion property.
         * 
         * @param value
         *     allowed object is
         *     {@link AvisoType }
         *     
         */
        public void setComunicacion(AvisoType value) {
            this.comunicacion = value;
        }

        /**
         * Gets the value of the minuta property.
         * 
         * @return
         *     possible object is
         *     {@link MinutaType }
         *     
         */
        public MinutaType getMinuta() {
            return minuta;
        }

        /**
         * Sets the value of the minuta property.
         * 
         * @param value
         *     allowed object is
         *     {@link MinutaType }
         *     
         */
        public void setMinuta(MinutaType value) {
            this.minuta = value;
        }

        /**
         * Gets the value of the factura property.
         * 
         * @return
         *     possible object is
         *     {@link FacturaType }
         *     
         */
        public FacturaType getFactura() {
            return factura;
        }

        /**
         * Sets the value of the factura property.
         * 
         * @param value
         *     allowed object is
         *     {@link FacturaType }
         *     
         */
        public void setFactura(FacturaType value) {
            this.factura = value;
        }

        /**
         * Gets the value of the tipoContenido property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTipoContenido() {
            return tipoContenido;
        }

        /**
         * Sets the value of the tipoContenido property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTipoContenido(BigInteger value) {
            this.tipoContenido = value;
        }

        /**
         * Gets the value of the identificadorContenido property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificadorContenido() {
            return identificadorContenido;
        }

        /**
         * Sets the value of the identificadorContenido property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificadorContenido(String value) {
            this.identificadorContenido = value;
        }

        /**
         * Gets the value of the idTramiteOrigen property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdTramiteOrigen() {
            return idTramiteOrigen;
        }

        /**
         * Sets the value of the idTramiteOrigen property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdTramiteOrigen(String value) {
            this.idTramiteOrigen = value;
        }

    }

}
