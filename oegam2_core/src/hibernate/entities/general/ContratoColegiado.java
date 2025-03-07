package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONTRATO_COLEGIADO database table.
 * 
 */
@Entity
@Table(name="CONTRATO_COLEGIADO")
public class ContratoColegiado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContratoColegiadoPK id;

	/*
	//bi-directional many-to-one association to Colegiado
    @ManyToOne
	@JoinColumn(name="NUM_COLEGIADO")
	private Colegiado colegiado;
	*/
	
    public ContratoColegiado() {
    }

	public ContratoColegiadoPK getId() {
		return this.id;
	}

	public void setId(ContratoColegiadoPK id) {
		this.id = id;
	}
	
	/*public Colegiado getColegiado() {
		return this.colegiado;
	}

	public void setColegiado(Colegiado colegiado) {
		this.colegiado = colegiado;
	}*/
	
}