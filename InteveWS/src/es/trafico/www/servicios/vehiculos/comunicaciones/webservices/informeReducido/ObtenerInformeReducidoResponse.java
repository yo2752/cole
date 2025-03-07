
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerInformeReducidoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerInformeReducidoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido/respuesta}ResultadoInformeReducido" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerInformeReducidoResponse", propOrder = {
    "resultadoInformeReducido"
})
public class ObtenerInformeReducidoResponse {

    @XmlElement(name = "ResultadoInformeReducido", namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido/respuesta")
    protected OutInformeReducido resultadoInformeReducido;

    /**
     * Obtiene el valor de la propiedad resultadoInformeReducido.
     * 
     * @return
     *     possible object is
     *     {@link OutInformeReducido }
     *     
     */
    public OutInformeReducido getResultadoInformeReducido() {
        return resultadoInformeReducido;
    }

    /**
     * Define el valor de la propiedad resultadoInformeReducido.
     * 
     * @param value
     *     allowed object is
     *     {@link OutInformeReducido }
     *     
     */
    public void setResultadoInformeReducido(OutInformeReducido value) {
        this.resultadoInformeReducido = value;
    }

}
