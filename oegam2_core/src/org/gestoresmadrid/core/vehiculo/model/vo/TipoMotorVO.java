package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_MOTOR database table.
 */
@Entity
@Table(name = "TIPO_MOTOR")
public class TipoMotorVO implements Serializable {

	private static final long serialVersionUID = -8463275923829983850L;

	@Id
	@Column(name = "TIPO_MOTOR")
	private String tipoMotor;

	@Column(name = "TIPO_MOTOR_DESC")
	private String tipoMotorDesc;

	public TipoMotorVO() {}

	public String getTipoMotor() {
		return this.tipoMotor;
	}

	public void setTipoMotor(String tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public String getTipoMotorDesc() {
		return this.tipoMotorDesc;
	}

	public void setTipoMotorDesc(String tipoMotorDesc) {
		this.tipoMotorDesc = tipoMotorDesc;
	}

}