package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "EVOLUCION_PRESENTACION_JPT")
public class EvolucionPresentacionJptVO implements Serializable {

	private static final long serialVersionUID = 7049162281882657233L;

	@EmbeddedId
	private EvolucionPresentacionJptPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	@Column(name="JEFATURA")
	private String jefatura;

	public EvolucionPresentacionJptVO() {
		super();
	}

	public EvolucionPresentacionJptPK getId() {
		return this.id;
	}

	public void setId(EvolucionPresentacionJptPK id) {
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

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

}