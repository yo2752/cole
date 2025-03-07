package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the FABRICANTE database table.
 */
@Entity
@Table(name = "FABRICANTE")
//@NamedQuery(name = "FabricanteVO.findAll", query = "SELECT f FROM Fabricante f")
public class FabricanteVO implements Serializable {
	private static final long serialVersionUID = -1946515245907343639L;

	@Id
	@GeneratedValue(generator = "codFabricanteSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "codFabricanteSeq", sequenceName = "COD_FABRICANTE_SEQ", allocationSize = 1)
	@Column(name = "COD_FABRICANTE")
	private Long codFabricante;

	private String fabricante;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "fabricantes")
	private Set<MarcaDgtVO> marcas;

	public FabricanteVO() {}

	public Set<MarcaDgtVO> getMarcas() {
		return marcas;
	}

	public void setMarcas(Set<MarcaDgtVO> marcas) {
		this.marcas = marcas;
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