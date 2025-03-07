//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.20 at 04:26:49 PM CEST 
//


package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LIC_EMCC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LIC_EMCC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_solicitud" type="{http://licencias/mensajes}string13type"/>
 *         &lt;element name="datos_comunicacion_solicitud" type="{http://licencias/mensajes}datos_comunicacion_solicitud"/>
 *         &lt;element name="interesado_principal" type="{http://licencias/mensajes}persona"/>
 *         &lt;element name="otros_interesados" type="{http://licencias/mensajes}persona" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="representante" type="{http://licencias/mensajes}persona" minOccurs="0"/>
 *         &lt;element name="notificacion" type="{http://licencias/mensajes}notificacion" minOccurs="0"/>
 *         &lt;element name="emplazamiento_actuacion" type="{http://licencias/mensajes}emplazamiento_lic"/>
 *         &lt;element name="tipo_actuacion" type="{http://licencias/mensajes}string2type"/>
 *         &lt;element name="datos_actuacion" type="{http://licencias/mensajes}datos_actuacion_slim" minOccurs="0"/>
 *         &lt;element name="solicitud_conjunta_autorizaciones" type="{http://licencias/mensajes}solicitud_conjunta_autorizaciones"/>
 *         &lt;element name="datos_obra" type="{http://licencias/mensajes}datos_obra_LIC"/>
 *         &lt;element name="descripcion" type="{http://licencias/mensajes}string600type"/>
 *         &lt;element name="otros_datos_actuacion" type="{http://licencias/mensajes}otros_datos_actuacion_lic"/>
 *         &lt;element name="parametros_edificacion_alta" type="{http://licencias/mensajes}parametros_edificacion_alta_lic" minOccurs="0"/>
 *         &lt;element name="parametros_edificacion_baja" type="{http://licencias/mensajes}parametros_edificacion_baja_lic" minOccurs="0"/>
 *         &lt;element name="equipos_inst_relevantes" type="{http://licencias/mensajes}equipos_inst_relevantes_ordinario" minOccurs="0"/>
 *         &lt;element name="ventilacion_forzada" type="{http://licencias/mensajes}ventilacion_forzada"/>
 *         &lt;element name="agua_caliente_sanitaria" type="{http://licencias/mensajes}agua_caliente_sanitaria"/>
 *         &lt;element name="instalaciones_calefaccion" type="{http://licencias/mensajes}instalaciones_calefaccion"/>
 *         &lt;element name="climatizacion_aire_acondicionado" type="{http://licencias/mensajes}climatizacion_aire_acondicionado"/>
 *         &lt;element name="captacion_energia_solar" type="{http://licencias/mensajes}captacion_energia_solar"/>
 *         &lt;element name="informacion_local" type="{http://licencias/mensajes}informacion_local_lic" minOccurs="0"/>
 *         &lt;element name="autoliquidaciones" type="{http://licencias/mensajes}autoliquidaciones_lic" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LIC_EMCC", propOrder = {
    "idSolicitud",
    "datosComunicacionSolicitud",
    "interesadoPrincipal",
    "otrosInteresados",
    "representante",
    "notificacion",
    "emplazamientoActuacion",
    "tipoActuacion",
    "datosActuacion",
    "solicitudConjuntaAutorizaciones",
    "datosObra",
    "descripcion",
    "otrosDatosActuacion",
    "parametrosEdificacionAlta",
    "parametrosEdificacionBaja",
    "equiposInstRelevantes",
    "ventilacionForzada",
    "aguaCalienteSanitaria",
    "instalacionesCalefaccion",
    "climatizacionAireAcondicionado",
    "captacionEnergiaSolar",
    "informacionLocal",
    "autoliquidaciones"
})
public class LICEMCC {

