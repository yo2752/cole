package trafico.beans;

import java.io.Serializable;

public class EpigrafeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5983220502392008324L;

	private String idEpigrafe; 
	private String direccion;
	public String getIdEpigrafe() {
		return idEpigrafe;
	}
	public void setIdEpigrafe(String idEpigrafe) {
		this.idEpigrafe = idEpigrafe;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	} 
	
	

}
