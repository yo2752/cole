package org.gestoresmadrid.core.importacionFichero.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_CONTRATO_IMP")
public class ControlContratoImpVO implements Serializable {

	private static final long serialVersionUID = -2644945076113422926L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "TIPO")
	private String tipo;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}
