package org.gestoresmadrid.core.proceso.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the PROCESO database table.
 */
@Embeddable
public class ProcesoPK implements Serializable {

	private static final long serialVersionUID = 1322141491413048202L;

	@Column(name = "NODO")
	private String nodo;

	@Column(name = "PROCESO")
	private String proceso;

	public ProcesoPK() {}

	public String getNodo() {
		return this.nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProcesoPK)) {
			return false;
		}
		ProcesoPK castOther = (ProcesoPK) other;
		return this.nodo.equals(castOther.nodo) && this.proceso.equals(castOther.proceso);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nodo.hashCode();
		hash = hash * prime + this.proceso.hashCode();

		return hash;
	}
}