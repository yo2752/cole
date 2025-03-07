package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import utilidades.estructuras.Fecha;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = -7203914344874591116L;

	private String anagrama;

	private String apellidosNombre;

	private String apellido1;

	private String apellido2;

	private String nombre;

	private String correoElectronico;

	private String estadoUsuario;

	private Fecha fechaNotificacion;

	private Fecha fechaRenovacion;

	private BigDecimal idUsuario;

	private String nif;

	private String numColegiado;

	private String numColegiadoNacional;

	private String password;

	private Fecha ultimaConexion;

	private String usuario;

	private String idGrupo;

	private String jefaturaJPT;

	private List<FuncionDto> permisos;
	
	private Fecha fechaAlta;

	private Fecha fechaFin;

	public String getAnagrama() {
		return anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getApellidosNombre() {
		return apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public Fecha getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Fecha fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Fecha getFechaRenovacion() {
		return fechaRenovacion;
	}

	public void setFechaRenovacion(Fecha fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Fecha getUltimaConexion() {
		return ultimaConexion;
	}

	public void setUltimaConexion(Fecha ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getJefaturaJPT() {
		return jefaturaJPT;
	}

	public void setJefaturaJPT(String jefaturaJPT) {
		this.jefaturaJPT = jefaturaJPT;
	}

	public List<FuncionDto> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<FuncionDto> permisos) {
		this.permisos = permisos;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}
}