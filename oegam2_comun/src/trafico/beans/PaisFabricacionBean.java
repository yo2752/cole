package trafico.beans;

import java.io.Serializable;


public class PaisFabricacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163956950381077061L;

	private String idPaisFabricacion; 
	private String descripcion;
	
	public PaisFabricacionBean(){}
	
	public PaisFabricacionBean(String idPaisFabricacion, String descripcion){
		this.idPaisFabricacion = idPaisFabricacion;
		this.descripcion = descripcion;
	}
	
	
	public String getIdPaisFabricacion() {
		return idPaisFabricacion;
	}

	public void setIdPaisFabricacion(String idPaisFabricacion) {
		this.idPaisFabricacion = idPaisFabricacion;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
}
