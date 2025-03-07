package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the PROPERTIES_CONTEXT database table.
 * 
 */
@Entity
@Table(name = "PROPERTIES_CONTEXT")
@NamedQuery(name = "PropertiesContext.findAll", query = "SELECT p FROM PropertiesContext p")
public class PropertiesContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String identificador;

	// bi-directional many-to-one association to Property
	@OneToMany(mappedBy = "propertiesContext")
	private List<Property> properties;

	public PropertiesContext() {
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<Property> getProperties() {
		return this.properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public Property addProperty(Property property) {
		getProperties().add(property);
		property.setPropertiesContext(this);

		return property;
	}

	public Property removeProperty(Property property) {
		getProperties().remove(property);
		property.setPropertiesContext(null);

		return property;
	}

}