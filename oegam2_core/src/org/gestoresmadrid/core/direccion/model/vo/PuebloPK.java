package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the PUEBLO database table.
 */
@Embeddable
public class PuebloPK implements Serializable {

	private static final long serialVersionUID = -7066014135227850138L;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@Column(name = "ID_MUNICIPIO")
	private String idMunicipio;
	
	private String pueblo;

	public PuebloPK() {}

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

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}
}