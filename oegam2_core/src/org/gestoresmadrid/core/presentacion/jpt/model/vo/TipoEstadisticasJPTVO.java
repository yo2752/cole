package org.gestoresmadrid.core.presentacion.jpt.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_ESTADISTICAS_JPT")
public class TipoEstadisticasJPTVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_ESTADISTICAS")
	private Long idTipoEstadistica;

	@Column(name="DESC_TIPO_ESTADISTICA")
	private String descripcion;

	@Column(name="VISIBLE",columnDefinition="NUMBER(1,0)")
	private Boolean visible;

	@Column(name="TIPO_INCIDENCIA",columnDefinition="NUMBER(1,0)")
	private Boolean incidencia;

	public Long getIdTipoEstadistica() {
		return idTipoEstadistica;
	}

	public void setIdTipoEstadistica(Long idTipoEstadistica) {
		this.idTipoEstadistica = idTipoEstadistica;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Boolean incidencia) {
		this.incidencia = incidencia;
	}

}