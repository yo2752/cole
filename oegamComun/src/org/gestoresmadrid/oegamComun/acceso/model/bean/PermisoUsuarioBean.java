package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;

public class PermisoUsuarioBean implements Serializable{
	 
	private static final long serialVersionUID = 6529560612382547974L;
	
	String codigoFuncion; 
	String descFuncion;
	
	public String getCodigoFuncion() {
		return codigoFuncion;
	}
	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}
	public String getDescFuncion() {
		return descFuncion;
	}
	public void setDescFuncion(String descFuncion) {
		this.descFuncion = descFuncion;
	} 
	
}
