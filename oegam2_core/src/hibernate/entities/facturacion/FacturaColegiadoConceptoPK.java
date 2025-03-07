package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FACTURA_COLEGIADO_CONCEPTO database table.
 * 
 */
@Embeddable
public class FacturaColegiadoConceptoPK implements Serializable {
	// Default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ID_COLEGIADO_CONCEPTO")
	private long idColegiadoConcepto;

	public FacturaColegiadoConceptoPK() {
	}
	public String getNumColegiado() {
		return this.numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public long getIdColegiadoConcepto() {
		return this.idColegiadoConcepto;
	}
	public void setIdColegiadoConcepto(long idColegiadoConcepto) {
		this.idColegiadoConcepto = idColegiadoConcepto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacturaColegiadoConceptoPK)) {
			return false;
		}
		FacturaColegiadoConceptoPK castOther = (FacturaColegiadoConceptoPK)other;
		return 
			this.numColegiado.equals(castOther.numColegiado)
			&& (this.idColegiadoConcepto == castOther.idColegiadoConcepto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + ((int) (this.idColegiadoConcepto ^ (this.idColegiadoConcepto >>> 32)));
		return hash;
	}
}