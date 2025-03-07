package hibernate.entities.personas;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PERSONA database table.
 * 
 */
@Embeddable
public class PersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String nif;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

    public PersonaPK() {
    }
	public String getNif() {
		return this.nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonaPK)) {
			return false;
		}
		PersonaPK castOther = (PersonaPK)other;
		return 
			this.nif.equals(castOther.nif)
			&& this.numColegiado.equals(castOther.numColegiado);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nif.hashCode();
		hash = hash * prime + this.numColegiado.hashCode();
		
		return hash;
    }
}