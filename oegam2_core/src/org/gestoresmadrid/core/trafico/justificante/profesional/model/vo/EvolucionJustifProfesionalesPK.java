package org.gestoresmadrid.core.trafico.justificante.profesional.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EvolucionJustifProfesionalesPK implements Serializable {

	private static final long serialVersionUID = 5963031100381062321L;

	@Column(name = "ID_JUSTIFICANTE_INTERNO")
	private Long idJustificanteInterno;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "FECHA_CAMBIO")
	private Date fechaCambio;

	@Column(name = "ESTADO_ANTERIOR")
	private BigDecimal estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	private BigDecimal estado;

	public long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
}
