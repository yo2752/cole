package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionDuplPermCondFilterBean implements Serializable {

	private static final long serialVersionUID = 3045303686498200647L;

	@FilterSimpleExpression(name = "idDuplicadoPermCond")
	private Long idDuplicadoPermCond;

	public Long getIdDuplicadoPermCond() {
		return idDuplicadoPermCond;
	}

	public void setIdDuplicadoPermCond(Long idDuplicadoPermCond) {
		this.idDuplicadoPermCond = idDuplicadoPermCond;
	}
}
