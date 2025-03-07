
package trafico.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaJustificanteTraficoBean {

	private BigDecimal numExpediente;
	private String matricula;
	private BigDecimal idJustificante;
	private FechaFraccionada fechaJustificante;

	public ConsultaJustificanteTraficoBean() {
	}

	public FechaFraccionada getFechaJustificante() {
		return fechaJustificante;
	}

	public void setFechaJustificante(FechaFraccionada fechaJustificante) {
		this.fechaJustificante = fechaJustificante;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(BigDecimal idJustificante) {
		this.idJustificante = idJustificante;
	}

}