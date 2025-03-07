package org.gestoresmadrid.core.trafico.eeff.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;

@Entity
@Table(name="EEFF_CONSULTA")
public class ConsultaEEFFVO implements Serializable {

	private static final long serialVersionUID = -135116835653002679L;

	@Id
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="NUM_EXPEDIENTE_TRAMITE")
	private BigDecimal numExpedienteTramite;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="REALIZADO")
	private Boolean realizado;

	@Column(name="FECHA_REALIZACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRealizacion;
	
	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name="TARJETA_BASTIDOR")
	private String tarjetaBastidor;

	@Column(name="TARJETA_NIVE")
	private String tarjetaNive;

	@Column(name="FIR_CIF")
	private String firCif;

	@Column(name="FIR_MARCA")
	private String firMarca;

	@Column(name="RESPUESTA")
	private String respuesta;
	
	@Column(name="NIF_REPRESENTADO")
	private String nifRepresentado;
	
	@Column(name="NOMBRE_REPRESENTADO")
	private String nombreRepresentado;
	
	@Column(name = "ESTADO")
	private BigDecimal estado;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable=false,updatable=false)
	private ContratoVO contrato;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_EXPEDIENTE_TRAMITE", referencedColumnName="NUM_EXPEDIENTE", insertable=false,updatable=false)
	private TramiteTrafMatrVO tramiteTraficoMatriculacion;
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}

	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Boolean getRealizado() {
		return realizado;
	}

	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}

	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}

	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}

	public String getTarjetaNive() {
		return tarjetaNive;
	}

	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}

	public String getFirCif() {
		return firCif;
	}

	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}

	public String getFirMarca() {
		return firMarca;
	}

	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getNifRepresentado() {
		return nifRepresentado;
	}

	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}

	public String getNombreRepresentado() {
		return nombreRepresentado;
	}

	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
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

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public TramiteTrafMatrVO getTramiteTraficoMatriculacion() {
		return tramiteTraficoMatriculacion;
	}

	public void setTramiteTraficoMatriculacion(
			TramiteTrafMatrVO tramiteTraficoMatriculacion) {
		this.tramiteTraficoMatriculacion = tramiteTraficoMatriculacion;
	}
	
}
