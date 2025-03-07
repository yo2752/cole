//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

import java.math.BigInteger;
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
 *         &lt;element ref="{}NUMERO_DOCUMENTO"/>
 *         &lt;element ref="{}REFERENCIA_PROPIA" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CREACION"/>
 *         &lt;element ref="{}FECHA_DEVOLUCION"/>
 *         &lt;element ref="{}OBSERVACIONES"/>
 *         &lt;element ref="{}MATRICULA"/>
 *         &lt;element ref="{}CAMBIO_SERVICIO" minOccurs="0"/>
 *         &lt;element ref="{}IMPRESO_PERMISO" minOccurs="0"/>
 *         &lt;element ref="{}RENTING" minOccurs="0"/>
 *         &lt;element ref="{}SIMULTANEA" minOccurs="0"/>
 *         &lt;element ref="{}CONSENTIMIENTO" minOccurs="0"/>
 *         &lt;element ref="{}ACTA_ADJUDICACION" minOccurs="0"/>
 *         &lt;element ref="{}CONTRATO_COMPRAVENTA" minOccurs="0"/>
 *         &lt;element ref="{}SENTENCIA_ADJUDICACION" minOccurs="0"/>
 *         &lt;element ref="{}ACREDITACION" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_ADQUIRIENTE"/>
 *         &lt;element ref="{}DATOS_TRANSMITENTE"/>
 *         &lt;element ref="{}DATOS_PRESENTADOR" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_VEHICULO" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_LIMITACION" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_ARRENDADOR" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_COMPRA_VENTA" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_620" minOccurs="0"/>
 *         &lt;element ref="{}DATOS_PRESENTACION"/>
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
@XmlRootElement(name = "TRANSMISION")
public class TRANSMISION {

    @XmlElement(name = "NUMERO_DOCUMENTO", required = true)
    protected String numerodocumento;
    @XmlElement(name = "REFERENCIA_PROPIA")
    protected String referenciapropia;
    @XmlElement(name = "FECHA_CREACION", required = true)
    protected String fechacreacion;
    @XmlElement(name = "FECHA_DEVOLUCION", required = true)
    protected String fechadevolucion;
    @XmlElement(name = "OBSERVACIONES", required = true)
    protected String observaciones;
    @XmlElement(name = "MATRICULA", required = true)
    protected String matricula;
    @XmlElement(name = "CAMBIO_SERVICIO")
    protected String cambioservicio;
    @XmlElement(name = "IMPRESO_PERMISO")
    protected String impresopermiso;
    @XmlElement(name = "RENTING")
    protected String renting;
    @XmlElement(name = "SIMULTANEA", defaultValue = "-1")
    protected BigInteger simultanea;
    @XmlElement(name = "CONSENTIMIENTO")
    protected String consentimiento;
    @XmlElement(name = "ACTA_ADJUDICACION")
    protected String actaadjudicacion;
    @XmlElement(name = "CONTRATO_COMPRAVENTA")
    protected String contratocompraventa;
    @XmlElement(name = "SENTENCIA_ADJUDICACION")
    protected String sentenciaadjudicacion;
    @XmlElement(name = "ACREDITACION")
    protected String acreditacion;
    @XmlElement(name = "DATOS_ADQUIRIENTE", required = true)
    protected DATOSADQUIRIENTE datosadquiriente;
    @XmlElement(name = "DATOS_TRANSMITENTE", required = true)
    protected DATOSTRANSMITENTE datostransmitente;
    @XmlElement(name = "DATOS_PRESENTADOR")
    protected DATOSPRESENTADOR datospresentador;
    @XmlElement(name = "DATOS_VEHICULO")
    protected DATOSVEHICULO datosvehiculo;
    @XmlElement(name = "DATOS_LIMITACION")
    protected DATOSLIMITACION datoslimitacion;
    @XmlElement(name = "DATOS_ARRENDADOR")
    protected DATOSARRENDADOR datosarrendador;
    @XmlElement(name = "DATOS_COMPRA_VENTA")
    protected DATOSCOMPRAVENTA datoscompraventa;
    @XmlElement(name = "DATOS_620")
    protected DATOS620 datos620;
    @XmlElement(name = "DATOS_PRESENTACION", required = true)
    protected DATOSPRESENTACION datospresentacion;

