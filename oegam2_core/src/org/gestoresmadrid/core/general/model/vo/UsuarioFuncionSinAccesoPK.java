package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the USUARIO_FUNCION_SIN_ACCESO database table.
 */
@Embeddable
public class UsuarioFuncionSinAccesoPK implements Serializable {

	private static final long serialVersionUID = 8950575908418828215L;

	@Column(name = "CODIGO_APLICACION")
	private String codigoAplicacion;

	@Column(name = "CODIGO_FUNCION")
	private String codigoFuncion;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	public UsuarioFuncionSinAccesoPK() {}

	public String getCodigoAplicacion() {
		return this.codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public String getCodigoFuncion() {
		return this.codigoFuncion;
	}

	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}

	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuarioFuncionSinAccesoPK)) {
			return false;
		}
		UsuarioFuncionSinAccesoPK castOther = (UsuarioFuncionSinAccesoPK) other;
		return this.codigoAplicacion.equals(castOther.codigoAplicacion) && this.codigoFuncion.equals(castOther.codigoFuncion) && (this.idContrato == castOther.idContrato)
				&& (this.idUsuario == castOther.idUsuario);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoAplicacion.hashCode();
		hash = hash * prime + this.codigoFuncion.hashCode();
		hash = hash * prime + ((int) (this.idContrato ^ (this.idContrato >>> 32)));
		hash = hash * prime + ((int) (this.idUsuario ^ (this.idUsuario >>> 32)));

		return hash;
	}
}