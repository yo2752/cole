package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MUNICIPIO database table.
 */
@Embeddable
public class MunicipioCamPK implements Serializable {

	private static final long serialVersionUID = 4649440763106476359L;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@Column(name = "ID_MUNICIPIO")
	private String idMunicipio;

	public MunicipioCamPK() {}

	public String getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MunicipioCamPK)) {
			return false;
		}
		MunicipioCamPK castOther = (MunicipioCamPK) other;
		return this.idProvincia.equals(castOther.idProvincia) && this.idMunicipio.equals(castOther.idMunicipio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProvincia.hashCode();
		hash = hash * prime + this.idMunicipio.hashCode();

		return hash;
	}
}