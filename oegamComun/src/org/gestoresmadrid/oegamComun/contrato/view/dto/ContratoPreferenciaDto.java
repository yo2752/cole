package org.gestoresmadrid.oegamComun.contrato.view.dto;

import java.io.Serializable;

public class ContratoPreferenciaDto implements Serializable {

	private static final long serialVersionUID = -5235061416487100552L;

	private Long idContrato;

	private String ordenDocbaseYb;

	private ContratoDto contrato;

	private String otrosDestinatariosSS;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getOrdenDocbaseYb() {
		return ordenDocbaseYb;
	}

	public void setOrdenDocbaseYb(String ordenDocbaseYb) {
		this.ordenDocbaseYb = ordenDocbaseYb;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public String getOtrosDestinatariosSS() {
		return otrosDestinatariosSS;
	}

	public void setOtrosDestinatariosSS(String otrosDestinatariosSS) {
		this.otrosDestinatariosSS = otrosDestinatariosSS;
	}

}