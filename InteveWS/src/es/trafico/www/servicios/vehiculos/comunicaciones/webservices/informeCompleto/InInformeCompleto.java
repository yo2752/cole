
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeCompleto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para inInformeCompleto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="inInformeCompleto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeCompleto}inServicioINTV"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codInforme" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="motivoSolicitud" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numTasa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inInformeCompleto", propOrder = {
    "codInforme",
    "motivoSolicitud",
    "numTasa"
})
public class InInformeCompleto
    extends InServicioINTV
{

    protected int codInforme;
    protected int motivoSolicitud;
    @XmlElement(required = true)
    protected String numTasa;

    /**
     * Obtiene el valor de la propiedad codInforme.
     * 
     */
    public int getCodInforme() {
        return codInforme;
    }

    /**
     * Define el valor de la propiedad codInforme.
     * 
     */
    public void setCodInforme(int value) {
        this.codInforme = value;
    }

    /**
     * Obtiene el valor de la propiedad motivoSolicitud.
     * 
     */
    public int getMotivoSolicitud() {
        return motivoSolicitud;
    }

    /**
     * Define el valor de la propiedad motivoSolicitud.
     * 
     */
    public void setMotivoSolicitud(int value) {
        this.motivoSolicitud = value;
    }

    /**
     * Obtiene el valor de la propiedad numTasa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTasa() {
        return numTasa;
    }

    /**
     * Define el valor de la propiedad numTasa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTasa(String value) {
        this.numTasa = value;
    }

}
