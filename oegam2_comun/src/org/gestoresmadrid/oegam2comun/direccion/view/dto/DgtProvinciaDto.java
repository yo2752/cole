package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class DgtProvinciaDto implements Serializable {

	private static final long serialVersionUID = 3035980423913539359L;

	private String idDgtProvincia;

	private String prefijoTlf;

	private String provincia;

	private String sigla;

	public String getIdDgtProvincia() {
		return idDgtProvincia;
	}

	public void setIdDgtProvincia(String idDgtProvincia) {
		this.idDgtProvincia = idDgtProvincia;
	}

	public String getPrefijoTlf() {
		return prefijoTlf;
	}

	public void setPrefijoTlf(String prefijoTlf) {
		this.prefijoTlf = prefijoTlf;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}