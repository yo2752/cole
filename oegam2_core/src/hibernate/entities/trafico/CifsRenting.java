package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CIFS_RENTING database table.
 * 
 */
@Entity
@Table(name = "CIFS_RENTING")
public class CifsRenting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cif;

	private String nombre;

	public CifsRenting() {
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}