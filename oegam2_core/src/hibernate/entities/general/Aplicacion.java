package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the APLICACION database table.
 * 
 */
@Entity
public class Aplicacion implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ALL = "ALL";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CODIGO_APLICACION")
	private String codigoAplicacion;

	@Column(name="\"ALIAS\"")
	private String alias;

	@Column(name="DESC_APLICACION")
	private String descAplicacion;

	//bi-directional many-to-many association to Url
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="APLICACION_URL",
			joinColumns={
					@JoinColumn(name="CODIGO_APLICACION")
			},
			inverseJoinColumns={
					@JoinColumn(name="CODIGO_URL")
			}
	)
	private List<Url> urls;

	//bi-directional many-to-many association to Contrato
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="CONTRATO_APLICACION",
			joinColumns={
					@JoinColumn(name="CODIGO_APLICACION")
			},
			inverseJoinColumns={
					@JoinColumn(name="ID_CONTRATO")
			}
	)
	private List<Contrato> contratos;

	@OneToMany(mappedBy="aplicacion")
	private List<Funcion> funciones;

	public Aplicacion() {
	}

	public String getCodigoAplicacion() {
		return this.codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescAplicacion() {
		return this.descAplicacion;
	}

	public void setDescAplicacion(String descAplicacion) {
		this.descAplicacion = descAplicacion;
	}

	public List<Url> getUrls() {
		return this.urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	public List<Contrato> getContratos() {
		return this.contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}

}