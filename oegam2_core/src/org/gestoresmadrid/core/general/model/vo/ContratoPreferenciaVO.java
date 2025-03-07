package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the CONTRATO_PREFERENCIAS database table.
 */
@Entity
@Table(name = "CONTRATO_PREFERENCIAS")
public class ContratoPreferenciaVO implements Serializable {

	private static final long serialVersionUID = 1726097438416727680L;

	@Id
	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ORDEN_DOCBASE_YB")
	private BigDecimal ordenDocbaseYb;

	// bi-directional many-to-one association to Contrato
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "OTROS_DESTINATARIOS_SS")
	private String otrosDestinatariosSS;

	public ContratoPreferenciaVO() {}

	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getOrdenDocbaseYb() {
		return this.ordenDocbaseYb;
	}

	public void setOrdenDocbaseYb(BigDecimal ordenDocbaseYb) {
		this.ordenDocbaseYb = ordenDocbaseYb;
	}

	public ContratoVO getContrato() {
		return this.contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public String getOtrosDestinatariosSS() {
		return otrosDestinatariosSS;
	}

	public void setOtrosDestinatariosSS(String otrosDestinatariosSS) {
		this.otrosDestinatariosSS = otrosDestinatariosSS;
	}
}