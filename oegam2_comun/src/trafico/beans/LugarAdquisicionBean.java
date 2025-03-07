package trafico.beans;

import java.io.Serializable;

public class LugarAdquisicionBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8141254158227453880L;

	private String idAdquisicion; 
	private String descripcion;
	public String getIdAdquisicion() {
		return idAdquisicion;
	}
	public void setIdAdquisicion(String idAdquisicion) {
		this.idAdquisicion= idAdquisicion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
	
}
