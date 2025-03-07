package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class StockFilterBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7014093651237960670L;

	@FilterSimpleExpression(name = "jefaturaProvincial.jefaturaProvincial")
	private String jefaturaProvincial;
	
	@FilterSimpleExpression(name = "materialVO.materialId")
	private Long materialId;
	
	@FilterSimpleExpression(name = "fecUltRecarga", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecUltRecarga;

	@FilterSimpleExpression(name = "fecUltConsumo", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecUltConsumo;

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public FechaFraccionada getFecUltRecarga() {
		return fecUltRecarga;
	}

	public void setFecUltRecarga(FechaFraccionada fecUltRecarga) {
		this.fecUltRecarga = fecUltRecarga;
	}

	public FechaFraccionada getFecUltConsumo() {
		return fecUltConsumo;
	}

	public void setFecUltConsumo(FechaFraccionada fecUltConsumo) {
		this.fecUltConsumo = fecUltConsumo;
	}


	

}
