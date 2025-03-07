package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.HashMap;

public class ValueFechaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4108662021059342069L;
	
	private HashMap<String, HashMap<String, Long>> valueFechaDistintivos;
	private HashMap<String, HashMap<String, Long>> valueFechaPermisos;
	private HashMap<String, HashMap<String, Long>> valueFechaFichasTecnicas;
	
	public ValueFechaBean() { };
	
	
	public ValueFechaBean(HashMap<String, HashMap<String, Long>> valueFechaDistintivos,
			HashMap<String, HashMap<String, Long>> valueFechaPermisos,
			HashMap<String, HashMap<String, Long>> valueFechaFichasTecnicas) {
		this.valueFechaDistintivos = valueFechaDistintivos;
		this.valueFechaPermisos = valueFechaPermisos;
		this.valueFechaFichasTecnicas = valueFechaFichasTecnicas;
	}


	public HashMap<String, HashMap<String, Long>> getValueFechaDistintivos() {
		return valueFechaDistintivos;
	}
	public void setValueFechaDistintivos(HashMap<String, HashMap<String, Long>> valueFechaDistintivos) {
		this.valueFechaDistintivos = valueFechaDistintivos;
	}
	public HashMap<String, HashMap<String, Long>> getValueFechaPermisos() {
		return valueFechaPermisos;
	}
	public void setValueFechaPermisos(HashMap<String, HashMap<String, Long>> valueFechaPermisos) {
		this.valueFechaPermisos = valueFechaPermisos;
	}
	public HashMap<String, HashMap<String, Long>> getValueFechaFichasTecnicas() {
		return valueFechaFichasTecnicas;
	}
	public void setValueFechaFichasTecnicas(HashMap<String, HashMap<String, Long>> valueFechaFichasTecnicas) {
		this.valueFechaFichasTecnicas = valueFechaFichasTecnicas;
	}
}
