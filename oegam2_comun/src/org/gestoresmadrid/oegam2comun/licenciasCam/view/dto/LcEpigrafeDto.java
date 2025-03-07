package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;

public class LcEpigrafeDto implements Serializable {

	private static final long serialVersionUID = -6314704555717187685L;

	private Long idEpigrafe;

	private String epigrafe;

	private String seccion;

	private Long idInfoLocal;

	public LcEpigrafeDto() {}

	public Long getIdEpigrafe() {
		return this.idEpigrafe;
	}

	public void setIdEpigrafe(Long idEpigrafe) {
		this.idEpigrafe = idEpigrafe;
	}

	public String getEpigrafe() {
		return this.epigrafe;
	}

	public void setEpigrafe(String epigrafe) {
		this.epigrafe = epigrafe;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public Long getIdInfoLocal() {
		return idInfoLocal;
	}

	public void setIdInfoLocal(Long idInfoLocal) {
		this.idInfoLocal = idInfoLocal;
	}

}