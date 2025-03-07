package org.gestoresmadrid.oegam2comun.personas.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class ConsultaEvolucionPersonaBean {

	@FilterSimpleExpression(name = "id.nif")
	private String nif;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}
