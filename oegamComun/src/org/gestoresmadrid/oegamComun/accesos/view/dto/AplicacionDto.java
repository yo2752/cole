package org.gestoresmadrid.oegamComun.accesos.view.dto;

import java.io.Serializable;
import java.util.List;

public class AplicacionDto implements Serializable {

	private static final long serialVersionUID = -5245563342529313774L;

	private String codigoAplicacion;

	private String alias;

	private String descAplicacion;

	private boolean asignada;

	private List<FuncionDto> funcionDtos;

	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescAplicacion() {
		return descAplicacion;
	}

	public void setDescAplicacion(String descAplicacion) {
		this.descAplicacion = descAplicacion;
	}

	public boolean isAsignada() {
		return asignada;
	}

	public void setAsignada(boolean asignada) {
		this.asignada = asignada;
	}

	public List<FuncionDto> getFuncionDtos() {
		return funcionDtos;
	}

	public void setFuncionDtos(List<FuncionDto> funcionDtos) {
		this.funcionDtos = funcionDtos;
	}
}