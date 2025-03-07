package org.gestoresmadrid.oegam2comun.vehiculo.view.beans;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaVehiculoBean {

	@FilterSimpleExpression(name = "idVehiculo")
	private Long idVehiculo;

	@FilterSimpleExpression(name = "tipoVehiculo")
	private String tipoVehiculo;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "bastidor")
	private String bastidor;

	@FilterSimpleExpression(name = "matricula")
	private String matricula;

	@FilterSimpleExpression(name = "fechaMatriculacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaMatriculacion;

	@FilterSimpleExpression(name = "fechaUltmModif", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaUltmModif;

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public FechaFraccionada getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(FechaFraccionada fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public FechaFraccionada getFechaUltmModif() {
		return fechaUltmModif;
	}

	public void setFechaUltmModif(FechaFraccionada fechaUltmModif) {
		this.fechaUltmModif = fechaUltmModif;
	}
}
