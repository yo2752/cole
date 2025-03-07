package org.gestoresmadrid.oegam2comun.view.dto;

import java.io.Serializable;

public class GruposDto implements Serializable {

	private static final long serialVersionUID = 5590433392653240822L;

	private String idGrupo;

	private String descGrupo;

	private String correoElectronico;

	private String tipo;

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}