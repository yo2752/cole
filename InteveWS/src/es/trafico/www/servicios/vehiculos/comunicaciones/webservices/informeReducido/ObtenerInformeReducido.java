
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerInformeReducido complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerInformeReducido"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="solicitudInforme" type="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido}inInformeReducido" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerInformeReducido", propOrder = {
    "solicitudInforme"
})
public class ObtenerInformeReducido {

    protected InInformeReducido solicitudInforme;

    /**
     * Obtiene el valor de la propiedad solicitudInforme.
     * 
     * @return
     *     possible object is
     *     {@link InInformeReducido }
     *     
     */
    public InInformeReducido getSolicitudInforme() {
        return solicitudInforme;
    }

    /**
     * Define el valor de la propiedad solicitudInforme.
     * 
     * @param value
     *     allowed object is
     *     {@link InInformeReducido }
     *     
     */
    public void setSolicitudInforme(InInformeReducido value) {
        this.solicitudInforme = value;
    }

}
