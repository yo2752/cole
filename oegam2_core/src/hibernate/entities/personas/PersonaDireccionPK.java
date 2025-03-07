package hibernate.entities.personas;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PERSONA_DIRECCION database table.
 * 
 */
@Embeddable
public class PersonaDireccionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="NIF")
	private String nif;

	@Column(name="ID_DIRECCION")	
	private long idDireccion;

    public PersonaDireccionPK() {
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
	public long getIdDireccion() {
		return this.idDireccion;
	}
	public void setIdDireccion(long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonaDireccionPK)) {
			return false;
		}
		PersonaDireccionPK castOther = (PersonaDireccionPK)other;
		return 
			this.numColegiado.equals(castOther.numColegiado)
			&& this.nif.equals(castOther.nif)
			&& (this.idDireccion == castOther.idDireccion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + this.nif.hashCode();
		hash = hash * prime + ((int) (this.idDireccion ^ (this.idDireccion >>> 32)));
		
		return hash;
    }
}