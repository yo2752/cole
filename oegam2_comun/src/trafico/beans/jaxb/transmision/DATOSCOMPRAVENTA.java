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
 *         &lt;element ref="{}DNI_COMPRA_VENTA"/>
 *         &lt;element ref="{}NOMBRE_APELLIDOS_COMPRA_VENTA"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_COMPRA_VENTA"/>
 *         &lt;element ref="{}SEXO_COMPRA_VENTA"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}LETRA_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}PISO_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}PROVINCIA_COMPRA_VENTA"/>
 *         &lt;element ref="{}MUNICIPIO_COMPRA_VENTA"/>
 *         &lt;element ref="{}PUEBLO_DIRECCION_COMPRA_VENTA"/>
 *         &lt;element ref="{}CP_COMPRA_VENTA"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}TELEFONO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}ACTUALIZACION_DOMICILIO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}AUTONOMO_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_IAE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}MOTIVO_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_INICIO_TUTELA_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_FIN_TUTELA_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}CONCEPTO_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}DOCUMENTOS_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_REPRESENTANTE_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}DNI_REPRESENTANTE_COMPRA_VENTA"/>
 *         &lt;element ref="{}NOMBRE_APELLIDOS_REPRESENTANTE_COMPRA_VENTA"/>
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
@XmlRootElement(name = "DATOS_COMPRA_VENTA")
public class DATOSCOMPRAVENTA {

