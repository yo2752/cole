package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;

public class DatosMatriculaPersonaAtex5Dto implements Serializable{

	private static final long serialVersionUID = 7178929459662904992L;
	
	private String matricula;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
