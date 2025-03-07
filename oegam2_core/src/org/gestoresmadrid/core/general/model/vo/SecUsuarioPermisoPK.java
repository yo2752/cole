package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SecUsuarioPermisoPK implements Serializable{

	private static final long serialVersionUID = -3005299251764612903L;

	@Column(name="ID_PERMISO")
	private Long idPermiso; 
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
