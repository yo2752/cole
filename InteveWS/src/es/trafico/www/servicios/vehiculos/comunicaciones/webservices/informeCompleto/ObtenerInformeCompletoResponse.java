
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerInformeCompletoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerInformeCompletoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto/respuesta}ResultadoInformeCompleto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerInformeCompletoResponse", propOrder = {
    "resultadoInformeCompleto"
})
public class ObtenerInformeCompletoResponse {

    @XmlElement(name = "ResultadoInformeCompleto", namespace = "http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto/respuesta")
    protected OutInformeCompleto resultadoInformeCompleto;

    /**
     * Obtiene el valor de la propiedad resultadoInformeCompleto.
     * 
     * @return
     *     possible object is
     *     {@link OutInformeCompleto }
     *     
     */
    public OutInformeCompleto getResultadoInformeCompleto() {
        return resultadoInformeCompleto;
    }

    /**
     * Define el valor de la propiedad resultadoInformeCompleto.
     * 
     * @param value
     *     allowed object is
     *     {@link OutInformeCompleto }
     *     
     */
    public void setResultadoInformeCompleto(OutInformeCompleto value) {
        this.resultadoInformeCompleto = value;
    }

}
