package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the MUNICIPIO database table.
 * 
 */
@Embeddable
public class UnidadPoblacionalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_PROVINCIA", insertable=false, updatable=false)
	private String idProvincia;
	
	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;
	
	@Column(name="ID_UNIDAD_POBLACIONAL")
	private String idUnidadPoblacional;

    public UnidadPoblacionalPK() {
    }

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdUnidadPoblacional() {
		return idUnidadPoblacional;
	}

	public void setIdUnidadPoblacional(String idUnidadPoblacional) {
		this.idUnidadPoblacional = idUnidadPoblacional;
	}
}