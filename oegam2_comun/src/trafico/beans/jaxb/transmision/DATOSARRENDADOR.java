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
 *         &lt;element ref="{}DNI_ARRENDADOR"/>
 *         &lt;element ref="{}NOMBRE_APELLIDOS_ARRENDADOR"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_ARRENDADOR"/>
 *         &lt;element ref="{}SEXO_ARRENDADOR"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}LETRA_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}PISO_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}PROVINCIA_ARRENDADOR"/>
 *         &lt;element ref="{}MUNICIPIO_ARRENDADOR"/>
 *         &lt;element ref="{}PUEBLO_DIRECCION_ARRENDADOR"/>
 *         &lt;element ref="{}CP_ARRENDADOR"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}TELEFONO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}ACTUALIZACION_DOMICILIO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}AUTONOMO_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_IAE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}MOTIVO_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_INICIO_TUTELA_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_FIN_TUTELA_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}CONCEPTO_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}DOCUMENTOS_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_REPRESENTANTE_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}DNI_REPRESENTANTE_ARRENDADOR"/>
 *         &lt;element ref="{}NOMBRE_APELLIDOS_REPRESENTANTE_ARRENDADOR"/>
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
@XmlRootElement(name = "DATOS_ARRENDADOR")
public class DATOSARRENDADOR {

