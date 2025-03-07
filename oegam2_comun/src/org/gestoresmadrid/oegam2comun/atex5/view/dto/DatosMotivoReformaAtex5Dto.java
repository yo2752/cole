package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;

public class DatosMotivoReformaAtex5Dto implements Serializable{

	private static final long serialVersionUID = -1601765236366292851L;
	
	private String datosReforma;
    private String observacionesReforma;
    
	public String getDatosReforma() {
		return datosReforma;
	}
	public void setDatosReforma(String datosReforma) {
		this.datosReforma = datosReforma;
	}
	public String getObservacionesReforma() {
		return observacionesReforma;
	}
	public void setObservacionesReforma(String observacionesReforma) {
		this.observacionesReforma = observacionesReforma;
	}
    
    
}
