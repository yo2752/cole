package trafico.beans;

import java.io.Serializable;

public class LugarPrimeraMatriculacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3529726612226384937L;

	private String idLugarPrimeraMatriculacion; 
	private String descripcion;

	public String getIdLugarPrimeraMatriculacion() {
		return idLugarPrimeraMatriculacion;
	}
	public void setIdLugarPrimeraMatriculacion(String idLugarPrimeraMatriculacion) {
		this.idLugarPrimeraMatriculacion = idLugarPrimeraMatriculacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	
	

}
