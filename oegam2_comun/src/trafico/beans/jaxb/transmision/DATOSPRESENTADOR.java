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
 *         &lt;element ref="{}DNI_PRESENTADOR"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_PRESENTADOR"/>
 *         &lt;element ref="{}APELLIDO2_PRESENTADOR"/>
 *         &lt;element ref="{}NOMBRE_PRESENTADOR"/>
 *         &lt;element ref="{}TELEFONO_PRESENTADOR"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}LETRA_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}PISO_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_PRESENTADOR"/>
 *         &lt;element ref="{}PROVINCIA_PRESENTADOR"/>
 *         &lt;element ref="{}MUNICIPIO_PRESENTADOR"/>
 *         &lt;element ref="{}CP_PRESENTADOR"/>
 *         &lt;element ref="{}SEXO_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}PUEBLO_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_PRESENTADOR" minOccurs="0"/>
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
@XmlRootElement(name = "DATOS_PRESENTADOR")
public class DATOSPRESENTADOR {

    @XmlElement(name = "DNI_PRESENTADOR", required = true)
    protected String dnipresentador;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_PRESENTADOR", required = true)
    protected String apellido1RAZONSOCIALPRESENTADOR;
    @XmlElement(name = "APELLIDO2_PRESENTADOR", required = true)
    protected String apellido2PRESENTADOR;
    @XmlElement(name = "NOMBRE_PRESENTADOR", required = true)
    protected String nombrepresentador;
    @XmlElement(name = "TELEFONO_PRESENTADOR", required = true)
    protected String telefonopresentador;
    @XmlElement(name = "SIGLAS_DIRECCION_PRESENTADOR", required = true)
    protected String siglasdireccionpresentador;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_PRESENTADOR", required = true)
    protected String nombreviadireccionpresentador;
    @XmlElement(name = "NUMERO_DIRECCION_PRESENTADOR", required = true)
    protected String numerodireccionpresentador;
    @XmlElement(name = "LETRA_DIRECCION_PRESENTADOR", required = true)
    protected String letradireccionpresentador;
    @XmlElement(name = "ESCALERA_DIRECCION_PRESENTADOR", required = true)
    protected String escaleradireccionpresentador;
    @XmlElement(name = "PISO_DIRECCION_PRESENTADOR", required = true)
    protected String pisodireccionpresentador;
    @XmlElement(name = "PUERTA_DIRECCION_PRESENTADOR", required = true)
    protected String puertadireccionpresentador;
    @XmlElement(name = "PROVINCIA_PRESENTADOR", required = true)
    protected String provinciapresentador;
    @XmlElement(name = "MUNICIPIO_PRESENTADOR", required = true)
    protected String municipiopresentador;
    @XmlElement(name = "CP_PRESENTADOR", required = true)
    protected String cppresentador;
    @XmlElement(name = "SEXO_PRESENTADOR")
    protected String sexopresentador;
    @XmlElement(name = "FECHA_NACIMIENTO_PRESENTADOR")
    protected String fechanacimientopresentador;
    @XmlElement(name = "PUEBLO_PRESENTADOR")
    protected String pueblopresentador;
    @XmlElement(name = "BLOQUE_DIRECCION_PRESENTADOR")
    protected String bloquedireccionpresentador;
    @XmlElement(name = "KM_DIRECCION_PRESENTADOR")
    protected String kmdireccionpresentador;
    @XmlElement(name = "HM_DIRECCION_PRESENTADOR")
    protected String hmdireccionpresentador;
    @XmlElement(name = "DIRECCION_ACTIVA_PRESENTADOR")
    protected String direccionactivapresentador;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_PRESENTADOR")
    protected String fechacaducidadnifpresentador;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_PRESENTADOR")
    protected String tipodocumentosustitutivopresentador;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_PRESENTADOR")
    protected String fechacaducidaddocumentosustitutivopresentador;

