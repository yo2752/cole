package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;
import java.util.List;

public class PedidosInfoRest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6754807500881902295L;
	
	private Long                   total;
	private List<PedidoInfoRest> records;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<PedidoInfoRest> getRecords() {
		return records;
	}
	public void setRecords(List<PedidoInfoRest> records) {
		this.records = records;
	}
}
