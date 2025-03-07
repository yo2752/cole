package hibernate.entities.trafico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TRAMITE_TRAF_IVTM")
public class TramiteTrafIvtm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;

	private String digitos;

	private String emisor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REQUEST")
	private Date fechaRequest;

	private String importe;

	@Column(name = "IMPORTE_ANUAL")
	private String importeAnual;

	@Column(name = "NUM_AUTOLIQUIDACION")
	private String numAutoliquidacion;

	@Column(name = "COD_GESTOR")
	private String codGestor;

	@Column(name = "ID_PETICION")
	private Long idPeticion;

	private String menserr;

	private String matricula;

	private String mensaje;

	// FIXME no existe la relación en TramiteTrafMatr
	// bi-directional one-to-one association to TramiteTrafico
	// @OneToMany(fetch=FetchType.LAZY, mappedBy="tramiteTrafIvtm")
	@Transient
	private List<TramiteTrafMatr> tramiteTrafMatr;

	public TramiteTrafIvtm() {
	}

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getDigitos() {
		return this.digitos;
	}

	public void setDigitos(String digitos) {
		this.digitos = digitos;
	}

	public String getEmisor() {
		return this.emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public Date getFechaRequest() {
		return this.fechaRequest;
	}

	public void setFechaRequest(Date fechaRequest) {
		this.fechaRequest = fechaRequest;
	}

	public String getImporte() {
		return this.importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getImporteAnual() {
		return this.importeAnual;
	}

	public void setImporteAnual(String importeAnual) {
		this.importeAnual = importeAnual;
	}

	public String getNumAutoliquidacion() {
		return this.numAutoliquidacion;
	}

	public void setNumAutoliquidacion(String numAutoliquidacion) {
		this.numAutoliquidacion = numAutoliquidacion;
	}

	public String getCodGestor() {
		return codGestor;
	}

	public void setCodGestor(String codGestor) {
		this.codGestor = codGestor;
	}

	public Long getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Long idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getMenserr() {
		return menserr;
	}

	public void setMenserr(String menserr) {
		this.menserr = menserr;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<TramiteTrafMatr> getTramiteTrafMatr() {
		return this.tramiteTrafMatr;
	}

	public void setTramiteTrafMatr(List<TramiteTrafMatr> tramiteTrafMatr) {
		this.tramiteTrafMatr = tramiteTrafMatr;
	}

}