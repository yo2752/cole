package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the CONTRATO_COLEGIADO database table.
 */
@Entity
@Table(name = "CONTRATO_COLEGIADO")
public class ContratoColegiadoVO implements Serializable {

	private static final long serialVersionUID = -6915903363898051875L;

	@EmbeddedId
	private ContratoColegiadoPK id;

	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@ManyToOne
	@JoinColumn(name = "NUM_COLEGIADO", insertable = false, updatable = false)
	private ColegiadoVO colegiado;

	public ContratoColegiadoVO() {}

	public ContratoColegiadoPK getId() {
		return id;
	}

	public void setId(ContratoColegiadoPK id) {
		this.id = id;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public ColegiadoVO getColegiado() {
		return colegiado;
	}

	public void setColegiado(ColegiadoVO colegiado) {
		this.colegiado = colegiado;
	}
}