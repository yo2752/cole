package hibernate.entities.general;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the SEC_GRUPO database table.
 * 
 */
@Entity
@Table(name = "SEC_GRUPO")
@NamedQuery(name = "SecGrupo.findAll", query = "SELECT s FROM SecGrupo s")
public class SecGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEC_GRUPO_IDGRUPO_GENERATOR", sequenceName = "SEC_GRUPO_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GRUPO_IDGRUPO_GENERATOR")
	@Column(name="ID_GRUPO")
	private long idGrupo;

	private String nombre;

	//bi-directional many-to-many association to Usuario
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sec_grupo_usuarios", catalog = "oegam2", joinColumns = {
			@JoinColumn(name = "ID_GRUPO", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "ID_USUARIO", nullable = false, updatable = false) })
	private List<Usuario> usuarios;

	//bi-directional many-to-many association to SecPermiso
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sec_grupo_permiso", catalog = "oegam2", joinColumns = {
			@JoinColumn(name = "ID_GRUPO", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "ID_PERMISO", nullable = false, updatable = false) })
	private List<SecPermiso> secPermisos;

	public SecGrupo() {
	}

	public long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<SecPermiso> getSecPermisos() {
		return this.secPermisos;
	}

	public void setSecPermisos(List<SecPermiso> secPermisos) {
		this.secPermisos = secPermisos;
	}

}