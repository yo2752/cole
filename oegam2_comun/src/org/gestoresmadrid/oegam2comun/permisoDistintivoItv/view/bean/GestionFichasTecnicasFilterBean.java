package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class GestionFichasTecnicasFilterBean implements Serializable{

	private static final long serialVersionUID = -6289858267338929909L;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;
		

	private String tipoTramite;
	
	@FilterSimpleExpression(name="tipoTramite", restriction=FilterSimpleExpressionRestriction.IN)
	private List<String> tiposTramites;
	
	@FilterSimpleExpression(name="tipoTramite")
	private String tipoTramiteFicha;
	
	@FilterSimpleExpression(name="id.nif")
	@FilterRelationship(name="intervinienteTraficos", joinType = Criteria.LEFT_JOIN)
	private String nif;
	
	@FilterSimpleExpression(name="id.tipoInterviniente")
	@FilterRelationship(name="intervinienteTraficos", joinType = Criteria.LEFT_JOIN)
	private String tipoInterviniente;
	
	@FilterSimpleExpression(name="jefaturaProvincial")
	@FilterRelationships (value= {@FilterRelationship(name="jefaturaTrafico", joinType=Criteria.INNER_JOIN)})
	private String jefaturaTrafico;
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	@FilterSimpleExpression(name="matricula")
	@FilterRelationship(name="vehiculo", joinType = Criteria.LEFT_JOIN)
	private String matricula;
	
	private String nive;
	
	@FilterSimpleExpression(name="nive", restriction=FilterSimpleExpressionRestriction.ISNOTNULL)
	@FilterRelationship(name="vehiculo", joinType = Criteria.LEFT_JOIN)
	private Boolean conNive;

	@FilterSimpleExpression(name="nive", restriction=FilterSimpleExpressionRestriction.ISNULL)
	@FilterRelationship(name="vehiculo", joinType = Criteria.LEFT_JOIN)
	private Boolean sinNive;
	
	@FilterSimpleExpression(name="bastidor")
	@FilterRelationship(name="vehiculo", joinType = Criteria.LEFT_JOIN)
	private String bastidor;
	
	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;
	
	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;
	
	@FilterSimpleExpression(name="estado", restriction=FilterSimpleExpressionRestriction.IN)
	private List<BigDecimal> estadoTramite;
	
	@FilterSimpleExpression(name="estadoImpFicha",restriction=FilterSimpleExpressionRestriction.IN_WITH_NULL )
	private List<String> estadosImpresion;
	
	@FilterSimpleExpression(name="estadoImpFicha")
	private String estadoImpresionFicha;
	
	private String estadoImpresion;
	
	@FilterSimpleExpression(name="estadoSolFicha",restriction=FilterSimpleExpressionRestriction.IN_WITH_NULL )
	private List<String> estadosSolicitud;
	
	@FilterSimpleExpression(name="estadoSolFicha")
	private String estadoSolicitudFicha;
	
	private String estadoSolicitud;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public List<BigDecimal> getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(List<BigDecimal> estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public List<String> getEstadosImpresion() {
		return estadosImpresion;
	}

	public void setEstadosImpresion(List<String> estadosImpresion) {
		this.estadosImpresion = estadosImpresion;
	}

	public List<String> getEstadosSolicitud() {
		return estadosSolicitud;
	}

	public void setEstadosSolicitud(List<String> estadosSolicitud) {
		this.estadosSolicitud = estadosSolicitud;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoImpresionFicha() {
		return estadoImpresionFicha;
	}

	public void setEstadoImpresionFicha(String estadoImpresionFicha) {
		this.estadoImpresionFicha = estadoImpresionFicha;
	}

	public String getEstadoSolicitudFicha() {
		return estadoSolicitudFicha;
	}

	public void setEstadoSolicitudFicha(String estadoSolicitudFicha) {
		this.estadoSolicitudFicha = estadoSolicitudFicha;
	}

	public List<String> getTiposTramites() {
		return tiposTramites;
	}

	public void setTiposTramites(List<String> tiposTramites) {
		this.tiposTramites = tiposTramites;
	}

	public String getTipoTramiteFicha() {
		return tipoTramiteFicha;
	}

	public void setTipoTramiteFicha(String tipoTramiteFicha) {
		this.tipoTramiteFicha = tipoTramiteFicha;
	}

	public String getJefaturaTrafico() {
		return jefaturaTrafico;
	}

	public void setJefaturaTrafico(String jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public Boolean getConNive() {
		return conNive;
	}

	public void setConNive(Boolean conNive) {
		this.conNive = conNive;
	}

	public Boolean getSinNive() {
		return sinNive;
	}

	public void setSinNive(Boolean sinNive) {
		this.sinNive = sinNive;
	}

}
