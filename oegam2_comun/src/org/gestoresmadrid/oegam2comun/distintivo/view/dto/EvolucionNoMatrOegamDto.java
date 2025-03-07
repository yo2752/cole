package org.gestoresmadrid.oegam2comun.distintivo.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionNoMatrOegamDto implements Serializable {

	private static final long serialVersionUID = 4651078661152118800L;

	private Long id;
	
	private String matricula;

	private String tipoActuacion;

	private Fecha fechaCambio;

	private String descripci�n;

	private Long idUsuario;

	private UsuarioDto usuario;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public Fecha getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Fecha fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getDescripci�n() {
		return descripci�n;
	}

	public void setDescripci�n(String descripci�n) {
		this.descripci�n = descripci�n;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
