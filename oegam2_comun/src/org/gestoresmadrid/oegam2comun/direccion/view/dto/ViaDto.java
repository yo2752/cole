package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ViaDto implements Serializable {

	private static final long serialVersionUID = -4172281708590319735L;

	private BigDecimal idVia;

	private String idMunicipio;

	private String via;

	private String idProvincia;

	private String tipoViaIne;

	public BigDecimal getIdVia() {
		return idVia;
	}

	public void setIdVia(BigDecimal idVia) {
		this.idVia = idVia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getTipoViaIne() {
		return tipoViaIne;
	}

	public void setTipoViaIne(String tipoViaIne) {
		this.tipoViaIne = tipoViaIne;
	}
}