    @XmlElement(name = "DNI_COMPRA_VENTA", required = true)
    protected String dnicompraventa;
    @XmlElement(name = "NOMBRE_APELLIDOS_COMPRA_VENTA", required = true)
    protected String nombreapellidoscompraventa;
    @XmlElement(name = "FECHA_NACIMIENTO_COMPRA_VENTA", required = true)
    protected String fechanacimientocompraventa;
    @XmlElement(name = "SEXO_COMPRA_VENTA", required = true)
    protected String sexocompraventa;
    @XmlElement(name = "SIGLAS_DIRECCION_COMPRA_VENTA", required = true)
    protected String siglasdireccioncompraventa;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_COMPRA_VENTA", required = true)
    protected String nombreviadireccioncompraventa;
    @XmlElement(name = "NUMERO_DIRECCION_COMPRA_VENTA", required = true)
    protected String numerodireccioncompraventa;
    @XmlElement(name = "LETRA_DIRECCION_COMPRA_VENTA", required = true)
    protected String letradireccioncompraventa;
    @XmlElement(name = "ESCALERA_DIRECCION_COMPRA_VENTA", required = true)
    protected String escaleradireccioncompraventa;
    @XmlElement(name = "PISO_DIRECCION_COMPRA_VENTA", required = true)
    protected String pisodireccioncompraventa;
    @XmlElement(name = "PUERTA_DIRECCION_COMPRA_VENTA", required = true)
    protected String puertadireccioncompraventa;
    @XmlElement(name = "PROVINCIA_COMPRA_VENTA", required = true)
    protected String provinciacompraventa;
    @XmlElement(name = "MUNICIPIO_COMPRA_VENTA", required = true)
    protected String municipiocompraventa;
    @XmlElement(name = "PUEBLO_DIRECCION_COMPRA_VENTA", required = true)
    protected String pueblodireccioncompraventa;
    @XmlElement(name = "CP_COMPRA_VENTA", required = true)
    protected String cpcompraventa;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_COMPRA_VENTA")
    protected String fechacaducidadnifcompraventa;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_COMPRA_VENTA")
    protected String tipodocumentosustitutivocompraventa;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_COMPRA_VENTA")
    protected String fechacaducidaddocumentosustitutivocompraventa;
    @XmlElement(name = "INDEFINIDO_COMPRA_VENTA")
    protected String indefinidocompraventa;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_COMPRA_VENTA")
    protected String apellido1RAZONSOCIALCOMPRAVENTA;
    @XmlElement(name = "APELLIDO2_COMPRA_VENTA")
    protected String apellido2COMPRAVENTA;
    @XmlElement(name = "NOMBRE_COMPRA_VENTA")
    protected String nombrecompraventa;
    @XmlElement(name = "TELEFONO_COMPRA_VENTA")
    protected String telefonocompraventa;
    @XmlElement(name = "BLOQUE_DIRECCION_COMPRA_VENTA")
    protected String bloquedireccioncompraventa;
    @XmlElement(name = "KM_DIRECCION_COMPRA_VENTA")
    protected String kmdireccioncompraventa;
    @XmlElement(name = "HM_DIRECCION_COMPRA_VENTA")
    protected String hmdireccioncompraventa;
    @XmlElement(name = "DIRECCION_ACTIVA_COMPRA_VENTA")
    protected String direccionactivacompraventa;
    @XmlElement(name = "ACTUALIZACION_DOMICILIO_COMPRA_VENTA")
    protected String actualizaciondomiciliocompraventa;
    @XmlElement(name = "AUTONOMO_COMPRA_VENTA")
    protected String autonomocompraventa;
    @XmlElement(name = "CODIGO_IAE_COMPRA_VENTA")
    protected String codigoiaecompraventa;
    @XmlElement(name = "NOMBRE_REPRESENTANTE_COMPRA_VENTA")
    protected String nombrerepresentantecompraventa;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_COMPRA_VENTA")
    protected String apellido1RAZONSOCIALREPRESENTANTECOMPRAVENTA;
    @XmlElement(name = "APELLIDO2_REPRESENTANTE_COMPRA_VENTA")
    protected String apellido2REPRESENTANTECOMPRAVENTA;
    @XmlElement(name = "MOTIVO_REPRESENTANTE_COMPRA_VENTA")
    protected String motivorepresentantecompraventa;
    @XmlElement(name = "FECHA_INICIO_TUTELA_COMPRA_VENTA")
    protected String fechainiciotutelacompraventa;
    @XmlElement(name = "FECHA_FIN_TUTELA_COMPRA_VENTA")
    protected String fechafintutelacompraventa;
    @XmlElement(name = "CONCEPTO_REPRESENTANTE_COMPRA_VENTA")
    protected String conceptorepresentantecompraventa;
    @XmlElement(name = "DOCUMENTOS_REPRESENTANTE_COMPRA_VENTA")
    protected String documentosrepresentantecompraventa;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_REPRESENTANTE_COMPRA_VENTA")
    protected String fechacaducidadnifrepresentantecompraventa;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_COMPRA_VENTA")
    protected String tipodocumentosustitutivorepresentantecompraventa;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_COMPRA_VENTA")
    protected String fechacaducidaddocumentosustitutivorepresentantecompraventa;
    @XmlElement(name = "INDEFINIDO_REPRESENTANTE_COMPRA_VENTA")
    protected String indefinidorepresentantecompraventa;
    @XmlElement(name = "DNI_REPRESENTANTE_COMPRA_VENTA", required = true)
    protected String dnirepresentantecompraventa;
    @XmlElement(name = "NOMBRE_APELLIDOS_REPRESENTANTE_COMPRA_VENTA", required = true)
    protected String nombreapellidosrepresentantecompraventa;

