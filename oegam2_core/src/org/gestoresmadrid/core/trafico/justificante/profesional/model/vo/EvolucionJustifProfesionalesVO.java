package org.gestoresmadrid.core.trafico.justificante.profesional.model.vo;

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
@Table(name = "EVOLUCION_JUSTIF_PROFESIONALES")
public class EvolucionJustifProfesionalesVO implements Serializable {

	private static final long serialVersionUID = 6906589132884890348L;

	@EmbeddedId
	private EvolucionJustifProfesionalesPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name = "ID_JUSTIFICANTE")
	private String idJustificante;
	
	private String comentarios;

	public String getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(String idJustificante) {
		this.idJustificante = idJustificante;
	}

	public EvolucionJustifProfesionalesPK getId() {
		return id;
	}

	public void setId(EvolucionJustifProfesionalesPK id) {
		this.id = id;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
