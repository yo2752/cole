package org.gestoresmadrid.oegam2comun.notificacion.view.beans;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class RespuestaSeguridadSocialBean {

	@FilterSimpleExpression(name = "codigo")
	@FilterRelationship(name = "notificacion", joinType = Criteria.INNER_JOIN)
	private Integer codigoNotificacion;

	@FilterSimpleExpression(name = "fechaNotificacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaNotificacion;

	public Integer getCodigoNotificacion() {
		return codigoNotificacion;
	}

	public void setCodigoNotificacion(Integer codigoNotificacion) {
		this.codigoNotificacion = codigoNotificacion;
	}

	public FechaFraccionada getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(FechaFraccionada fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

}