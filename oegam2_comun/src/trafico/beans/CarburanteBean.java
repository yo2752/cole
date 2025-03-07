package trafico.beans;

import java.io.Serializable;

public class CarburanteBean implements Serializable {

	private static final long serialVersionUID = -2450934619307134341L;

	private String idCarburante;
	private String descripcion;

	public CarburanteBean(String idCarburante, String descripcion) {
		this.idCarburante = idCarburante;
		this.descripcion = descripcion;
	}

	public CarburanteBean(){}

	public String getIdCarburante() {
		return idCarburante;
	}
	public void setIdCarburante(String idCarburante) {
		this.idCarburante = idCarburante;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}