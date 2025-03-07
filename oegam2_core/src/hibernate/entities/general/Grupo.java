package hibernate.entities.general;

import hibernate.entities.datosSensibles.DatosSensiblesBastidor;
import hibernate.entities.datosSensibles.DatosSensiblesMatricula;
import hibernate.entities.datosSensibles.DatosSensiblesNif;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the GRUPOS database table.
 * 
 */
@Entity
@Table(name = "GRUPOS")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_GRUPO")
	private String idGrupo;

	@Column(name = "DESC_GRUPO")
	private String descGrupo;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "TIPO")
	private String tipo;

	// bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy = "grupo")
	private List<Usuario> usuarios;

	// bi-directional many-to-one association to DatosSensiblesBastidor
	@OneToMany(mappedBy = "grupo")
	private List<DatosSensiblesBastidor> datosSensiblesBastidors;

	// bi-directional many-to-one association to DatosSensiblesMatricula
	@OneToMany(mappedBy = "grupo")
	private List<DatosSensiblesMatricula> datosSensiblesMatriculas;

	// bi-directional many-to-one association to DatosSensiblesNif
	@OneToMany(mappedBy = "grupo")
	private List<DatosSensiblesNif> datosSensiblesNifs;

	public Grupo() {
	}

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

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<DatosSensiblesBastidor> getDatosSensiblesBastidors() {
		return this.datosSensiblesBastidors;
	}

	public void setDatosSensiblesBastidors(List<DatosSensiblesBastidor> datosSensiblesBastidors) {
		this.datosSensiblesBastidors = datosSensiblesBastidors;
	}

	public List<DatosSensiblesMatricula> getDatosSensiblesMatriculas() {
		return this.datosSensiblesMatriculas;
	}

	public void setDatosSensiblesMatriculas(List<DatosSensiblesMatricula> datosSensiblesMatriculas) {
		this.datosSensiblesMatriculas = datosSensiblesMatriculas;
	}

	public List<DatosSensiblesNif> getDatosSensiblesNifs() {
		return this.datosSensiblesNifs;
	}

	public void setDatosSensiblesNifs(List<DatosSensiblesNif> datosSensiblesNifs) {
		this.datosSensiblesNifs = datosSensiblesNifs;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

}