package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class IncidenciaInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2181776427634537609L;
	
	private Long    id;
	private String  fecha;
	private String  numserie;
	private String  observaciones;
	private String  tipo;
	
	private ColegioRest      colegio;
	private DelegacionRest   delegacion;
	private AutorRest        autor;
	private MaterialInfoRest material;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNumserie() {
		return numserie;
	}
	public void setNumserie(String numserie) {
		this.numserie = numserie;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	public AutorRest getAutor() {
		return autor;
	}
	public void setAutor(AutorRest autor) {
		this.autor = autor;
	}
	public MaterialInfoRest getMaterial() {
		return material;
	}
	public void setMaterial(MaterialInfoRest material) {
		this.material = material;
	}
}
