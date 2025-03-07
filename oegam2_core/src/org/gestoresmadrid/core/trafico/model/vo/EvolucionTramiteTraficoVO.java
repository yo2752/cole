package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the EVOLUCION_TRAMITE_TRAFICO database table.
 */
@Entity
@Table(name = "EVOLUCION_TRAMITE_TRAFICO")
public class EvolucionTramiteTraficoVO implements Serializable {

	private static final long serialVersionUID = -6778252347232585842L;

	@EmbeddedId
	private EvolucionTramiteTraficoPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	public EvolucionTramiteTraficoVO() {}

	public EvolucionTramiteTraficoPK getId() {
		return this.id;
	}

	public void setId(EvolucionTramiteTraficoPK id) {
		this.id = id;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

}