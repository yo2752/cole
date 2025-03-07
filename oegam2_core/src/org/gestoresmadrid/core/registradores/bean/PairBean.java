package org.gestoresmadrid.core.registradores.bean;

import java.io.Serializable;

public class PairBean implements Serializable {

	private static final long serialVersionUID = -276404314238895498L;

	private boolean cambio;

	private String caracter;

	public boolean isCambio() {
		return cambio;
	}

	public void setCambio(boolean cambio) {
		this.cambio = cambio;
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}
}
