
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;
import java.util.List;

public class Provincias implements Serializable {

	private static final long serialVersionUID = 245987618181426680L;

	private List<Provincia> provincia;

	private Integer numeroProvincias;

	public List<Provincia> getProvincia() {
		return provincia;
	}

	public void setProvincia(List<Provincia> provincia) {
		this.provincia = provincia;
	}

	public Integer getNumeroProvincias() {
		return numeroProvincias;
	}

	public void setNumeroProvincias(Integer numeroProvincias) {
		this.numeroProvincias = numeroProvincias;
	}
}
