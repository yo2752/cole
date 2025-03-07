package org.gestoresmadrid.oegamComun.interviniente.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class IntervinienteTraficoDto implements Serializable {

	private static final long serialVersionUID = -4939576833697325634L;

	private Boolean autonomo;

	private Boolean cambioDomicilio;

	private String codigoIAE;

	private String conceptoRepre;

	private String datosDocumento;

	private Fecha fechaFin;

	private Fecha fechaInicio;

	private String horaFin;

	private String horaInicio;

	private String idMotivoTutela;

	private String nifInterviniente;

	private String numColegiado;

	private BigDecimal numExpediente;

	private BigDecimal numInterviniente;

	private String tipoInterviniente;

	private String tipoIntervinienteDes;

	private DireccionDto direccion;

	private PersonaDto persona;

	public Boolean getAutonomo() {
		return autonomo;
	}

	public void setAutonomo(Boolean autonomo) {
		this.autonomo = autonomo;
	}

	public Boolean getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(Boolean cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCodigoIAE() {
		return codigoIAE;
	}

	public void setCodigoIAE(String codigoIAE) {
		this.codigoIAE = codigoIAE;
	}

	public String getConceptoRepre() {
		return conceptoRepre;
	}

	public void setConceptoRepre(String conceptoRepre) {
		this.conceptoRepre = conceptoRepre;
	}

	public String getDatosDocumento() {
		return datosDocumento;
	}

	public void setDatosDocumento(String datosDocumento) {
		this.datosDocumento = datosDocumento;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getIdMotivoTutela() {
		return idMotivoTutela;
	}

	public void setIdMotivoTutela(String idMotivoTutela) {
		this.idMotivoTutela = idMotivoTutela;
	}

	public String getNifInterviniente() {
		return nifInterviniente;
	}

	public void setNifInterviniente(String nifInterviniente) {
		this.nifInterviniente = nifInterviniente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumInterviniente() {
		return numInterviniente;
	}

	public void setNumInterviniente(BigDecimal numInterviniente) {
		this.numInterviniente = numInterviniente;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getTipoIntervinienteDes() {
		return tipoIntervinienteDes;
	}

	public void setTipoIntervinienteDes(String tipoIntervinienteDes) {
		this.tipoIntervinienteDes = tipoIntervinienteDes;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
}