package hibernate.entities.general;

import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.EvolucionTramiteTrafico;
import hibernate.entities.trafico.SolicitudPlaca;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the USUARIO database table.
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USUARIO")
	private long idUsuario;

	private String anagrama;

	@Column(name = "APELLIDOS_NOMBRE")
	private String apellidosNombre;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name = "ESTADO_USUARIO")
	private BigDecimal estadoUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NOTIFICACION")
	private Date fechaNotificacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_RENOVACION")
	private Date fechaRenovacion;

	// Mantis 11562. David Sierra: Agregados dos nuevos campos a la tabla
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;
	// Fin Mantis

	private String nif;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NUM_COLEGIADO_NACIONAL")
	private String numColegiadoNacional;

	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name = "ULTIMA_CONEXION")
	private Date ultimaConexion;

	private String usuario;

	// bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name = "ID_GRUPO")
	private Grupo grupo;

	@OneToMany(mappedBy = "usuario")
	private List<EvolucionTramiteTrafico> evolucionTramiteTrafico;

	@OneToMany(mappedBy = "usuario")
	private List<AccionTramite> accionTramite;

	/**
	 * Autor: Julio Molina Fecha: 26/12/2012 Metodo De momento comentamos esto ya que los Usuarios no todos tendran grupo
	 */

	// //bi-directional many-to-one association to DatosSensiblesBastidor
	// @OneToMany(mappedBy="usuario")
	// private List<DatosSensiblesBastidor> datosSensiblesBastidors;
	//
	// //bi-directional many-to-one association to DatosSensiblesMatricula
	// @OneToMany(mappedBy="usuario")
	// private List<DatosSensiblesMatricula> datosSensiblesMatriculas;
	//
	// //bi-directional many-to-one association to DatosSensiblesNif
	// @OneToMany(mappedBy="usuario")
	// private List<DatosSensiblesNif> datosSensiblesNifs;

	// bi-directional many-to-one association to Tasa
	@OneToMany(mappedBy = "usuario")
	private List<Tasa> tasas;

	// bi-directional many-to-many association to SecGrupo
	@ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
	private List<SecGrupo> secGrupos;

	// bi-directional many-to-many association to SecPermiso
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sec_usuario_permiso", catalog = "oegam2", joinColumns = { @JoinColumn(name = "ID_USUARIO", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_PERMISO", nullable = false, updatable = false) })
	private List<SecPermiso> secPermisos;

	@OneToMany(mappedBy = "usuario")
	private List<SolicitudPlaca> solicitudPlaca;

	public Usuario() {}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAnagrama() {
		return this.anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getApellidosNombre() {
		return this.apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public BigDecimal getEstadoUsuario() {
		return this.estadoUsuario;
	}

	public void setEstadoUsuario(BigDecimal estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public Date getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Date getFechaRenovacion() {
		return this.fechaRenovacion;
	}

	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getUltimaConexion() {
		return this.ultimaConexion;
	}

	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Tasa> getTasas() {
		return this.tasas;
	}

	public void setTasas(List<Tasa> tasas) {
		this.tasas = tasas;
	}

	public List<EvolucionTramiteTrafico> getEvolucionTramiteTrafico() {
		return evolucionTramiteTrafico;
	}

	public void setEvolucionTramiteTrafico(List<EvolucionTramiteTrafico> evolucionTramiteTrafico) {
		this.evolucionTramiteTrafico = evolucionTramiteTrafico;
	}

	public List<AccionTramite> getAccionTramite() {
		return accionTramite;
	}

	public void setAccionTramite(List<AccionTramite> accionTramite) {
		this.accionTramite = accionTramite;
	}

	public List<SecGrupo> getSecGrupos() {
		return this.secGrupos;
	}

	public void setSecGrupos(List<SecGrupo> secGrupos) {
		this.secGrupos = secGrupos;
	}

	public List<SecPermiso> getSecPermisos() {
		return this.secPermisos;
	}

	public void setSecPermisos(List<SecPermiso> secPermisos) {
		this.secPermisos = secPermisos;
	}

	public List<SolicitudPlaca> getSolicitudPlaca() {
		return solicitudPlaca;
	}

	public void setSolicitudPlaca(List<SolicitudPlaca> solicitudPlaca) {
		this.solicitudPlaca = solicitudPlaca;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}