package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

/**
 * Bean de la pantalla de búsqueda de solicitudes de placas de matriculación
 * @author Alvaro Fernandez
 * @date 27/12/2018
 *
 */
public class ConsultaPlacasBean {
	
	private BigDecimal idContrato;
	
	private String numColegiado;
	private String numExpediente;
	private FechaFraccionada fecha;
	private String matricula;
	private String tipoMatricula;

	public ConsultaPlacasBean() {
		fecha = new FechaFraccionada();
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getNumExpediente() {
		try {
			return new Long(numExpediente);
		} catch (Exception e) {
			return null;
		}
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoMatricula() {
		return tipoMatricula;
	}

	public void setTipoMatricula(String tipoMatricula) {
		this.tipoMatricula = tipoMatricula;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

}
