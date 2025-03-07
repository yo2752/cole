package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;

public class StockResultadosBean implements Serializable {

	private static final long serialVersionUID = 3185699907363709326L;

	private Long materialStockId;

	private String jefaturaProvincial;

	private String idJefatura;

	private String materialVO;

	private Long unidades;

	private String tipo;

	private Date fecUltRecarga;

	private Date fecUltConsumo;

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
	
	public String getIdJefatura() {
		return idJefatura;
	}

	public void setIdJefatura(String idJefatura) {
		this.idJefatura = idJefatura;
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

	public Long getUnidades() {
		return unidades;
	}

	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMaterialVO() {
		return materialVO;
	}

	public void setMaterialVO(String materialVO) {
		this.materialVO = materialVO;
	}
}
