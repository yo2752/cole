
package es.trafico.servicios.vehiculos.consulta.atex.webservices;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}tuplaDatos" maxOccurs="unbounded"/&gt;
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
    "tuplaDatos"
})
@XmlRootElement(name = "ListaDatos")
public class ListaDatos {

    @XmlElement(required = true)
    protected List<TuplaDatos> tuplaDatos;

    /**
     * Gets the value of the tuplaDatos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tuplaDatos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTuplaDatos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TuplaDatos }
     * 
     * 
     */
    public List<TuplaDatos> getTuplaDatos() {
        if (tuplaDatos == null) {
            tuplaDatos = new ArrayList<TuplaDatos>();
        }
        return this.tuplaDatos;
    }

}
