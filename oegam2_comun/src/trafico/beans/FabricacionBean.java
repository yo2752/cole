package trafico.beans;

import java.io.Serializable;

public class FabricacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011214754069165892L;

	private String idFabricacion; 
	private String descripcion;
	
	public FabricacionBean(){}
	
	public FabricacionBean(String idFabricacion, String descripcion){
		this.idFabricacion = idFabricacion;
		this.descripcion = descripcion;
	}
	
	public String getIdFabricacion() {
		return idFabricacion;
	}
	public void setIdFabricacion(String idFabricacion) {
		this.idFabricacion = idFabricacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
}
