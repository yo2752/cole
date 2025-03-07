package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CAMBIO_SERVICIO_PERMITIDO database table.
 */
@Embeddable
public class CambioServPermitidoPK implements Serializable {

	private static final long serialVersionUID = -322253853484853678L;

	@Column(name="ID_SERVICIO_NUEVO", insertable = false, updatable = false)
	String idServicioTraficoNuevo;
	
	@Column(name="ID_SERVICIO_ACTUAL", insertable = false, updatable = false)
	String idServicioTraficoActual;	
	

	public CambioServPermitidoPK() {}

	public String getIdServicioTraficoNuevo() {
		return idServicioTraficoNuevo;
	}

	public void setIdServicioTraficoNuevo(String idServicioTraficoNuevo) {
		this.idServicioTraficoNuevo = idServicioTraficoNuevo;
	}

	public String getIdServicioTraficoActual() {
		return idServicioTraficoActual;
	}

	public void setIdServicioTraficoActual(String idServicioTraficoActual) {
		this.idServicioTraficoActual = idServicioTraficoActual;
	}	
}