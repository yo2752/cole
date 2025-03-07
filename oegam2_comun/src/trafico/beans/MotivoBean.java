package trafico.beans;

import java.io.Serializable;

public class MotivoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6147843228281980270L;

	private String idMotivo; 
	private String descripcion;
	public String getIdMotivo() {
		return idMotivo;
	}
	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
	
	
	
}
