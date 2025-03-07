package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContratoPreferenciaDto implements Serializable{

	private static final long serialVersionUID = -5235061416487100552L;

	private Long idContrato;

	private BigDecimal ordenDocbaseYb;

	private ContratoDto contrato;
	
	private String otrosDestinatariosSS;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getOrdenDocbaseYb() {
		return ordenDocbaseYb;
	}

	public void setOrdenDocbaseYb(BigDecimal ordenDocbaseYb) {
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