    @XmlElement(name = "id_solicitud", required = true)
    protected String idSolicitud;
    @XmlElement(name = "datos_comunicacion_solicitud", required = true)
    protected DatosComunicacionSolicitud datosComunicacionSolicitud;
    @XmlElement(name = "interesado_principal", required = true)
    protected Persona interesadoPrincipal;
    @XmlElement(name = "otros_interesados")
    protected List<Persona> otrosInteresados;
    protected Persona representante;
    protected Notificacion notificacion;
    @XmlElement(name = "emplazamiento_actuacion", required = true)
    protected EmplazamientoLic emplazamientoActuacion;
    @XmlElement(name = "tipo_actuacion", required = true)
    protected String tipoActuacion;
    @XmlElement(name = "datos_actuacion")
    protected DatosActuacionSlim datosActuacion;
    @XmlElement(name = "solicitud_conjunta_autorizaciones", required = true)
    protected SolicitudConjuntaAutorizaciones solicitudConjuntaAutorizaciones;
    @XmlElement(name = "datos_obra", required = true)
    protected DatosObraLIC datosObra;
    @XmlElement(required = true)
    protected String descripcion;
    @XmlElement(name = "otros_datos_actuacion", required = true)
    protected OtrosDatosActuacionLic otrosDatosActuacion;
    @XmlElement(name = "parametros_edificacion_alta")
    protected ParametrosEdificacionAltaLic parametrosEdificacionAlta;
    @XmlElement(name = "parametros_edificacion_baja")
    protected ParametrosEdificacionBajaLic parametrosEdificacionBaja;
    @XmlElement(name = "equipos_inst_relevantes")
    protected EquiposInstRelevantesOrdinario equiposInstRelevantes;
    @XmlElement(name = "ventilacion_forzada", required = true)
    protected VentilacionForzada ventilacionForzada;
    @XmlElement(name = "agua_caliente_sanitaria", required = true)
    protected AguaCalienteSanitaria aguaCalienteSanitaria;
    @XmlElement(name = "instalaciones_calefaccion", required = true)
    protected InstalacionesCalefaccion instalacionesCalefaccion;
    @XmlElement(name = "climatizacion_aire_acondicionado", required = true)
    protected ClimatizacionAireAcondicionado climatizacionAireAcondicionado;
    @XmlElement(name = "captacion_energia_solar", required = true)
    protected CaptacionEnergiaSolar captacionEnergiaSolar;
    @XmlElement(name = "informacion_local")
    protected InformacionLocalLic informacionLocal;
    protected AutoliquidacionesLic autoliquidaciones;

    /**
     * Gets the value of the idSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the value of the idSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSolicitud(String value) {
        this.idSolicitud = value;
    }

    /**
     * Gets the value of the datosComunicacionSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link DatosComunicacionSolicitud }
     *     
     */
    public DatosComunicacionSolicitud getDatosComunicacionSolicitud() {
        return datosComunicacionSolicitud;
    }

    /**
     * Sets the value of the datosComunicacionSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosComunicacionSolicitud }
     *     
     */
    public void setDatosComunicacionSolicitud(DatosComunicacionSolicitud value) {
        this.datosComunicacionSolicitud = value;
    }

    /**
     * Gets the value of the interesadoPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link Persona }
     *     
     */
    public Persona getInteresadoPrincipal() {
        return interesadoPrincipal;
    }

    /**
     * Sets the value of the interesadoPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Persona }
     *     
     */
    public void setInteresadoPrincipal(Persona value) {
        this.interesadoPrincipal = value;
    }

    /**
     * Gets the value of the otrosInteresados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otrosInteresados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtrosInteresados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Persona }
     * 
     * 
     */
    public List<Persona> getOtrosInteresados() {
        if (otrosInteresados == null) {
            otrosInteresados = new ArrayList<Persona>();
        }
        return this.otrosInteresados;
    }

    /**
     * Gets the value of the representante property.
     * 
     * @return
     *     possible object is
     *     {@link Persona }
     *     
     */
    public Persona getRepresentante() {
        return representante;
    }

    /**
     * Sets the value of the representante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Persona }
     *     
     */
    public void setRepresentante(Persona value) {
        this.representante = value;
    }

    /**
     * Gets the value of the notificacion property.
     * 
     * @return
     *     possible object is
     *     {@link Notificacion }
     *     
     */
    public Notificacion getNotificacion() {
        return notificacion;
    }

