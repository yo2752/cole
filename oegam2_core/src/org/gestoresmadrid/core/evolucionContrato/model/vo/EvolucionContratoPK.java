package org.gestoresmadrid.core.evolucionContrato.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EvolucionContratoPK implements Serializable{

	private static final long serialVersionUID = 1304203640600050019L;

	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
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