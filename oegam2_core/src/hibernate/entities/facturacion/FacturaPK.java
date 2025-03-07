package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FACTURA database table.
 * 
 */
@Embeddable
public class FacturaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO", unique=true, nullable=false, length=6)
	private String numColegiado;

	@Column(name="NUM_FACTURA", unique=true, nullable=false, length=30)
	private String numFactura;

	public FacturaPK() {
	}
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNumFactura() {
		return this.numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacturaPK)) {
			return false;
		}
		FacturaPK castOther = (FacturaPK) other;
		return this.numColegiado.equals(castOther.numColegiado) && this.numFactura.equals(castOther.numFactura);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + this.numFactura.hashCode();

		return hash;
	}
}