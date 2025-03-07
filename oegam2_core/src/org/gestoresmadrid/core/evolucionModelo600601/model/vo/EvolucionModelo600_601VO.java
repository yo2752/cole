package org.gestoresmadrid.core.evolucionModelo600601.model.vo;

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
@Table(name="EVOLUCION_MODELO_600_601")
public class EvolucionModelo600_601VO implements Serializable{

	private static final long serialVersionUID = -6675724226402162443L;

	@EmbeddedId 
	private EvolucionModelo600_601PK id;
	
	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public EvolucionModelo600_601PK getId() {
		return id;
	}

	public void setId(EvolucionModelo600_601PK id) {
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