    /**
     * Obtiene el valor de la propiedad dnicompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNICOMPRAVENTA() {
        return dnicompraventa;
    }

    /**
     * Define el valor de la propiedad dnicompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNICOMPRAVENTA(String value) {
        this.dnicompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreapellidoscompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREAPELLIDOSCOMPRAVENTA() {
        return nombreapellidoscompraventa;
    }

    /**
     * Define el valor de la propiedad nombreapellidoscompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREAPELLIDOSCOMPRAVENTA(String value) {
        this.nombreapellidoscompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOCOMPRAVENTA() {
        return fechanacimientocompraventa;
    }

    /**
     * Define el valor de la propiedad fechanacimientocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOCOMPRAVENTA(String value) {
        this.fechanacimientocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad sexocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOCOMPRAVENTA() {
        return sexocompraventa;
    }

    /**
     * Define el valor de la propiedad sexocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOCOMPRAVENTA(String value) {
        this.sexocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONCOMPRAVENTA() {
        return siglasdireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad siglasdireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONCOMPRAVENTA(String value) {
        this.siglasdireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONCOMPRAVENTA() {
        return nombreviadireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad nombreviadireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONCOMPRAVENTA(String value) {
        this.nombreviadireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONCOMPRAVENTA() {
        return numerodireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad numerodireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONCOMPRAVENTA(String value) {
        this.numerodireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad letradireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONCOMPRAVENTA() {
        return letradireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad letradireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONCOMPRAVENTA(String value) {
        this.letradireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONCOMPRAVENTA() {
        return escaleradireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad escaleradireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONCOMPRAVENTA(String value) {
        this.escaleradireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONCOMPRAVENTA() {
        return pisodireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad pisodireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONCOMPRAVENTA(String value) {
        this.pisodireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONCOMPRAVENTA() {
        return puertadireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad puertadireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONCOMPRAVENTA(String value) {
        this.puertadireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciacompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIACOMPRAVENTA() {
        return provinciacompraventa;
    }

    /**
     * Define el valor de la propiedad provinciacompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIACOMPRAVENTA(String value) {
        this.provinciacompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad municipiocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOCOMPRAVENTA() {
        return municipiocompraventa;
    }

    /**
     * Define el valor de la propiedad municipiocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOCOMPRAVENTA(String value) {
        this.municipiocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblodireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLODIRECCIONCOMPRAVENTA() {
        return pueblodireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad pueblodireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLODIRECCIONCOMPRAVENTA(String value) {
        this.pueblodireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad cpcompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPCOMPRAVENTA() {
        return cpcompraventa;
    }

    /**
     * Define el valor de la propiedad cpcompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPCOMPRAVENTA(String value) {
        this.cpcompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifcompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFCOMPRAVENTA() {
        return fechacaducidadnifcompraventa;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifcompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFCOMPRAVENTA(String value) {
        this.fechacaducidadnifcompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOCOMPRAVENTA() {
        return tipodocumentosustitutivocompraventa;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOCOMPRAVENTA(String value) {
        this.tipodocumentosustitutivocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA() {
        return fechacaducidaddocumentosustitutivocompraventa;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA(String value) {
        this.fechacaducidaddocumentosustitutivocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOCOMPRAVENTA() {
        return indefinidocompraventa;
    }

    /**
     * Define el valor de la propiedad indefinidocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOCOMPRAVENTA(String value) {
        this.indefinidocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALCOMPRAVENTA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALCOMPRAVENTA() {
        return apellido1RAZONSOCIALCOMPRAVENTA;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALCOMPRAVENTA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALCOMPRAVENTA(String value) {
        this.apellido1RAZONSOCIALCOMPRAVENTA = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2COMPRAVENTA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2COMPRAVENTA() {
        return apellido2COMPRAVENTA;
    }

    /**
     * Define el valor de la propiedad apellido2COMPRAVENTA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2COMPRAVENTA(String value) {
        this.apellido2COMPRAVENTA = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBRECOMPRAVENTA() {
        return nombrecompraventa;
    }

    /**
     * Define el valor de la propiedad nombrecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBRECOMPRAVENTA(String value) {
        this.nombrecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOCOMPRAVENTA() {
        return telefonocompraventa;
    }

    /**
     * Define el valor de la propiedad telefonocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOCOMPRAVENTA(String value) {
        this.telefonocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONCOMPRAVENTA() {
        return bloquedireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad bloquedireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONCOMPRAVENTA(String value) {
        this.bloquedireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONCOMPRAVENTA() {
        return kmdireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad kmdireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONCOMPRAVENTA(String value) {
        this.kmdireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdireccioncompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONCOMPRAVENTA() {
        return hmdireccioncompraventa;
    }

    /**
     * Define el valor de la propiedad hmdireccioncompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONCOMPRAVENTA(String value) {
        this.hmdireccioncompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivacompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVACOMPRAVENTA() {
        return direccionactivacompraventa;
    }

    /**
     * Define el valor de la propiedad direccionactivacompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVACOMPRAVENTA(String value) {
        this.direccionactivacompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad actualizaciondomiciliocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTUALIZACIONDOMICILIOCOMPRAVENTA() {
        return actualizaciondomiciliocompraventa;
    }

    /**
     * Define el valor de la propiedad actualizaciondomiciliocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTUALIZACIONDOMICILIOCOMPRAVENTA(String value) {
        this.actualizaciondomiciliocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad autonomocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUTONOMOCOMPRAVENTA() {
        return autonomocompraventa;
    }

    /**
     * Define el valor de la propiedad autonomocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUTONOMOCOMPRAVENTA(String value) {
        this.autonomocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoiaecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOIAECOMPRAVENTA() {
        return codigoiaecompraventa;
    }

    /**
     * Define el valor de la propiedad codigoiaecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOIAECOMPRAVENTA(String value) {
        this.codigoiaecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrerepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREREPRESENTANTECOMPRAVENTA() {
        return nombrerepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad nombrerepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREREPRESENTANTECOMPRAVENTA(String value) {
        this.nombrerepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTECOMPRAVENTA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA() {
        return apellido1RAZONSOCIALREPRESENTANTECOMPRAVENTA;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTECOMPRAVENTA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA(String value) {
        this.apellido1RAZONSOCIALREPRESENTANTECOMPRAVENTA = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2REPRESENTANTECOMPRAVENTA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2REPRESENTANTECOMPRAVENTA() {
        return apellido2REPRESENTANTECOMPRAVENTA;
    }

    /**
     * Define el valor de la propiedad apellido2REPRESENTANTECOMPRAVENTA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2REPRESENTANTECOMPRAVENTA(String value) {
        this.apellido2REPRESENTANTECOMPRAVENTA = value;
    }

    /**
     * Obtiene el valor de la propiedad motivorepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVOREPRESENTANTECOMPRAVENTA() {
        return motivorepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad motivorepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVOREPRESENTANTECOMPRAVENTA(String value) {
        this.motivorepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechainiciotutelacompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAINICIOTUTELACOMPRAVENTA() {
        return fechainiciotutelacompraventa;
    }

    /**
     * Define el valor de la propiedad fechainiciotutelacompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAINICIOTUTELACOMPRAVENTA(String value) {
        this.fechainiciotutelacompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechafintutelacompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFINTUTELACOMPRAVENTA() {
        return fechafintutelacompraventa;
    }

    /**
     * Define el valor de la propiedad fechafintutelacompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFINTUTELACOMPRAVENTA(String value) {
        this.fechafintutelacompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptorepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTOREPRESENTANTECOMPRAVENTA() {
        return conceptorepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad conceptorepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTOREPRESENTANTECOMPRAVENTA(String value) {
        this.conceptorepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad documentosrepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTOSREPRESENTANTECOMPRAVENTA() {
        return documentosrepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad documentosrepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTOSREPRESENTANTECOMPRAVENTA(String value) {
        this.documentosrepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifrepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFREPRESENTANTECOMPRAVENTA() {
        return fechacaducidadnifrepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifrepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFREPRESENTANTECOMPRAVENTA(String value) {
        this.fechacaducidadnifrepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivorepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA() {
        return tipodocumentosustitutivorepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivorepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA(String value) {
        this.tipodocumentosustitutivorepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA() {
        return fechacaducidaddocumentosustitutivorepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA(String value) {
        this.fechacaducidaddocumentosustitutivorepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidorepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOREPRESENTANTECOMPRAVENTA() {
        return indefinidorepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad indefinidorepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOREPRESENTANTECOMPRAVENTA(String value) {
        this.indefinidorepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad dnirepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIREPRESENTANTECOMPRAVENTA() {
        return dnirepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad dnirepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIREPRESENTANTECOMPRAVENTA(String value) {
        this.dnirepresentantecompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreapellidosrepresentantecompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA() {
        return nombreapellidosrepresentantecompraventa;
    }

    /**
     * Define el valor de la propiedad nombreapellidosrepresentantecompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA(String value) {
        this.nombreapellidosrepresentantecompraventa = value;
    }

}
