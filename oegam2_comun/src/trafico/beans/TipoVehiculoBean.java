package trafico.beans;

import java.io.Serializable;

public class TipoVehiculoBean implements Serializable {

	private static final long serialVersionUID = -6982509210057321351L;

	private String tipoVehiculo;
	private String descripcion;
	private String tipoTramite;

	public TipoVehiculoBean(boolean inicializar) {
	}
	public TipoVehiculoBean() {
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}