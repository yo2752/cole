//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}DATOS_GESTORIA"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "CABECERA")
public class CABECERA {

    @XmlElement(name = "DATOS_GESTORIA", required = true)
    protected DATOSGESTORIA datosgestoria;

    /**
     * Obtiene el valor de la propiedad datosgestoria.
     * 
     * @return
     *     possible object is
     *     {@link DATOSGESTORIA }
     *     
     */
    public DATOSGESTORIA getDATOSGESTORIA() {
        return datosgestoria;
    }

    /**
     * Define el valor de la propiedad datosgestoria.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSGESTORIA }
     *     
     */
    public void setDATOSGESTORIA(DATOSGESTORIA value) {
        this.datosgestoria = value;
    }

}
