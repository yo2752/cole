package org.gestoresmadrid.core.ivtmMatriculacion.model.beans;

//TODO MPC. Cambio IVTM. Clase añadida.

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Globaltms
 *
 */
public class IvtmConsultaMatriculacionDto {

	private BigDecimal idPeticion;
	private String matricula;
	private String mensaje;
	private String numColegiado;
	private Date fechaReq;

	public BigDecimal getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(BigDecimal idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaReq() {
		return fechaReq;
	}

	public void setFechaReq(Date fechaReq) {
		this.fechaReq = fechaReq;
	}

}