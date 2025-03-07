//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}NIF"/>
 *         &lt;element ref="{}NOMBRE"/>
 *         &lt;element ref="{}PROFESIONAL"/>
 *         &lt;element ref="{}PROVINCIA"/>
 *         &lt;element ref="{}TIPO_DGT" minOccurs="0"/>
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
@XmlRootElement(name = "DATOS_GESTORIA")
public class DATOSGESTORIA {

    @XmlElement(name = "NIF", required = true)
    protected String nif;
    @XmlElement(name = "NOMBRE", required = true)
    protected String nombre;
    @XmlElement(name = "PROFESIONAL", required = true)
    protected String profesional;
    @XmlElement(name = "PROVINCIA", required = true)
    protected String provincia;
    @XmlElement(name = "TIPO_DGT")
    protected String tipodgt;

    /**
     * Obtiene el valor de la propiedad nif.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIF() {
        return nif;
    }

    /**
     * Define el valor de la propiedad nif.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIF(String value) {
        this.nif = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBRE() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBRE(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad profesional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROFESIONAL() {
        return profesional;
    }

    /**
     * Define el valor de la propiedad profesional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROFESIONAL(String value) {
        this.profesional = value;
    }

    /**
     * Obtiene el valor de la propiedad provincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Define el valor de la propiedad provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodgt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODGT() {
        return tipodgt;
    }

    /**
     * Define el valor de la propiedad tipodgt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODGT(String value) {
        this.tipodgt = value;
    }

}
