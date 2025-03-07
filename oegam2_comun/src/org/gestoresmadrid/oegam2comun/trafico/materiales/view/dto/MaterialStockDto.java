package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.Date;

public class MaterialStockDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4373876494437495242L;
	
	private Long   materialStockId;
	private Long   stockInvId;
	private String jefaturaProvincial;
	private Long   materialId;
	private Long   stock;
	private Date   fecUltRecarga;
	private Date   fecUltConsumo;
	private String tipo;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getMaterialStockId() {
		return materialStockId;
	}
	public void setMaterialStockId(Long materialStockId) {
		this.materialStockId = materialStockId;
	}
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public Date getFecUltRecarga() {
		return fecUltRecarga;
	}
	public void setFecUltRecarga(Date fecUltRecarga) {
		this.fecUltRecarga = fecUltRecarga;
	}
	public Date getFecUltConsumo() {
		return fecUltConsumo;
	}
	public void setFecUltConsumo(Date fecUltConsumo) {
		this.fecUltConsumo = fecUltConsumo;
	}
	public Long getStockInvId() {
		return stockInvId;
	}
	public void setStockInvId(Long stockInvId) {
		this.stockInvId = stockInvId;
	}
	
}
