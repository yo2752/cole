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
 *         &lt;element ref="{}TIPO_LIMITACION"/>
 *         &lt;element ref="{}FECHA_LIMITACION"/>
 *         &lt;element ref="{}NUMERO_REGISTRO_LIMITACION"/>
 *         &lt;element ref="{}FINANCIERA_LIMITACION"/>
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
@XmlRootElement(name = "DATOS_LIMITACION")
public class DATOSLIMITACION {

    @XmlElement(name = "TIPO_LIMITACION", required = true)
    protected String tipolimitacion;
    @XmlElement(name = "FECHA_LIMITACION", required = true)
    protected String fechalimitacion;
    @XmlElement(name = "NUMERO_REGISTRO_LIMITACION", required = true)
    protected String numeroregistrolimitacion;
    @XmlElement(name = "FINANCIERA_LIMITACION", required = true)
    protected String financieralimitacion;

    /**
     * Obtiene el valor de la propiedad tipolimitacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOLIMITACION() {
        return tipolimitacion;
    }

    /**
     * Define el valor de la propiedad tipolimitacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOLIMITACION(String value) {
        this.tipolimitacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechalimitacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHALIMITACION() {
        return fechalimitacion;
    }

    /**
     * Define el valor de la propiedad fechalimitacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHALIMITACION(String value) {
        this.fechalimitacion = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroregistrolimitacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROREGISTROLIMITACION() {
        return numeroregistrolimitacion;
    }

    /**
     * Define el valor de la propiedad numeroregistrolimitacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROREGISTROLIMITACION(String value) {
        this.numeroregistrolimitacion = value;
    }

    /**
     * Obtiene el valor de la propiedad financieralimitacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFINANCIERALIMITACION() {
        return financieralimitacion;
    }

    /**
     * Define el valor de la propiedad financieralimitacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFINANCIERALIMITACION(String value) {
        this.financieralimitacion = value;
    }

}
