package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaJustificanteProfBean implements Serializable{

	private static final long serialVersionUID = -7230869561729509295L;

	public File fichero;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name = "idJustificante")
	private BigDecimal idJustificante;

	@FilterSimpleExpression(name = "matricula")
	@FilterRelationships(value = { @FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "vehiculo", joinType = Criteria.LEFT_JOIN) })
	private String matricula;

	@FilterSimpleExpression(name = "numColegiado")
	@FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN)
	private String numColegiado;

	@FilterSimpleExpression(name = "fechaInicio", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaIni;

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.GE)
	private Date fechaMaxIni;

	@FilterSimpleExpression(name = "fechaFin", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFin;

	@FilterSimpleExpression(name = "fechaInicio", restriction = FilterSimpleExpressionRestriction.LE)
	private Date fechaMinFin;

	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;

	@FilterSimpleExpression(name = "idJustificanteInterno")
	private Long idJustificanteInterno;

	@FilterSimpleExpression(name = "codigoVerificacion", restriction = FilterSimpleExpressionRestriction.ISNULL)
	private Boolean codigoVerificacion;
	
	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationships(value = { @FilterRelationship(name = "tramiteTrafico", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "contrato", joinType = Criteria.LEFT_JOIN) })
	private Long idContrato;
	private Boolean error;
	private String mensaje;

	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(BigDecimal idJustificante) {
		this.idJustificante = idJustificante;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(FechaFraccionada fechaIni) {
		this.fechaIni = fechaIni;
	}

	public FechaFraccionada getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(FechaFraccionada fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(Long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}

	public Boolean getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(Boolean codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public Date getFechaMaxIni() {
		return fechaMaxIni;
	}

	public void setFechaMaxIni(Date fechaMaxIni) {
		this.fechaMaxIni = fechaMaxIni;
	}

	public Date getFechaMinFin() {
		return fechaMinFin;
	}

	public void setFechaMinFin(Date fechaMinFin) {
		this.fechaMinFin = fechaMinFin;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	
	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public File getFichero() {
		return fichero;
	}
	
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}


}
