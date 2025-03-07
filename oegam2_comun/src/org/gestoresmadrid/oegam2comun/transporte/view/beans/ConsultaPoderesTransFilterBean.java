package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaPoderesTransFilterBean implements Serializable{

	private static final long serialVersionUID = 6009898146631110133L;

	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name="nifEmpresa")
	private String nifEmpresa;
	
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name="idProvincia")
	private String idProvincia;
	
	@FilterSimpleExpression(name="nifPoderdante")
	private String nifPoderdante;
	
	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNifPoderdante() {
		return nifPoderdante;
	}

	public void setNifPoderdante(String nifPoderdante) {
		this.nifPoderdante = nifPoderdante;
	}

}
