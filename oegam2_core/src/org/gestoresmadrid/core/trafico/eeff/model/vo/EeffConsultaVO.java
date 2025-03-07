package org.gestoresmadrid.core.trafico.eeff.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EEFF_CONSULTA")
public class EeffConsultaVO extends EeffVO implements Serializable {

	private static final long serialVersionUID = 6291116625703183651L;
	@Id
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="NUM_EXPEDIENTE_TRAMITE")
	private BigDecimal numExpedienteTramite;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumExpedienteTramite() {
		return numExpedienteTramite;
	}

	public void setNumExpedienteTramite(BigDecimal numExpedienteTramite) {
		this.numExpedienteTramite = numExpedienteTramite;
	}
	
	
	
}
