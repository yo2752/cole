package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.gestoresmadrid.core.direccion.model.vo.PactoVO;

/**
 * The persistent class for the TRAMITE_REG_RBM database table.
 * 
 */
@Entity
@Table(name="TRAMITE_REG_RBM")
@PrimaryKeyJoinColumn(name="ID_TRAMITE_REGISTRO")
public class TramiteRegRbmVO extends TramiteRegistroVO implements Serializable {

	private static final long serialVersionUID = -2040751165749399362L;

	@Column(name="APROBADO_DGRN")
	private String aprobadoDgrn;

	@Column(name="ESTADO_FACTURACION")
	private String estadoFacturacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="ID_OFICINA_REGISTRO")
	private BigDecimal idOficinaRegistro;

	@Column(name="MODELO_CONTRATO")
	private String modeloContrato;

	@Column(name="NOMBRE_ASOCIACION")
	private String nombreAsociacion;

	@Column(name="NOMBRE_DOCUMENTO")
	private String nombreDocumento;

	@Column(name="NUMERO_EJEMPLAR")
	private BigDecimal numeroEjemplar;

	@Column(name="NUMERO_EMISION")
	private String numeroEmision;

	@Column(name="NUMERO_IMPRESO")
	private String numeroImpreso;

	@Column(name="NUMERO_OPERACION")
	private String numeroOperacion;

	@Column(name="PERSONA_NO_DETERMINADA")
	private String personaNoDeterminada;

	@Column(name="TIPO_CESION_TERCERO")
	private String tipoCesionTercero;

	@Column(name="TIPO_CONTRATO")
	private String tipoContrato;

	@Column(name="TIPO_CONTRATO_LR")
	private String tipoContratoLr;

	@Column(name="TIPO_OPERACION")
	private String tipoOperacion;

	@Column(name="EMAIL")
	private String email;

	@Column(name="EMAIL2")
	private String email2;

	@Column(name="TELEFONO")
	private String telefono;

	@Column(name="TELEFONO2")
	private String telefono2;

	//Desistimientos
	@Column(name="FECHA_ENTRADA_ORIGEN")
	private Date fecEntradaOrigen;
	
	@Column(name="NUMERO_ENTRADA_ORIGEN")
	private BigDecimal numEntradaOrigen;
	
	@Column(name="FECHA_PRESENTACION_ORIGEN")
	private Date fecPresentacionOrigen;
	
	@Column(name="NUMERO_PRESENTACION_ORIGEN")
	private BigDecimal numPresentacionOrigen;

	
	//bi-directional One-to-many association to Clausula
	@OneToMany(mappedBy="tramiteRegRbm")
	private Set<ClausulaVO> clausulas;

	//bi-directional One-to-many association to DatoFirma
	@OneToMany(mappedBy="tramiteRegRbm")
	private Set<DatoFirmaVO> datoFirmas;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_DATOS_FINANCIEROS")
	private DatosFinancierosVO datosFinanciero;

	//bi-directional many-to-one association to Mensaje
	@OneToMany(mappedBy="tramiteRegRbm", fetch = FetchType.LAZY)
	private Set<MensajeVO> mensajes;

	//bi-directional One-to-many association to Propiedad
	@OneToMany(mappedBy="idTramiteRegistro", fetch = FetchType.LAZY)
	private Set<PropiedadVO> propiedades;

	//bi-directional many-to-many association to IntervinienteRegistro
	@ManyToMany(mappedBy="tramites", fetch = FetchType.LAZY)
	private Set<IntervinienteRegistroVO> intervinienteRegistro;

	//bi-directional One-to-many association to Propiedad
	@OneToMany(mappedBy="idTramiteRegistro", fetch = FetchType.LAZY)
	private Set<PactoVO> pactos;

	@Column(name="CAUSA_CANCELACION")
	private String causaCancelacion;

	@Column(name="IMPORTE_COMISION_CANCELACION")
	private BigDecimal importeComisionCancelacion;

	@Column(name="SITUACION_JURIDICA_CANCELACION")
	private String situacionJuridicaCancelacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ENTIDAD_SUSCRIPTORA", referencedColumnName="ID_ENTIDAD")
	private EntidadCancelacionVO entidadSuscriptora;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ENTIDAD_SUCESORA", referencedColumnName="ID_ENTIDAD")
	private EntidadCancelacionVO entidadSucesora;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_DATOS_INSCRIPCION")
	private DatosInscripcionVO datosInscripcion;

	public TramiteRegRbmVO() {
	}

	public String getAprobadoDgrn() {
		return this.aprobadoDgrn;
	}

	public void setAprobadoDgrn(String aprobadoDgrn) {
		this.aprobadoDgrn = aprobadoDgrn;
	}

	public String getEstadoFacturacion() {
		return this.estadoFacturacion;
	}

	public void setEstadoFacturacion(String estadoFacturacion) {
		this.estadoFacturacion = estadoFacturacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getIdOficinaRegistro() {
		return this.idOficinaRegistro;
	}

	public void setIdOficinaRegistro(BigDecimal idOficinaRegistro) {
		this.idOficinaRegistro = idOficinaRegistro;
	}

	public String getModeloContrato() {
		return this.modeloContrato;
	}

	public void setModeloContrato(String modeloContrato) {
		this.modeloContrato = modeloContrato;
	}

	public String getNombreAsociacion() {
		return this.nombreAsociacion;
	}

	public void setNombreAsociacion(String nombreAsociacion) {
		this.nombreAsociacion = nombreAsociacion;
	}

	public String getNombreDocumento() {
		return this.nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public BigDecimal getNumeroEjemplar() {
		return this.numeroEjemplar;
	}

	public void setNumeroEjemplar(BigDecimal numeroEjemplar) {
		this.numeroEjemplar = numeroEjemplar;
	}

	public String getNumeroEmision() {
		return this.numeroEmision;
	}

	public void setNumeroEmision(String numeroEmision) {
		this.numeroEmision = numeroEmision;
	}

	public String getNumeroImpreso() {
		return this.numeroImpreso;
	}

	public void setNumeroImpreso(String numeroImpreso) {
		this.numeroImpreso = numeroImpreso;
	}

	public String getNumeroOperacion() {
		return this.numeroOperacion;
	}

	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public String getPersonaNoDeterminada() {
		return this.personaNoDeterminada;
	}

	public void setPersonaNoDeterminada(String personaNoDeterminada) {
		this.personaNoDeterminada = personaNoDeterminada;
	}

	public String getTipoCesionTercero() {
		return this.tipoCesionTercero;
	}

	public void setTipoCesionTercero(String tipoCesionTercero) {
		this.tipoCesionTercero = tipoCesionTercero;
	}

	public String getTipoContrato() {
		return this.tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getTipoContratoLr() {
		return this.tipoContratoLr;
	}

	public void setTipoContratoLr(String tipoContratoLr) {
		this.tipoContratoLr = tipoContratoLr;
	}

	public String getTipoOperacion() {
		return this.tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public DatosFinancierosVO getDatosFinanciero() {
		return this.datosFinanciero;
	}

	public void setDatosFinanciero(DatosFinancierosVO datosFinanciero) {
		this.datosFinanciero = datosFinanciero;
	}

	public Set<ClausulaVO> getClausulas() {
		return clausulas;
	}

	public void setClausulas(Set<ClausulaVO> clausulas) {
		this.clausulas = clausulas;
	}

	public Set<DatoFirmaVO> getDatoFirmas() {
		return datoFirmas;
	}

	public void setDatoFirmas(Set<DatoFirmaVO> datoFirmas) {
		this.datoFirmas = datoFirmas;
	}

	public Set<MensajeVO> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Set<MensajeVO> mensajes) {
		this.mensajes = mensajes;
	}

	public Set<PropiedadVO> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Set<PropiedadVO> propiedades) {
		this.propiedades = propiedades;
	}

	public Set<IntervinienteRegistroVO> getIntervinienteRegistro() {
		return intervinienteRegistro;
	}

	public void setIntervinienteRegistro(Set<IntervinienteRegistroVO> intervinienteRegistro) {
		this.intervinienteRegistro = intervinienteRegistro;
	}

	public Set<PactoVO> getPactos() {
		return pactos;
	}

	public void setPactos(Set<PactoVO> pactos) {
		this.pactos = pactos;
	}

	public String getCausaCancelacion() {
		return causaCancelacion;
	}

	public void setCausaCancelacion(String causaCancelacion) {
		this.causaCancelacion = causaCancelacion;
	}

	public BigDecimal getImporteComisionCancelacion() {
		return importeComisionCancelacion;
	}

	public void setImporteComisionCancelacion(BigDecimal importeComisionCancelacion) {
		this.importeComisionCancelacion = importeComisionCancelacion;
	}

	public String getSituacionJuridicaCancelacion() {
		return situacionJuridicaCancelacion;
	}

	public void setSituacionJuridicaCancelacion(String situacionJuridicaCancelacion) {
		this.situacionJuridicaCancelacion = situacionJuridicaCancelacion;
	}

	public DatosInscripcionVO getDatosInscripcion() {
		return datosInscripcion;
	}

	public void setDatosInscripcion(DatosInscripcionVO datosInscripcion) {
		this.datosInscripcion = datosInscripcion;
	}

	public EntidadCancelacionVO getEntidadSuscriptora() {
		return entidadSuscriptora;
	}

	public void setEntidadSuscriptora(EntidadCancelacionVO entidadSuscriptora) {
		this.entidadSuscriptora = entidadSuscriptora;
	}

	public EntidadCancelacionVO getEntidadSucesora() {
		return entidadSucesora;
	}

	public void setEntidadSucesora(EntidadCancelacionVO entidadSucesora) {
		this.entidadSucesora = entidadSucesora;
	}

	public Date getFecEntradaOrigen() {
		return fecEntradaOrigen;
	}

	public void setFecEntradaOrigen(Date fecEntradaOrigen) {
		this.fecEntradaOrigen = fecEntradaOrigen;
	}

	public BigDecimal getNumEntradaOrigen() {
		return numEntradaOrigen;
	}

	public void setNumEntradaOrigen(BigDecimal numEntradaOrigen) {
		this.numEntradaOrigen = numEntradaOrigen;
	}

	public Date getFecPresentacionOrigen() {
		return fecPresentacionOrigen;
	}

	public void setFecPresentacionOrigen(Date fecPresentacionOrigen) {
		this.fecPresentacionOrigen = fecPresentacionOrigen;
	}

	public BigDecimal getNumPresentacionOrigen() {
		return numPresentacionOrigen;
	}

	public void setNumPresentacionOrigen(BigDecimal numPresentacionOrigen) {
		this.numPresentacionOrigen = numPresentacionOrigen;
	}


}