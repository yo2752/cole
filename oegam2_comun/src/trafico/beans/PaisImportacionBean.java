package trafico.beans;

import java.io.Serializable;

public class PaisImportacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3574785150180119888L;

	private String idPaisImportacion; 
	private String descripcion;
	
	public PaisImportacionBean(){}
	
	public PaisImportacionBean(String idPaisImportacion, String descripcion){
		this.idPaisImportacion = idPaisImportacion;
		this.descripcion = descripcion;
	}
	
	public String getIdPaisImportacion() {
		return idPaisImportacion;
	}
	public void setIdPaisImportacion(String idPaisImportacion) {
		this.idPaisImportacion = idPaisImportacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 

}
