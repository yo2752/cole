package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TIPO_CREDITO_TRAMITE database table.
 */
@Entity
@Table(name = "TIPO_CREDITO_TRAMITE")
public class TipoCreditoTramiteVO implements Serializable {

	private static final long serialVersionUID = -7884411169235409216L;

	@Id
	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "TIPO_CREDITO")
	private String tipoCredito;

	public TipoCreditoTramiteVO() {}

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