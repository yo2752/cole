package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncidenciaDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -77116686189637977L;
	
	private Long   incidenciaId;
	private Long   materiaId;
	private String materiaNombre;
	private String jefaturaProvincial;
	private String jefaturaDescripcion;
	private String observaciones;
	private Date   fecha;
	private Long   estadoInc;
	private String estadoNom;
	private String tipo;
	private String tipoNombre;
	
	private List<IncidenciaSerialDto> listaSerial = new ArrayList<IncidenciaSerialDto>();
	private List<IncidenciaIntervalosDto> listaIntervalos = new ArrayList<IncidenciaIntervalosDto>();
	
	
	public IncidenciaDto() {}
	
	
	public IncidenciaDto(Long materiaId, String jefaturaProvincial, String observaciones, String tipo,
			List<IncidenciaSerialDto> listaSerial) {

		this.materiaId = materiaId;
		this.jefaturaProvincial = jefaturaProvincial;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.listaSerial = listaSerial;
	}


	public Long getMateriaId() {
		return materiaId;
	}
	public void setMateriaId(Long materiaId) {
		this.materiaId = materiaId;
	}
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
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
	public Long getEstadoInc() {
		return estadoInc;
	}
	public void setEstadoInc(Long estadoInc) {
		this.estadoInc = estadoInc;
	}
	public String getEstadoNom() {
		return estadoNom;
	}
	public void setEstadoNom(String estadoNom) {
		this.estadoNom = estadoNom;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getIncidenciaId() {
		return incidenciaId;
	}
	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}
	public String getJefaturaDescripcion() {
		return jefaturaDescripcion;
	}
	public void setJefaturaDescripcion(String jefaturaDescripcion) {
		this.jefaturaDescripcion = jefaturaDescripcion;
	}
	public String getMateriaNombre() {
		return materiaNombre;
	}
	public void setMateriaNombre(String materiaNombre) {
		this.materiaNombre = materiaNombre;
	}
	public List<IncidenciaSerialDto> getListaSerial() {
		return listaSerial;
	}
	public void setListaSerial(List<IncidenciaSerialDto> listaSerial) {
		this.listaSerial = listaSerial;
	}
	public List<IncidenciaIntervalosDto> getListaIntervalos() {
		return listaIntervalos;
	}
	public void setListaIntervalos(List<IncidenciaIntervalosDto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}
	public String getTipoNombre() {
		return tipoNombre;
	}
	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}
	
}
