package hibernate.entities.general;

import hibernate.entities.personas.Persona;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the RECONOCIMIENTO_MEDICO database table.
 * 
 */
@Entity
@Table(name = "RECONOCIMIENTO_MEDICO")
public class ReconocimientoMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "RECONOCIMIENTO_MEDICO_IDRECONOCIMIENTO_GENERATOR", sequenceName = "CLAVE_RECONOCIMIENTO_MED_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECONOCIMIENTO_MEDICO_IDRECONOCIMIENTO_GENERATOR")
	@Column(name = "ID_RECONOCIMIENTO")
	private Long idReconocimiento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_RECONOCIMIENTO")
	private Date fechaReconocimiento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULT_MODIF")
	private Date fechaUltModif;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REAL_VISITA")
	private Date fechaRealVisita;

	//bi-directional many-to-one association to Colegiado
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_COLEGIADO", insertable = false, updatable = false)
	private Colegiado colegiado;

	//bi-directional many-to-one association to Contrato
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO")
	private Contrato contrato;

	//bi-directional many-to-one association to Contrato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_CLINICA")
	private Contrato clinica;

	//bi-directional many-to-one association to Persona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "NIF", referencedColumnName = "NIF"),
		@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO")
	})
	private Persona persona;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	//bi-directional many-to-one association to TipoTramiteRenovacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "TIPO_TRAMITE_RENOVACION")
	private TipoTramiteRenovacion tipoTramiteRenovacion;

	public ReconocimientoMedico() {
	}

	public Long getIdReconocimiento() {
		return this.idReconocimiento;
	}

	public void setIdReconocimiento(Long idReconocimiento) {
		this.idReconocimiento = idReconocimiento;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaReconocimiento() {
		return this.fechaReconocimiento;
	}

	public void setFechaReconocimiento(Date fechaReconocimiento) {
		this.fechaReconocimiento = fechaReconocimiento;
	}

	public Date getFechaUltModif() {
		return this.fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Date getFechaRealVisita() {
		return fechaRealVisita;
	}

	public void setFechaRealVisita(Date fechaRealVisita) {
		this.fechaRealVisita = fechaRealVisita;
	}

	public Colegiado getColegiado() {
		return this.colegiado;
	}

	public void setColegiado(Colegiado colegiado) {
		this.colegiado = colegiado;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Contrato getClinica() {
		return this.clinica;
	}

	public void setClinica(Contrato clinica) {
		this.clinica = clinica;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public TipoTramiteRenovacion getTipoTramiteRenovacion() {
		return tipoTramiteRenovacion;
	}

	public void setTipoTramiteRenovacion(TipoTramiteRenovacion tipoTramiteRenovacion) {
		this.tipoTramiteRenovacion = tipoTramiteRenovacion;
	}

}