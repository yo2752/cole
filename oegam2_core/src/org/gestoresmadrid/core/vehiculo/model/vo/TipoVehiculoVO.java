package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_VEHICULO database table.
 */
@Entity
@Table(name = "TIPO_VEHICULO")
public class TipoVehiculoVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 5192661169146610723L;

	@Id
	@Column(name = "TIPO_VEHICULO")
	private String tipoVehiculo;

	private String descripcion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	public TipoVehiculoVO() {}

	public String getTipoVehiculo() {
		return this.tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
}