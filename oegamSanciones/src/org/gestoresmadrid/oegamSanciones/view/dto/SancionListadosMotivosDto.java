package org.gestoresmadrid.oegamSanciones.view.dto;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SancionListadosMotivos")
public class SancionListadosMotivosDto {

	private List<SancionDto> listaSancionMotivoAle;
	private List<SancionDto> listaSancionMotivoRes;
	private List<SancionDto> listaSancionMotivoRec;
	private List<SancionDto> listaSancionUnificados;
	
	public SancionListadosMotivosDto () {
		listaSancionMotivoAle = new ArrayList<SancionDto>();
		listaSancionMotivoRes = new ArrayList<SancionDto>();
		listaSancionMotivoRec = new ArrayList<SancionDto>();
		listaSancionUnificados = new ArrayList<SancionDto>();
	}
	
	public List<SancionDto> getListaSancionMotivoAle() {
		return listaSancionMotivoAle;
	}
	
	public void setListaSancionMotivoAle(List<SancionDto> listaSancionMotivoAle) {
		this.listaSancionMotivoAle = listaSancionMotivoAle;
	}
	
	public List<SancionDto> getListaSancionMotivoRes() {
		return listaSancionMotivoRes;
	}
	
	public void setListaSancionMotivoRes(List<SancionDto> listaSancionMotivoRes) {
		this.listaSancionMotivoRes = listaSancionMotivoRes;
	}
	
	public List<SancionDto> getListaSancionMotivoRec() {
		return listaSancionMotivoRec;
	}
	
	public void setListaSancionMotivoRec(List<SancionDto> listaSancionMotivoRec) {
		this.listaSancionMotivoRec = listaSancionMotivoRec;
	}

	public List<SancionDto> getListaSancionUnificados() {
		return listaSancionUnificados;
	}

	public void setListaSancionUnificados(List<SancionDto> listaSancionUnificados) {
		this.listaSancionUnificados = listaSancionUnificados;
	}
}
