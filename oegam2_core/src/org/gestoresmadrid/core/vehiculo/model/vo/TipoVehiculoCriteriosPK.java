package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the TIPO_VEHICULO_CRITERIOS database table.
 */
@Embeddable
public class TipoVehiculoCriteriosPK implements Serializable {

	private static final long serialVersionUID = 6695890122061916373L;

	@Column(name = "TIPO_VEHICULO")
	private String tipoVehiculo;

	@Column(name = "CRITERIO_UTILIZACION")
	private String criterioUtilizacion;

	@Column(name = "CRITERIO_CONSTRUCCION")
	private String criterioConstruccion;

	public TipoVehiculoCriteriosPK() {}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getCriterioUtilizacion() {
		return criterioUtilizacion;
	}

	public void setCriterioUtilizacion(String criterioUtilizacion) {
		this.criterioUtilizacion = criterioUtilizacion;
	}

	public String getCriterioConstruccion() {
		return criterioConstruccion;
	}

	public void setCriterioConstruccion(String criterioConstruccion) {
		this.criterioConstruccion = criterioConstruccion;
	}
}