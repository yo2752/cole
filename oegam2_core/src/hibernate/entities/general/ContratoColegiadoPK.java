package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONTRATO_COLEGIADO database table.
 * 
 */
@Embeddable
public class ContratoColegiadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ID_CONTRATO")
	private long idContrato;

    public ContratoColegiadoPK() {
    }
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public long getIdContrato() {
		return this.idContrato;
	}
	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContratoColegiadoPK)) {
			return false;
		}
		ContratoColegiadoPK castOther = (ContratoColegiadoPK)other;
		return 
			this.numColegiado.equals(castOther.numColegiado)
			&& (this.idContrato == castOther.idContrato);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + ((int) (this.idContrato ^ (this.idContrato >>> 32)));
		
		return hash;
    }
}