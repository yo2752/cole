package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class BienFilterBean implements Serializable{

	private static final long serialVersionUID = 4600510239222172188L;
	
	@FilterSimpleExpression(name="idProvincia")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String idProvincia;
	
	@FilterSimpleExpression(name="idMunicipio")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String idMunicipio;

	@FilterSimpleExpression(name="codPostal")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String codPostal;

	@FilterSimpleExpression(name="id.idTipoInmueble")
	@FilterRelationship(name="tipoInmueble", joinType = Criteria.LEFT_JOIN)
	private String idTipoInmueble;
	
	@FilterSimpleExpression(name="id.idTipoBien")
	@FilterRelationship(name="tipoInmueble", joinType = Criteria.LEFT_JOIN)
	private String idTipoBien;

	@FilterSimpleExpression(name="refCatrastal")
	private String refCatrastal;
	
	@FilterSimpleExpression(name="idufir")
	private Long idufir;

	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getIdTipoInmueble() {
		return idTipoInmueble;
	}

	public void setIdTipoInmueble(String idTipoInmueble) {
		this.idTipoInmueble = idTipoInmueble;
	}

	public String getRefCatrastal() {
		return refCatrastal;
	}

	public void setRefCatrastal(String refCatrastal) {
		this.refCatrastal = refCatrastal;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getIdTipoBien() {
		return idTipoBien;
	}

	public void setIdTipoBien(String idTipoBien) {
		this.idTipoBien = idTipoBien;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}
	
}
