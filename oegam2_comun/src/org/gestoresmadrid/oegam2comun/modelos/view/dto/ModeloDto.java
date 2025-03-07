package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;
import java.util.List;

public class ModeloDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String modelo;
	private String version;
	private String descModelo;
	private List<FundamentoExencionDto> fundamentosExencion;
	private List<BonificacionDto> bonificaciones;
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescModelo() {
		return descModelo;
	}
	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}
	public List<FundamentoExencionDto> getFundamentosExencion() {
		return fundamentosExencion;
	}
	public void setFundamentosExencion(
			List<FundamentoExencionDto> fundamentosExencion) {
		this.fundamentosExencion = fundamentosExencion;
	}
	public List<BonificacionDto> getBonificaciones() {
		return bonificaciones;
	}
	public void setBonificaciones(List<BonificacionDto> bonificaciones) {
		this.bonificaciones = bonificaciones;
	}
}
