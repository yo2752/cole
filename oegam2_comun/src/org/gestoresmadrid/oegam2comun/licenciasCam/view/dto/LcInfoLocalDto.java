package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcInfoLocalDto implements Serializable {

	private static final long serialVersionUID = -9203366268223412032L;

	private Long idInfoLocal;

	private Boolean accesoPrincipalIgual;

	private String actividadAnterior;

	private String codLocal;

	private String descOtros;

	private String descripcionActividad;

	private String escalera;

	private String expedienteActividadAnterior;

	private BigDecimal idLocal;

	private BigDecimal kwa;

	private String localizacion;

	private String nombreAgrupacion;

	private String numLocal;

	private String planta;

	private BigDecimal potenciaNominal;

	private BigDecimal presupuestoObraActividad;

	private String puerta;

	private String referenciaCatastral;

	private String rotuloSolicitado;

	private Boolean solicitaRotulo;

	private Boolean sujetaAOtros;

	private BigDecimal superficieUtilLocal;

	private BigDecimal superficieUtilUsoPublico;

	private LcDireccionDto lcDirInfoLocal;

	private Long idLcDirInfoLocal;

	private List<LcEpigrafeDto> lcEpigrafes;

	// Objetos de pantalla
	private LcEpigrafeDto lcEpigrafeDto;

	public LcInfoLocalDto() {}

	public Long getIdInfoLocal() {
		return idInfoLocal;
	}

	public void setIdInfoLocal(Long idInfoLocal) {
		this.idInfoLocal = idInfoLocal;
	}

	public Boolean getAccesoPrincipalIgual() {
		return accesoPrincipalIgual;
	}

	public void setAccesoPrincipalIgual(Boolean accesoPrincipalIgual) {
		this.accesoPrincipalIgual = accesoPrincipalIgual;
	}

	public String getActividadAnterior() {
		return actividadAnterior;
	}

	public void setActividadAnterior(String actividadAnterior) {
		this.actividadAnterior = actividadAnterior;
	}

	public String getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(String codLocal) {
		this.codLocal = codLocal;
	}

	public String getDescOtros() {
		return descOtros;
	}

	public void setDescOtros(String descOtros) {
		this.descOtros = descOtros;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getExpedienteActividadAnterior() {
		return expedienteActividadAnterior;
	}

	public void setExpedienteActividadAnterior(String expedienteActividadAnterior) {
		this.expedienteActividadAnterior = expedienteActividadAnterior;
	}

	public BigDecimal getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(BigDecimal idLocal) {
		this.idLocal = idLocal;
	}

	public BigDecimal getKwa() {
		return kwa;
	}

	public void setKwa(BigDecimal kwa) {
		this.kwa = kwa;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getNombreAgrupacion() {
		return nombreAgrupacion;
	}

	public void setNombreAgrupacion(String nombreAgrupacion) {
		this.nombreAgrupacion = nombreAgrupacion;
	}

	public String getNumLocal() {
		return numLocal;
	}

	public void setNumLocal(String numLocal) {
		this.numLocal = numLocal;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public BigDecimal getPotenciaNominal() {
		return potenciaNominal;
	}

	public void setPotenciaNominal(BigDecimal potenciaNominal) {
		this.potenciaNominal = potenciaNominal;
	}

	public BigDecimal getPresupuestoObraActividad() {
		return presupuestoObraActividad;
	}

	public void setPresupuestoObraActividad(BigDecimal presupuestoObraActividad) {
		this.presupuestoObraActividad = presupuestoObraActividad;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getReferenciaCatastral() {
		return referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral) {
		this.referenciaCatastral = referenciaCatastral;
	}

	public String getRotuloSolicitado() {
		return rotuloSolicitado;
	}

	public void setRotuloSolicitado(String rotuloSolicitado) {
		this.rotuloSolicitado = rotuloSolicitado;
	}

	public Boolean getSolicitaRotulo() {
		return solicitaRotulo;
	}

	public void setSolicitaRotulo(Boolean solicitaRotulo) {
		this.solicitaRotulo = solicitaRotulo;
	}

	public Boolean getSujetaAOtros() {
		return sujetaAOtros;
	}

	public void setSujetaAOtros(Boolean sujetaAOtros) {
		this.sujetaAOtros = sujetaAOtros;
	}

	public BigDecimal getSuperficieUtilLocal() {
		return superficieUtilLocal;
	}

	public void setSuperficieUtilLocal(BigDecimal superficieUtilLocal) {
		this.superficieUtilLocal = superficieUtilLocal;
	}

	public BigDecimal getSuperficieUtilUsoPublico() {
		return superficieUtilUsoPublico;
	}

	public void setSuperficieUtilUsoPublico(BigDecimal superficieUtilUsoPublico) {
		this.superficieUtilUsoPublico = superficieUtilUsoPublico;
	}

	public LcDireccionDto getLcDirInfoLocal() {
		return lcDirInfoLocal;
	}

	public void setLcDirInfoLocal(LcDireccionDto lcDirInfoLocal) {
		this.lcDirInfoLocal = lcDirInfoLocal;
	}

	public Long getIdLcDirInfoLocal() {
		return idLcDirInfoLocal;
	}

	public void setIdLcDirInfoLocal(Long idLcDirInfoLocal) {
		this.idLcDirInfoLocal = idLcDirInfoLocal;
	}

	public List<LcEpigrafeDto> getLcEpigrafes() {
		return lcEpigrafes;
	}

	public void setLcEpigrafes(List<LcEpigrafeDto> lcEpigrafes) {
		this.lcEpigrafes = lcEpigrafes;
	}

	public LcEpigrafeDto getLcEpigrafeDto() {
		return lcEpigrafeDto;
	}

	public void setLcEpigrafeDto(LcEpigrafeDto lcEpigrafeDto) {
		this.lcEpigrafeDto = lcEpigrafeDto;
	}

}