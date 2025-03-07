package org.gestoresmadrid.oegam2comun.registradores.clases.adicionales;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Representa los paises en la codificación requerida por el registro de bienes
 * muebles
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaisRegistro {

	// Identificador unívoco del recurso
	private Integer id;
	// Fecha de la creación
	private Date createdOn;
	// Fecha de modificación
	private Date modifiedOn;
	// Código del pais
	private String countryCode;
	private String name;
	private String description;

	private String key;
	private String sinonimo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSinonimo() {
		return sinonimo;
	}

	public void setSinonimo(String sinonimo) {
		this.sinonimo = sinonimo;
	}

	@Override
	public String toString() {
		return "CorpmeCountry [id=" + id + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", countryCode=" + countryCode
				+ ", name=" + name + ", description=" + description + ", key="
				+ key + ", sinonimo=" + sinonimo + "]";
	}

}
