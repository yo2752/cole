package org.gestoresmadrid.core.evolucionAtex5.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EvolucionAtex5PK implements Serializable{

	private static final long serialVersionUID = -3786012092566897445L;

	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;


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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
