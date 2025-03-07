package hibernate.entities.trafico;

//TODO MPC. Cambio IVTM. Clase nueva.
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the IVTM_MATRICULACION database table.
 * 
 */
@Entity
@Table(name = "IVTM_MATRICULACION")
public class IvtmMatriculacion implements Serializable {

	private static final long serialVersionUID = -2472411806195008967L;

	@Id
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "BONMEDAM")
	private Boolean bonmedam;

	@Column(name = "COD_GESTOR")
	private String codGestor;

	@Column(name = "CODIGO_RESPUESTA_PAGO")
	private String codigoRespuestaPago;

	@Column(name = "DIGITO_CONTROL")
	private String digitoControl;

	@Column(name = "EMISOR")
	private String emisor;

	@Column(name = "ESTADO_IVTM")
	private BigDecimal estadoIvtm;

	@Column(name = "EXENTO")
	private Boolean exento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PAGO")
	private Date fechaPago;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_REQ")
	private Date fechaReq;

	@Column(name = "IBAN")
	private String iban;

	@Column(name = "ID_PETICION")
	private BigDecimal idPeticion;

	@Column(name = "IMPORTE")
	private BigDecimal importe;

	@Column(name = "IMPORTE_ANUAL")
	private BigDecimal importeAnual;

	@Column(name = "MENSAJE")
	private String mensaje;

	@Column(name = "NRC")
	private String nrc;

	@Column(name = "PORCENTAJEBONMEDAM")
	private BigDecimal porcentajebonmedam;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "BASTIDOR")
	private String bastidor;

	// bi-directional one-to-one association to TramiteTrafico
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ivtmMatriculacion")
	private List<TramiteTrafMatr> tramiteTrafMatr;

	public IvtmMatriculacion() {
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Boolean isBonmedam() {
		return this.bonmedam;
	}

	public void setBonmedam(Boolean bonmedam) {
		this.bonmedam = bonmedam;
	}

	public String getCodGestor() {
		return this.codGestor;
	}

	public void setCodGestor(String codGestor) {
		this.codGestor = codGestor;
	}

	public String getCodigoRespuestaPago() {
		return codigoRespuestaPago;
	}

	public void setCodigoRespuestaPago(String codigoRespuestaPago) {
		this.codigoRespuestaPago = codigoRespuestaPago;
	}

	public Boolean getBonmedam() {
		return bonmedam;
	}

	public Boolean getExento() {
		return exento;
	}

	public String getDigitoControl() {
		return this.digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}

	public String getEmisor() {
		return this.emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public BigDecimal getEstadoIvtm() {
		return this.estadoIvtm;
	}

	public void setEstadoIvtm(BigDecimal estadoIvtm) {
		this.estadoIvtm = estadoIvtm;
	}

	public Boolean isExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Date getFechaReq() {
		return this.fechaReq;
	}

	public void setFechaReq(Date fechaReq) {
		this.fechaReq = fechaReq;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getIdPeticion() {
		return this.idPeticion;
	}

	public void setIdPeticion(BigDecimal idPeticion) {
		this.idPeticion = idPeticion;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getImporteAnual() {
		return this.importeAnual;
	}

	public void setImporteAnual(BigDecimal importeAnual) {
		this.importeAnual = importeAnual;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNrc() {
		return this.nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public BigDecimal getPorcentajebonmedam() {
		return this.porcentajebonmedam;
	}

	public void setPorcentajebonmedam(BigDecimal porcentajebonmedam) {
		this.porcentajebonmedam = porcentajebonmedam;
	}

	public List<TramiteTrafMatr> getTramiteTrafMatr() {
		return tramiteTrafMatr;
	}

	public void setTramiteTrafMatr(List<TramiteTrafMatr> tramiteTrafMatr) {
		this.tramiteTrafMatr = tramiteTrafMatr;
	}

}