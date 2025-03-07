package org.gestoresmadrid.core.trafico.model.vo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.FetchMode;

@Entity
@SecondaryTable(name = "TRAMITE_TRAF_CAMBIO_SERVICIO")
@org.hibernate.annotations.Table(appliesTo = "TRAMITE_TRAF_CAMBIO_SERVICIO", fetch = FetchMode.SELECT)
@DiscriminatorValue("T27")
public class TramiteTrafCambioServicioVO extends TramiteTraficoVO {

	private static final long serialVersionUID = -3760085932615877069L;

	@Column(table = "TRAMITE_TRAF_CAMBIO_SERVICIO", name = "MOTIVO_CAMBIO_SERVICIO")
	private String motivoCambioServicio;

	@Column(table = "TRAMITE_TRAF_CAMBIO_SERVICIO", name = "IMPR_PERMISO_CIRCU")
	private String imprPermisoCirculacion;

	public String getMotivoCambioServicio() {
		return motivoCambioServicio;
	}

	public void setMotivoCambioServicio(String motivoCambioServicio) {
		this.motivoCambioServicio = motivoCambioServicio;
	}

	public String getImprPermisoCirculacion() {
		return imprPermisoCirculacion;
	}

	public void setImprPermisoCirculacion(String imprPermisoCirculacion) {
		this.imprPermisoCirculacion = imprPermisoCirculacion;
	}

}