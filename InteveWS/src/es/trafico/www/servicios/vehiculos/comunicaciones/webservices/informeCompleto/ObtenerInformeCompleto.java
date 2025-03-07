
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerInformeCompleto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerInformeCompleto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="solicitudInforme" type="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto}inInformeCompleto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerInformeCompleto", propOrder = {
    "solicitudInforme"
})
public class ObtenerInformeCompleto {

    protected InInformeCompleto solicitudInforme;

    /**
     * Obtiene el valor de la propiedad solicitudInforme.
     * 
     * @return
     *     possible object is
     *     {@link InInformeCompleto }
     *     
     */
    public InInformeCompleto getSolicitudInforme() {
        return solicitudInforme;
    }

    /**
     * Define el valor de la propiedad solicitudInforme.
     * 
     * @param value
     *     allowed object is
     *     {@link InInformeCompleto }
     *     
     */
    public void setSolicitudInforme(InInformeCompleto value) {
        this.solicitudInforme = value;
    }

}
