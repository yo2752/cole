package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;

/**
 * The persistent class for the COLEGIO_PROVINCIA database table.
 */
@Entity
@Table(name = "COLEGIO_PROVINCIA")
public class ColegioProvinciaVO implements Serializable {

	private static final long serialVersionUID = 3022499291380519436L;

	@EmbeddedId
	private ColegioProvinciaPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COLEGIO", insertable = false, updatable = false)
	private ColegioVO colegio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provincia;

	public ColegioProvinciaVO() {}

	public ColegioProvinciaPK getId() {
		return id;
	}

	public void setId(ColegioProvinciaPK id) {
		this.id = id;
	}

	public ColegioVO getColegio() {
		return colegio;
	}

	public void setColegio(ColegioVO colegio) {
		this.colegio = colegio;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}
}