package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

public class ColegioDto implements Serializable {

	private static final long serialVersionUID = 7817744470560456052L;

	private String cif;

	private String colegio;

	private String correoElectronico;

	private String nombre;

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}