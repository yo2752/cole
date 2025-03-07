package org.gestoresmadrid.oegamImportacion.cet.bean;

import java.io.Serializable;

public class PegatinaBean implements Serializable {

	private static final long serialVersionUID = 2925267416481840726L;

	private String bastidor;

	private String matricula;

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
