package trafico.beans;

import java.io.Serializable;

public class CategoriaElectricaBean implements Serializable {

	private static final long serialVersionUID = -4041583548471323480L;

	private String idCategoriaElectrica;
	private String descripcion;

	public CategoriaElectricaBean(String idCategoriaElectrica, String descripcion) {
		this.idCategoriaElectrica = idCategoriaElectrica;
		this.descripcion = descripcion;
	}

	public CategoriaElectricaBean(){}

	public String getIdCategoriaElectrica() {
		return idCategoriaElectrica;
	}

	public void setIdCategoriaElectrica(String idCategoriaElectrica) {
		this.idCategoriaElectrica = idCategoriaElectrica;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}