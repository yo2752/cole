package org.gestoresmadrid.oegam2comun.administracion.view.dto;

import java.io.Serializable;

public class CertificateDto implements Serializable {

	private static final long serialVersionUID = 3156925470099791895L;

	private String alias;

	private String validoDesde;

	private String validoHasta;

	private Long diasValidezRestantes;

	private String info;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getValidoDesde() {
		return validoDesde;
	}

	public void setValidoDesde(String validoDesde) {
		this.validoDesde = validoDesde;
	}

	public String getValidoHasta() {
		return validoHasta;
	}

	public void setValidoHasta(String validoHasta) {
		this.validoHasta = validoHasta;
	}

	public Long getDiasValidezRestantes() {
		return diasValidezRestantes;
	}

	public void setDiasValidezRestantes(Long diasValidezRestantes) {
		this.diasValidezRestantes = diasValidezRestantes;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
