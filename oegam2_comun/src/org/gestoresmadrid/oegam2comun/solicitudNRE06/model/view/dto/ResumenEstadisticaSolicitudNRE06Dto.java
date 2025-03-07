package org.gestoresmadrid.oegam2comun.solicitudNRE06.model.view.dto;

import java.io.Serializable;

import utilidades.estructuras.FechaFraccionada;

public class ResumenEstadisticaSolicitudNRE06Dto  implements Serializable{


	private static final long serialVersionUID = -3111425532562462553L;

	private FechaFraccionada fecha;

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	
	
}
