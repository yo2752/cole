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
 *         &lt;element ref="{}SIGLAS_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}LETRA_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}PISO_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}PROVINCIA_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}MUNICIPIO_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}PUEBLO_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}CP_DISTINTA_ADQUIRIENTE"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_DISTINTA_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_DISTINTA_ADQUIRIENTE" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_DISTINTA_ADQUIRIENTE" minOccurs="0"/>
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
@XmlRootElement(name = "DIRECCION_DISTINTA_ADQUIRIENTE")
public class DIRECCIONDISTINTAADQUIRIENTE {

    @XmlElement(name = "SIGLAS_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String siglasdirecciondistintaadquiriente;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String nombreviadirecciondistintaadquiriente;
    @XmlElement(name = "NUMERO_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String numerodirecciondistintaadquiriente;
    @XmlElement(name = "LETRA_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String letradirecciondistintaadquiriente;
    @XmlElement(name = "ESCALERA_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String escaleradirecciondistintaadquiriente;
    @XmlElement(name = "PISO_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String pisodirecciondistintaadquiriente;
    @XmlElement(name = "PUERTA_DIRECCION_DISTINTA_ADQUIRIENTE", required = true)
    protected String puertadirecciondistintaadquiriente;
    @XmlElement(name = "PROVINCIA_DISTINTA_ADQUIRIENTE", required = true)
    protected String provinciadistintaadquiriente;
    @XmlElement(name = "MUNICIPIO_DISTINTA_ADQUIRIENTE", required = true)
    protected String municipiodistintaadquiriente;
    @XmlElement(name = "PUEBLO_DISTINTA_ADQUIRIENTE", required = true)
    protected String pueblodistintaadquiriente;
    @XmlElement(name = "CP_DISTINTA_ADQUIRIENTE", required = true)
    protected String cpdistintaadquiriente;
    @XmlElement(name = "BLOQUE_DIRECCION_DISTINTA_ADQUIRIENTE")
    protected String bloquedirecciondistintaadquiriente;
    @XmlElement(name = "KM_DIRECCION_DISTINTA_ADQUIRIENTE")
    protected String kmdirecciondistintaadquiriente;
    @XmlElement(name = "HM_DIRECCION_DISTINTA_ADQUIRIENTE")
    protected String hmdirecciondistintaadquiriente;

    /**
     * Obtiene el valor de la propiedad siglasdirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONDISTINTAADQUIRIENTE() {
        return siglasdirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad siglasdirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.siglasdirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONDISTINTAADQUIRIENTE() {
        return nombreviadirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad nombreviadirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.nombreviadirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONDISTINTAADQUIRIENTE() {
        return numerodirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad numerodirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.numerodirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad letradirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONDISTINTAADQUIRIENTE() {
        return letradirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad letradirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.letradirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONDISTINTAADQUIRIENTE() {
        return escaleradirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad escaleradirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.escaleradirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONDISTINTAADQUIRIENTE() {
        return pisodirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad pisodirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.pisodirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONDISTINTAADQUIRIENTE() {
        return puertadirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad puertadirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.puertadirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciadistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIADISTINTAADQUIRIENTE() {
        return provinciadistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad provinciadistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIADISTINTAADQUIRIENTE(String value) {
        this.provinciadistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad municipiodistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIODISTINTAADQUIRIENTE() {
        return municipiodistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad municipiodistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIODISTINTAADQUIRIENTE(String value) {
        this.municipiodistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblodistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLODISTINTAADQUIRIENTE() {
        return pueblodistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad pueblodistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLODISTINTAADQUIRIENTE(String value) {
        this.pueblodistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad cpdistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPDISTINTAADQUIRIENTE() {
        return cpdistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad cpdistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPDISTINTAADQUIRIENTE(String value) {
        this.cpdistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONDISTINTAADQUIRIENTE() {
        return bloquedirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad bloquedirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.bloquedirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONDISTINTAADQUIRIENTE() {
        return kmdirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad kmdirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.kmdirecciondistintaadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdirecciondistintaadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONDISTINTAADQUIRIENTE() {
        return hmdirecciondistintaadquiriente;
    }

    /**
     * Define el valor de la propiedad hmdirecciondistintaadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONDISTINTAADQUIRIENTE(String value) {
        this.hmdirecciondistintaadquiriente = value;
    }

}
