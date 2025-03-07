package hibernate.entities.personas;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EVOLUCION_PERSONA database table.
 * 
 */
@Embeddable
public class EvolucionPersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	private String nif;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FECHA_HORA")
	private java.util.Date fechaHora;

    public EvolucionPersonaPK() {
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
	public java.util.Date getFechaHora() {
		return this.fechaHora;
	}
	public void setFechaHora(java.util.Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvolucionPersonaPK)) {
			return false;
		}
		EvolucionPersonaPK castOther = (EvolucionPersonaPK)other;
		return 
			this.numColegiado.equals(castOther.numColegiado)
			&& this.nif.equals(castOther.nif)
			&& this.fechaHora.equals(castOther.fechaHora);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numColegiado.hashCode();
		hash = hash * prime + this.nif.hashCode();
		hash = hash * prime + this.fechaHora.hashCode();
		
		return hash;
    }
}