package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.List;

public class DatosSeguridadAtex5Dto implements Serializable{

	private static final long serialVersionUID = 6596294209891908427L;
	
	private List<DatosElementoSeguridadAtex5Dto> listaElementosSeguridad;
	private List<DatosNcapAtex5Dto> listaNcap;
	private List<DatosHojaRescateAtex5Dto> listaHojaRescate;
	
	public List<DatosElementoSeguridadAtex5Dto> getListaElementosSeguridad() {
		return listaElementosSeguridad;
	}
	public void setListaElementosSeguridad(List<DatosElementoSeguridadAtex5Dto> listaElementosSeguridad) {
		this.listaElementosSeguridad = listaElementosSeguridad;
	}
	public List<DatosNcapAtex5Dto> getListaNcap() {
		return listaNcap;
	}
	public void setListaNcap(List<DatosNcapAtex5Dto> listaNcap) {
		this.listaNcap = listaNcap;
	}
	public List<DatosHojaRescateAtex5Dto> getListaHojaRescate() {
		return listaHojaRescate;
	}
	public void setListaHojaRescate(List<DatosHojaRescateAtex5Dto> listaHojaRescate) {
		this.listaHojaRescate = listaHojaRescate;
	}
	
}
