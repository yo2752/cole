package hibernate.entities.trafico;

import hibernate.entities.personas.PersonaDireccion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the INTERVINIENTE_TRAFICO database table.
 * 
 */
@Entity
@Table(name = "INTERVINIENTE_TRAFICO")
public class IntervinienteTrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IntervinienteTraficoPK id;

	private String autonomo;

	@Column(name = "CAMBIO_DOMICILIO")
	private String cambioDomicilio;

	@Column(name = "CODIGO_IAE")
	private String codigoIae;

	@Column(name = "CONCEPTO_REPRE")
	private String conceptoRepre;

	@Column(name = "DATOS_DOCUMENTO")
	private String datosDocumento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "HORA_FIN")
	private String horaFin;

	@Column(name = "HORA_INICIO")
	private String horaInicio;

	@Column(name = "ID_MOTIVO_TUTELA")
	private String idMotivoTutela;

	@Column(name = "NUM_INTERVINIENTE")
	private Integer numInterviniente;

	// bi-directional many-to-one association to PersonaDireccion
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false),
			@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false) })
	private PersonaDireccion personaDireccion;

	// bi-directional many-to-one association to TipoInterviniente
	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVINIENTE", insertable = false, updatable = false)
	private TipoInterviniente tipoIntervinienteBean;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTrafico tramiteTrafico;

	public IntervinienteTrafico() {
	}

	public IntervinienteTraficoPK getId() {
		return this.id;
	}

	public void setId(IntervinienteTraficoPK id) {
		this.id = id;
	}

	public String getAutonomo() {
		return this.autonomo;
	}

	public void setAutonomo(String autonomo) {
		this.autonomo = autonomo;
	}

	public String getCambioDomicilio() {
		return this.cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCodigoIae() {
		return this.codigoIae;
	}

	public void setCodigoIae(String codigoIae) {
		this.codigoIae = codigoIae;
	}

	public String getConceptoRepre() {
		return this.conceptoRepre;
	}

	public void setConceptoRepre(String conceptoRepre) {
		this.conceptoRepre = conceptoRepre;
	}

	public String getDatosDocumento() {
		return this.datosDocumento;
	}

	public void setDatosDocumento(String datosDocumento) {
		this.datosDocumento = datosDocumento;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getIdMotivoTutela() {
		return this.idMotivoTutela;
	}

	public void setIdMotivoTutela(String idMotivoTutela) {
		this.idMotivoTutela = idMotivoTutela;
	}

	public PersonaDireccion getPersonaDireccion() {
		return this.personaDireccion;
	}

	public void setPersonaDireccion(PersonaDireccion personaDireccion) {
		this.personaDireccion = personaDireccion;
	}

	public TipoInterviniente getTipoIntervinienteBean() {
		return this.tipoIntervinienteBean;
	}

	public void setTipoIntervinienteBean(TipoInterviniente tipoIntervinienteBean) {
		this.tipoIntervinienteBean = tipoIntervinienteBean;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Integer getnumInterviniente() {
		return this.numInterviniente;
	}

	public void setnumInterviniente(Integer numInterviniente) {
		this.numInterviniente = numInterviniente;
	}

}