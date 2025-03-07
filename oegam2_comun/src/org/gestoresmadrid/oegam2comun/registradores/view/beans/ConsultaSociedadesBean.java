package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ConsultaSociedadesBean {

	@FilterSimpleExpression(name = "id.nif")
	private String cifSociedad;

	@FilterSimpleExpression(name = "apellido1RazonSocial", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String denominacionSocial;

	@FilterSimpleExpression(name = "ius")
	private BigDecimal ius;

	@FilterSimpleExpression(name = "seccion")
	private BigDecimal seccion;

	@FilterSimpleExpression(name = "hoja")
	private BigDecimal hoja;

	@FilterSimpleExpression(name = "hojaBis")
	private String hojaBis;
	
	@FilterSimpleExpression(name = "tipoPersona")
	private String tipoPersona;

	@FilterSimpleExpression(name = "subtipo")
	private String subtipo;

	@FilterSimpleExpression(name = "correoElectronico", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String correoElectronico;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getDenominacionSocial() {
		return denominacionSocial;
	}

	public void setDenominacionSocial(String denominacionSocial) {
		this.denominacionSocial = denominacionSocial;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public BigDecimal getIus() {
		return ius;
	}

	public void setIus(BigDecimal ius) {
		this.ius = ius;
	}

	public BigDecimal getSeccion() {
		return seccion;
	}

	public void setSeccion(BigDecimal seccion) {
		this.seccion = seccion;
	}

	public BigDecimal getHoja() {
		return hoja;
	}

	public void setHoja(BigDecimal hoja) {
		this.hoja = hoja;
	}

	public String getHojaBis() {
		return hojaBis;
	}

	public void setHojaBis(String hojaBis) {
		this.hojaBis = hojaBis;
	}
	
	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}
	
	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}
