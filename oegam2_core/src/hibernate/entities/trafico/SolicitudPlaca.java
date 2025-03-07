package hibernate.entities.trafico;

import hibernate.entities.general.Contrato;
import hibernate.entities.general.Usuario;
import hibernate.entities.personas.Persona;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SOLICITUD_PLACA database table.
 * 
 */
@Entity
@Table(name = "SOLICITUD_PLACA")
@NamedQuery(name = "SolicitudPlaca.findAll", query = "SELECT s FROM SolicitudPlaca s")
public class SolicitudPlaca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "solicitud_placa_seq_gen")
	@SequenceGenerator(name = "solicitud_placa_seq_gen", sequenceName = "SOLICITUD_PLACA_SEQ")
	@Column(name = "ID_SOLICITUD")
	private Integer idSolicitud;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_SOLICITUD")
	private Date fechaSolicitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private Usuario usuario;

	@Column(name = "TIPO_DELANTERA")
	private Integer tipoDelantera;

	@Column(name = "TIPO_TRASERA")
	private Integer tipoTrasera;

	@Column(name = "TIPO_ADICIONAL")
	private Integer tipoAdicional;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne
	@JoinColumn(name = "NUM_EXPEDIENTE")
	private TramiteTrafico tramiteTrafico;

	@Column(name = "ESTADO")
	private Integer estado;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO", insertable = true, updatable = true),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = true, updatable = true) })
	private Vehiculo vehiculo;

	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
	private Contrato contrato;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "NIF_TITULAR", referencedColumnName = "NIF", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private Persona titular;

	@Column(name = "NIF_TITULAR")
	private String nifTitular;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "DUPLICADA")
	private Integer duplicada;

	public SolicitudPlaca() {
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Integer getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Integer getTipoDelantera() {
		return this.tipoDelantera;
	}

	public void setTipoDelantera(Integer tipoDelantera) {
		this.tipoDelantera = tipoDelantera;
	}

	public Integer getTipoTrasera() {
		return this.tipoTrasera;
	}

	public void setTipoTrasera(Integer tipoTrasera) {
		this.tipoTrasera = tipoTrasera;
	}

	public Integer getTipoAdicional() {
		return this.tipoAdicional;
	}

	public void setTipoAdicional(Integer tipoAdicional) {
		this.tipoAdicional = tipoAdicional;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Persona getTitular() {
		return titular;
	}

	public void setTitular(Persona titular) {
		this.titular = titular;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getDuplicada() {
		return duplicada;
	}

	public void setDuplicada(Integer duplicada) {
		this.duplicada = duplicada;
	}

}