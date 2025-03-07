package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_PERSONA database table.
 */
@Entity
@Table(name = "LC_PERSONA")
public class LcPersonaVO implements Serializable {

	private static final long serialVersionUID = 39404774730914905L;

	@Id
	@SequenceGenerator(name = "lc_persona", sequenceName = "LC_PERSONA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_persona")
	@Column(name = "ID_PERSONA")
	private Long idPersona;

	@Column(name = "TIPO_SUJETO")
	private String tipoSujeto;

	@Column(name = "APELLIDO1_RAZON_SOCIAL")
	private String apellido1RazonSocial;

	private String apellido2;

	private String nombre;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "NUM_TELEFONO1")
	private String numTelefono1;

	@Column(name = "NUM_TELEFONO2")
	private String numTelefono2;

	@Column(name = "NUM_FAX")
	private String numFax;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoSujeto() {
		return tipoSujeto;
	}

	public void setTipoSujeto(String tipoSujeto) {
		this.tipoSujeto = tipoSujeto;
	}

	public String getApellido1RazonSocial() {
		return apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumTelefono1() {
		return numTelefono1;
	}

	public void setNumTelefono1(String numTelefono1) {
		this.numTelefono1 = numTelefono1;
	}

	public String getNumTelefono2() {
		return numTelefono2;
	}

	public void setNumTelefono2(String numTelefono2) {
		this.numTelefono2 = numTelefono2;
	}

	public String getNumFax() {
		return numFax;
	}

	public void setNumFax(String numFax) {
		this.numFax = numFax;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}