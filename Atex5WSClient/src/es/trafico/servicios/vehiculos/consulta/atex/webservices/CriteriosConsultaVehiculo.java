
package es.trafico.servicios.vehiculos.consulta.atex.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bastidor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "", propOrder = {
    "matricula",
    "bastidor",
    "nive"
})
@XmlRootElement(name = "CriteriosConsultaVehiculo")
public class CriteriosConsultaVehiculo {

    protected String matricula;
    protected String bastidor;
    protected String nive;

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
