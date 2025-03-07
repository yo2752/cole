package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the EVOLUCION_TRAMITE_REGISTRO database table.
 */
@Entity
@Table(name = "EVOLUCION_TRAMITE_REGISTRO")
public class EvolucionTramiteRegistroVO implements Serializable {

	private static final long serialVersionUID = 6498665751075289412L;

	@EmbeddedId
	private EvolucionTramiteRegistroPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRAMITE_REGISTRO", insertable = false, updatable = false)
	private TramiteRegistroVO tramiteRegistro;

	public EvolucionTramiteRegistroVO() {}

	public EvolucionTramiteRegistroPK getId() {
		return this.id;
	}

	public void setId(EvolucionTramiteRegistroPK id) {
		this.id = id;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public TramiteRegistroVO getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroVO tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}
}