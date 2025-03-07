package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImprExpBean implements Serializable{

	private static final long serialVersionUID = -2406880036874820672L;
	
	BigDecimal numExpediente;
	Long idImpr;
	
	public ImprExpBean(BigDecimal numExpediente, Long idImpr) {
		super();
		this.numExpediente = numExpediente;
		this.idImpr = idImpr;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Long getIdImpr() {
		return idImpr;
	}
	public void setIdImpr(Long idImpr) {
		this.idImpr = idImpr;
	}
	
	

}
