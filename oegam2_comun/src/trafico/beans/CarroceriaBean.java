package trafico.beans;

import java.io.Serializable;

public class CarroceriaBean implements Serializable {

	private static final long serialVersionUID = -5433432619232314792L;

	private String idCarroceria;
	private String descripcion;

	public CarroceriaBean(){
	}

	public CarroceriaBean(String idCarroceria, String descripcion){
		this.idCarroceria = idCarroceria;
		this.descripcion = descripcion;
	}

	public String getIdCarroceria() {
		return idCarroceria;
	}
	public void setIdCarroceria(String idCarroceria) {
		this.idCarroceria = idCarroceria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}