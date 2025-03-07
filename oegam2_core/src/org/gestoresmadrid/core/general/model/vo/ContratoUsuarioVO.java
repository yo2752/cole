package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the CONTRATO_USUARIO database table.
 */
@Entity
@Table(name = "CONTRATO_USUARIO")
public class ContratoUsuarioVO implements Serializable {

	private static final long serialVersionUID = -6915903363898051875L;

	@EmbeddedId
	private ContratoUsuarioPK id;

	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	
	@Column(name = "ESTADO_USU_CONT")
	private BigDecimal estadoUsuarioContrato;
	
		
	
	public ContratoUsuarioVO() {}

	public ContratoUsuarioPK getId() {
		return id;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public void setId(ContratoUsuarioPK id) {
		this.id = id;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public BigDecimal getEstadoUsuarioContrato() {
		return estadoUsuarioContrato;
	}

	public void setEstadoUsuarioContrato(BigDecimal estadoUsuarioContrato) {
		this.estadoUsuarioContrato = estadoUsuarioContrato;
	}

	

	

	

	
}