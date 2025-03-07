package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.Serializable;

public class FicheroKoBean implements Serializable{

	private static final long serialVersionUID = 4515187988786564114L;

	String nombreFichero;
	byte[] fichero;

	public FicheroKoBean(String nombreFichero, byte[] fichero) {
		super();
		this.nombreFichero = nombreFichero;
		this.fichero = fichero;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public byte[] getFichero() {
		return fichero;
	}
	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
}