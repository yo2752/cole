package trafico.beans;

import java.io.Serializable;

public class TipoTarjetaItvBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8842993957327790262L;

	private String idTipoTarjetaItv; 
	private String descripcion;
	public String getIdTipoTarjetaItv() {
		return idTipoTarjetaItv;
	}
	public void setIdTipoTarjetaItv(String idTipoTarjetaItv) {
		this.idTipoTarjetaItv = idTipoTarjetaItv;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
	
	

}
