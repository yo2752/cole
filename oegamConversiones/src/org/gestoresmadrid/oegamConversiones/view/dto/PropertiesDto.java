package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

public class PropertiesDto implements Serializable{

	private static final long serialVersionUID = 250237820228992057L;

	private long id;

	private String comentario;

	private String nombre;

	private String valor;

	private String idContext;
	
	private PropertiesContextDto propertiesContext;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getIdContext() {
		return idContext;
	}

	public void setIdContext(String idContext) {
		this.idContext = idContext;
	}

	public PropertiesContextDto getPropertiesContext() {
		return propertiesContext;
	}

	public void setPropertiesContext(PropertiesContextDto propertiesContext) {
		this.propertiesContext = propertiesContext;
	}
	
}
