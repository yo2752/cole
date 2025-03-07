package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SEC_USUARIO_PERMISO")
public class SecUsuarioPermisoVO implements Serializable{

	private static final long serialVersionUID = 1063823637061524831L;

	@EmbeddedId 
	private SecUsuarioPermisoPK id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERMISO",insertable=false,updatable=false)
	private SecPermisoVO secPermiso;

	public SecUsuarioPermisoPK getId() {
		return id;
	}

	public void setId(SecUsuarioPermisoPK id) {
		this.id = id;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public SecPermisoVO getSecPermiso() {
		return secPermiso;
	}

	public void setSecPermiso(SecPermisoVO secPermiso) {
		this.secPermiso = secPermiso;
	}
	
}
