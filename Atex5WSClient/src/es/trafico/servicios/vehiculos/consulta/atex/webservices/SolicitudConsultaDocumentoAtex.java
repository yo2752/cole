
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
 *         &lt;element name="tasa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://webservices.atex.consulta.vehiculos.servicios.trafico.es}CriteriosConsultaDocumento"/&gt;
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
    "tasa",
    "criteriosConsultaDocumento"
})
@XmlRootElement(name = "SolicitudConsultaDocumentoAtex")
public class SolicitudConsultaDocumentoAtex {

    @XmlElement(required = true)
    protected String idUsuario;
    protected String idOrganismoResponsable;
    @XmlElement(required = true)
    protected String idResponsable;
    @XmlElement(required = true)
    protected String version;
    protected String tasa;
    @XmlElement(name = "CriteriosConsultaDocumento", required = true)
    protected CriteriosConsultaDocumento criteriosConsultaDocumento;

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
     * Obtiene el valor de la propiedad tasa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasa() {
        return tasa;
    }

    /**
     * Define el valor de la propiedad tasa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasa(String value) {
        this.tasa = value;
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

}
