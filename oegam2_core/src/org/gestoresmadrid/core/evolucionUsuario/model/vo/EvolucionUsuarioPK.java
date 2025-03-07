package org.gestoresmadrid.core.evolucionUsuario.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EvolucionUsuarioPK implements Serializable{

	private static final long serialVersionUID = 1304203640600050019L;

	@Column(name="FECHA_CAMBIO")
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp fechaCambio;

	@Column(name="ID_USUARIO")
	private Long idUsuario;

	public Timestamp getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Timestamp fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}