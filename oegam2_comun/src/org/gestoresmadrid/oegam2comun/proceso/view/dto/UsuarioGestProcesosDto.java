package org.gestoresmadrid.oegam2comun.proceso.view.dto;

import java.io.Serializable;
import java.util.Date;

public class UsuarioGestProcesosDto implements Serializable {

	private static final long serialVersionUID = -2508002431644229480L;

	private Long idGestProcesos;

	private String userName;

	private String password;

	private Date fechaAlta;

	private Date fechaMandato;

	private Long estado;

	private Long idUsuario;

	private String rol;

	public Long getIdGestProcesos() {
		return idGestProcesos;
	}

	public void setIdGestProcesos(Long idGestProcesos) {
		this.idGestProcesos = idGestProcesos;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaMandato() {
		return fechaMandato;
	}

	public void setFechaMandato(Date fechaMandato) {
		this.fechaMandato = fechaMandato;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}