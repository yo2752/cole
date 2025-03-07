
package es.trafico.servicios.vehiculos.consulta.atex.webservices;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numReferencia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}ListaDatos" minOccurs="0"/&gt;
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
    "numReferencia",
    "listaDatos"
})
@XmlRootElement(name = "InformacionRespuesta")
public class InformacionRespuesta {

    @XmlElement(required = true)
    protected String numReferencia;
    @XmlElement(name = "ListaDatos")
    protected ListaDatos listaDatos;

    /**
     * Obtiene el valor de la propiedad numReferencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumReferencia() {
        return numReferencia;
    }

    /**
     * Define el valor de la propiedad numReferencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumReferencia(String value) {
        this.numReferencia = value;
    }

    /**
     * Obtiene el valor de la propiedad listaDatos.
     * 
     * @return
     *     possible object is
     *     {@link ListaDatos }
     *     
     */
    public ListaDatos getListaDatos() {
        return listaDatos;
    }

    /**
     * Define el valor de la propiedad listaDatos.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDatos }
     *     
     */
    public void setListaDatos(ListaDatos value) {
        this.listaDatos = value;
    }

}
