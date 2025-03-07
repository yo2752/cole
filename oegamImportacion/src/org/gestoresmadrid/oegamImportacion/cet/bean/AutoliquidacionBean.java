package org.gestoresmadrid.oegamImportacion.cet.bean;

import java.io.Serializable;

public class AutoliquidacionBean implements Serializable {

	private static final long serialVersionUID = -4463586500652909169L;

	private String cet;

	private String matricula;

	private String codigo;

	public String getCet() {
		return cet;
	}

	public void setCet(String cet) {
		this.cet = cet;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
