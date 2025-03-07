package org.gestoresmadrid.oegam2comun.clonarTramites.view.dto;

public class ResultClonarTramites {

	private int numCopias;

	private ClonarTramiteMatriculacionDto matriculacion;

	public ClonarTramiteMatriculacionDto getMatriculacion() {
		return matriculacion;
	}

	public void setMatriculacion(ClonarTramiteMatriculacionDto matriculacion) {
		this.matriculacion = matriculacion;
	}

	public int getNumCopias() {
		return numCopias;
	}

	public void setNumCopias(int numCopias) {
		this.numCopias = numCopias;
	}

}