package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaNotificacionesTransFilterBean implements Serializable{

	private static final long serialVersionUID = 2088629274336247550L;

	@FilterSimpleExpression(name="estado")
	private String estado;
	
	@FilterSimpleExpression(name="fechaAlta", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name="nifEmpresa")
	private String nifEmpresa;
	
	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;
	
	@FilterSimpleExpression(name="nombreEmpresa")
	private String nombreEmpresa;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

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

	public String getNombreEmpresa() {
		if(nombreEmpresa != null && !nombreEmpresa.isEmpty()){
			if(nombreEmpresa.contains("%")) {
				nombreEmpresa = nombreEmpresa.replace("%", "");
			}
		}
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

}
