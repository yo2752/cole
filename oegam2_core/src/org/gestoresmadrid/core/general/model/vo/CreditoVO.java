package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CREDITOS database table.
 */
@Entity
@Table(name = "CREDITOS")
public class CreditoVO implements Serializable {

	private static final long serialVersionUID = 2889531908193273971L;

	@EmbeddedId
	private CreditoPK id;

	private BigDecimal creditos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_CREDITO", referencedColumnName = "TIPO_CREDITO", insertable = false, updatable = false)
	private TipoCreditoVO tipoCreditoVO;

	public CreditoVO() {}

	public BigDecimal getCreditos() {
		return this.creditos;
	}

	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}

	public CreditoPK getId() {
		return id;
	}

	public void setId(CreditoPK id) {
		this.id = id;
	}

	public TipoCreditoVO getTipoCreditoVO() {
		return tipoCreditoVO;
	}

	public void setTipoCreditoVO(TipoCreditoVO tipoCreditoVO) {
		this.tipoCreditoVO = tipoCreditoVO;
	}
}