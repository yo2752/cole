package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the MARCA_FABRICANTE database table.
 */
@Entity
@Table(name = "MARCA_FABRICANTE")
//@NamedQuery(name = "MarcaFabricanteVO.findAll", query = "SELECT m FROM MarcaFabricante m")
public class MarcaFabricanteVO implements Serializable {

	private static final long serialVersionUID = 4911709325346940384L;

	@EmbeddedId
	private MarcaFabricantePK id;

	public MarcaFabricanteVO() {}

	public MarcaFabricantePK getId() {
		return this.id;
	}

	public void setId(MarcaFabricantePK id) {
		this.id = id;
	}
}