    /**
     * Obtiene el valor de la propiedad dnipresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIPRESENTADOR() {
        return dnipresentador;
    }

    /**
     * Define el valor de la propiedad dnipresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIPRESENTADOR(String value) {
        this.dnipresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALPRESENTADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALPRESENTADOR() {
        return apellido1RAZONSOCIALPRESENTADOR;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALPRESENTADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALPRESENTADOR(String value) {
        this.apellido1RAZONSOCIALPRESENTADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2PRESENTADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2PRESENTADOR() {
        return apellido2PRESENTADOR;
    }

    /**
     * Define el valor de la propiedad apellido2PRESENTADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2PRESENTADOR(String value) {
        this.apellido2PRESENTADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrepresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREPRESENTADOR() {
        return nombrepresentador;
    }

    /**
     * Define el valor de la propiedad nombrepresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREPRESENTADOR(String value) {
        this.nombrepresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOPRESENTADOR() {
        return telefonopresentador;
    }

    /**
     * Define el valor de la propiedad telefonopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOPRESENTADOR(String value) {
        this.telefonopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONPRESENTADOR() {
        return siglasdireccionpresentador;
    }

    /**
     * Define el valor de la propiedad siglasdireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONPRESENTADOR(String value) {
        this.siglasdireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONPRESENTADOR() {
        return nombreviadireccionpresentador;
    }

    /**
     * Define el valor de la propiedad nombreviadireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONPRESENTADOR(String value) {
        this.nombreviadireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONPRESENTADOR() {
        return numerodireccionpresentador;
    }

    /**
     * Define el valor de la propiedad numerodireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONPRESENTADOR(String value) {
        this.numerodireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad letradireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONPRESENTADOR() {
        return letradireccionpresentador;
    }

    /**
     * Define el valor de la propiedad letradireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONPRESENTADOR(String value) {
        this.letradireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONPRESENTADOR() {
        return escaleradireccionpresentador;
    }

    /**
     * Define el valor de la propiedad escaleradireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONPRESENTADOR(String value) {
        this.escaleradireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONPRESENTADOR() {
        return pisodireccionpresentador;
    }

    /**
     * Define el valor de la propiedad pisodireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONPRESENTADOR(String value) {
        this.pisodireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONPRESENTADOR() {
        return puertadireccionpresentador;
    }

    /**
     * Define el valor de la propiedad puertadireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONPRESENTADOR(String value) {
        this.puertadireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciapresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAPRESENTADOR() {
        return provinciapresentador;
    }

    /**
     * Define el valor de la propiedad provinciapresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAPRESENTADOR(String value) {
        this.provinciapresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad municipiopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOPRESENTADOR() {
        return municipiopresentador;
    }

    /**
     * Define el valor de la propiedad municipiopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOPRESENTADOR(String value) {
        this.municipiopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad cppresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPPRESENTADOR() {
        return cppresentador;
    }

    /**
     * Define el valor de la propiedad cppresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPPRESENTADOR(String value) {
        this.cppresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad sexopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOPRESENTADOR() {
        return sexopresentador;
    }

    /**
     * Define el valor de la propiedad sexopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOPRESENTADOR(String value) {
        this.sexopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOPRESENTADOR() {
        return fechanacimientopresentador;
    }

    /**
     * Define el valor de la propiedad fechanacimientopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOPRESENTADOR(String value) {
        this.fechanacimientopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLOPRESENTADOR() {
        return pueblopresentador;
    }

    /**
     * Define el valor de la propiedad pueblopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLOPRESENTADOR(String value) {
        this.pueblopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONPRESENTADOR() {
        return bloquedireccionpresentador;
    }

    /**
     * Define el valor de la propiedad bloquedireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONPRESENTADOR(String value) {
        this.bloquedireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONPRESENTADOR() {
        return kmdireccionpresentador;
    }

    /**
     * Define el valor de la propiedad kmdireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONPRESENTADOR(String value) {
        this.kmdireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdireccionpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONPRESENTADOR() {
        return hmdireccionpresentador;
    }

    /**
     * Define el valor de la propiedad hmdireccionpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONPRESENTADOR(String value) {
        this.hmdireccionpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivapresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVAPRESENTADOR() {
        return direccionactivapresentador;
    }

    /**
     * Define el valor de la propiedad direccionactivapresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVAPRESENTADOR(String value) {
        this.direccionactivapresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifpresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFPRESENTADOR() {
        return fechacaducidadnifpresentador;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifpresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFPRESENTADOR(String value) {
        this.fechacaducidadnifpresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOPRESENTADOR() {
        return tipodocumentosustitutivopresentador;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOPRESENTADOR(String value) {
        this.tipodocumentosustitutivopresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivopresentador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRESENTADOR() {
        return fechacaducidaddocumentosustitutivopresentador;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivopresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRESENTADOR(String value) {
        this.fechacaducidaddocumentosustitutivopresentador = value;
    }

}
