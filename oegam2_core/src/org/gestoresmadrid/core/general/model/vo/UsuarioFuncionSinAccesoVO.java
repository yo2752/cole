package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USUARIO_FUNCION_SIN_ACCESO database table.
 */
@Entity
@Table(name = "USUARIO_FUNCION_SIN_ACCESO")
public class UsuarioFuncionSinAccesoVO implements Serializable {

	private static final long serialVersionUID = 6175228332132176752L;

	@EmbeddedId
	private UsuarioFuncionSinAccesoPK id;

	public UsuarioFuncionSinAccesoVO() {}

	public UsuarioFuncionSinAccesoPK getId() {
		return this.id;
	}

	public void setId(UsuarioFuncionSinAccesoPK id) {
		this.id = id;
	}
}