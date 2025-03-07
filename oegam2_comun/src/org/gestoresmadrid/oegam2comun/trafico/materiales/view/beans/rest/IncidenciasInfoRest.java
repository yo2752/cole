package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;
import java.util.List;

public class IncidenciasInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6172909450978359023L;
	
	private Long                     total;
	private List<IncidenciaInfoRest> records;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<IncidenciaInfoRest> getRecords() {
		return records;
	}
	public void setRecords(List<IncidenciaInfoRest> records) {
		this.records = records;
	}
}
