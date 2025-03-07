package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ConsultaInmuebleBean implements Serializable {

	private static final long serialVersionUID = 5559057162774567329L;

	@FilterSimpleExpression(name = "direccion.idProvincia")
	private String idProvincia;

	@FilterSimpleExpression(name = "direccion.idMunicipio")
	private String idMunicipio;

	@FilterSimpleExpression(name = "idufir", restriction = FilterSimpleExpressionRestriction.ISNOTNULL)
	private Boolean idufirFiltro;

	@FilterSimpleExpression(name = "idufir")
	private Long idufir;

	@FilterSimpleExpression(name = "numeroFinca")
	private Long numeroFinca;

	@FilterSimpleExpression(name = "numFincaDupl")
	private Long numFincaDupl;

	@FilterSimpleExpression(name = "seccion")
	private Long seccion;

	@FilterSimpleExpression(name = "subnumFinca")
	private String subnumFinca;

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

	public Long getNumeroFinca() {
		return numeroFinca;
	}

	public void setNumeroFinca(Long numeroFinca) {
		this.numeroFinca = numeroFinca;
	}

	public Long getNumFincaDupl() {
		return numFincaDupl;
	}

	public void setNumFincaDupl(Long numFincaDupl) {
		this.numFincaDupl = numFincaDupl;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public String getSubnumFinca() {
		return subnumFinca;
	}

	public void setSubnumFinca(String subnumFinca) {
		this.subnumFinca = subnumFinca;
	}

	public Boolean getIdufirFiltro() {
		return idufirFiltro;
	}

	public void setIdufirFiltro(Boolean idufirFiltro) {
		this.idufirFiltro = idufirFiltro;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}

}
