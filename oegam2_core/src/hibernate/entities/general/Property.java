package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the PROPERTIES database table.
 * 
 */
@Entity
@Table(name = "PROPERTIES")
@NamedQuery(name = "Property.findAll", query = "SELECT p FROM Property p")
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String comentario;

	private String nombre;

	private String valor;

	// bi-directional many-to-one association to PropertiesContext
	@ManyToOne
	@JoinColumn(name = "ID_CONTEXT")
	private PropertiesContext propertiesContext;

	public Property() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public PropertiesContext getPropertiesContext() {
		return this.propertiesContext;
	}

	public void setPropertiesContext(PropertiesContext propertiesContext) {
		this.propertiesContext = propertiesContext;
	}

}