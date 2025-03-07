package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcDocumentoLicenciaDto implements Serializable {

	private static final long serialVersionUID = 4702030742221860969L;

	private Long idDocumentoLicencia;

	private BigDecimal numExpediente;

	private String tipoDocumento;

	private Long idContrato;

	public Long getIdDocumentoLicencia() {
		return idDocumentoLicencia;
	}

	public void setIdDocumentoLicencia(Long idDocumentoLicencia) {
		this.idDocumentoLicencia = idDocumentoLicencia;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}