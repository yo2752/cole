package org.gestoresmadrid.oegam2comun.mandato.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class MandatoFilterBean implements Serializable {

	private static final long serialVersionUID = 7307526935144662780L;

	@FilterSimpleExpression(name = "codigoMandato")
	private String codigoMandato;

	@FilterSimpleExpression(name = "fechaMandato", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaMandato;

	@FilterSimpleExpression(name = "cif")
	private String cif;

	@FilterSimpleExpression(name = "empresa")
	private String empresa;

	@FilterSimpleExpression(name = "dniAdministrador")
	private String dniAdministrador;

	@FilterSimpleExpression(name = "nombreAdministrador")
	private String nombreAdministrador;

	@FilterSimpleExpression(name = "dniAdministrador2")
	private String dniAdministrador2;

	@FilterSimpleExpression(name = "nombreAdministrador2")
	private String nombreAdministrador2;

	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name = "visible")
	private Boolean visible;

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public FechaFraccionada getFechaMandato() {
		return fechaMandato;
	}

	public void setFechaMandato(FechaFraccionada fechaMandato) {
		this.fechaMandato = fechaMandato;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDniAdministrador() {
		return dniAdministrador;
	}

	public void setDniAdministrador(String dniAdministrador) {
		this.dniAdministrador = dniAdministrador;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public String getDniAdministrador2() {
		return dniAdministrador2;
	}

	public void setDniAdministrador2(String dniAdministrador2) {
		this.dniAdministrador2 = dniAdministrador2;
	}

	public String getNombreAdministrador2() {
		return nombreAdministrador2;
	}

	public void setNombreAdministrador2(String nombreAdministrador2) {
		this.nombreAdministrador2 = nombreAdministrador2;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
