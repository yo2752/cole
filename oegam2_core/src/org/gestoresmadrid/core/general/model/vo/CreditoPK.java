package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CREDITOS database table.
 */
@Embeddable
public class CreditoPK implements Serializable {

	private static final long serialVersionUID = 924332708047462480L;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "TIPO_CREDITO")
	private String tipoCredito;

	public CreditoPK() {}

	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoCredito() {
		return this.tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CreditoPK)) {
			return false;
		}
		CreditoPK castOther = (CreditoPK) other;
		return (this.idContrato == castOther.idContrato) && this.tipoCredito.equals(castOther.tipoCredito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idContrato ^ (this.idContrato >>> 32)));
		hash = hash * prime + this.tipoCredito.hashCode();

		return hash;
	}
}