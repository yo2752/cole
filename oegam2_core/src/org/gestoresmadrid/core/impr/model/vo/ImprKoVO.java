package org.gestoresmadrid.core.impr.model.vo;

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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;


@Entity
@Table(name = "IMPR_KO")
public class ImprKoVO implements Serializable {

	private static final long serialVersionUID = -6585824414675100312L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "impr_ko_secuencia", sequenceName = "ID_IMPR_KO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "impr_ko_secuencia")
	Long id;

	@Column(name = "MATRICULA")
	String matricula;

	@Column(name = "BASTIDOR")
	String bastidor;

	@Column(name = "TIPO_IMPR")
	String tipoImpr;

	@Column(name = "TIPO_TRAMITE")
	String tipoTramite;

	@Column(name = "NUM_EXPEDIENTE")
	BigDecimal numExpediente;

	@Column(name = "ESTADO")
	String estado;

	@Column(name = "FECHA_ALTA")
	Date fechaAlta;

	@Column(name = "FECHA_ANOTACION")
	Date fechaAnotacion;

	@Column(name = "FECHA_PRESENTACION")
	Date fechaPresentacion;

	@Column(name = "JEFATURA")
	String jefatura;

	@Column(name = "ID_IMPR")
	Long idImpr;

	@Column(name = "ID_CONTRATO")
	Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	ContratoVO contrato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getTipoImpr() {
		return tipoImpr;
	}

	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAnotacion() {
		return fechaAnotacion;
	}

	public void setFechaAnotacion(Date fechaAnotacion) {
		this.fechaAnotacion = fechaAnotacion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getIdImpr() {
		return idImpr;
	}

	public void setIdImpr(Long idImpr) {
		this.idImpr = idImpr;
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

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
}
