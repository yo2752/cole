package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the NOTARIO database table.
 * 
 */
@Entity
@Table(name="NOTARIO_REGISTRO")
public class NotarioRegistroVO implements Serializable {

	private static final long serialVersionUID = 8576793796997778493L;

	@Id
	@SequenceGenerator(name = "notario_registro_secuencia", sequenceName = "NOTARIO_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "notario_registro_secuencia")
	@Column(name="CODIGO")
	private long codigo;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="APELLIDO1")
	private String apellido1;
	
	@Column(name="APELLIDO2")
	private String apellido2;
	
	@Column(name="NIF")
	private String nif;
	
	@Column(name="FEC_OTORGAMIENTO")
	private Date fecOtorgamiento;
	
	@Column(name="NUM_PROTOCOLO")
	private String numProtocolo;
	
	@Column(name="TIPO_PERSONA")
	private String tipoPersona;
	
	@Column(name="COD_PROVINCIA")
	private String codProvincia;

	@Column(name="COD_MUNICIPIO")
	private String codMunicipio;
	
	@Column(name="PLAZA_NOTARIO")
	private String plazaNotario;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigoNotario) {
		this.codigo = codigoNotario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Date getFecOtorgamiento() {
		return fecOtorgamiento;
	}

	public void setFecOtorgamiento(Date fecOtorgamiento) {
		this.fecOtorgamiento = fecOtorgamiento;
	}

	public String getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(String numProtocolo) {
		this.numProtocolo = numProtocolo;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getPlazaNotario() {
		return plazaNotario;
	}

	public void setPlazaNotario(String plazaNotario) {
		this.plazaNotario = plazaNotario;
	}


}