package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;

public class DatosDefectoItvAtex5Dto implements Serializable{

	private static final long serialVersionUID = -2719494195488399294L;
	
	private String gravedadDefectoItv;
	private String tipoDefectoItv;
	 
	public String getGravedadDefectoItv() {
		return gravedadDefectoItv;
	}
	public void setGravedadDefectoItv(String gravedadDefectoItv) {
		this.gravedadDefectoItv = gravedadDefectoItv;
	}
	public String getTipoDefectoItv() {
		return tipoDefectoItv;
	}
	public void setTipoDefectoItv(String tipoDefectoItv) {
		this.tipoDefectoItv = tipoDefectoItv;
	}
}
