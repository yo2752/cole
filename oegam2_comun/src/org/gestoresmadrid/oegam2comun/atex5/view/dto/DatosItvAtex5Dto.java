package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DatosItvAtex5Dto implements Serializable{

	private static final long serialVersionUID = -8456538318956797205L;
	
	private String anotacion;
    private Integer cuentaHoras;
    private String estacion;
    private Date fechaCaducidad;
    private Date fechaFinAnterior;
    private Date fechaItv;
    private Integer kilometraje;
    private List<DatosDefectoItvAtex5Dto> listaDefectos;
    private String sListaDefectosPantalla;
    private String motivoItv;
    private String provincia;
    private String resultadoItv;
    
	public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public Integer getCuentaHoras() {
		return cuentaHoras;
	}
	public void setCuentaHoras(Integer cuentaHoras) {
		this.cuentaHoras = cuentaHoras;
	}
	public String getEstacion() {
		return estacion;
	}
	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public Date getFechaFinAnterior() {
		return fechaFinAnterior;
	}
	public void setFechaFinAnterior(Date fechaFinAnterior) {
		this.fechaFinAnterior = fechaFinAnterior;
	}
	public Date getFechaItv() {
		return fechaItv;
	}
	public void setFechaItv(Date fechaItv) {
		this.fechaItv = fechaItv;
	}
	public Integer getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(Integer kilometraje) {
		this.kilometraje = kilometraje;
	}
	
	public List<DatosDefectoItvAtex5Dto> getListaDefectos() {
		return listaDefectos;
	}
	public void setListaDefectos(List<DatosDefectoItvAtex5Dto> listaDefectos) {
		this.listaDefectos = listaDefectos;
	}
	public String getMotivoItv() {
		return motivoItv;
	}
	public void setMotivoItv(String motivoItv) {
		this.motivoItv = motivoItv;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getResultadoItv() {
		return resultadoItv;
	}
	public void setResultadoItv(String resultadoItv) {
		this.resultadoItv = resultadoItv;
	}
	public String getsListaDefectosPantalla() {
		return sListaDefectosPantalla;
	}
	public void setsListaDefectosPantalla(String sListaDefectosPantalla) {
		this.sListaDefectosPantalla = sListaDefectosPantalla;
	}
    
}
