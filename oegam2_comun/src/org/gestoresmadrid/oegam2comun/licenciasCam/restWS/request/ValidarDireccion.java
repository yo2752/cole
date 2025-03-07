package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request;

import java.io.Serializable;

public class ValidarDireccion implements Serializable {

	private static final long serialVersionUID = -5589920872906249466L;

	private String nomPais;

	private String nomProvincia;

	private String nomPueblo;

	private String nomClase;

	private String nomVial;

	private String nomApp;

	private String numApp;

	private String calApp;

	private String escalera;

	private String planta;

	private String puerta;

	private String intercambioBDC;

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	public String getNomProvincia() {
		return nomProvincia;
	}

	public void setNomProvincia(String nomProvincia) {
		this.nomProvincia = nomProvincia;
	}

	public String getNomPueblo() {
		return nomPueblo;
	}

	public void setNomPueblo(String nomPueblo) {
		this.nomPueblo = nomPueblo;
	}

	public String getNomClase() {
		return nomClase;
	}

	public void setNomClase(String nomClase) {
		this.nomClase = nomClase;
	}

	public String getNomVial() {
		return nomVial;
	}

	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}

	public String getNomApp() {
		return nomApp;
	}

	public void setNomApp(String nomApp) {
		this.nomApp = nomApp;
	}

	public String getNumApp() {
		return numApp;
	}

	public void setNumApp(String numApp) {
		this.numApp = numApp;
	}

	public String getCalApp() {
		return calApp;
	}

	public void setCalApp(String calApp) {
		this.calApp = calApp;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getIntercambioBDC() {
		return intercambioBDC;
	}

	public void setIntercambioBDC(String intercambioBDC) {
		this.intercambioBDC = intercambioBDC;
	}
}
