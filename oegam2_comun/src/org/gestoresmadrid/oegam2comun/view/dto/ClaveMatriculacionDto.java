package org.gestoresmadrid.oegam2comun.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ClaveMatriculacionDto implements Serializable {

	private static final long serialVersionUID = -499802216355070242L;

	private String clave;

	private BigDecimal idClave;

	private String nombreClave;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BigDecimal getIdClave() {
		return idClave;
	}

	public void setIdClave(BigDecimal idClave) {
		this.idClave = idClave;
	}

	public String getNombreClave() {
		return nombreClave;
	}

	public void setNombreClave(String nombreClave) {
		this.nombreClave = nombreClave;
	}
}