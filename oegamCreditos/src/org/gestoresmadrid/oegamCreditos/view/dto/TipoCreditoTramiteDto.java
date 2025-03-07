package org.gestoresmadrid.oegamCreditos.view.dto;

import java.io.Serializable;

public class TipoCreditoTramiteDto implements Serializable {

	private static final long serialVersionUID = -7884411169235409216L;

	private String tipoTramite;

	private String tipoCredito;

	public TipoCreditoTramiteDto() {}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoCredito() {
		return this.tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}
}