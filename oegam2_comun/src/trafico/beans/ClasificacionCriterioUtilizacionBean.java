package trafico.beans;

import java.io.Serializable;

public class ClasificacionCriterioUtilizacionBean implements Serializable {

	private static final long serialVersionUID = -3256344648072564450L;

	private String idCriterioUtilizacion;
	private String descripcion;

	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}
	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}