package org.gestoresmadrid.oegam2comun.canjes.view.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CanjesDto implements Serializable{

	private static final long serialVersionUID = -4743438060191458454L;
	
	private String idCanje;
	private String numColegiado;
	private String dninie;
	private String nombreapell;
	private String pais;
	private String fechaNac;
	private String numCarnet;
	private String categorias;
	private String fechaExp;
	private String lugarExp;
	
	
	private ArrayList<CanjesDto> listaCanjes;
	
	
	public String getIdCanje() {
		return idCanje;
	}
	public void setIdCanje(String idCanje) {
		this.idCanje = idCanje;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getNumCarnet() {
		return numCarnet;
	}
	public void setNumCarnet(String numCarnet) {
		this.numCarnet = numCarnet;
	}
	public String getCategorias() {
		return categorias;
	}
	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}
	public String getFechaExp() {
		return fechaExp;
	}
	public void setFechaExp(String fechaExp) {
		this.fechaExp = fechaExp;
	}
	public String getLugarExp() {
		return lugarExp;
	}
	public void setLugarExp(String lugarExp) {
		this.lugarExp = lugarExp;
	}
	public String getDninie() {
		return dninie;
	}
	public void setDninie(String dninie) {
		this.dninie = dninie;
	}
	public String getNombreapell() {
		return nombreapell;
	}
	public void setNombreapell(String nombreapell) {
		this.nombreapell = nombreapell;
	}
	public ArrayList<CanjesDto> getListaCanjes() {
		return listaCanjes;
	}
	public void setListaCanjes(ArrayList<CanjesDto> listaCanjes) {
		this.listaCanjes = listaCanjes;
	}
	
	
}