    /**
     * Sets the value of the notificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notificacion }
     *     
     */
    public void setNotificacion(Notificacion value) {
        this.notificacion = value;
    }

    /**
     * Gets the value of the emplazamientoActuacion property.
     * 
     * @return
     *     possible object is
     *     {@link EmplazamientoLic }
     *     
     */
    public EmplazamientoLic getEmplazamientoActuacion() {
        return emplazamientoActuacion;
    }

    /**
     * Sets the value of the emplazamientoActuacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmplazamientoLic }
     *     
     */
    public void setEmplazamientoActuacion(EmplazamientoLic value) {
        this.emplazamientoActuacion = value;
    }

    /**
     * Gets the value of the tipoActuacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoActuacion() {
        return tipoActuacion;
    }

    /**
     * Sets the value of the tipoActuacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoActuacion(String value) {
        this.tipoActuacion = value;
    }

    /**
     * Gets the value of the datosActuacion property.
     * 
     * @return
     *     possible object is
     *     {@link DatosActuacionSlim }
     *     
     */
    public DatosActuacionSlim getDatosActuacion() {
        return datosActuacion;
    }

    /**
     * Sets the value of the datosActuacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosActuacionSlim }
     *     
     */
    public void setDatosActuacion(DatosActuacionSlim value) {
        this.datosActuacion = value;
    }

    /**
     * Gets the value of the solicitudConjuntaAutorizaciones property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudConjuntaAutorizaciones }
     *     
     */
    public SolicitudConjuntaAutorizaciones getSolicitudConjuntaAutorizaciones() {
        return solicitudConjuntaAutorizaciones;
    }

    /**
     * Sets the value of the solicitudConjuntaAutorizaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudConjuntaAutorizaciones }
     *     
     */
    public void setSolicitudConjuntaAutorizaciones(SolicitudConjuntaAutorizaciones value) {
        this.solicitudConjuntaAutorizaciones = value;
    }

    /**
     * Gets the value of the datosObra property.
     * 
     * @return
     *     possible object is
     *     {@link DatosObraLIC }
     *     
     */
    public DatosObraLIC getDatosObra() {
        return datosObra;
    }

