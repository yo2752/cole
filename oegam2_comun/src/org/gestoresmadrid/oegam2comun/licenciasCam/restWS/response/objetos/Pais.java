
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class Pais implements Serializable {

	private static final long serialVersionUID = -4107628231791305085L;

	private Integer codpais;

	private String nompais;

	private Integer orden;

	public Integer getCodpais() {
		return codpais;
	}

	public void setCodpais(Integer codpais) {
		this.codpais = codpais;
	}

	public String getNompais() {
		return nompais;
	}

	public void setNompais(String nompais) {
		this.nompais = nompais;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
