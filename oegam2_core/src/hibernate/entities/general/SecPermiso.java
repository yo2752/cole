package hibernate.entities.general;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.List;

/**
 * The persistent class for the SEC_PERMISO database table.
 * 
 */
@Entity
@Table(name = "SEC_PERMISO")
@NamedQuery(name = "SecPermiso.findAll", query = "SELECT s FROM SecPermiso s")
public class SecPermiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEC_PERMISO_IDPERMISO_GENERATOR", sequenceName = "SEC_PERMISO_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PERMISO_IDPERMISO_GENERATOR")
	@Column(name = "ID_PERMISO")
	private long idPermiso;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "INACTIVO")
	@Type(type = "yes_no")
	private Boolean inactivo;

	//bi-directional many-to-many association to SecGrupo
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "secPermisos")
	private List<SecGrupo> secGrupos;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "secPermisos")
	private List<Usuario> usuarios;

	public SecPermiso() {
	}

	public long getIdPermiso() {
		return this.idPermiso;
	}

	public void setIdPermiso(long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getInactivo() {
		return inactivo;
	}

	public void setInactivo(Boolean inactivo) {
		this.inactivo = inactivo;
	}

	public List<SecGrupo> getSecGrupos() {
		return this.secGrupos;
	}

	public void setSecGrupos(List<SecGrupo> secGrupos) {
		this.secGrupos = secGrupos;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}