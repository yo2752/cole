package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Set;

/**
 * The persistent class for the MARCA_DGT database table.
 */
@Entity
@Table(name = "MARCA_DGT")
@NamedQuery(name = "MarcaDgtVO.findAll", query = "SELECT m FROM MarcaDgtVO m")
public class MarcaDgtVO implements Serializable {

	private static final long serialVersionUID = 956012409620116530L;

	@Id
	@GeneratedValue(generator = "idMarcaDgtSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "idMarcaDgtSeq", sequenceName = "ID_MARCA_DGT_SEQ")
	@Column(name = "CODIGO_MARCA")
	private Long codigoMarca;

	private Long version;

	private String marca;

	@Column(name = "MARCA_SIN_EDITAR")
	private String marcaSinEditar;

	@Transient
	private boolean mate;

	@Transient
	private boolean matw;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "MARCA_FABRICANTE", joinColumns = { 
			@JoinColumn(name = "CODIGO_MARCA", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "COD_FABRICANTE", 
					nullable = false, updatable = false) })
	public Set<FabricanteVO> fabricantes;
	
	
	public Set<FabricanteVO> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(Set<FabricanteVO> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public MarcaDgtVO() {}

	public Long getCodigoMarca() {
		return this.codigoMarca;
	}

	public void setCodigoMarca(Long codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMarcaSinEditar() {
		return this.marcaSinEditar;
	}

	public void setMarcaSinEditar(String marcaSinEditar) {
		this.marcaSinEditar = marcaSinEditar;
	}

	public boolean isMate() {
		return mate;
	}

	public void setMate(boolean mate) {
		this.mate = mate;
	}

	public boolean isMatw() {
		return matw;
	}

	public void setMatw(boolean matw) {
		this.matw = matw;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
}