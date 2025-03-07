package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the INTERVINIENTE_TRAFICO database table.
 * 
 */
@Embeddable
public class IntervinienteTraficoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_EXPEDIENTE")
	private long numExpediente;

	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NIF")
	private String nif;

	public IntervinienteTraficoPK() {
	}

	public long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(long numExpediente) {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IntervinienteTraficoPK)) {
			return false;
		}
		IntervinienteTraficoPK castOther = (IntervinienteTraficoPK) other;
		return (this.numExpediente == castOther.numExpediente)
				&& this.tipoInterviniente.equals(castOther.tipoInterviniente)
				&& this.numColegiado.equals(castOther.numColegiado) && this.nif.equals(castOther.nif);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numExpediente ^ (this.numExpediente >>> 32)));
		hash = hash * prime + this.tipoInterviniente.hashCode();
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + this.nif.hashCode();

		return hash;
	}
}