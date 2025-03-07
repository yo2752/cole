package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

public class TramiteTrafFacturacionPK implements Serializable {

	private static final long serialVersionUID = -3646677171480282928L;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	public TramiteTrafFacturacionPK() {}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getCodigoTasa() {
		return this.codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}
}
