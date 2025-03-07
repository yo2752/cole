package org.gestoresmadrid.core.accionTramite.model.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

import java.util.Date;

/**
 * The persistent class for the ACCION_TRAMITE database table.
 */
@Entity
@Table(name = "ACCION_TRAMITE")
@NamedQuery(name = "AccionTramiteVO.findAll", query = "SELECT a FROM AccionTramiteVO a")
public class AccionTramiteVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccionTramitePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name = "RESPUESTA")
	private String respuesta;

	public AccionTramiteVO() {}

	public AccionTramitePK getId() {
		return this.id;
	}

	public void setId(AccionTramitePK id) {
		this.id = id;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}