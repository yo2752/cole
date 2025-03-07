package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the OPERACION_REGISTRO database table.
 */
@Embeddable
public class OperacionRegistroPK implements Serializable {

	private static final long serialVersionUID = 1959717326809620868L;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	public OperacionRegistroPK() {}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
}