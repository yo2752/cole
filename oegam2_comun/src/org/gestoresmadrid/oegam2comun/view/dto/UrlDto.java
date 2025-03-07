package org.gestoresmadrid.oegam2comun.view.dto;

import java.io.Serializable;

public class UrlDto implements Serializable {

	private static final long serialVersionUID = 604033396408677350L;

	private String codigoUrl;

	private String patronUrl;

	private String orden;

	public String getCodigoUrl() {
		return codigoUrl;
	}

	public void setCodigoUrl(String codigoUrl) {
		this.codigoUrl = codigoUrl;
	}

	public String getPatronUrl() {
		return patronUrl;
	}

	public void setPatronUrl(String patronUrl) {
		this.patronUrl = patronUrl;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
}