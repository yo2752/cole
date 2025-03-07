package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionPermisoInternacionalFilterBean implements Serializable {

	private static final long serialVersionUID = 6545350811536070965L;

	@FilterSimpleExpression(name = "idPermiso")
	private Long idPermiso;

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}
}
