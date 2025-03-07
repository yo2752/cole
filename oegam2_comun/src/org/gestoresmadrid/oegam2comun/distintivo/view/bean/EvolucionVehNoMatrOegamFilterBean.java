package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionVehNoMatrOegamFilterBean implements Serializable {

	private static final long serialVersionUID = -4620278568198908470L;

	@FilterSimpleExpression(name = "idVehNoMatOegam")
	private Long idVehNoMatOegam;

	public Long getIdVehNoMatOegam() {
		return idVehNoMatOegam;
	}

	public void setIdVehNoMatOegam(Long idVehNoMatOegam) {
		this.idVehNoMatOegam = idVehNoMatOegam;
	}
	
}