    @XmlElement(name = "DNI_ARRENDADOR", required = true)
    protected String dniarrendador;
    @XmlElement(name = "NOMBRE_APELLIDOS_ARRENDADOR", required = true)
    protected String nombreapellidosarrendador;
    @XmlElement(name = "FECHA_NACIMIENTO_ARRENDADOR", required = true)
    protected String fechanacimientoarrendador;
    @XmlElement(name = "SEXO_ARRENDADOR", required = true)
    protected String sexoarrendador;
    @XmlElement(name = "SIGLAS_DIRECCION_ARRENDADOR", required = true)
    protected String siglasdireccionarrendador;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_ARRENDADOR", required = true)
    protected String nombreviadireccionarrendador;
    @XmlElement(name = "NUMERO_DIRECCION_ARRENDADOR", required = true)
    protected String numerodireccionarrendador;
    @XmlElement(name = "LETRA_DIRECCION_ARRENDADOR", required = true)
    protected String letradireccionarrendador;
    @XmlElement(name = "ESCALERA_DIRECCION_ARRENDADOR", required = true)
    protected String escaleradireccionarrendador;
    @XmlElement(name = "PISO_DIRECCION_ARRENDADOR", required = true)
    protected String pisodireccionarrendador;
    @XmlElement(name = "PUERTA_DIRECCION_ARRENDADOR", required = true)
    protected String puertadireccionarrendador;
    @XmlElement(name = "PROVINCIA_ARRENDADOR", required = true)
    protected String provinciaarrendador;
    @XmlElement(name = "MUNICIPIO_ARRENDADOR", required = true)
    protected String municipioarrendador;
    @XmlElement(name = "PUEBLO_DIRECCION_ARRENDADOR", required = true)
    protected String pueblodireccionarrendador;
    @XmlElement(name = "CP_ARRENDADOR", required = true)
    protected String cparrendador;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_ARRENDADOR")
    protected String fechacaducidadnifarrendador;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_ARRENDADOR")
    protected String tipodocumentosustitutivoarrendador;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_ARRENDADOR")
    protected String fechacaducidaddocumentosustitutivoarrendador;
    @XmlElement(name = "INDEFINIDO_ARRENDADOR")
    protected String indefinidoarrendador;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_ARRENDADOR")
    protected String apellido1RAZONSOCIALARRENDADOR;
    @XmlElement(name = "APELLIDO2_ARRENDADOR")
    protected String apellido2ARRENDADOR;
    @XmlElement(name = "NOMBRE_ARRENDADOR")
    protected String nombrearrendador;
    @XmlElement(name = "TELEFONO_ARRENDADOR")
    protected String telefonoarrendador;
    @XmlElement(name = "BLOQUE_DIRECCION_ARRENDADOR")
    protected String bloquedireccionarrendador;
    @XmlElement(name = "KM_DIRECCION_ARRENDADOR")
    protected String kmdireccionarrendador;
    @XmlElement(name = "HM_DIRECCION_ARRENDADOR")
    protected String hmdireccionarrendador;
    @XmlElement(name = "DIRECCION_ACTIVA_ARRENDADOR")
    protected String direccionactivaarrendador;
    @XmlElement(name = "ACTUALIZACION_DOMICILIO_ARRENDADOR")
    protected String actualizaciondomicilioarrendador;
    @XmlElement(name = "AUTONOMO_ARRENDADOR")
    protected String autonomoarrendador;
    @XmlElement(name = "CODIGO_IAE_ARRENDADOR")
    protected String codigoiaearrendador;
    @XmlElement(name = "NOMBRE_REPRESENTANTE_ARRENDADOR")
    protected String nombrerepresentantearrendador;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_ARRENDADOR")
    protected String apellido1RAZONSOCIALREPRESENTANTEARRENDADOR;
    @XmlElement(name = "APELLIDO2_REPRESENTANTE_ARRENDADOR")
    protected String apellido2REPRESENTANTEARRENDADOR;
    @XmlElement(name = "MOTIVO_REPRESENTANTE_ARRENDADOR")
    protected String motivorepresentantearrendador;
    @XmlElement(name = "FECHA_INICIO_TUTELA_ARRENDADOR")
    protected String fechainiciotutelaarrendador;
    @XmlElement(name = "FECHA_FIN_TUTELA_ARRENDADOR")
    protected String fechafintutelaarrendador;
    @XmlElement(name = "CONCEPTO_REPRESENTANTE_ARRENDADOR")
    protected String conceptorepresentantearrendador;
    @XmlElement(name = "DOCUMENTOS_REPRESENTANTE_ARRENDADOR")
    protected String documentosrepresentantearrendador;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_REPRESENTANTE_ARRENDADOR")
    protected String fechacaducidadnifrepresentantearrendador;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ARRENDADOR")
    protected String tipodocumentosustitutivorepresentantearrendador;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_ARRENDADOR")
    protected String fechacaducidaddocumentosustitutivorepresentantearrendador;
    @XmlElement(name = "INDEFINIDO_REPRESENTANTE_ARRENDADOR")
    protected String indefinidorepresentantearrendador;
    @XmlElement(name = "DNI_REPRESENTANTE_ARRENDADOR", required = true)
    protected String dnirepresentantearrendador;
    @XmlElement(name = "NOMBRE_APELLIDOS_REPRESENTANTE_ARRENDADOR", required = true)
    protected String nombreapellidosrepresentantearrendador;

