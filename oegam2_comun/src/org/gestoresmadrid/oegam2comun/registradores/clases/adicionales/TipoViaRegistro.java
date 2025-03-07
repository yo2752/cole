package org.gestoresmadrid.oegam2comun.registradores.clases.adicionales;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Representa los tipos de vía en la codificación requerida por el registro de bienes muebles
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoViaRegistro {

	// Identificador unívoco del recurso
	private Integer id;
	// Fecha de la creación
	private Date createdOn;
	// Fecha de modificación
	private Date modifiedOn;
	private String name;
	private String sinonimo;
	private String key;
	private String description;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinonimo() {
		return sinonimo;
	}
	public void setSinonimo(String sinonimo) {
		this.sinonimo = sinonimo;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "TipoViaRegistro [id=" + id + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", name=" + name
				+ ", sinonimo=" + sinonimo + ", key=" + key + ", description="
				+ description + "]";
	}

}
