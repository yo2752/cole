
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="bastidor" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="doi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="nive" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    InInformeCompleto.class
})
public abstract class InServicioINTV {

    protected List<String> bastidor;
    @XmlElement(required = true)
    protected String doi;
    protected List<String> matricula;
    protected List<String> nive;

    /**
     * Gets the value of the bastidor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bastidor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBastidor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBastidor() {
        if (bastidor == null) {
            bastidor = new ArrayList<String>();
        }
        return this.bastidor;
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
     * Gets the value of the matricula property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matricula property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatricula().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMatricula() {
        if (matricula == null) {
            matricula = new ArrayList<String>();
        }
        return this.matricula;
    }

    /**
     * Gets the value of the nive property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nive property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNive().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNive() {
        if (nive == null) {
            nive = new ArrayList<String>();
        }
        return this.nive;
    }

}
