package trafico.beans;

import java.io.Serializable;

public class VehiculoPreverBean implements Serializable {

	private static final long serialVersionUID = 5231973132414488050L;

	private String idVehiculoPrever;
	private String descripcion;
	public String getIdVehiculoPrever() {
		return idVehiculoPrever;
	}
	public void setIdVehiculoPrever(String idVehiculoPrever) {
		this.idVehiculoPrever = idVehiculoPrever;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}