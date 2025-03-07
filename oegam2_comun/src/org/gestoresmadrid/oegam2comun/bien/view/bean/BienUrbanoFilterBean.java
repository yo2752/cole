package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class BienUrbanoFilterBean implements Serializable {

	private static final long serialVersionUID = -7155835589646193096L;

	@FilterSimpleExpression(name = "idProvincia")
	@FilterRelationship(name = "direccion", joinType = Criteria.LEFT_JOIN)
	private String idProvincia;

	@FilterSimpleExpression(name = "idMunicipio")
	@FilterRelationship(name = "direccion", joinType = Criteria.LEFT_JOIN)
	private String idMunicipio;

	@FilterSimpleExpression(name = "codPostal")
	@FilterRelationship(name = "direccion", joinType = Criteria.LEFT_JOIN)
	private String codPostal;

	@FilterSimpleExpression(name = "nombreVia")
	@FilterRelationship(name = "direccion", joinType = Criteria.LEFT_JOIN)
	private String nombreVia;

	@FilterSimpleExpression(name = "anioContratacion")
	private BigDecimal anioContratacion;

	@FilterSimpleExpression(name = "tipoInmueble")
	@FilterRelationship(name = "tipoInmueble", joinType = Criteria.LEFT_JOIN)
	private String tipoInmueble;

	@FilterSimpleExpression(name = "superficieConst")
	private Long superficieConst;

	@FilterSimpleExpression(name = "tipoInmueble")
	@FilterRelationship(name = "tipoInmueble", joinType = Criteria.LEFT_JOIN)
	private String idTipoInmueble;

	@FilterSimpleExpression(name = "refCatrastal")
	private String refCatrastal;

	@FilterSimpleExpression(name = "fechaConstruccion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaConstruccion;

	@FilterSimpleExpression(name = "arrendamiento")
	private BigDecimal arrendamiento;

	@FilterSimpleExpression(name = "viviendaProtOficial")
	private BigDecimal viviendaProtOficial;

	@FilterSimpleExpression(name = "estado")
	private Long estado;

	@FilterSimpleExpression(name = "idufir")
	private Long idufir;

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

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public BigDecimal getAnioContratacion() {
		return anioContratacion;
	}

	public void setAnioContratacion(BigDecimal anioContratacion) {
		this.anioContratacion = anioContratacion;
	}

	public String getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(String tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public Long getSuperficieConst() {
		return superficieConst;
	}

	public void setSuperficieConst(Long superficieConst) {
		this.superficieConst = superficieConst;
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

	public FechaFraccionada getFechaConstruccion() {
		return fechaConstruccion;
	}

	public void setFechaConstruccion(FechaFraccionada fechaConstruccion) {
		this.fechaConstruccion = fechaConstruccion;
	}

	public BigDecimal getArrendamiento() {
		return arrendamiento;
	}

	public void setArrendamiento(BigDecimal arrendamiento) {
		this.arrendamiento = arrendamiento;
	}

	public BigDecimal getViviendaProtOficial() {
		return viviendaProtOficial;
	}

	public void setViviendaProtOficial(BigDecimal viviendaProtOficial) {
		this.viviendaProtOficial = viviendaProtOficial;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}

}
