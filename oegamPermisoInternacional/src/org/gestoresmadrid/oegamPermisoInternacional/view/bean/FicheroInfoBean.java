package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;

public class FicheroInfoBean implements Serializable {

	private static final long serialVersionUID = -239382301495197091L;

	String nombreFichero;
	Long idPermisoInternacional;
	String numReferencia;

	public FicheroInfoBean(String nombreFichero, Long idPermisoInternacional, String numReferencia) {
		super();
		this.nombreFichero = nombreFichero;
		this.idPermisoInternacional = idPermisoInternacional;
		this.numReferencia = numReferencia;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Long getIdPermisoInternacional() {
		return idPermisoInternacional;
	}

	public void setIdPermisoInternacional(Long idPermisoInternacional) {
		this.idPermisoInternacional = idPermisoInternacional;
	}

	public String getNumReferencia() {
		return numReferencia;
	}

	public void setNumReferencia(String numReferencia) {
		this.numReferencia = numReferencia;
	}

}