    /**
     * Obtiene el valor de la propiedad numerodocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODOCUMENTO() {
        return numerodocumento;
    }

    /**
     * Define el valor de la propiedad numerodocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODOCUMENTO(String value) {
        this.numerodocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad referenciapropia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREFERENCIAPROPIA() {
        return referenciapropia;
    }

    /**
     * Define el valor de la propiedad referenciapropia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREFERENCIAPROPIA(String value) {
        this.referenciapropia = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacreacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACREACION() {
        return fechacreacion;
    }

    /**
     * Define el valor de la propiedad fechacreacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACREACION(String value) {
        this.fechacreacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechadevolucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHADEVOLUCION() {
        return fechadevolucion;
    }

    /**
     * Define el valor de la propiedad fechadevolucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHADEVOLUCION(String value) {
        this.fechadevolucion = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBSERVACIONES() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBSERVACIONES(String value) {
        this.observaciones = value;
    }

    /**
     * Obtiene el valor de la propiedad matricula.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATRICULA() {
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
    public void setMATRICULA(String value) {
        this.matricula = value;
    }

    /**
     * Obtiene el valor de la propiedad cambioservicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAMBIOSERVICIO() {
        return cambioservicio;
    }

    /**
     * Define el valor de la propiedad cambioservicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAMBIOSERVICIO(String value) {
        this.cambioservicio = value;
    }

    /**
     * Obtiene el valor de la propiedad impresopermiso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPRESOPERMISO() {
        return impresopermiso;
    }

    /**
     * Define el valor de la propiedad impresopermiso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPRESOPERMISO(String value) {
        this.impresopermiso = value;
    }

    /**
     * Obtiene el valor de la propiedad renting.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRENTING() {
        return renting;
    }

    /**
     * Define el valor de la propiedad renting.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRENTING(String value) {
        this.renting = value;
    }

    /**
     * Obtiene el valor de la propiedad simultanea.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSIMULTANEA() {
        return simultanea;
    }

    /**
     * Define el valor de la propiedad simultanea.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSIMULTANEA(BigInteger value) {
        this.simultanea = value;
    }

    /**
     * Obtiene el valor de la propiedad consentimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONSENTIMIENTO() {
        return consentimiento;
    }

    /**
     * Define el valor de la propiedad consentimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONSENTIMIENTO(String value) {
        this.consentimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad actaadjudicacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTAADJUDICACION() {
        return actaadjudicacion;
    }

    /**
     * Define el valor de la propiedad actaadjudicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTAADJUDICACION(String value) {
        this.actaadjudicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad contratocompraventa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRATOCOMPRAVENTA() {
        return contratocompraventa;
    }

    /**
     * Define el valor de la propiedad contratocompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRATOCOMPRAVENTA(String value) {
        this.contratocompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad sentenciaadjudicacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSENTENCIAADJUDICACION() {
        return sentenciaadjudicacion;
    }

    /**
     * Define el valor de la propiedad sentenciaadjudicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSENTENCIAADJUDICACION(String value) {
        this.sentenciaadjudicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad acreditacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACREDITACION() {
        return acreditacion;
    }

    /**
     * Define el valor de la propiedad acreditacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACREDITACION(String value) {
        this.acreditacion = value;
    }

    /**
     * Obtiene el valor de la propiedad datosadquiriente.
     * 
     * @return
     *     possible object is
     *     {@link DATOSADQUIRIENTE }
     *     
     */
    public DATOSADQUIRIENTE getDATOSADQUIRIENTE() {
        return datosadquiriente;
    }

    /**
     * Define el valor de la propiedad datosadquiriente.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSADQUIRIENTE }
     *     
     */
    public void setDATOSADQUIRIENTE(DATOSADQUIRIENTE value) {
        this.datosadquiriente = value;
    }

