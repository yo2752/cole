
package es.iam.sbae.sbintopexterna.latest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RespuestaPeticion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaPeticion"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="resultadoServicio" type="{http://sbintopexterna.sbae.iam.es}ResultadoServicio" minOccurs="0"/&gt;
 *         &lt;element name="respuestaServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaPeticion", propOrder = {
    "resultadoServicio",
    "respuestaServicio"
})
public class RespuestaPeticion {

    protected ResultadoServicio resultadoServicio;
    protected String respuestaServicio;

    /**
     * Obtiene el valor de la propiedad resultadoServicio.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoServicio }
     *     
     */
    public ResultadoServicio getResultadoServicio() {
        return resultadoServicio;
    }

    /**
     * Define el valor de la propiedad resultadoServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoServicio }
     *     
     */
    public void setResultadoServicio(ResultadoServicio value) {
        this.resultadoServicio = value;
    }

    /**
     * Obtiene el valor de la propiedad respuestaServicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespuestaServicio() {
        return respuestaServicio;
    }

    /**
     * Define el valor de la propiedad respuestaServicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespuestaServicio(String value) {
        this.respuestaServicio = value;
    }

}
