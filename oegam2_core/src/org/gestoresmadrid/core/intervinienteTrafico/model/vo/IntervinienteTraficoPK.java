package org.gestoresmadrid.core.intervinienteTrafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the INTERVINIENTE_TRAFICO database table.
 */
@Embeddable
public class IntervinienteTraficoPK implements Serializable {

	private static final long serialVersionUID = -8871142306028002465L;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NIF")
	private String nif;

	public IntervinienteTraficoPK() {}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoInterviniente() {
		return this.tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IntervinienteTraficoPK)) {
			return false;
		}
		IntervinienteTraficoPK castOther = (IntervinienteTraficoPK) other;
		return (this.numExpediente == castOther.numExpediente) && this.tipoInterviniente.equals(castOther.tipoInterviniente) && this.numColegiado.equals(castOther.numColegiado)
				&& this.nif.equals(castOther.nif);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((numExpediente == null) ? 0 : numExpediente.hashCode());
		result = prime * result + ((tipoInterviniente == null) ? 0 : tipoInterviniente.hashCode());
		return result;
	}

}