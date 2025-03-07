package org.gestoresmadrid.core.properties.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PROPERTIES")
public class PropertiesVO implements Serializable{

	private static final long serialVersionUID = 8144989486636352997L;

	@Id
	@Column(name="ID")
	private long id;

	@Column(name="COMENTARIO")
	private String comentario;

	@Column(name="NOMBRE")
	private String nombre;

	@Column(name="VALOR")
	private String valor;

	@Column(name="ID_CONTEXT")
	private String idContext;

	//bi-directional many-to-one association to PropertiesContext
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_CONTEXT", insertable=false, updatable=false)
	private PropertiesContextVO propertiesContext;

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

	public PropertiesContextVO getPropertiesContext() {
		return propertiesContext;
	}

	public void setPropertiesContext(PropertiesContextVO propertiesContext) {
		this.propertiesContext = propertiesContext;
	}

}