package org.gestoresmadrid.oegam2comun.materiales.view.bean;

import java.math.BigDecimal;
import java.util.Date;

public class EvolucionStockMaterialBean {

	private Long id;
	private Long idStock;
	private String usuarioId;
	private BigDecimal unidadesAnteriores;
	private BigDecimal unidadesActuales;
	private Date fecha;
	private String operacion;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdStock() {
		return idStock;
	}
	public void setIdStock(Long idStock) {
		this.idStock = idStock;
	}
	
	public BigDecimal getUnidadesAnteriores() {
		return unidadesAnteriores;
	}
	public void setUnidadesAnteriores(BigDecimal unidadesAnteriores) {
		this.unidadesAnteriores = unidadesAnteriores;
	}
	public BigDecimal getUnidadesActuales() {
		return unidadesActuales;
	}
	public void setUnidadesActuales(BigDecimal unidadesActuales) {
		this.unidadesActuales = unidadesActuales;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
}
