package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;

public class EvolucionVehNoMatrOegamBean implements Serializable{

	
	private static final long serialVersionUID = 7771523197657574983L;

	private String matricula;

	private String tipoActuacion;

	private String fechaCambio;

	private String usuario;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(String fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
