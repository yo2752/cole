package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the SOCIEDAD_CARGO database table.
 */
@Embeddable
public class SociedadCargoPK implements Serializable {

	private static final long serialVersionUID = -2746847930957106496L;

	@Column(name = "NIF_CARGO")
	private String nifCargo;

	@Column(name = "CIF_SOCIEDAD")
	private String cifSociedad;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "CODIGO_CARGO")
	private String codigoCargo;

	public SociedadCargoPK() {}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getCifSociedad() {
		return cifSociedad;
	}

	public void setCifSociedad(String cifSociedad) {
		this.cifSociedad = cifSociedad;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
}