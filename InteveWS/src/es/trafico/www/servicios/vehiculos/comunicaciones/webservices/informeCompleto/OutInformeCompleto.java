
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para outInformeCompleto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="outInformeCompleto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto}outServicioINTV"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="informePdf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outInformeCompleto", propOrder = {
    "informePdf"
})
public class OutInformeCompleto
    extends OutServicioINTV
{

    protected String informePdf;

    /**
     * Obtiene el valor de la propiedad informePdf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformePdf() {
        return informePdf;
    }

    /**
     * Define el valor de la propiedad informePdf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformePdf(String value) {
        this.informePdf = value;
    }

}
