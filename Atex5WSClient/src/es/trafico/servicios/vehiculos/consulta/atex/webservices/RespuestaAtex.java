
package es.trafico.servicios.vehiculos.consulta.atex.webservices;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idOrganismoResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idResponsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}CriteriosConsultaVehiculo" minOccurs="0"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}CriteriosConsultaPersona" minOccurs="0"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}CriteriosConsultaDocumento" minOccurs="0"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}CriteriosConsultaEucaris" minOccurs="0"/&gt;
 *         &lt;element name="resultado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="respuesta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idUsuario",
    "idOrganismoResponsable",
    "idResponsable",
    "version",
    "criteriosConsultaVehiculo",
    "criteriosConsultaPersona",
    "criteriosConsultaDocumento",
    "criteriosConsultaEucaris",
    "resultado",
    "respuesta"
})
@XmlRootElement(name = "RespuestaAtex")
public class RespuestaAtex {

    @XmlElement(required = true)
    protected String idUsuario;
    protected String idOrganismoResponsable;
    @XmlElement(required = true)
    protected String idResponsable;
    @XmlElement(required = true)
    protected String version;
    @XmlElement(name = "CriteriosConsultaVehiculo")
    protected CriteriosConsultaVehiculo criteriosConsultaVehiculo;
    @XmlElement(name = "CriteriosConsultaPersona")
    protected CriteriosConsultaPersona criteriosConsultaPersona;
    @XmlElement(name = "CriteriosConsultaDocumento")
    protected CriteriosConsultaDocumento criteriosConsultaDocumento;
    @XmlElement(name = "CriteriosConsultaEucaris")
    protected CriteriosConsultaEucaris criteriosConsultaEucaris;
    @XmlElement(required = true)
    protected String resultado;
    @XmlElement(required = true)
    protected String respuesta;

    /**
     * Obtiene el valor de la propiedad idUsuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define el valor de la propiedad idUsuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

    /**
     * Obtiene el valor de la propiedad idOrganismoResponsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrganismoResponsable() {
        return idOrganismoResponsable;
    }

    /**
     * Define el valor de la propiedad idOrganismoResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrganismoResponsable(String value) {
        this.idOrganismoResponsable = value;
    }

    /**
     * Obtiene el valor de la propiedad idResponsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdResponsable() {
        return idResponsable;
    }

    /**
     * Define el valor de la propiedad idResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdResponsable(String value) {
        this.idResponsable = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtiene el valor de la propiedad criteriosConsultaVehiculo.
     * 
     * @return
     *     possible object is
     *     {@link CriteriosConsultaVehiculo }
     *     
     */
    public CriteriosConsultaVehiculo getCriteriosConsultaVehiculo() {
        return criteriosConsultaVehiculo;
    }

    /**
     * Define el valor de la propiedad criteriosConsultaVehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link CriteriosConsultaVehiculo }
     *     
     */
    public void setCriteriosConsultaVehiculo(CriteriosConsultaVehiculo value) {
        this.criteriosConsultaVehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad criteriosConsultaPersona.
     * 
     * @return
     *     possible object is
     *     {@link CriteriosConsultaPersona }
     *     
     */
    public CriteriosConsultaPersona getCriteriosConsultaPersona() {
        return criteriosConsultaPersona;
    }

    /**
     * Define el valor de la propiedad criteriosConsultaPersona.
     * 
     * @param value
     *     allowed object is
     *     {@link CriteriosConsultaPersona }
     *     
     */
    public void setCriteriosConsultaPersona(CriteriosConsultaPersona value) {
        this.criteriosConsultaPersona = value;
    }

    /**
     * Obtiene el valor de la propiedad criteriosConsultaDocumento.
     * 
     * @return
     *     possible object is
     *     {@link CriteriosConsultaDocumento }
     *     
     */
    public CriteriosConsultaDocumento getCriteriosConsultaDocumento() {
        return criteriosConsultaDocumento;
    }

    /**
     * Define el valor de la propiedad criteriosConsultaDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link CriteriosConsultaDocumento }
     *     
     */
    public void setCriteriosConsultaDocumento(CriteriosConsultaDocumento value) {
        this.criteriosConsultaDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad criteriosConsultaEucaris.
     * 
     * @return
     *     possible object is
     *     {@link CriteriosConsultaEucaris }
     *     
     */
    public CriteriosConsultaEucaris getCriteriosConsultaEucaris() {
        return criteriosConsultaEucaris;
    }

    /**
     * Define el valor de la propiedad criteriosConsultaEucaris.
     * 
     * @param value
     *     allowed object is
     *     {@link CriteriosConsultaEucaris }
     *     
     */
    public void setCriteriosConsultaEucaris(CriteriosConsultaEucaris value) {
        this.criteriosConsultaEucaris = value;
    }

    /**
     * Obtiene el valor de la propiedad resultado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Define el valor de la propiedad resultado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultado(String value) {
        this.resultado = value;
    }

    /**
     * Obtiene el valor de la propiedad respuesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Define el valor de la propiedad respuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespuesta(String value) {
        this.respuesta = value;
    }

}
