package hibernate.entities.general;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * The persistent class for the APLICACION_URL database table.
 * 
 */
@Entity
@Table(name = "APLICACION_URL")
public class AplicacionUrl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDENTIFICADOR")
	private String identificador;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CODIGO_APLICACION", insertable = false, updatable = false)
	private Aplicacion aplicacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumns({
		@JoinColumn(name = "CODIGO_FUNCION", referencedColumnName = "CODIGO_FUNCION"),
		@JoinColumn(name = "CODIGO_APLICACION", referencedColumnName = "CODIGO_APLICACION")
	})
	@NotFound(action=NotFoundAction.IGNORE)
	private Funcion funcion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CODIGO_URL", insertable = false, updatable = false)
	private Url url;

	public AplicacionUrl() {
	}

	public Aplicacion getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public Funcion getFuncion() {
		return this.funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Url getUrl() {
		return this.url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

}