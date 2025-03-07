package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class PedidosFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4746517686636274327L;

	@FilterSimpleExpression(name = "pedidoId")
	private Long pedidoId;

	@FilterSimpleExpression(name = "codigoInicial")
	private String codigoInicial;

	@FilterSimpleExpression(name = "codigoFinal")
	private String codigoFinal;

	@FilterSimpleExpression(name = "estado")
	private Long estado;

	@FilterSimpleExpression(name = "jefaturaProvincial.jefaturaProvincial")
	private String jefaturaProvincial;
	
	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;

	@FilterSimpleExpression(name = "materialVO.materialId")
	private Long materialId;

	public String getCodigoInicial() {
		return codigoInicial;
	}

	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}

	public String getCodigoFinal() {
		return codigoFinal;
	}

	public void setCodigoFinal(String codigoFinal) {
		this.codigoFinal = codigoFinal;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

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

}
