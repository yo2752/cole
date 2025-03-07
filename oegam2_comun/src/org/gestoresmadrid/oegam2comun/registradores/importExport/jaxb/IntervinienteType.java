//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.29 at 01:11:02 PM CEST 
//


package org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntervinienteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntervinienteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Iden_Sujeto" type="{}Iden_SujetoType" minOccurs="0"/>
 *         &lt;element name="Domicilio" type="{}Domicilio_INEType" minOccurs="0"/>
 *         &lt;element name="Correo_Electronico" type="{}Correo_ElectronicoType" minOccurs="0"/>
 *         &lt;element name="Datos_Registrales_Mercantil" type="{}Datos_Registrales_Mercantil2Type" minOccurs="0"/>
 *         &lt;element name="Representante" type="{}Representante2Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervinienteType", propOrder = {
    "idenSujeto",
    "domicilio",
    "correoElectronico",
    "datosRegistralesMercantil",
    "representante",
    "telefono"
})
public class IntervinienteType {

    @XmlElement(name = "Iden_Sujeto")
    protected IdenSujetoType idenSujeto;
    @XmlElement(name = "Domicilio")
    protected DomicilioINEType domicilio;
    @XmlElement(name = "Correo_Electronico")
    protected String correoElectronico;
    @XmlElement(name = "Datos_Registrales_Mercantil")
    protected DatosRegistralesMercantil2Type datosRegistralesMercantil;
    @XmlElement(name = "Representante")
    protected List<Representante2Type> representante;
    @XmlElement(name = "Telefono")
    protected String telefono;

    /**
     * Gets the value of the idenSujeto property.
     * 
     * @return
     *     possible object is
     *     {@link IdenSujetoType }
     *     
     */
    public IdenSujetoType getIdenSujeto() {
        return idenSujeto;
    }

    /**
     * Sets the value of the idenSujeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdenSujetoType }
     *     
     */
    public void setIdenSujeto(IdenSujetoType value) {
        this.idenSujeto = value;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link DomicilioINEType }
     *     
     */
    public DomicilioINEType getDomicilio() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomicilioINEType }
     *     
     */
    public void setDomicilio(DomicilioINEType value) {
        this.domicilio = value;
    }

    /**
     * Gets the value of the correoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Sets the value of the correoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreoElectronico(String value) {
        this.correoElectronico = value;
    }

    /**
     * Gets the value of the datosRegistralesMercantil property.
     * 
     * @return
     *     possible object is
     *     {@link DatosRegistralesMercantil2Type }
     *     
     */
    public DatosRegistralesMercantil2Type getDatosRegistralesMercantil() {
        return datosRegistralesMercantil;
    }

    /**
     * Sets the value of the datosRegistralesMercantil property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosRegistralesMercantil2Type }
     *     
     */
    public void setDatosRegistralesMercantil(DatosRegistralesMercantil2Type value) {
        this.datosRegistralesMercantil = value;
    }

    /**
     * Gets the value of the representante property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the representante property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepresentante().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Representante2Type }
     * 
     * 
     */
    public List<Representante2Type> getRepresentante() {
        if (representante == null) {
            representante = new ArrayList<Representante2Type>();
        }
        return this.representante;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

}
