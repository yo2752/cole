package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaSociedadCargoBean {

	@FilterSimpleExpression(name = "id.cifSociedad")
	private String cifSociedad;
	
	@FilterSimpleExpression(name = "id.nifCargo")
	private String nifCargo;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;
	
	@FilterSimpleExpression(name = "id.codigoCargo")
	private String codigoCargo;

	@FilterSimpleExpression(name = "fechaInicio", restriction = FilterSimpleExpressionRestriction.BETWEEN_WITH_NULL)
	private FechaFraccionada fechaInicio;

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.BETWEEN_WITH_NULL)
	private FechaFraccionada fechaFin;
	
	@FilterSimpleExpression(name = "personaCargo.nombre", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String nombre;
	
	@FilterSimpleExpression(name = "personaCargo.apellido1RazonSocial", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String apellido1;
	
	@FilterSimpleExpression(name = "personaCargo.apellido2", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String apellido2;
	
	@FilterSimpleExpression(name = "personaCargo.correoElectronico", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String correoElectronico;


	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(FechaFraccionada fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public FechaFraccionada getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(FechaFraccionada fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}
