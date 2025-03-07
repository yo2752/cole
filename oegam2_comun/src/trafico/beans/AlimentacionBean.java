package trafico.beans;

import java.io.Serializable;

public class AlimentacionBean implements Serializable {

	private static final long serialVersionUID = 1906072041879656888L;

	private String idAlimentacion;
	private String descripcion;

	public String getIdAlimentacion() {
		return idAlimentacion;
	}
	public void setIdAlimentacion(String idAlimentacion) {
		this.idAlimentacion = idAlimentacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}