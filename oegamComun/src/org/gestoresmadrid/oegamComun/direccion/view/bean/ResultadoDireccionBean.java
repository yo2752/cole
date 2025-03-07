package org.gestoresmadrid.oegamComun.direccion.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;

public class ResultadoDireccionBean implements Serializable{

	private static final long serialVersionUID = 1617838680677810579L;
	
	Boolean error;
	String mensaje;
	DireccionVO direccion;
	Boolean guardarDir;
	
	public ResultadoDireccionBean(Boolean error) {
		super();
		this.error = error;
		this.guardarDir = Boolean.FALSE;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public DireccionVO getDireccion() {
		return direccion;
	}
	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}
	public Boolean getGuardarDir() {
		return guardarDir;
	}
	public void setGuardarDir(Boolean guardarDir) {
		this.guardarDir = guardarDir;
	}
	
}
