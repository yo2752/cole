package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import trafico.utiles.enumerados.RespuestaPagoIVTM;
import utilidades.estructuras.Fecha;

public class IvtmMatriculacionDto implements Serializable {

	private static final long serialVersionUID = -2176252953892530453L;

	private BigDecimal numExpediente;

	private Boolean bonmedam;

	private String codGestor;

	private String digitoControl;

	private String emisor;

	private BigDecimal estadoIvtm;

	private Boolean exento;

	private Fecha fechaPago;

	private Fecha fechaReq;

	private String iban;

	private BigDecimal idPeticion;

	private BigDecimal importe;

	private BigDecimal importeAnual;

	private String mensaje;

	private String nrc;

	private BigDecimal porcentajebonmedam;

	private RespuestaPagoIVTM codigoRespuestaPago;
	
	private String numColegiado;
	
	private String bastidor;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Boolean isBonmedam() {
		return bonmedam;
	}

	public void setBonmedam(Boolean bonmedam) {
		this.bonmedam = bonmedam;
	}

	public String getCodGestor() {
		return codGestor;
	}

	public void setCodGestor(String codGestor) {
		this.codGestor = codGestor;
	}

	public String getDigitoControl() {
		return digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public BigDecimal getEstadoIvtm() {
		return estadoIvtm;
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

	public Fecha getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Fecha fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Fecha getFechaReq() {
		return fechaReq;
	}

	public void setFechaReq(Fecha fechaReq) {
		this.fechaReq = fechaReq;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public RespuestaPagoIVTM getCodigoRespuestaPago() {
		return codigoRespuestaPago;
	}

	public void setCodigoRespuestaPago(RespuestaPagoIVTM codigoRespuestaPago) {
		this.codigoRespuestaPago = codigoRespuestaPago;
	}

	public Boolean getBonmedam() {
		return bonmedam;
	}

	public Boolean getExento() {
		return exento;
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
}
