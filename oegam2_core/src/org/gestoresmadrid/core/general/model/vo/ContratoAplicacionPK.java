package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the CONTRATO_APLICACION database table.
 */
@Embeddable
public class ContratoAplicacionPK implements Serializable {

	private static final long serialVersionUID = 2393835003615680815L;

	@Column(name = "CODIGO_APLICACION")
	private String codigoAplicacion;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	public ContratoAplicacionPK() {}

	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}