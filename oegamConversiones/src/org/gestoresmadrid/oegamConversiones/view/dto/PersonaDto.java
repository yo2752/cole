package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import utilidades.estructuras.Fecha;

public class PersonaDto implements Serializable, Cloneable {

	private static final long serialVersionUID = 4826540372050093082L;

	private String anagrama;

	private String apellido1RazonSocial;

	private String apellido2;

	private String codigoMandato;

	private String correoElectronico;

	private String estado;

	private String estadoCivil;

	private String fa;

	private Fecha fechaCadCarnetCond;

	private Fecha fechaCaducidadAlternativo;

	private Fecha fechaCaducidadNif;

	private Fecha fechaNacimiento;

	private BigDecimal hoja;

	private String hojaBis;

	private Boolean indefinido;

	private BigDecimal ius;

	private String ncorpme;

	private String usuarioCorpme;

	private String passwordCorpme;

	private String nif;

	private String nombre;

	private String numColegiado;

	private String iban;

	private BigDecimal pirpf;

	private BigDecimal poderesEnFicha;

	private BigDecimal seccion;

	private String sexo;

	private String subtipo;

	private String telefonos;

	private String tipoDocumentoAlternativo;

	private String tipoPersona;

	private String paisNacimiento;

	private String nacionalidad;

	private DireccionDto direccionDto;

	private Boolean otroDocumentoIdentidad;

	// Para el XML Corpme
	private String apellidos;
	private String tipoPersonalidad;
	private String tipoDocumento;

	public String getAnagrama() {
		return anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
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

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getFa() {
		return fa;
	}

	public void setFa(String fa) {
		this.fa = fa;
	}

	public Fecha getFechaCadCarnetCond() {
		return fechaCadCarnetCond;
	}

	public void setFechaCadCarnetCond(Fecha fechaCadCarnetCond) {
		this.fechaCadCarnetCond = fechaCadCarnetCond;
	}

	public Fecha getFechaCaducidadAlternativo() {
		return fechaCaducidadAlternativo;
	}

	public void setFechaCaducidadAlternativo(Fecha fechaCaducidadAlternativo) {
		this.fechaCaducidadAlternativo = fechaCaducidadAlternativo;
	}

	public Fecha getFechaCaducidadNif() {
		return fechaCaducidadNif;
	}

	public void setFechaCaducidadNif(Fecha fechaCaducidadNif) {
		this.fechaCaducidadNif = fechaCaducidadNif;
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public BigDecimal getHoja() {
		return hoja;
	}

	public void setHoja(BigDecimal hoja) {
		this.hoja = hoja;
	}

	public String getHojaBis() {
		return hojaBis;
	}

	public void setHojaBis(String hojaBis) {
		this.hojaBis = hojaBis;
	}

	public Boolean getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(Boolean indefinido) {
		this.indefinido = indefinido;
	}

	public BigDecimal getIus() {
		return ius;
	}

	public void setIus(BigDecimal ius) {
		this.ius = ius;
	}

	public String getNcorpme() {
		return ncorpme;
	}

	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
	}

	public String getUsuarioCorpme() {
		return usuarioCorpme;
	}

	public void setUsuarioCorpme(String usuarioCorpme) {
		this.usuarioCorpme = usuarioCorpme;
	}

	public String getPasswordCorpme() {
		return passwordCorpme;
	}

	public void setPasswordCorpme(String passwordCorpme) {
		this.passwordCorpme = passwordCorpme;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getPirpf() {
		return pirpf;
	}

	public void setPirpf(BigDecimal pirpf) {
		this.pirpf = pirpf;
	}

	public BigDecimal getPoderesEnFicha() {
		return poderesEnFicha;
	}

	public void setPoderesEnFicha(BigDecimal poderesEnFicha) {
		this.poderesEnFicha = poderesEnFicha;
	}

	public BigDecimal getSeccion() {
		return seccion;
	}

	public void setSeccion(BigDecimal seccion) {
		this.seccion = seccion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getTipoDocumentoAlternativo() {
		return tipoDocumentoAlternativo;
	}

	public void setTipoDocumentoAlternativo(String tipoDocumentoAlternativo) {
		this.tipoDocumentoAlternativo = tipoDocumentoAlternativo;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public DireccionDto getDireccionDto() {
		return direccionDto;
	}

	public void setDireccionDto(DireccionDto direccionDto) {
		this.direccionDto = direccionDto;
	}

	public Boolean isOtroDocumentoIdentidad() {
		otroDocumentoIdentidad = Boolean.FALSE;
		if (otroDocumentoIdentidad == null) {
			if (fechaCaducidadAlternativo != null && !fechaCaducidadAlternativo.isfechaNula() && tipoDocumentoAlternativo != null && !tipoDocumentoAlternativo.isEmpty()) {
				otroDocumentoIdentidad = Boolean.TRUE;
			}
		}
		return otroDocumentoIdentidad;
	}

	public void setOtroDocumentoIdentidad(Boolean otroDocumentoIdentidad) {
		this.otroDocumentoIdentidad = otroDocumentoIdentidad;
	}

	public void setOtroDocumentoIdentidad(Object o) {
		try {
			if (o instanceof Object[]) {
				setOtroDocumentoIdentidad(new Boolean(((Object[]) o)[0].toString()));
			} else {
				setOtroDocumentoIdentidad(new Boolean(o.toString()));

			}
		} catch (Exception e) {}
	}

	public String getApellidos() {
		apellidos = getApellido1RazonSocial() + (StringUtils.isNotBlank(getApellido2()) ? " " + getApellido2() : "");
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoPersonalidad() {
		return tipoPersonalidad;
	}

	public void setTipoPersonalidad(String tipoPersonalidad) {
		this.tipoPersonalidad = tipoPersonalidad;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getPaisNacimiento() {
		return paisNacimiento;
	}

	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
}