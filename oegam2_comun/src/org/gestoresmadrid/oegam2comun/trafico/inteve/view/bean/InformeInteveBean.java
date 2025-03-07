package org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean;

import java.io.Serializable;

public class InformeInteveBean implements Serializable{
	
	private static final long serialVersionUID = 7171092957247556101L;
	private byte[] fichero;
	private String nombreFichero;
	private String resultado;
	private Long idSolInfo;
	
	
	public InformeInteveBean(byte[] fichero, String nombreFichero, String resultado, Long idSolInfo) {
		super();
		this.fichero = fichero;
		this.nombreFichero = nombreFichero;
		this.resultado = resultado;
		this.idSolInfo = idSolInfo;
	}
	public byte[] getFichero() {
		return fichero;
	}
	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Long getIdSolInfo() {
		return idSolInfo;
	}
	public void setIdSolInfo(Long idSolInfo) {
		this.idSolInfo = idSolInfo;
	}
	
	

}
