package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

@Entity
@Table(name = "DUPLICADO_PERM_COND")
public class DuplicadoPermisoConducirVO implements Serializable {

	private static final long serialVersionUID = -4118926011556945566L;

	@Id
	@Column(name = "ID_DUPLICADO_PERM_COND")
	@SequenceGenerator(name = "duplicado_permiso_cond_secuencia", sequenceName = "DUPLICADO_PERMISO_COND_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "duplicado_permiso_cond_secuencia")
	private Long idDuplicadoPermCond;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "TIPO_DUPLICADO")
	private String tipoDuplicado;

	@Column(name = "DOI_TIPO")
	private String doiTipo;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "ESTADO_IMPRESION")
	private String estadoImpresion;

	@Column(name = "RESPUESTA")
	private String respuesta;

	@Column(name = "DOI_TITULAR")
	private String doiTitular;

	@Column(name = "OBSERVACIONES")
	private String observaciones;

	@Column(name = "JEFATURA")
	private String jefatura;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULT_MODIF")
	private Date fechaUltModif;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "EMAIL_AUTE")
	private String emailAute;

	@Column(name = "ESTADO_DECLARACION")
	private String estadoDeclaracion;

	@Column(name = "ESTADO_SOLICITUD")
	private String estadoSolicitud;

	@Column(name = "ESTADO_DECLARACION_TITULAR")
	private String estadoDeclaracionTitular;

	@Column(name = "ESTADO_MANDATO")
	private String estadoMandato;

	@Column(name = "ESTADO_OTRO_DOCUMENTO")
	private String estadoOtroDocumento;

	public Long getIdDuplicadoPermCond() {
		return idDuplicadoPermCond;
	}

	public void setIdDuplicadoPermCond(Long idDuplicadoPermCond) {
		this.idDuplicadoPermCond = idDuplicadoPermCond;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getDoiTipo() {
		return doiTipo;
	}

	public void setDoiTipo(String doiTipo) {
		this.doiTipo = doiTipo;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getDoiTitular() {
		return doiTitular;
	}

	public void setDoiTitular(String doiTitular) {
		this.doiTitular = doiTitular;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getTipoDuplicado() {
		return tipoDuplicado;
	}

	public void setTipoDuplicado(String tipoDuplicado) {
		this.tipoDuplicado = tipoDuplicado;
	}

	public String getEmailAute() {
		return emailAute;
	}

	public void setEmailAute(String emailAute) {
		this.emailAute = emailAute;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getEstadoDeclaracion() {
		return estadoDeclaracion;
	}

	public void setEstadoDeclaracion(String estadoDeclaracion) {
		this.estadoDeclaracion = estadoDeclaracion;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoDeclaracionTitular() {
		return estadoDeclaracionTitular;
	}

	public void setEstadoDeclaracionTitular(String estadoDeclaracionTitular) {
		this.estadoDeclaracionTitular = estadoDeclaracionTitular;
	}

	public String getEstadoMandato() {
		return estadoMandato;
	}

	public void setEstadoMandato(String estadoMandato) {
		this.estadoMandato = estadoMandato;
	}

	public String getEstadoOtroDocumento() {
		return estadoOtroDocumento;
	}

	public void setEstadoOtroDocumento(String estadoOtroDocumento) {
		this.estadoOtroDocumento = estadoOtroDocumento;
	}
}
