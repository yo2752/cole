package org.gestoresmadrid.oegam2comun.evolucionConsultaKo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionConsultaKoFilterBean implements Serializable{

	private static final long serialVersionUID = -5055631508453829011L;
	@FilterSimpleExpression(name="idConsultaKo")
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
