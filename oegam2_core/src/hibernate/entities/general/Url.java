package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;


/**
 * The persistent class for the URL database table.
 * 
 */
@Entity
public class Url implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CODIGO_URL")
	private String codigoUrl;

	@Column(name="PATRON_URL")
	private String patronUrl;

	//bi-directional many-to-many association to Aplicacion
	@ManyToMany(mappedBy="urls")
	private List<Aplicacion> aplicaciones;

	@ManyToMany(mappedBy="urls", fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Funcion> funciones;

	@Column(name="ORDEN")
	private String orden;

	public Url() {
	}

	public String getCodigoUrl() {
		return this.codigoUrl;
	}

	public void setCodigoUrl(String codigoUrl) {
		this.codigoUrl = codigoUrl;
	}

	public String getPatronUrl() {
		return this.patronUrl;
	}

	public void setPatronUrl(String patronUrl) {
		this.patronUrl = patronUrl;
	}

	public List<Aplicacion> getAplicaciones() {
		return this.aplicaciones;
	}

	public void setAplicaciones(List<Aplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}