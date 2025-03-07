package org.gestoresmadrid.core.ivtmMatriculacion.model.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class IvtmModifBean  {

	private BigDecimal numExpediente;
	private String numColegiado;
	private String autoliquidacion;
	private FechaFraccionada fechaBusqueda;
	private String matricula;
	private String bastidor;

	public IvtmModifBean(){
	}

	public IvtmModifBean(boolean inicio) {
		fechaBusqueda = new FechaFraccionada();
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getAutoliquidacion() {
		return autoliquidacion;
	}

	public void setAutoliquidacion(String autoliquidacion) {
		this.autoliquidacion = autoliquidacion;
	}

	public FechaFraccionada getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(FechaFraccionada fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

}