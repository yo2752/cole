package org.gestoresmadrid.oegamCreditos.view.bean;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class GastoMensualCreditosBean {

	@FilterSimpleExpression
	@FilterRelationship(name = "contrato")
	Long idContrato;

	@FilterSimpleExpression
	@FilterRelationships(value = { @FilterRelationship(name = "tipoTramite", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "tipoCredito", joinType = Criteria.LEFT_JOIN) })
	String tipoCredito;

	@FilterSimpleExpression(name = "fecha", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	FechaFraccionada fechaCreditos;

	@FilterSimpleExpression(name = "idTramite")
	@FilterRelationship(name = "creditoFacturadoTramites", joinType = Criteria.LEFT_JOIN)
	String tramite;

	@FilterSimpleExpression
	ConceptoCreditoFacturado concepto;

	String keyconcepto;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public FechaFraccionada getFechaCreditos() {
		return fechaCreditos;
	}

	public void setFechaCreditos(FechaFraccionada fechaCreditos) {
		this.fechaCreditos = fechaCreditos;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public ConceptoCreditoFacturado getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoCreditoFacturado concepto) {
		this.concepto = concepto;
	}

	public String getKeyconcepto() {
		return concepto != null ? concepto.getValorEnum() : null;
	}

	public void setKeyconcepto(String keyconcepto) {
		this.concepto = ConceptoCreditoFacturado.convertir(keyconcepto);
	}

}
