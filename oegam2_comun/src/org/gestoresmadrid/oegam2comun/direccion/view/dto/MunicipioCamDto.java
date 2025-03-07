package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class MunicipioCamDto implements Serializable {

	private static final long serialVersionUID = -5781808660701028411L;

	private String idMunicipio;

	private String idProvincia;

	private String idMunicipioCam;

	private String nombre;
	
	private ProvinciaCamDto provincia;

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipioCam() {
		return idMunicipioCam;
	}

	public void setIdMunicipioCam(String idMunicipioCam) {
		this.idMunicipioCam = idMunicipioCam;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProvinciaCamDto getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaCamDto provincia) {
		this.provincia = provincia;
	}
	
	
}