    /**
     * Obtiene el valor de la propiedad dniarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIARRENDADOR() {
        return dniarrendador;
    }

    /**
     * Define el valor de la propiedad dniarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIARRENDADOR(String value) {
        this.dniarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreapellidosarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREAPELLIDOSARRENDADOR() {
        return nombreapellidosarrendador;
    }

    /**
     * Define el valor de la propiedad nombreapellidosarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREAPELLIDOSARRENDADOR(String value) {
        this.nombreapellidosarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOARRENDADOR() {
        return fechanacimientoarrendador;
    }

    /**
     * Define el valor de la propiedad fechanacimientoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOARRENDADOR(String value) {
        this.fechanacimientoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad sexoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOARRENDADOR() {
        return sexoarrendador;
    }

    /**
     * Define el valor de la propiedad sexoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOARRENDADOR(String value) {
        this.sexoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONARRENDADOR() {
        return siglasdireccionarrendador;
    }

    /**
     * Define el valor de la propiedad siglasdireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONARRENDADOR(String value) {
        this.siglasdireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONARRENDADOR() {
        return nombreviadireccionarrendador;
    }

    /**
     * Define el valor de la propiedad nombreviadireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONARRENDADOR(String value) {
        this.nombreviadireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONARRENDADOR() {
        return numerodireccionarrendador;
    }

    /**
     * Define el valor de la propiedad numerodireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONARRENDADOR(String value) {
        this.numerodireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad letradireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONARRENDADOR() {
        return letradireccionarrendador;
    }

    /**
     * Define el valor de la propiedad letradireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONARRENDADOR(String value) {
        this.letradireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONARRENDADOR() {
        return escaleradireccionarrendador;
    }

    /**
     * Define el valor de la propiedad escaleradireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONARRENDADOR(String value) {
        this.escaleradireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONARRENDADOR() {
        return pisodireccionarrendador;
    }

    /**
     * Define el valor de la propiedad pisodireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONARRENDADOR(String value) {
        this.pisodireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONARRENDADOR() {
        return puertadireccionarrendador;
    }

    /**
     * Define el valor de la propiedad puertadireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONARRENDADOR(String value) {
        this.puertadireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciaarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAARRENDADOR() {
        return provinciaarrendador;
    }

    /**
     * Define el valor de la propiedad provinciaarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAARRENDADOR(String value) {
        this.provinciaarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad municipioarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOARRENDADOR() {
        return municipioarrendador;
    }

    /**
     * Define el valor de la propiedad municipioarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOARRENDADOR(String value) {
        this.municipioarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblodireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLODIRECCIONARRENDADOR() {
        return pueblodireccionarrendador;
    }

    /**
     * Define el valor de la propiedad pueblodireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLODIRECCIONARRENDADOR(String value) {
        this.pueblodireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad cparrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPARRENDADOR() {
        return cparrendador;
    }

    /**
     * Define el valor de la propiedad cparrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPARRENDADOR(String value) {
        this.cparrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFARRENDADOR() {
        return fechacaducidadnifarrendador;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFARRENDADOR(String value) {
        this.fechacaducidadnifarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOARRENDADOR() {
        return tipodocumentosustitutivoarrendador;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOARRENDADOR(String value) {
        this.tipodocumentosustitutivoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR() {
        return fechacaducidaddocumentosustitutivoarrendador;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR(String value) {
        this.fechacaducidaddocumentosustitutivoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOARRENDADOR() {
        return indefinidoarrendador;
    }

    /**
     * Define el valor de la propiedad indefinidoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOARRENDADOR(String value) {
        this.indefinidoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALARRENDADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALARRENDADOR() {
        return apellido1RAZONSOCIALARRENDADOR;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALARRENDADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALARRENDADOR(String value) {
        this.apellido1RAZONSOCIALARRENDADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2ARRENDADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2ARRENDADOR() {
        return apellido2ARRENDADOR;
    }

    /**
     * Define el valor de la propiedad apellido2ARRENDADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2ARRENDADOR(String value) {
        this.apellido2ARRENDADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREARRENDADOR() {
        return nombrearrendador;
    }

    /**
     * Define el valor de la propiedad nombrearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREARRENDADOR(String value) {
        this.nombrearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOARRENDADOR() {
        return telefonoarrendador;
    }

    /**
     * Define el valor de la propiedad telefonoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOARRENDADOR(String value) {
        this.telefonoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONARRENDADOR() {
        return bloquedireccionarrendador;
    }

    /**
     * Define el valor de la propiedad bloquedireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONARRENDADOR(String value) {
        this.bloquedireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONARRENDADOR() {
        return kmdireccionarrendador;
    }

    /**
     * Define el valor de la propiedad kmdireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONARRENDADOR(String value) {
        this.kmdireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdireccionarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONARRENDADOR() {
        return hmdireccionarrendador;
    }

    /**
     * Define el valor de la propiedad hmdireccionarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONARRENDADOR(String value) {
        this.hmdireccionarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivaarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVAARRENDADOR() {
        return direccionactivaarrendador;
    }

    /**
     * Define el valor de la propiedad direccionactivaarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVAARRENDADOR(String value) {
        this.direccionactivaarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad actualizaciondomicilioarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTUALIZACIONDOMICILIOARRENDADOR() {
        return actualizaciondomicilioarrendador;
    }

    /**
     * Define el valor de la propiedad actualizaciondomicilioarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTUALIZACIONDOMICILIOARRENDADOR(String value) {
        this.actualizaciondomicilioarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad autonomoarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUTONOMOARRENDADOR() {
        return autonomoarrendador;
    }

    /**
     * Define el valor de la propiedad autonomoarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUTONOMOARRENDADOR(String value) {
        this.autonomoarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoiaearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOIAEARRENDADOR() {
        return codigoiaearrendador;
    }

    /**
     * Define el valor de la propiedad codigoiaearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOIAEARRENDADOR(String value) {
        this.codigoiaearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrerepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREREPRESENTANTEARRENDADOR() {
        return nombrerepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad nombrerepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREREPRESENTANTEARRENDADOR(String value) {
        this.nombrerepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTEARRENDADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR() {
        return apellido1RAZONSOCIALREPRESENTANTEARRENDADOR;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTEARRENDADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR(String value) {
        this.apellido1RAZONSOCIALREPRESENTANTEARRENDADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2REPRESENTANTEARRENDADOR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2REPRESENTANTEARRENDADOR() {
        return apellido2REPRESENTANTEARRENDADOR;
    }

    /**
     * Define el valor de la propiedad apellido2REPRESENTANTEARRENDADOR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2REPRESENTANTEARRENDADOR(String value) {
        this.apellido2REPRESENTANTEARRENDADOR = value;
    }

    /**
     * Obtiene el valor de la propiedad motivorepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVOREPRESENTANTEARRENDADOR() {
        return motivorepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad motivorepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVOREPRESENTANTEARRENDADOR(String value) {
        this.motivorepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechainiciotutelaarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAINICIOTUTELAARRENDADOR() {
        return fechainiciotutelaarrendador;
    }

    /**
     * Define el valor de la propiedad fechainiciotutelaarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAINICIOTUTELAARRENDADOR(String value) {
        this.fechainiciotutelaarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechafintutelaarrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFINTUTELAARRENDADOR() {
        return fechafintutelaarrendador;
    }

    /**
     * Define el valor de la propiedad fechafintutelaarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFINTUTELAARRENDADOR(String value) {
        this.fechafintutelaarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptorepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTOREPRESENTANTEARRENDADOR() {
        return conceptorepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad conceptorepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTOREPRESENTANTEARRENDADOR(String value) {
        this.conceptorepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad documentosrepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTOSREPRESENTANTEARRENDADOR() {
        return documentosrepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad documentosrepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTOSREPRESENTANTEARRENDADOR(String value) {
        this.documentosrepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifrepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFREPRESENTANTEARRENDADOR() {
        return fechacaducidadnifrepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifrepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFREPRESENTANTEARRENDADOR(String value) {
        this.fechacaducidadnifrepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivorepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR() {
        return tipodocumentosustitutivorepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivorepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR(String value) {
        this.tipodocumentosustitutivorepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR() {
        return fechacaducidaddocumentosustitutivorepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR(String value) {
        this.fechacaducidaddocumentosustitutivorepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidorepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOREPRESENTANTEARRENDADOR() {
        return indefinidorepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad indefinidorepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOREPRESENTANTEARRENDADOR(String value) {
        this.indefinidorepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad dnirepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIREPRESENTANTEARRENDADOR() {
        return dnirepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad dnirepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIREPRESENTANTEARRENDADOR(String value) {
        this.dnirepresentantearrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreapellidosrepresentantearrendador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREAPELLIDOSREPRESENTANTEARRENDADOR() {
        return nombreapellidosrepresentantearrendador;
    }

    /**
     * Define el valor de la propiedad nombreapellidosrepresentantearrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREAPELLIDOSREPRESENTANTEARRENDADOR(String value) {
        this.nombreapellidosrepresentantearrendador = value;
    }

}
