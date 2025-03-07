package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class StockInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4907150626626388236L;
	
	private Long             id;
	private Long             unidades;
	private String           observaciones;
	private String           fecha;
	
	private MaterialInfoRest material;
	private ColegioRest      colegio;
	private DelegacionRest   delegacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public MaterialInfoRest getMaterial() {
		return material;
	}
	public void setMaterial(MaterialInfoRest material) {
		this.material = material;
	}
	public ColegioRest getColegio() {
		return colegio;
	}
	public void setColegio(ColegioRest colegio) {
		this.colegio = colegio;
	}
	public DelegacionRest getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(DelegacionRest delegacion) {
		this.delegacion = delegacion;
	}
	
	@Override
	public String toString() {
		return "StockInfoRest [id=" + id + ", unidades=" + unidades + ", observaciones=" + observaciones + ", fecha="
				+ fecha + "]";
	}
	
	
}
