package org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto;

public class ResumenImportacion {
	
	private String tipoTramite;
	private int incorrectos = 0;
	private int correctos = 0;
	
	public ResumenImportacion() {}
	
	public ResumenImportacion(String tipoTramite){
		this.tipoTramite = tipoTramite;
	}
	
	public int addIncorrecto() {
		this.incorrectos ++;
		return this.incorrectos;
	}

	public int getIncorrectos() {
		return incorrectos;
	}

	public void setIncorrectos(int incorrectos) {
		this.incorrectos = incorrectos;
	}

	public int addCorrecto(){
		this.correctos ++;
		return this.correctos;
	}
	public int getCorrectos() {
		return correctos;
	}

	public void setCorrectos(int correctos) {
		this.correctos = correctos;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	
	
}
