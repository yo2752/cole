package org.gestoresmadrid.core.trafico.inteve.model.vo;

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

@Entity
@Table(name = "TRAMITE_TRAFICO_INTEVE")
public class TramiteTraficoSolInteveVO implements Serializable {

	private static final long serialVersionUID = 904527472368949321L;

	@Id
	@Column(name = "ID_TRAMITE_INTEVE")
	@SequenceGenerator(name = "tramite_inteve_secuencia", sequenceName = "ID_TRAMITE_INTEVE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tramite_inteve_secuencia")
	private Long idTramiteInteve;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "TIPO_INFORME")
	private String tipoInforme;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "BASTIDOR")
	private String bastidor;

	@Column(name = "NIVE")
	private String nive;

	@Column(name = "RESPUESTA_DGT")
	private String respuestaDgt;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Column(name = "ACEPTACION")
	private Integer aceptacion;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoInteveVO tramiteTraficoInteve;

	public Long getIdTramiteInteve() {
		return idTramiteInteve;
	}

	public void setIdTramiteInteve(Long idTramiteInteve) {
		this.idTramiteInteve = idTramiteInteve;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
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

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRespuestaDgt() {
		return respuestaDgt;
	}

	public void setRespuestaDgt(String respuestaDgt) {
		this.respuestaDgt = respuestaDgt;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public TramiteTraficoInteveVO getTramiteTraficoInteve() {
		return tramiteTraficoInteve;
	}

	public void setTramiteTraficoInteve(TramiteTraficoInteveVO tramiteTraficoInteve) {
		this.tramiteTraficoInteve = tramiteTraficoInteve;
	}

	public int getAceptacion() {
		return aceptacion;
	}

	public void setAceptacion(int aceptacion) {
		this.aceptacion = aceptacion;
	}

}
