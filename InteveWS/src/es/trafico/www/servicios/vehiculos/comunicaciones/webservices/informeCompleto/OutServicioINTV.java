
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para outServicioINTV complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="outServicioINTV"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="resultadoComunicacion" type="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto}resultadoComunicacion" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outServicioINTV", propOrder = {
    "resultadoComunicacion"
})
@XmlSeeAlso({
    OutInformeCompleto.class
})
public abstract class OutServicioINTV {

    protected ResultadoComunicacion resultadoComunicacion;

    /**
     * Obtiene el valor de la propiedad resultadoComunicacion.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoComunicacion }
     *     
     */
    public ResultadoComunicacion getResultadoComunicacion() {
        return resultadoComunicacion;
    }

    /**
     * Define el valor de la propiedad resultadoComunicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoComunicacion }
     *     
     */
    public void setResultadoComunicacion(ResultadoComunicacion value) {
        this.resultadoComunicacion = value;
    }

}
