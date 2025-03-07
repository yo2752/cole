package trafico.beans; 

import java.io.Serializable;

public class ClasificacionCriterioConstruccionBean implements Serializable {

	private static final long serialVersionUID = 1964482514613071217L;

	private String idCriterioConstruccion;
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}
	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

}