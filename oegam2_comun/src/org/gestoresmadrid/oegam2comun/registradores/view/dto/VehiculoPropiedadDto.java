package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

public class VehiculoPropiedadDto implements Serializable {

	private static final long serialVersionUID = -811260204082827574L;
	
	private String numColegiado;

	private PropiedadDto propiedad;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public PropiedadDto getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(PropiedadDto propiedad) {
		this.propiedad = propiedad;
	}

}
