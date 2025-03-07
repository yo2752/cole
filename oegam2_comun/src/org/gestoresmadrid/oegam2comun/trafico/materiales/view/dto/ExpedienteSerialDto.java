package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteSerialDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8753755548816067092L;
	
	private Long expedienteSerialId;
	private Long pedidoId;
	private Long materialId;
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	private String numSeriePermiso;
	private Long numExpediente;
	private Date fecha;
	
	public Long getExpedienteSerialId() {
		return expedienteSerialId;
	}
	public void setExpedienteSerialId(Long expedienteSerialId) {
		this.expedienteSerialId = expedienteSerialId;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getNumSeriePermiso() {
		return numSeriePermiso;
	}
	public void setNumSeriePermiso(String numSeriePermiso) {
		this.numSeriePermiso = numSeriePermiso;
	}
	public Long getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
