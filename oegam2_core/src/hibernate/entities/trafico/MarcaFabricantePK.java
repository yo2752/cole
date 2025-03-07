package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MARCA_FABRICANTE database table.
 * 
 */
@Embeddable
public class MarcaFabricantePK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CODIGO_MARCA", insertable = false, updatable = false)
	private long codigoMarca;

	@Column(name = "COD_FABRICANTE", insertable = false, updatable = false)
	private long codFabricante;

	public MarcaFabricantePK() {
	}

	public long getCodigoMarca() {
		return this.codigoMarca;
	}

	public void setCodigoMarca(long codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public long getCodFabricante() {
		return this.codFabricante;
	}

	public void setCodFabricante(long codFabricante) {
		this.codFabricante = codFabricante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MarcaFabricantePK)) {
			return false;
		}
		MarcaFabricantePK castOther = (MarcaFabricantePK) other;
		return (this.codigoMarca == castOther.codigoMarca) && (this.codFabricante == castOther.codFabricante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.codigoMarca ^ (this.codigoMarca >>> 32)));
		hash = hash * prime + ((int) (this.codFabricante ^ (this.codFabricante >>> 32)));

		return hash;
	}
}