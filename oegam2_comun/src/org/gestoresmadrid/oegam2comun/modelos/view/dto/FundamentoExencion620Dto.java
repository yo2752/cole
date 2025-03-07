package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;

public class FundamentoExencion620Dto implements Serializable{

	private static final long serialVersionUID = -597241107556400493L;

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
