package org.gestoresmadrid.core.mandato.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "EVOLUCION_MANDATO")
public class EvolucionMandatoVO implements Serializable {

	private static final long serialVersionUID = -2035043676557639037L;

	@EmbeddedId
	private EvolucionMandatoPK id;

	@Column(name = "CODIGO_MANDATO")
	private String codigoMandato;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	public EvolucionMandatoPK getId() {
		return id;
	}

	public void setId(EvolucionMandatoPK id) {
		this.id = id;
	}

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
}
