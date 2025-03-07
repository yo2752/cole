package org.gestoresmadrid.oegam2comun.tasas.view.beans;

public class ResumenTasas {
	
	private String tipoTasa = "";
	private int guardadasBien = 0;
	private int guardadasMal = 0;
	
	public ResumenTasas(){		
	}
	
	public ResumenTasas(String tipoTasa){
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
	
}
