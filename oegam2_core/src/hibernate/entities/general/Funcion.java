package hibernate.entities.general;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


/**
 * The persistent class for the FUNCION database table.
 * 
 */
@Entity
public class Funcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FuncionPK id;

	@Column(name="DESC_FUNCION")
	private String descFuncion;

	//bi-directional many-to-one association to Aplicacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_APLICACION", insertable=false, updatable=false)
	private Aplicacion aplicacion;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="APLICACION_URL",
			joinColumns={
					@JoinColumn(name="CODIGO_APLICACION"),
					@JoinColumn(name="CODIGO_FUNCION")
			},
			inverseJoinColumns={
					@JoinColumn(name="CODIGO_URL")
			}
	)
	private List<Url> urls;

	public Funcion() {
	}

	public FuncionPK getId() {
		return this.id;
	}

	public void setId(FuncionPK id) {
		this.id = id;
	}

	public String getDescFuncion() {
		return this.descFuncion;
	}

	public void setDescFuncion(String descFuncion) {
		this.descFuncion = descFuncion;
	}

	public Aplicacion getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

}