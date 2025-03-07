package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.List;

public class DatosLibroTallerAtex5Dto implements Serializable{

	private static final long serialVersionUID = 2989648774943226142L;
	
	private List<DatosIncidenciaAtex5Dto> listaDetalleIncidencias;
	private DatosIncidenciaAtex5Dto detalleIncidenciaSeleccionada;
	
	public List<DatosIncidenciaAtex5Dto> getListaDetalleIncidencias() {
		return listaDetalleIncidencias;
	}
	public void setListaDetalleIncidencias(List<DatosIncidenciaAtex5Dto> listaDetalleIncidencias) {
		this.listaDetalleIncidencias = listaDetalleIncidencias;
	}
	public DatosIncidenciaAtex5Dto getDetalleIncidenciaSeleccionada() {
		return detalleIncidenciaSeleccionada;
	}
	public void setDetalleIncidenciaSeleccionada(DatosIncidenciaAtex5Dto detalleIncidenciaSeleccionada) {
		this.detalleIncidenciaSeleccionada = detalleIncidenciaSeleccionada;
	}
	
}
