package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class EstadisticaImportacionFilterBean implements Serializable{

	private static final long serialVersionUID = 207669835201649345L;

	
	@FilterSimpleExpression(name = "fecha", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fecha;
	
	@FilterSimpleExpression(name = "tipo")
	private String tipo;
	
	@FilterSimpleExpression(name = "tipoError")
	private String tipoError;

	@FilterSimpleExpression(name = "numOk")
	private String numOk;
	
	@FilterSimpleExpression(name = "numError")
	private String numError;
	
	@FilterSimpleExpression(name = "origen")
	private String origen;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;


	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getNumOk() {
		return numOk;
	}

	public void setNumOk(String numOk) {
		this.numOk = numOk;
	}

	public String getNumError() {
		return numError;
	}

	public void setNumError(String numError) {
		this.numError = numError;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
}
