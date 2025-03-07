package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;

public class FundamentoExencionDto implements Serializable{

	private static final long serialVersionUID = -7696785698426272588L;

	private String fundamentoExcencion;
	
	private String descripcion;

	public String getFundamentoExcencion() {
		return fundamentoExcencion;
	}

	public void setFundamentoExcencion(String fundamentoExcencion) {
		this.fundamentoExcencion = fundamentoExcencion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
