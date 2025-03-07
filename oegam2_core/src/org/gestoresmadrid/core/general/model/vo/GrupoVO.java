package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the GRUPOS database table.
 */
@Entity
@Table(name = "GRUPOS")
public class GrupoVO implements Serializable {

	private static final long serialVersionUID = 4046707480803066702L;

	@Id
	@Column(name = "ID_GRUPO")
	private String idGrupo;

	@Column(name = "DESC_GRUPO")
	private String descGrupo;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "TIPO")
	private String tipo;

	// bi-directional many-to-one association to UsuarioVO
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
	private Set<UsuarioVO> usuarios;

	// bi-directional many-to-one association to DatosSensiblesBastidorVO
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
	private Set<DatosSensiblesBastidorVO> datosSensiblesBastidors;

	// bi-directional many-to-one association to DatosSensiblesMatriculaVO
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
	private Set<DatosSensiblesMatriculaVO> datosSensiblesMatriculas;

	// bi-directional many-to-one association to DatosSensiblesNifVO
	@OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
	private Set<DatosSensiblesNifVO> datosSensiblesNifs;

	public GrupoVO() {}

	public String getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getDescGrupo() {
		return this.descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Set<UsuarioVO> getUsuarios() {
		return usuarios;
	}

	public List<UsuarioVO> getUsuariosAsList() {
		// Map from Set to List
		List<UsuarioVO> lista = new ArrayList<UsuarioVO>();
		if (usuarios != null) {
			lista.addAll(usuarios);
		}
		return lista;
	}

	public UsuarioVO getFirstElementUsuario() {
		ListsOperator<UsuarioVO> listOperator = new ListsOperator<UsuarioVO>();
		return listOperator.getFirstElement(getUsuarios());
	}

	public void setUsuarios(Set<UsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}

	public void setUsuarios(List<UsuarioVO> usuarios) {
		if (usuarios == null) {
			this.usuarios = null;
		} else {
			// Map from List to Set
			Set<UsuarioVO> set = new HashSet<UsuarioVO>();
			set.addAll(usuarios);
			this.usuarios = set;
		}
	}

	public Set<DatosSensiblesBastidorVO> getDatosSensiblesBastidors() {
		return datosSensiblesBastidors;
	}

	public List<DatosSensiblesBastidorVO> getDatosSensiblesBastidorsAsList() {
		// Map from Set to List
		List<DatosSensiblesBastidorVO> lista = new ArrayList<DatosSensiblesBastidorVO>();
		if (datosSensiblesBastidors != null) {
			lista.addAll(datosSensiblesBastidors);
		}
		return lista;
	}

	public DatosSensiblesBastidorVO getFirstElementDatosSensiblesBastidor() {
		ListsOperator<DatosSensiblesBastidorVO> listOperator = new ListsOperator<DatosSensiblesBastidorVO>();
		return listOperator.getFirstElement(getDatosSensiblesBastidors());
	}

	public void setDatosSensiblesBastidors(Set<DatosSensiblesBastidorVO> datosSensiblesBastidors) {
		this.datosSensiblesBastidors = datosSensiblesBastidors;
	}

	public void setDatosSensiblesBastidors(List<DatosSensiblesBastidorVO> datosSensiblesBastidors) {
		if (datosSensiblesBastidors == null) {
			this.datosSensiblesBastidors = null;
		} else {
			// Map from List to Set
			Set<DatosSensiblesBastidorVO> set = new HashSet<DatosSensiblesBastidorVO>();
			set.addAll(datosSensiblesBastidors);
			this.datosSensiblesBastidors = set;
		}
	}

	public Set<DatosSensiblesMatriculaVO> getDatosSensiblesMatriculas() {
		return datosSensiblesMatriculas;
	}

	public List<DatosSensiblesMatriculaVO> getDatosSensiblesMatriculasAsList() {
		// Map from Set to List
		List<DatosSensiblesMatriculaVO> lista = new ArrayList<DatosSensiblesMatriculaVO>();
		if (datosSensiblesBastidors != null) {
			lista.addAll(datosSensiblesMatriculas);
		}
		return lista;
	}

	public DatosSensiblesMatriculaVO getFirstElementDatosSensiblesMatricula() {
		ListsOperator<DatosSensiblesMatriculaVO> listOperator = new ListsOperator<DatosSensiblesMatriculaVO>();
		return listOperator.getFirstElement(getDatosSensiblesMatriculas());
	}

	public void setDatosSensiblesMatriculas(Set<DatosSensiblesMatriculaVO> datosSensiblesMatriculas) {
		this.datosSensiblesMatriculas = datosSensiblesMatriculas;
	}

	public void setDatosSensiblesMatriculas(List<DatosSensiblesMatriculaVO> datosSensiblesMatriculas) {
		if (datosSensiblesMatriculas == null) {
			this.datosSensiblesMatriculas = null;
		} else {
			// Map from List to Set
			Set<DatosSensiblesMatriculaVO> set = new HashSet<DatosSensiblesMatriculaVO>();
			set.addAll(datosSensiblesMatriculas);
			this.datosSensiblesMatriculas = set;
		}
	}

	public Set<DatosSensiblesNifVO> getDatosSensiblesNifs() {
		return datosSensiblesNifs;
	}

	public List<DatosSensiblesNifVO> getDatosSensiblesNifsAsList() {
		// Map from Set to List
		List<DatosSensiblesNifVO> lista = new ArrayList<DatosSensiblesNifVO>();
		if (datosSensiblesNifs != null) {
			lista.addAll(datosSensiblesNifs);
		}
		return lista;
	}

	public DatosSensiblesNifVO getFirstElementDatosSensible() {
		ListsOperator<DatosSensiblesNifVO> listOperator = new ListsOperator<DatosSensiblesNifVO>();
		return listOperator.getFirstElement(getDatosSensiblesNifs());
	}

	public void setDatosSensiblesNifs(Set<DatosSensiblesNifVO> datosSensiblesNifs) {
		this.datosSensiblesNifs = datosSensiblesNifs;
	}

	public void setDatosSensiblesNifs(List<DatosSensiblesNifVO> datosSensiblesNifs) {
		if (datosSensiblesNifs == null) {
			this.datosSensiblesNifs = null;
		} else {
			// Map from List to Set
			Set<DatosSensiblesNifVO> set = new HashSet<DatosSensiblesNifVO>();
			set.addAll(datosSensiblesNifs);
			this.datosSensiblesNifs = set;
		}
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}