package hibernate.entities.trafico;

import hibernate.entities.general.Contrato;
import hibernate.entities.general.Usuario;
import hibernate.entities.tasas.Tasa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TRAMITE_TRAF_ANUNTIS_SOLINFO database table.
 * 
 */
@Entity
@Table(name = "TRAMITE_TRAF_ANUNTIS")
public class TramiteAnuntis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private Long numExpediente;

	@Column(name = "NIF")
	private String nif;

	@Column(name = "CORREO_ELECTRONICO")
	private String email;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "ID_REQUEST")
	private Integer idRequest;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REQUEST")
	private Date fechaRequest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO")
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	private String respuesta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ULT_MODIF")
	private Date fechaUltModif;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	private BigDecimal estado;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA")
	private Tasa tasa;

	@Column(name = "NUM_COLEGIADO", updatable = false)
	private String numColegiado;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(Integer idRequest) {
		this.idRequest = idRequest;
	}

	public Date getFechaRequest() {
		return fechaRequest;
	}

	public void setFechaRequest(Date fechaRequest) {
		this.fechaRequest = fechaRequest;
	}

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

}