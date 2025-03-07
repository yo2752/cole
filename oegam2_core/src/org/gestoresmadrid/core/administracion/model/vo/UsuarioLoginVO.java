package org.gestoresmadrid.core.administracion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the USUARIO_LOGIN database table.
 * 
 */
@NamedQueries({
	@NamedQuery(name = UsuarioLoginVO.FINALIZAR_SESIONES, query = UsuarioLoginVO.FINALIZAR_SESIONES_QUERY) })
@Entity
@Table(name="USUARIO_LOGIN")
public class UsuarioLoginVO implements Serializable {


	private static final long serialVersionUID = 8583137925076253978L;

	public static final String FINALIZAR_SESIONES = "UsuarioLoginVO.finalizarSesiones";
	static final String FINALIZAR_SESIONES_QUERY = "update UsuarioLoginVO set fechaFin = :fechaActual where fechaFin is null and frontal = :numFrontal";

	@Id	
	@Column(name="ID_SESION")
	private String idSesion;

	@Column(name="APELLIDOS_NOMBRE")
	private String apellidosNombre;

	@Column(name="FECHA_FIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@Column(name="FECHA_LOGIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaLogin;

	@Column(name="FRONTAL")
	private int frontal;
	
	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name="IP")
	private String ip;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	public String getIdSesion() {
		return this.idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public String getApellidosNombre() {
		return this.apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaLogin() {
		return this.fechaLogin;
	}

	public void setFechaLogin(Date fechaLogin) {
		this.fechaLogin = fechaLogin;
	}

	public int getFrontal() {
		return this.frontal;
	}

	public void setFrontal(int frontal) {
		this.frontal = frontal;
	}

	public String getIp() {
		return this.ip;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

}