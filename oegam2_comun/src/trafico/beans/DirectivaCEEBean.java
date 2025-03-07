package trafico.beans;

import java.io.Serializable;

public class DirectivaCEEBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7974883119246349805L;

	private String idDirectivaCEE; 
	private String descripcion;
	public String getIdDirectivaCEE() {
		return idDirectivaCEE;
	}
	public void setIdDirectivaCEE(String idDirectivaCEE) {
		this.idDirectivaCEE = idDirectivaCEE;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
	
	
	
	
}
