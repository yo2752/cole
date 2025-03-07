package hibernate.entities.trafico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the FABRICANTE database table.
 * 
 */
@Entity
@NamedQuery(name = "Fabricante.findAll", query = "SELECT f FROM Fabricante f")
public class Fabricante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "codFabricanteSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "codFabricanteSeq", sequenceName = "COD_FABRICANTE_SEQ", allocationSize = 1)
	@Column(name = "COD_FABRICANTE")
	private Long codFabricante;

	private String fabricante;

	public Fabricante() {
	}

	public Long getCodFabricante() {
		return this.codFabricante;
	}

	public void setCodFabricante(Long codFabricante) {
		this.codFabricante = codFabricante;
	}

	public String getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}