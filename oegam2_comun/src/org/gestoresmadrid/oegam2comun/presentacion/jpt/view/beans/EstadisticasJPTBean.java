package org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans;

import java.util.List;

import utilidades.estructuras.FechaFraccionada;

public class EstadisticasJPTBean {

	private Long numColegiado;
	
	private FechaFraccionada fecha;
	
	private List<TipoEstadisticasJPTBean> listadoTipoEstadisticasJPTBean;
	
	private Boolean incidencias;
	
	private String jefaturaJPT;
	
	public List<TipoEstadisticasJPTBean> getListadoTipoEstadisticasJPTBean() {
		return listadoTipoEstadisticasJPTBean;
	}

	public void setListadoTipoEstadisticasJPTBean(
			List<TipoEstadisticasJPTBean> listadoTipoEstadisticasJPTBean) {
		this.listadoTipoEstadisticasJPTBean = listadoTipoEstadisticasJPTBean;
	}

	public void setNumColegiado(Long numColegiado) {
		this.numColegiado = numColegiado;
	}

	public long getNumColegiado() {
		return numColegiado;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public Boolean getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Boolean incidencias) {
		this.incidencias = incidencias;
	}

	public String getJefaturaJPT() {
		return jefaturaJPT;
	}

	public void setJefaturaJPT(String jefaturaJPT) {
		this.jefaturaJPT = jefaturaJPT;
	}
}
