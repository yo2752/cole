package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the COLEGIADO database table.
 */
@Entity
@Table(name = "COLEGIADO")
public class ColegiadoVO implements Serializable {

	private static final long serialVersionUID = 2095259268088255161L;

	@Id
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	private String alias;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	@Column(name = "CLAVE_FACTURACION")
	private String claveFacturacion;

	@Column(name = "NUM_COLEGIADO_NACIONAL")
	private String numColegiadoNacional;

	@Column(name = "FECHA_CADUCIDAD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCaducidad;

	public ColegiadoVO() {}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getClaveFacturacion() {
		return claveFacturacion;
	}

	public void setClaveFacturacion(String claveFacturacion) {
		this.claveFacturacion = claveFacturacion;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

}