package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the SEC_GRUPO database table.
 * 
 */
@Entity
@Table(name="SEC_GRUPO")
public class SecGrupoVO implements Serializable {

	private static final long serialVersionUID = 4970768406981954356L;

	@Id
	@SequenceGenerator(name="SEC_GRUPO_IDGRUPO_GENERATOR", sequenceName="SEC_GRUPO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_GRUPO_IDGRUPO_GENERATOR")
	@Column(name="ID_GRUPO")
	private long idGrupo;

	private String nombre;

	//bi-directional many-to-many association to Usuario
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sec_grupo_usuarios", joinColumns = { 
			@JoinColumn(name = "ID_GRUPO", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ID_USUARIO", nullable = false, updatable = false) })
	private List<UsuarioVO> usuarios;

	//bi-directional many-to-many association to SecPermiso
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable(name = "sec_grupo_permiso", joinColumns = { 
			@JoinColumn(name = "ID_GRUPO", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ID_PERMISO", nullable = false, updatable = false) })
	private List<SecPermisoVO> secPermisos;
	
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

	public List<SecPermisoVO> getSecPermisos() {
		return secPermisos;
	}

	public void setSecPermisos(List<SecPermisoVO> secPermisos) {
		this.secPermisos = secPermisos;
	}

}