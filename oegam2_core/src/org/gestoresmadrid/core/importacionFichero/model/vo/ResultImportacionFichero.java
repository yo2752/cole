package org.gestoresmadrid.core.importacionFichero.model.vo;

import java.io.Serializable;

public class ResultImportacionFichero implements Serializable{

	private static final long serialVersionUID = 4075712769283677791L;
	
	private Long numOk;
	private Long numError;
	private String tipo;
	private String fecha;
	private String numColegiado;
	private String TipoError;
	public Long getNumOk() {
		return numOk;
	}
	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}
	public Long getNumError() {
		return numError;
	}
	public void setNumError(Long numError) {
		this.numError = numError;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getTipoError() {
		return TipoError;
	}
	public void setTipoError(String tipoError) {
		TipoError = tipoError;
	}
	
	
}
