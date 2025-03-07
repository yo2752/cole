package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the CONTRATO_COLEGIADO database table.
 */
@Embeddable
public class ContratoColegiadoPK implements Serializable {

	private static final long serialVersionUID = 319234251055271893L;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	public ContratoColegiadoPK() {}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}