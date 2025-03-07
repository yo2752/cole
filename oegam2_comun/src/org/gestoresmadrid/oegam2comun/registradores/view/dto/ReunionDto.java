package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import utilidades.estructuras.Fecha;

public class ReunionDto implements Serializable {

	private static final long serialVersionUID = 460010507113817852L;

	private Long idReunion;

	private String ambito;

	private String caracter;

	private String contenidoConvo;

	private Fecha fecha;

	private Fecha fechaAproActa;

	private String formaAproActa;

	private String lugar;

	private Long porcentajeCapital;

	private Long porcentajeSocios;

	private String tipoReunion;

	private BigDecimal idTramiteRegistro;

	private ConvocatoriaDto convocatoria;

	private List<MedioConvocatoriaDto> medios;

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getContenidoConvo() {
		return contenidoConvo;
	}

	public void setContenidoConvo(String contenidoConvo) {
		this.contenidoConvo = contenidoConvo;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public Fecha getFechaAproActa() {
		return fechaAproActa;
	}

	public void setFechaAproActa(Fecha fechaAproActa) {
		this.fechaAproActa = fechaAproActa;
	}

	public String getFormaAproActa() {
		return formaAproActa;
	}

	public void setFormaAproActa(String formaAproActa) {
		this.formaAproActa = formaAproActa;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Long getPorcentajeCapital() {
		return porcentajeCapital;
	}

	public void setPorcentajeCapital(Long porcentajeCapital) {
		this.porcentajeCapital = porcentajeCapital;
	}

	public Long getPorcentajeSocios() {
		return porcentajeSocios;
	}

	public void setPorcentajeSocios(Long porcentajeSocios) {
		this.porcentajeSocios = porcentajeSocios;
	}

	public String getTipoReunion() {
		return tipoReunion;
	}

	public void setTipoReunion(String tipoReunion) {
		this.tipoReunion = tipoReunion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public ConvocatoriaDto getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(ConvocatoriaDto convocatoria) {
		this.convocatoria = convocatoria;
	}

	public List<MedioConvocatoriaDto> getMedios() {
		return medios;
	}

	public void setMedios(List<MedioConvocatoriaDto> medios) {
		this.medios = medios;
	}
}