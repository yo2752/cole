package org.gestoresmadrid.oegam2comun.bien.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.hibernate.Criteria;

public class BienRusticoFilterBean implements Serializable{

	private static final long serialVersionUID = -7155835589646193096L;
	
	@FilterSimpleExpression(name="idProvincia")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String idProvincia;
	
	@FilterSimpleExpression(name="idMunicipio")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String idMunicipio;

	@FilterSimpleExpression(name="codPostal")
	@FilterRelationship(name="direccion", joinType = Criteria.LEFT_JOIN)
	private String codPostal;

	@FilterSimpleExpression(name="paraje")
	private String paraje;
	
	@FilterSimpleExpression(name="poligono")
	private String poligono;
	
	@FilterSimpleExpression(name="parcela")
	private String parcela;
	
	@FilterSimpleExpression(name="subParcela")
	private String subParcela;
	
	@FilterSimpleExpression(name="idUsoRustico")
	@FilterRelationship(name="usoRustico", joinType = Criteria.LEFT_JOIN)
	private String idUsoRusticoGanaderia;
	
	@FilterSimpleExpression(name="idUsoRustico")
	@FilterRelationship(name="usoRustico", joinType = Criteria.LEFT_JOIN)
	private String idUsoRusticoCultivo;
	
	@FilterSimpleExpression(name="idUsoRustico")
	@FilterRelationship(name="usoRustico", joinType = Criteria.LEFT_JOIN)
	private String idUsoRusticoOtros;
	
	@FilterSimpleExpression(name="tipoInmueble")
	@FilterRelationship(name="tipoInmueble", joinType = Criteria.LEFT_JOIN)
	private String idTipoInmueble;
	
	@FilterSimpleExpression(name="refCatrastal")
	private String refCatrastal;
	
	@FilterSimpleExpression(name="idSistemaExplotacion")
	@FilterRelationship(name="sistemaExplotacion", joinType = Criteria.LEFT_JOIN)
	private String idSistemaExplotacion;
	
	@FilterSimpleExpression(name="idufir")
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

	public String getParaje() {
		return paraje;
	}

	public void setParaje(String paraje) {
		this.paraje = paraje;
	}

	public String getPoligono() {
		return poligono;
	}

	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getSubParcela() {
		return subParcela;
	}

	public void setSubParcela(String subParcela) {
		this.subParcela = subParcela;
	}

	public String getIdUsoRusticoGanaderia() {
		return idUsoRusticoGanaderia;
	}

	public void setIdUsoRusticoGanaderia(String idUsoRusticoGanaderia) {
		this.idUsoRusticoGanaderia = idUsoRusticoGanaderia;
	}

	public String getIdUsoRusticoCultivo() {
		return idUsoRusticoCultivo;
	}

	public void setIdUsoRusticoCultivo(String idUsoRusticoCultivo) {
		this.idUsoRusticoCultivo = idUsoRusticoCultivo;
	}

	public String getIdUsoRusticoOtros() {
		return idUsoRusticoOtros;
	}

	public void setIdUsoRusticoOtros(String idUsoRusticoOtros) {
		this.idUsoRusticoOtros = idUsoRusticoOtros;
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

	public String getIdSistemaExplotacion() {
		return idSistemaExplotacion;
	}

	public void setIdSistemaExplotacion(String idSistemaExplotacion) {
		this.idSistemaExplotacion = idSistemaExplotacion;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

}
