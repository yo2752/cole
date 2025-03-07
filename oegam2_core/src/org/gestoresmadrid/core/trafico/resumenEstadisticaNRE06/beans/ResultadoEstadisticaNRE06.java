package org.gestoresmadrid.core.trafico.resumenEstadisticaNRE06.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResultadoEstadisticaNRE06 implements Serializable{

	private static final long serialVersionUID = 8622719839000393809L;
	
	private BigDecimal numColegiado;
	private Date fechaRegistro;
	private int numRegistro;
	
	public BigDecimal getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(BigDecimal numColegiado) {
		this.numColegiado = numColegiado;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getNumRegistro() {
		return numRegistro;
	}
	public void setNumRegistro(int numRegistro) {
		this.numRegistro = numRegistro;
	}
	

}