    /**
     * Sets the value of the datosObra property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosObraLIC }
     *     
     */
    public void setDatosObra(DatosObraLIC value) {
        this.datosObra = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the otrosDatosActuacion property.
     * 
     * @return
     *     possible object is
     *     {@link OtrosDatosActuacionLic }
     *     
     */
    public OtrosDatosActuacionLic getOtrosDatosActuacion() {
        return otrosDatosActuacion;
    }

    /**
     * Sets the value of the otrosDatosActuacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtrosDatosActuacionLic }
     *     
     */
    public void setOtrosDatosActuacion(OtrosDatosActuacionLic value) {
        this.otrosDatosActuacion = value;
    }

    /**
     * Gets the value of the parametrosEdificacionAlta property.
     * 
     * @return
     *     possible object is
     *     {@link ParametrosEdificacionAltaLic }
     *     
     */
    public ParametrosEdificacionAltaLic getParametrosEdificacionAlta() {
        return parametrosEdificacionAlta;
    }

    /**
     * Sets the value of the parametrosEdificacionAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametrosEdificacionAltaLic }
     *     
     */
    public void setParametrosEdificacionAlta(ParametrosEdificacionAltaLic value) {
        this.parametrosEdificacionAlta = value;
    }

    /**
     * Gets the value of the parametrosEdificacionBaja property.
     * 
     * @return
     *     possible object is
     *     {@link ParametrosEdificacionBajaLic }
     *     
     */
    public ParametrosEdificacionBajaLic getParametrosEdificacionBaja() {
        return parametrosEdificacionBaja;
    }

    /**
     * Sets the value of the parametrosEdificacionBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametrosEdificacionBajaLic }
     *     
     */
    public void setParametrosEdificacionBaja(ParametrosEdificacionBajaLic value) {
        this.parametrosEdificacionBaja = value;
    }

    /**
     * Gets the value of the equiposInstRelevantes property.
     * 
     * @return
     *     possible object is
     *     {@link EquiposInstRelevantesOrdinario }
     *     
     */
    public EquiposInstRelevantesOrdinario getEquiposInstRelevantes() {
        return equiposInstRelevantes;
    }

    /**
     * Sets the value of the equiposInstRelevantes property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquiposInstRelevantesOrdinario }
     *     
     */
    public void setEquiposInstRelevantes(EquiposInstRelevantesOrdinario value) {
        this.equiposInstRelevantes = value;
    }

    /**
     * Gets the value of the ventilacionForzada property.
     * 
     * @return
     *     possible object is
     *     {@link VentilacionForzada }
     *     
     */
    public VentilacionForzada getVentilacionForzada() {
        return ventilacionForzada;
    }

    /**
     * Sets the value of the ventilacionForzada property.
     * 
     * @param value
     *     allowed object is
     *     {@link VentilacionForzada }
     *     
     */
    public void setVentilacionForzada(VentilacionForzada value) {
        this.ventilacionForzada = value;
    }

    /**
     * Gets the value of the aguaCalienteSanitaria property.
     * 
     * @return
     *     possible object is
     *     {@link AguaCalienteSanitaria }
     *     
     */
    public AguaCalienteSanitaria getAguaCalienteSanitaria() {
        return aguaCalienteSanitaria;
    }

    /**
     * Sets the value of the aguaCalienteSanitaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link AguaCalienteSanitaria }
     *     
     */
    public void setAguaCalienteSanitaria(AguaCalienteSanitaria value) {
        this.aguaCalienteSanitaria = value;
    }

    /**
     * Gets the value of the instalacionesCalefaccion property.
     * 
     * @return
     *     possible object is
     *     {@link InstalacionesCalefaccion }
     *     
     */
    public InstalacionesCalefaccion getInstalacionesCalefaccion() {
        return instalacionesCalefaccion;
    }

    /**
     * Sets the value of the instalacionesCalefaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstalacionesCalefaccion }
     *     
     */
    public void setInstalacionesCalefaccion(InstalacionesCalefaccion value) {
        this.instalacionesCalefaccion = value;
    }

    /**
     * Gets the value of the climatizacionAireAcondicionado property.
     * 
     * @return
     *     possible object is
     *     {@link ClimatizacionAireAcondicionado }
     *     
     */
    public ClimatizacionAireAcondicionado getClimatizacionAireAcondicionado() {
        return climatizacionAireAcondicionado;
    }

    /**
     * Sets the value of the climatizacionAireAcondicionado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClimatizacionAireAcondicionado }
     *     
     */
    public void setClimatizacionAireAcondicionado(ClimatizacionAireAcondicionado value) {
        this.climatizacionAireAcondicionado = value;
    }

    /**
     * Gets the value of the captacionEnergiaSolar property.
     * 
     * @return
     *     possible object is
     *     {@link CaptacionEnergiaSolar }
     *     
     */
    public CaptacionEnergiaSolar getCaptacionEnergiaSolar() {
        return captacionEnergiaSolar;
    }

    /**
     * Sets the value of the captacionEnergiaSolar property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaptacionEnergiaSolar }
     *     
     */
    public void setCaptacionEnergiaSolar(CaptacionEnergiaSolar value) {
        this.captacionEnergiaSolar = value;
    }

    /**
     * Gets the value of the informacionLocal property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionLocalLic }
     *     
     */
    public InformacionLocalLic getInformacionLocal() {
        return informacionLocal;
    }

    /**
     * Sets the value of the informacionLocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionLocalLic }
     *     
     */
    public void setInformacionLocal(InformacionLocalLic value) {
        this.informacionLocal = value;
    }

    /**
     * Gets the value of the autoliquidaciones property.
     * 
     * @return
     *     possible object is
     *     {@link AutoliquidacionesLic }
     *     
     */
    public AutoliquidacionesLic getAutoliquidaciones() {
        return autoliquidaciones;
    }

    /**
     * Sets the value of the autoliquidaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoliquidacionesLic }
     *     
     */
    public void setAutoliquidaciones(AutoliquidacionesLic value) {
        this.autoliquidaciones = value;
    }

}
