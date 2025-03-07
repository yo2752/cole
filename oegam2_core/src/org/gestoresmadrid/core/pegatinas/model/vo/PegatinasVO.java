package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS")
public class PegatinasVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasVO() {
	}

	@Id
	@Column(name = "ID_PEGATINA")
	@SequenceGenerator(name = "secuencia_pegatinas", sequenceName = "PEGATINAS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinas")
	private Integer idPegatina;

	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name = "ESTADO")
	private int estado;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "DESCR_ESTADO")
	private String descrEstado;
	
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "JEFATURA")
	private String jefatura;
	
	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Integer getIdPegatina() {
		return idPegatina;
	}

	public void setIdPegatina(Integer idPegatina) {
		this.idPegatina = idPegatina;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrEstado() {
		return descrEstado;
	}

	public void setDescrEstado(String descrEstado) {
		this.descrEstado = descrEstado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}