package trafico.beans;

import java.io.Serializable;

public class HomologacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8659940957908812707L;

	private String idHomologacion; 
	private String descripcion;
	
	public HomologacionBean(){}
	
	public HomologacionBean(String idHomologacion, String descripcion){
		this.idHomologacion = idHomologacion;
		this.descripcion = descripcion;
	}
	
	public String getIdHomologacion() {
		return idHomologacion;
	}
	public void setIdHomologacion(String idHomologacion) {
		this.idHomologacion = idHomologacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
		
}
