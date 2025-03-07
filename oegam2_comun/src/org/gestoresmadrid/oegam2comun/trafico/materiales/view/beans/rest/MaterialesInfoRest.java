package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="materiales")
public class MaterialesInfoRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3039145492512907030L;
	
	private Long                   total;
	private List<MaterialInfoRest> records;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<MaterialInfoRest> getRecords() {
		return records;
	}

	public void setRecords(List<MaterialInfoRest> records) {
		this.records = records;
	}
	
}
