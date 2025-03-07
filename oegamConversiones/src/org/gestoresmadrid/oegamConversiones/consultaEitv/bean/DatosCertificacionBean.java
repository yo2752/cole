package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosCertificacionBean implements Serializable{

	private static final long serialVersionUID = 348307585039669443L;
	
	private String autorizado;
    private String contraseña;
    private String lugarfirma;
    private String fechafirma;
    private String firmante;
    private String sociedadinscrita;
    
	public DatosCertificacionBean() {
		super();
	}

	public String getAutorizado() {
		return autorizado;
	}

	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getLugarfirma() {
		return lugarfirma;
	}

	public void setLugarfirma(String lugarfirma) {
		this.lugarfirma = lugarfirma;
	}

	public String getFechafirma() {
		return fechafirma;
	}

	public void setFechafirma(String fechafirma) {
		this.fechafirma = fechafirma;
	}

	public String getFirmante() {
		return firmante;
	}

	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}

	public String getSociedadinscrita() {
		return sociedadinscrita;
	}

	public void setSociedadinscrita(String sociedadinscrita) {
		this.sociedadinscrita = sociedadinscrita;
	}
	
}
