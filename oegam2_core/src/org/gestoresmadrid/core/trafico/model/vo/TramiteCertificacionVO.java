package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRAMITE_CERTIFICACION")
public class TramiteCertificacionVO implements Serializable{

	private static final long serialVersionUID = 2930128821990960680L;

	@Id
	@Column(name="HASH")
	private String hash;

	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name="VERSION")
	private String version;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}