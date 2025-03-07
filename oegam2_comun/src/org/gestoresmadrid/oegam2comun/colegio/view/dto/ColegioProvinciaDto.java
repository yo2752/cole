package org.gestoresmadrid.oegam2comun.colegio.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;

public class ColegioProvinciaDto implements Serializable {

	private static final long serialVersionUID = -602208191581962993L;
	
	private ColegioDto colegio;

	private ProvinciaDto provincia;

	public ColegioDto getColegio() {
		return colegio;
	}

	public void setColegio(ColegioDto colegio) {
		this.colegio = colegio;
	}

	public ProvinciaDto getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaDto provincia) {
		this.provincia = provincia;
	}
}