package org.gestoresmadrid.core.trafico.permiso.internacional.model.vo;

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
@Table(name = "PERMISO_INTERNACIONAL")
public class PermisoInternacionalVO implements Serializable {

	private static final long serialVersionUID = 904527472368949321L;

	@Id
	@Column(name = "ID_PERMISO")
	@SequenceGenerator(name = "permiso_internacional_secuencia", sequenceName = "PERMISO_INTERNACIONAL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "permiso_internacional_secuencia")
	private Long idPermiso;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

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

	@Column(name = "DOC_APORTADA")
	private String documentacionAportada;

	@Column(name = "NUM_REFERENCIA")
	private String numReferencia;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ESTADO_DECLARACION")
	private String estadoDeclaracion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
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

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getDocumentacionAportada() {
		return documentacionAportada;
	}

	public void setDocumentacionAportada(String documentacionAportada) {
		this.documentacionAportada = documentacionAportada;
	}

	public String getNumReferencia() {
		return numReferencia;
	}

	public void setNumReferencia(String numReferencia) {
		this.numReferencia = numReferencia;
	}

	public String getEstadoDeclaracion() {
		return estadoDeclaracion;
	}

	public void setEstadoDeclaracion(String estadoDeclaracion) {
		this.estadoDeclaracion = estadoDeclaracion;
	}
}
