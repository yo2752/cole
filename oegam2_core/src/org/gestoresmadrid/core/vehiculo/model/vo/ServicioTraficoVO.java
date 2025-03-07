package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SERVICIO_TRAFICO database table.
 */
@Entity
@Table(name = "SERVICIO_TRAFICO")
public class ServicioTraficoVO implements Serializable {

	private static final long serialVersionUID = -3519263199094655659L;

	@Id
	@Column(name = "ID_SERVICIO")
	private String idServicio;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "PERMITIDOS")
	private String permitidos;

	@Column(name = "NO_PERMITIDOS")
	private String noPermitidos;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getPermitidos() {
		return permitidos;
	}

	public void setPermitidos(String permitidos) {
		this.permitidos = permitidos;
	}

	public String getNoPermitidos() {
		return noPermitidos;
	}

	public void setNoPermitidos(String noPermitidos) {
		this.noPermitidos = noPermitidos;
	}
}