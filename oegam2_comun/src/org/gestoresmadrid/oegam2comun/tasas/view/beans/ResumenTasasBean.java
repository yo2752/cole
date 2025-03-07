package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.util.List;

public class ResumenTasasBean {

	private String tipoTasa = "";
	private int guardadasBien = 0;
	private int guardadasMal = 0;
	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	
	public ResumenTasasBean(){		
		super();
	}
	
	public ResumenTasasBean(String tipoTasa){
		this.tipoTasa=tipoTasa;
	}
	
	
	public String getTipoTasa() {
		return tipoTasa;
	}
	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}
	public int getGuardadasBien() {
		return guardadasBien;
	}
	public void setGuardadasBien(int guardadasBien) {
		this.guardadasBien = guardadasBien;
	}
	public int getGuardadasMal() {
		return guardadasMal;
	}
	public void setGuardadasMal(int guardadasMal) {
		this.guardadasMal = guardadasMal;
	}
	
	public int addCorrecta(){
		this.guardadasBien++;
		return this.guardadasBien;
	}
	
	public int addIncorrecta(){
		this.guardadasMal++;
		return this.guardadasMal;
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {
		return listaOk;
	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	
}
