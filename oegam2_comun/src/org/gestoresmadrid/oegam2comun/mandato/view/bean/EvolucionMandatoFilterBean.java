package org.gestoresmadrid.oegam2comun.mandato.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionMandatoFilterBean implements Serializable {

	private static final long serialVersionUID = 1705569053414739901L;

	@FilterSimpleExpression(name = "id.idMandato")
	private Long idMandato;

	public Long getIdMandato() {
		return idMandato;
	}

	public void setIdMandato(Long idMandato) {
		this.idMandato = idMandato;
	}
}
