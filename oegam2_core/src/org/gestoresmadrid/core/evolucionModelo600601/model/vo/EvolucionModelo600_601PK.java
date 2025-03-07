package org.gestoresmadrid.core.evolucionModelo600601.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EvolucionModelo600_601PK implements Serializable{
	
	private static final long serialVersionUID = 8833140772978557395L;

	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CAMBIO")
	private Date fechaCambio;
	
	@Column(name="ESTADO_ANTERIOR")
	private BigDecimal estadoAnterior;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;

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

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
	
	
}
