package org.gestoresmadrid.core.personas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the PERSONA database table.
 */
@Entity
@Table(name = "PERSONA")
public class PersonaVO implements Serializable, Cloneable {

	private static final long serialVersionUID = -4564509643622050637L;

	@EmbeddedId
	private PersonaPK id;

	private String anagrama;

	@Column(name = "APELLIDO1_RAZON_SOCIAL")
	private String apellido1RazonSocial;

	private String apellido2;

	@Column(name = "CODIGO_MANDATO")
	private String codigoMandato;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "IBAN")
	private String iban;

	private Long estado;

	@Column(name = "ESTADO_CIVIL")
	private String estadoCivil;

	private String fa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CAD_CARNET_COND")
	private Date fechaCaducidadCarnet;

	private BigDecimal hoja;

	@Column(name = "HOJA_BIS")
	private String hojaBis;

	private BigDecimal ius;

	private String ncorpme;

	@Column(name = "USUARIO_CORPME")
	private String usuarioCorpme;

	@Column(name = "PASSWORD_CORPME")
	private String passwordCorpme;

	private String nombre;

	@Column(name = "PODERES_EN_FICHA")
	private BigDecimal poderesEnFicha;

	private BigDecimal pirpf;

	private BigDecimal seccion;

	private String sexo;

	private String subtipo;

	private String telefonos;

	@Column(name = "PAIS_NACIMIENTO")
	private String paisNacimiento;

	private String nacionalidad;

	@Column(name = "TIPO_PERSONA")
	private String tipoPersona;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CADUCIDAD_NIF")
	private Date fechaCaducidadNIF;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CADUCIDAD_ALTERNATIVO")
	private Date fechaCaducidadAlternativo;

	@Column(name = "TIPO_DOCUMENTO_ALTERNATIVO")
	private String tipoDocumentoAlternativo;

	private String indefinido;

	// bi-directional many-to-one association to PersonaDireccion
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", insertable = false, updatable = false), @JoinColumn(name = "NIF", insertable = false, updatable = false) })
	private Set<PersonaDireccionVO> personaDireccion;

	// bi-directional many-to-one association to EvolucionPersona
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private Set<EvolucionPersonaVO> evolucionPersona;

	@Transient
	private DireccionVO direccion;

	public PersonaPK getId() {
		return this.id;
	}

	public void setId(PersonaPK id) {
		this.id = id;
	}

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

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaCaducidadCarnet() {
		return fechaCaducidadCarnet;
	}

	public void setFechaCaducidadCarnet(Date fechaCaducidadCarnet) {
		this.fechaCaducidadCarnet = fechaCaducidadCarnet;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPoderesEnFicha() {
		return poderesEnFicha;
	}

	public void setPoderesEnFicha(BigDecimal poderesEnFicha) {
		this.poderesEnFicha = poderesEnFicha;
	}

	public BigDecimal getPirpf() {
		return pirpf;
	}

	public void setPirpf(BigDecimal pirpf) {
		this.pirpf = pirpf;
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

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Date getFechaCaducidadNIF() {
		return fechaCaducidadNIF;
	}

	public void setFechaCaducidadNIF(Date fechaCaducidadNIF) {
		this.fechaCaducidadNIF = fechaCaducidadNIF;
	}

	public Date getFechaCaducidadAlternativo() {
		return fechaCaducidadAlternativo;
	}

	public void setFechaCaducidadAlternativo(Date fechaCaducidadAlternativo) {
		this.fechaCaducidadAlternativo = fechaCaducidadAlternativo;
	}

	public String getTipoDocumentoAlternativo() {
		return tipoDocumentoAlternativo;
	}

	public void setTipoDocumentoAlternativo(String tipoDocumentoAlternativo) {
		this.tipoDocumentoAlternativo = tipoDocumentoAlternativo;
	}

	public String getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(String indefinido) {
		this.indefinido = indefinido;
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

	public Set<PersonaDireccionVO> getPersonaDireccion() {
		return personaDireccion;
	}

	public List<PersonaDireccionVO> getPersonaDireccionAsList() {
		// Map from Set to List
		List<PersonaDireccionVO> lista = new ArrayList<PersonaDireccionVO>();
		if (personaDireccion != null) {
			lista.addAll(personaDireccion);
		}
		return lista;
	}

	public PersonaDireccionVO getFirstElementPersonaDireccion() {
		ListsOperator<PersonaDireccionVO> listOperator = new ListsOperator<PersonaDireccionVO>();
		return listOperator.getFirstElement(getPersonaDireccion());
	}

	public void setPersonaDireccion(Set<PersonaDireccionVO> personaDireccion) {
		this.personaDireccion = personaDireccion;
	}

	public void setPersonaDireccion(List<PersonaDireccionVO> personaDireccion) {
		if (personaDireccion == null) {
			this.personaDireccion = null;
		} else {
			// Map from List to Set
			Set<PersonaDireccionVO> set = new HashSet<PersonaDireccionVO>();
			set.addAll(personaDireccion);
			this.personaDireccion = set;
		}
	}

	public Set<EvolucionPersonaVO> getEvolucionPersona() {
		return evolucionPersona;
	}

	public List<EvolucionPersonaVO> getEvolucionPersonaAsList() {
		// Map from Set to List
		List<EvolucionPersonaVO> lista = new ArrayList<EvolucionPersonaVO>();
		if (evolucionPersona != null) {
			lista.addAll(evolucionPersona);
		}
		return lista;
	}

	public EvolucionPersonaVO getFirstElementEvolucionPersona() {
		ListsOperator<EvolucionPersonaVO> listOperator = new ListsOperator<EvolucionPersonaVO>();
		return listOperator.getFirstElement(getEvolucionPersona());
	}

	public void setEvolucionPersona(Set<EvolucionPersonaVO> evolucionPersona) {
		this.evolucionPersona = evolucionPersona;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public void setEvolucionPersona(List<EvolucionPersonaVO> evolucionPersona) {
		if (evolucionPersona == null) {
			this.evolucionPersona = null;
		} else {
			// Map from List to Set
			Set<EvolucionPersonaVO> set = new HashSet<EvolucionPersonaVO>();
			set.addAll(evolucionPersona);
			this.evolucionPersona = set;
		}
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