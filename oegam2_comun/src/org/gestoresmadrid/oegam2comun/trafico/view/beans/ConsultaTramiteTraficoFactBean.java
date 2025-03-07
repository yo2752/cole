package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTramiteTraficoFactBean {

	@FilterSimpleExpression(name = "id.numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "id.codigoTasa")
	private String codigoTasa;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "matricula")
	private String matricula;

	@FilterSimpleExpression(name = "nif")
	private String nif;

	@FilterSimpleExpression(name = "bastidor")
	private String bastidor;

	@FilterSimpleExpression(name = "fechaAplicacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFiltradoAplicacion;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public FechaFraccionada getFechaFiltradoAplicacion() {
		return fechaFiltradoAplicacion;
	}

	public void setFechaFiltradoAplicacion(FechaFraccionada fechaFiltradoAplicacion) {
		this.fechaFiltradoAplicacion = fechaFiltradoAplicacion;
	}
}
