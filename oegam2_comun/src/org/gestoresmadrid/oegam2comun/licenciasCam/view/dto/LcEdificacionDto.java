package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcEdificacionDto implements Serializable {

	private static final long serialVersionUID = -6348549902664594131L;

	private Long idEdificacion;

	private String tipoEdificacion;

	private BigDecimal numEdificios;

	private String descripcion;

	private String tipologia;

	private String tipoDemolicion;

	private String industrial;

	private String expPlanEspecialUrbanistico;

	private Boolean superaDoceMil;

	private Boolean superaSeisMil;

	private BigDecimal dotacionalEnEdificio;

	private BigDecimal libreDisposicionEnEdificio;

	private BigDecimal dotacionalEnSuperficie;

	private BigDecimal libreDisposicionEnSuperficie;

	private BigDecimal dotacionalDispacitados;

	private BigDecimal libreDisposicionDiscapacitados;

	private List<LcResumenEdificacionDto> lcResumenEdificacion;

	private List<LcInfoEdificioAltaDto> lcInfoEdificiosAlta;

	private List<LcInfoEdificioBajaDto> lcInfoEdificiosBaja;

	private BigDecimal numExpediente;

	// Objetos de pantalla
	private LcResumenEdificacionDto lcResumenEdificacionVivienda;
	private LcResumenEdificacionDto lcResumenEdificacionLocal;
	private LcResumenEdificacionDto lcResumenEdificacionGaraje;
	private LcResumenEdificacionDto lcResumenEdificacionTrastero;

	private LcInfoEdificioAltaDto lcInfoEdificioAlta;

	private LcInfoEdificioBajaDto lcInfoEdificioBaja;

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}

	public String getTipoEdificacion() {
		return tipoEdificacion;
	}

	public void setTipoEdificacion(String tipoEdificacion) {
		this.tipoEdificacion = tipoEdificacion;
	}

	public BigDecimal getNumEdificios() {
		return numEdificios;
	}

	public void setNumEdificios(BigDecimal numEdificios) {
		this.numEdificios = numEdificios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipoDemolicion() {
		return tipoDemolicion;
	}

	public void setTipoDemolicion(String tipoDemolicion) {
		this.tipoDemolicion = tipoDemolicion;
	}

	public String getIndustrial() {
		return industrial;
	}

	public void setIndustrial(String industrial) {
		this.industrial = industrial;
	}

	public String getExpPlanEspecialUrbanistico() {
		return expPlanEspecialUrbanistico;
	}

	public void setExpPlanEspecialUrbanistico(String expPlanEspecialUrbanistico) {
		this.expPlanEspecialUrbanistico = expPlanEspecialUrbanistico;
	}

	public Boolean getSuperaDoceMil() {
		return superaDoceMil;
	}

	public void setSuperaDoceMil(Boolean superaDoceMil) {
		this.superaDoceMil = superaDoceMil;
	}

	public Boolean getSuperaSeisMil() {
		return superaSeisMil;
	}

	public void setSuperaSeisMil(Boolean superaSeisMil) {
		this.superaSeisMil = superaSeisMil;
	}

	public BigDecimal getDotacionalEnEdificio() {
		return dotacionalEnEdificio;
	}

	public void setDotacionalEnEdificio(BigDecimal dotacionalEnEdificio) {
		this.dotacionalEnEdificio = dotacionalEnEdificio;
	}

	public BigDecimal getLibreDisposicionEnEdificio() {
		return libreDisposicionEnEdificio;
	}

	public void setLibreDisposicionEnEdificio(BigDecimal libreDisposicionEnEdificio) {
		this.libreDisposicionEnEdificio = libreDisposicionEnEdificio;
	}

	public BigDecimal getDotacionalEnSuperficie() {
		return dotacionalEnSuperficie;
	}

	public void setDotacionalEnSuperficie(BigDecimal dotacionalEnSuperficie) {
		this.dotacionalEnSuperficie = dotacionalEnSuperficie;
	}

	public BigDecimal getLibreDisposicionEnSuperficie() {
		return libreDisposicionEnSuperficie;
	}

	public void setLibreDisposicionEnSuperficie(BigDecimal libreDisposicionEnSuperficie) {
		this.libreDisposicionEnSuperficie = libreDisposicionEnSuperficie;
	}

	public BigDecimal getDotacionalDispacitados() {
		return dotacionalDispacitados;
	}

	public void setDotacionalDispacitados(BigDecimal dotacionalDispacitados) {
		this.dotacionalDispacitados = dotacionalDispacitados;
	}

	public BigDecimal getLibreDisposicionDiscapacitados() {
		return libreDisposicionDiscapacitados;
	}

	public void setLibreDisposicionDiscapacitados(BigDecimal libreDisposicionDiscapacitados) {
		this.libreDisposicionDiscapacitados = libreDisposicionDiscapacitados;
	}

	public List<LcResumenEdificacionDto> getLcResumenEdificacion() {
		return lcResumenEdificacion;
	}

	public void setLcResumenEdificacion(List<LcResumenEdificacionDto> lcResumenEdificacion) {
		this.lcResumenEdificacion = lcResumenEdificacion;
	}

	public List<LcInfoEdificioAltaDto> getLcInfoEdificiosAlta() {
		return lcInfoEdificiosAlta;
	}

	public void setLcInfoEdificiosAlta(List<LcInfoEdificioAltaDto> lcInfoEdificiosAlta) {
		this.lcInfoEdificiosAlta = lcInfoEdificiosAlta;
	}

	public List<LcInfoEdificioBajaDto> getLcInfoEdificiosBaja() {
		return lcInfoEdificiosBaja;
	}

	public void setLcInfoEdificiosBaja(List<LcInfoEdificioBajaDto> lcInfoEdificiosBaja) {
		this.lcInfoEdificiosBaja = lcInfoEdificiosBaja;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public LcResumenEdificacionDto getLcResumenEdificacionVivienda() {
		return lcResumenEdificacionVivienda;
	}

	public void setLcResumenEdificacionVivienda(LcResumenEdificacionDto lcResumenEdificacionVivienda) {
		this.lcResumenEdificacionVivienda = lcResumenEdificacionVivienda;
	}

	public LcResumenEdificacionDto getLcResumenEdificacionLocal() {
		return lcResumenEdificacionLocal;
	}

	public void setLcResumenEdificacionLocal(LcResumenEdificacionDto lcResumenEdificacionLocal) {
		this.lcResumenEdificacionLocal = lcResumenEdificacionLocal;
	}

	public LcResumenEdificacionDto getLcResumenEdificacionGaraje() {
		return lcResumenEdificacionGaraje;
	}

	public void setLcResumenEdificacionGaraje(LcResumenEdificacionDto lcResumenEdificacionGaraje) {
		this.lcResumenEdificacionGaraje = lcResumenEdificacionGaraje;
	}

	public LcInfoEdificioAltaDto getLcInfoEdificioAlta() {
		return lcInfoEdificioAlta;
	}

	public void setLcInfoEdificioAlta(LcInfoEdificioAltaDto lcInfoEdificioAlta) {
		this.lcInfoEdificioAlta = lcInfoEdificioAlta;
	}

	public LcInfoEdificioBajaDto getLcInfoEdificioBaja() {
		return lcInfoEdificioBaja;
	}

	public void setLcInfoEdificioBaja(LcInfoEdificioBajaDto lcInfoEdificioBaja) {
		this.lcInfoEdificioBaja = lcInfoEdificioBaja;
	}

	public LcResumenEdificacionDto getLcResumenEdificacionTrastero() {
		return lcResumenEdificacionTrastero;
	}

	public void setLcResumenEdificacionTrastero(LcResumenEdificacionDto lcResumenEdificacionTrastero) {
		this.lcResumenEdificacionTrastero = lcResumenEdificacionTrastero;
	}

}
