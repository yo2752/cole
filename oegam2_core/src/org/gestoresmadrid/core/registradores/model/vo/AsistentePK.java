package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the ASISTENTE database table.
 */
@Embeddable
public class AsistentePK implements Serializable {

	private static final long serialVersionUID = 2540698238501555249L;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Column(name = "NIF_CARGO")
	private String nifCargo;

	@Column(name = "CIF_SOCIEDAD")
	private String cifSociedad;

	@Column(name = "CODIGO_CARGO")
	private String codigoCargo;
	
	@Column(name = "ID_REUNION")
	private String idReunion;
	
	public AsistentePK() {}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

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

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(String idReunion) {
		this.idReunion = idReunion;
	}
}