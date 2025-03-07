package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="materiales")
public class StocksInfoRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3039145492512907030L;
	
	private Long                total;
	private List<StockInfoRest> records;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<StockInfoRest> getRecords() {
		return records;
	}

	public void setRecords(List<StockInfoRest> records) {
		this.records = records;
	}
	
}
