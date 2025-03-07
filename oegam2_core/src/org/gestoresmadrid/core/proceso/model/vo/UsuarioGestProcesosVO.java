package org.gestoresmadrid.core.proceso.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USUARIO_GEST_PROCESOS database table.
 */
@Entity
@Table(name = "USUARIO_GEST_PROCESOS")
public class UsuarioGestProcesosVO implements Serializable {

	private static final long serialVersionUID = -235117389951334749L;

	@Id
	@Column(name = "ID_GEST_PROCESOS")
	@SequenceGenerator(name = "usuario_gest_procesos_secuencia", sequenceName = "USUARIO_GEST_PROCESOS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_gest_procesos_secuencia")
	private Long idGestProcesos;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name = "FECHA_CAMBIO_PASSWORD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambioPassword;

	@Column(name = "ESTADO")
	private Long estado;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "ROL")
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

	public Date getFechaCambioPassword() {
		return fechaCambioPassword;
	}

	public void setFechaCambioPassword(Date fechaCambioPassword) {
		this.fechaCambioPassword = fechaCambioPassword;
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