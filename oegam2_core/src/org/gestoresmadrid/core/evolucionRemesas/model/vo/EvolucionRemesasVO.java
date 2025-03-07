package org.gestoresmadrid.core.evolucionRemesas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="EVOLUCION_REMESAS")
public class EvolucionRemesasVO implements Serializable{
	
	private static final long serialVersionUID = -277233016549994427L;

	@EmbeddedId
	private EvolucionRemesasPK id;
	
	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public EvolucionRemesasPK getId() {
		return id;
	}

	public void setId(EvolucionRemesasPK id) {
		this.id = id;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
	
	
}
