package trafico.beans;

import java.io.Serializable;

public class TipoMotorBean implements Serializable {

	private static final long serialVersionUID = -2305477300420652040L;

	private String idTipoMotor;
	private String descripcion;

	public TipoMotorBean(String idTipoMotor, String descripcion){
		this.idTipoMotor = idTipoMotor;
		this.descripcion = descripcion;
	}

	public TipoMotorBean(){}

	public String getIdTipoMotor() {
		return idTipoMotor;
	}
	public void setIdTipoMotor(String idTipoMotor) {
		this.idTipoMotor = idTipoMotor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}