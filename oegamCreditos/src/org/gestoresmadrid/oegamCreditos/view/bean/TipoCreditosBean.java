package org.gestoresmadrid.oegamCreditos.view.bean;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class TipoCreditosBean {

	@FilterSimpleExpression(name = "tipoCredito")
	private String tipoCredito;

	@FilterSimpleExpression(name = "descripcion", restriction = FilterSimpleExpressionRestriction.LIKE_PORCENTAJE)
	private String descripcion;
	
	@FilterSimpleExpression(name = "estado", restriction = FilterSimpleExpressionRestriction.EQ)
	private BigDecimal estado;
	
//	@FilterSimpleExpression(name = "tipoTramites.activo", restriction = FilterSimpleExpressionRestriction.EQ)
//	private BigDecimal tipoTramiteActivo;

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
//
//	public BigDecimal getTipoTramiteActivo() {
//		return tipoTramiteActivo;
//	}
//
//	public void setTipoTramiteActivo(BigDecimal tipoTramiteActivo) {
//		this.tipoTramiteActivo = tipoTramiteActivo;
//	}

}
