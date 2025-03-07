package org.gestoresmadrid.oegamComun.semaforo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionSemaforoFilterBean implements Serializable {

	private static final long serialVersionUID = -2569838989119968048L;

	@FilterSimpleExpression(name = "idSemaforo")
	private Long idSemaforo;

	public Long getIdSemaforo() {
		return idSemaforo;
	}

	public void setIdSemaforo(Long idSemaforo) {
		this.idSemaforo = idSemaforo;
	}

}