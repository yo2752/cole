package hibernate.entities.trafico;

import hibernate.entities.general.Usuario;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the EVOLUCION_TRAMITE_TRAFICO database table.
 * 
 */
@Entity
@Table(name = "EVOLUCION_TRAMITE_TRAFICO")
public class EvolucionTramiteTrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvolucionTramiteTraficoPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTrafico tramiteTrafico;

	public EvolucionTramiteTrafico() {
	}

	public EvolucionTramiteTraficoPK getId() {
		return this.id;
	}

	public void setId(EvolucionTramiteTraficoPK id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

}