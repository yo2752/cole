package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the PUEBLO database table.
 */
@Entity
@Table(name = "PUEBLO")
public class PuebloVO implements Serializable {

	private static final long serialVersionUID = -8552443154603669459L;

	@EmbeddedId
	private PuebloPK id;

	public PuebloPK getId() {
		return id;
	}

	public void setId(PuebloPK id) {
		this.id = id;
	}
}