    /**
     * Obtiene el valor de la propiedad datostransmitente.
     * 
     * @return
     *     possible object is
     *     {@link DATOSTRANSMITENTE }
     *     
     */
    public DATOSTRANSMITENTE getDATOSTRANSMITENTE() {
        return datostransmitente;
    }

    /**
     * Define el valor de la propiedad datostransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSTRANSMITENTE }
     *     
     */
    public void setDATOSTRANSMITENTE(DATOSTRANSMITENTE value) {
        this.datostransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad datospresentador.
     * 
     * @return
     *     possible object is
     *     {@link DATOSPRESENTADOR }
     *     
     */
    public DATOSPRESENTADOR getDATOSPRESENTADOR() {
        return datospresentador;
    }

    /**
     * Define el valor de la propiedad datospresentador.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSPRESENTADOR }
     *     
     */
    public void setDATOSPRESENTADOR(DATOSPRESENTADOR value) {
        this.datospresentador = value;
    }

    /**
     * Obtiene el valor de la propiedad datosvehiculo.
     * 
     * @return
     *     possible object is
     *     {@link DATOSVEHICULO }
     *     
     */
    public DATOSVEHICULO getDATOSVEHICULO() {
        return datosvehiculo;
    }

    /**
     * Define el valor de la propiedad datosvehiculo.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSVEHICULO }
     *     
     */
    public void setDATOSVEHICULO(DATOSVEHICULO value) {
        this.datosvehiculo = value;
    }

    /**
     * Obtiene el valor de la propiedad datoslimitacion.
     * 
     * @return
     *     possible object is
     *     {@link DATOSLIMITACION }
     *     
     */
    public DATOSLIMITACION getDATOSLIMITACION() {
        return datoslimitacion;
    }

    /**
     * Define el valor de la propiedad datoslimitacion.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSLIMITACION }
     *     
     */
    public void setDATOSLIMITACION(DATOSLIMITACION value) {
        this.datoslimitacion = value;
    }

    /**
     * Obtiene el valor de la propiedad datosarrendador.
     * 
     * @return
     *     possible object is
     *     {@link DATOSARRENDADOR }
     *     
     */
    public DATOSARRENDADOR getDATOSARRENDADOR() {
        return datosarrendador;
    }

    /**
     * Define el valor de la propiedad datosarrendador.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSARRENDADOR }
     *     
     */
    public void setDATOSARRENDADOR(DATOSARRENDADOR value) {
        this.datosarrendador = value;
    }

    /**
     * Obtiene el valor de la propiedad datoscompraventa.
     * 
     * @return
     *     possible object is
     *     {@link DATOSCOMPRAVENTA }
     *     
     */
    public DATOSCOMPRAVENTA getDATOSCOMPRAVENTA() {
        return datoscompraventa;
    }

    /**
     * Define el valor de la propiedad datoscompraventa.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSCOMPRAVENTA }
     *     
     */
    public void setDATOSCOMPRAVENTA(DATOSCOMPRAVENTA value) {
        this.datoscompraventa = value;
    }

    /**
     * Obtiene el valor de la propiedad datos620.
     * 
     * @return
     *     possible object is
     *     {@link DATOS620 }
     *     
     */
    public DATOS620 getDATOS620() {
        return datos620;
    }

    /**
     * Define el valor de la propiedad datos620.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOS620 }
     *     
     */
    public void setDATOS620(DATOS620 value) {
        this.datos620 = value;
    }

    /**
     * Obtiene el valor de la propiedad datospresentacion.
     * 
     * @return
     *     possible object is
     *     {@link DATOSPRESENTACION }
     *     
     */
    public DATOSPRESENTACION getDATOSPRESENTACION() {
        return datospresentacion;
    }

    /**
     * Define el valor de la propiedad datospresentacion.
     * 
     * @param value
     *     allowed object is
     *     {@link DATOSPRESENTACION }
     *     
     */
    public void setDATOSPRESENTACION(DATOSPRESENTACION value) {
        this.datospresentacion = value;
    }

}
