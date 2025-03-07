package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the COLEGIO_PROVINCIA database table.
 */
@Embeddable
public class ColegioProvinciaPK implements Serializable {

	private static final long serialVersionUID = -6293408840949651595L;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	private String colegio;

	public ColegioProvinciaPK() {}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
}