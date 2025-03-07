package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;

public class DatosCotitularAtex5Dto implements Serializable{

	private static final long serialVersionUID = 106429823763299349L;
	
	private String nifCotitular;
	private String filiacionCotitular;
	
	public String getNifCotitular() {
		return nifCotitular;
	}
	public void setNifCotitular(String nifCotitular) {
		this.nifCotitular = nifCotitular;
	}
	public String getFiliacionCotitular() {
		return filiacionCotitular;
	}
	public void setFiliacionCotitular(String filiacionCotitular) {
		this.filiacionCotitular = filiacionCotitular;
	}
	
}
