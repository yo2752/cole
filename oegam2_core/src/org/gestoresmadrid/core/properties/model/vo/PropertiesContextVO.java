package org.gestoresmadrid.core.properties.model.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PROPERTIES_CONTEXT")
public class PropertiesContextVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDENTIFICADOR")
	private String identificador;

	//bi-directional many-to-one association to Property
	@OneToMany(mappedBy="propertiesContext")
	private List<PropertiesVO> properties;

	public PropertiesContextVO() {
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<PropertiesVO> getProperties() {
		return this.properties;
	}

	public void setProperties(List<PropertiesVO> properties) {
		this.properties = properties;
	}

}