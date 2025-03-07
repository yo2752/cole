
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para inServicioINTV complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="inServicioINTV"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bastidor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="doi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inServicioINTV", propOrder = {
    "bastidor",
    "doi",
    "matricula",
    "nive"
})
@XmlSeeAlso({
    InInformeReducido.class
})
public abstract class InServicioINTV {

    protected String bastidor;
    protected String doi;
    protected String matricula;
    protected String nive;

    /**
     * Obtiene el valor de la propiedad bastidor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBastidor() {
        return bastidor;
    }

    /**
     * Define el valor de la propiedad bastidor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBastidor(String value) {
        this.bastidor = value;
    }

    /**
     * Obtiene el valor de la propiedad doi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Define el valor de la propiedad doi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoi(String value) {
        this.doi = value;
    }

    /**
     * Obtiene el valor de la propiedad matricula.
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
     * Define el valor de la propiedad matricula.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricula(String value) {
        this.matricula = value;
    }

    /**
     * Obtiene el valor de la propiedad nive.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNive() {
        return nive;
    }

    /**
     * Define el valor de la propiedad nive.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNive(String value) {
        this.nive = value;
    }

}
