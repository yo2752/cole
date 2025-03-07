package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the MARCA_FABRICANTE database table.
 * 
 */
@Entity
@Table(name = "MARCA_FABRICANTE")
@NamedQuery(name = "MarcaFabricante.findAll", query = "SELECT m FROM MarcaFabricante m")
public class MarcaFabricante implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MarcaFabricantePK id;

	public MarcaFabricante() {
	}

	public MarcaFabricantePK getId() {
		return this.id;
	}

	public void setId(MarcaFabricantePK id) {
		this.id = id;
	}

}