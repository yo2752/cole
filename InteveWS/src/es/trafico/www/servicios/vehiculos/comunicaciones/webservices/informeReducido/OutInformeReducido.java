
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para outInformeReducido complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="outInformeReducido"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido}outServicioINTV"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bastidor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="combustible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estadoVerificacion" type="{http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/informeReducido}resultadoVerificacionVehiculo" minOccurs="0"/&gt;
 *         &lt;element name="fechaPrimeraMatricula" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="fechaSolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outInformeReducido", propOrder = {
    "bastidor",
    "combustible",
    "estadoVerificacion",
    "fechaPrimeraMatricula",
    "fechaSolicitud",
    "marca",
    "matricula",
    "modelo",
    "nive"
})
public class OutInformeReducido
    extends OutServicioINTV
{

    protected String bastidor;
    protected String combustible;
    @XmlSchemaType(name = "string")
    protected ResultadoVerificacionVehiculo estadoVerificacion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaPrimeraMatricula;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaSolicitud;
    protected String marca;
    protected String matricula;
    protected String modelo;
    protected String nive;

    /**
     * Obtiene el valor de la propiedad bastidor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBastidor() {
        return bastidor;
    }

    /**
     * Define el valor de la propiedad bastidor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBastidor(String value) {
        this.bastidor = value;
    }

    /**
     * Obtiene el valor de la propiedad combustible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombustible() {
        return combustible;
    }

    /**
     * Define el valor de la propiedad combustible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombustible(String value) {
        this.combustible = value;
    }

    /**
     * Obtiene el valor de la propiedad estadoVerificacion.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoVerificacionVehiculo }
     *     
     */
    public ResultadoVerificacionVehiculo getEstadoVerificacion() {
        return estadoVerificacion;
    }

    /**
     * Define el valor de la propiedad estadoVerificacion.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoVerificacionVehiculo }
     *     
     */
    public void setEstadoVerificacion(ResultadoVerificacionVehiculo value) {
        this.estadoVerificacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPrimeraMatricula.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPrimeraMatricula() {
        return fechaPrimeraMatricula;
    }

    /**
     * Define el valor de la propiedad fechaPrimeraMatricula.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPrimeraMatricula(XMLGregorianCalendar value) {
        this.fechaPrimeraMatricula = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Define el valor de la propiedad fechaSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaSolicitud(XMLGregorianCalendar value) {
        this.fechaSolicitud = value;
    }

    /**
     * Obtiene el valor de la propiedad marca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define el valor de la propiedad marca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarca(String value) {
        this.marca = value;
    }

    /**
     * Obtiene el valor de la propiedad matricula.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Define el valor de la propiedad matricula.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricula(String value) {
        this.matricula = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define el valor de la propiedad modelo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelo(String value) {
        this.modelo = value;
    }

    /**
     * Obtiene el valor de la propiedad nive.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNive() {
        return nive;
    }

    /**
     * Define el valor de la propiedad nive.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNive(String value) {
        this.nive = value;
    }

}
