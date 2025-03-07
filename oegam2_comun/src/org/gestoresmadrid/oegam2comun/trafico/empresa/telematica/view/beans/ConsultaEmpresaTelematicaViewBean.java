package org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.criterion.CriteriaSpecification;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaEmpresaTelematicaViewBean implements Serializable {

	private static final long serialVersionUID = 3371313182554602717L;

	@FilterSimpleExpression(name = "empresa")
	private String nombreEmpresa;

	@FilterSimpleExpression(name = "cifEmpresa")
	private String cifEmpresa;

	@FilterSimpleExpression(name = "codigoPostal")
	private String codigoPostal;

	@FilterSimpleExpression(name = "municipio")
	private String municipio;

	@FilterSimpleExpression(name = "provincia")
	private String provincia;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;

	@FilterSimpleExpression(name = "fechaBaja", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaBaja;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name = "contratoVO", joinType = CriteriaSpecification.LEFT_JOIN)
	private Long idContrato;

	@FilterSimpleExpression(name = "estado")
	private Long estado;

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getCifEmpresa() {
		return cifEmpresa;
	}

	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(FechaFraccionada fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

}
