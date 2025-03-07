package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the CONTRATO_USUARIO database table.
 */
@Embeddable
public class ContratoUsuarioPK implements Serializable {

	private static final long serialVersionUID = 319234251055271893L;

	@Column(name = "ID_USUARIO")
	private String idUsuario;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	public ContratoUsuarioPK() {}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}