package org.gestoresmadrid.oegam2comun.registradores.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTramiteRegistroBean {

	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;

	@FilterSimpleExpression(name = "idTramiteRegistro")
	private BigDecimal idTramiteRegistro;
	
	@FilterSimpleExpression(name = "idTramiteCorpme")
	private String idTramiteCorpme;

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;

	@FilterSimpleExpression(name = "cif")
	private String cif;

	@FilterSimpleExpression(name = "refPropia")
	private String refPropia;

	@FilterSimpleExpression(name = "lugar")
	private String lugar;

	@FilterSimpleExpression(name = "fechaCreacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaCreacion;

	@FilterSimpleExpression(name = "fechaEnvio", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaEnvio;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "subsanacion")
	private String subsanacion;
	
	@FilterSimpleExpression(name = "sociedad.apellido1RazonSocial")
	private String sociedad;
	
	@FilterSimpleExpression(name="idContrato")
	private BigDecimal idContrato;
	
	@FilterSimpleExpression(name="registro.id")
	private Long idRegistro;
	
	@FilterSimpleExpression(name="registro.tipo")
	private String tipoRegistro;
	
	@FilterSimpleExpression(name = "idTramiteOrigen", restriction = FilterSimpleExpressionRestriction.ISNULL)
	private boolean idTramiteOrigen;


	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Long getIdRegistro() {
		return idRegistro;
	}
 
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public FechaFraccionada getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(FechaFraccionada fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public FechaFraccionada getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(FechaFraccionada fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getSubsanacion() {
		return subsanacion;
	}

	public void setSubsanacion(String subsanacion) {
		this.subsanacion = subsanacion;
	}

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public boolean isIdTramiteOrigen() {
		return idTramiteOrigen;
	}

	public void setIdTramiteOrigen(boolean idTramiteOrigen) {
		this.idTramiteOrigen = idTramiteOrigen;
	}

	public String getIdTramiteCorpme() {
		return idTramiteCorpme;
	}

	public void setIdTramiteCorpme(String idTramiteCorpme) {
		this.idTramiteCorpme = idTramiteCorpme;
	}


}
