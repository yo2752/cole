package org.gestoresmadrid.core.ivtmMatriculacion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IVTM_MATRICULACION")
public class IvtmMatriculacionVO implements Serializable {

	private static final long serialVersionUID = -7449763861454994098L;

	@Id
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "ESTADO_IVTM")
	private BigDecimal estadoIvtm;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PAGO")
	private Date fechaPago;

	@Column(name = "EXENTO")
	private Boolean exento;

	@Column(name = "IMPORTE")
	private BigDecimal importe;

	@Column(name = "IMPORTE_ANUAL")
	private BigDecimal importeAnual;

	@Column(name = "BONMEDAM")
	private Boolean bonmedam;

	@Column(name = "NRC")
	private String nrc;

	@Column(name = "PORCENTAJEBONMEDAM")
	private BigDecimal porcentajebonmedam;

	@Column(name = "IBAN")
	private String iban;

	@Column(name = "ID_PETICION")
	private BigDecimal idPeticion;

	@Column(name = "EMISOR")
	private String emisor;

	@Column(name = "CODIGO_RESPUESTA_PAGO")
	private String codigoRespuestaPago;

	@Column(name = "DIGITO_CONTROL")
	private String digitoControl;

	@Column(name = "COD_GESTOR")
	private String codGestor;

	@Column(name = "MENSAJE")
	private String mensaje;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REQ")
	private Date fechaRequerida;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "BASTIDOR")
	private String bastidor;
	
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
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getEstadoIvtm() {
		return estadoIvtm;
	}

	public void setEstadoIvtm(BigDecimal estadoIvtm) {
		this.estadoIvtm = estadoIvtm;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Boolean getExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getImporteAnual() {
		return importeAnual;
	}

	public void setImporteAnual(BigDecimal importeAnual) {
		this.importeAnual = importeAnual;
	}

	public Boolean getBonmedam() {
		return bonmedam;
	}

	public void setBonmedam(Boolean bonmedam) {
		this.bonmedam = bonmedam;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public BigDecimal getPorcentajebonmedam() {
		return porcentajebonmedam;
	}

	public void setPorcentajebonmedam(BigDecimal porcentajebonmedam) {
		this.porcentajebonmedam = porcentajebonmedam;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(BigDecimal idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getCodigoRespuestaPago() {
		return codigoRespuestaPago;
	}

	public void setCodigoRespuestaPago(String codigoRespuestaPago) {
		this.codigoRespuestaPago = codigoRespuestaPago;
	}

	public String getDigitoControl() {
		return digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}

	public String getCodGestor() {
		return codGestor;
	}

	public void setCodGestor(String codGestor) {
		this.codGestor = codGestor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaRequerida() {
		return fechaRequerida;
	}

	public void setFechaRequerida(Date fechaRequerida) {
		this.fechaRequerida = fechaRequerida;
	}
}
