package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans;

import java.io.Serializable;
import java.util.Date;

public class IncidenciasResultadosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6176229675183986803L;
	
	private Long   incidenciaId;
	private Long   incidenciaInvId;
	private Long   materialId;	
	private String name;
	private String jefaturaProvincial;
	private String descripcion;
	private String numSerie;
	private String observaciones;
	private Date   fecha;
	private String tipo;
	private String autor;
	
	public Long getIncidenciaId() {
		return incidenciaId;
	}
	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Long getIncidenciaInvId() {
		return incidenciaInvId;
	}
	public void setIncidenciaInvId(Long incidenciaInvId) {
		this.incidenciaInvId = incidenciaInvId;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
