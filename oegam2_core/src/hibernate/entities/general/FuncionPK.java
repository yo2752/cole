package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FUNCION database table.
 * 
 */
@Embeddable
public class FuncionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CODIGO_APLICACION")
	private String codigoAplicacion;

	@Column(name="CODIGO_FUNCION")
	private String codigoFuncion;

	public FuncionPK() {
	}
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FuncionPK)) {
			return false;
		}
		FuncionPK castOther = (FuncionPK)other;
		return 
			this.codigoAplicacion.equals(castOther.codigoAplicacion)
			&& this.codigoFuncion.equals(castOther.codigoFuncion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoAplicacion.hashCode();
		hash = hash * prime + this.codigoFuncion.hashCode();
		
		return hash;
	}
}