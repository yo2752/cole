package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.criterion.CriteriaSpecification;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaDistintivoDgtFilterBean implements Serializable{

	private static final long serialVersionUID = -7392410013483434414L;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name="tipoDistintivo")
	private String tipoDistintivo;

	@FilterSimpleExpression(name="distintivo")
	private String distintivo;

	@FilterSimpleExpression(name="respPetPermDstv")
	private String respPetPermDstv;

	@FilterSimpleExpression(name="matricula")
	@FilterRelationship(name="vehiculo", joinType = CriteriaSpecification.LEFT_JOIN)
	private String matricula;

	@FilterSimpleExpression(name="bastidor")
	@FilterRelationship(name="vehiculo", joinType = CriteriaSpecification.LEFT_JOIN)
	private String bastidor;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = CriteriaSpecification.LEFT_JOIN)
	private Long idContrato;

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name="estado", restriction=FilterSimpleExpressionRestriction.IN)
	private List<BigDecimal> estadoTramite;

	@FilterSimpleExpression(name="estadoImpDstv")
	private String estadoImpresionDstv;

	@FilterSimpleExpression(name="estadoPetPermDstv")
	private String estadoPeticionDstv;

	@FilterSimpleExpression(name="estadoPetPermDstv", restriction=FilterSimpleExpressionRestriction.IN_WITH_NULL)
	private List<String> listaEstadoPeticionPermDstv;

	@FilterSimpleExpression(name="estadoImpDstv", restriction=FilterSimpleExpressionRestriction.IN_WITH_NULL)
	private List<String> listaEstadoImpresionDstv;

	@FilterSimpleExpression(name="id.nif")
	@FilterRelationship(name="intervinienteTraficos", joinType = CriteriaSpecification.LEFT_JOIN)
	private String nif;

	@FilterSimpleExpression(name="refPropia")
	private String refPropia;

	private String estadoSolicitud;

	private String estadoImpresion;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
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

	public String getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}

	public void setEstadoTramite(List<BigDecimal> estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public List<BigDecimal> getEstadoTramite() {
		return estadoTramite;
	}
	public String getEstadoImpresion() {
		return estadoImpresion;
	}
	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}
	public List<String> getListaEstadoImpresionDstv() {
		return listaEstadoImpresionDstv;
	}
	public void setListaEstadoImpresionDstv(List<String> listaEstadoImpresionDstv) {
		this.listaEstadoImpresionDstv = listaEstadoImpresionDstv;
	}
	public List<String> getListaEstadoPeticionPermDstv() {
		return listaEstadoPeticionPermDstv;
	}

	public String getEstadoPeticionDstv() {
		return estadoPeticionDstv;
	}

	public void setEstadoPeticionDstv(String estadoPeticionDstv) {
		this.estadoPeticionDstv = estadoPeticionDstv;
	}

	public void setListaEstadoPeticionPermDstv(List<String> listaEstadoPeticionPermDstv) {
		this.listaEstadoPeticionPermDstv = listaEstadoPeticionPermDstv;
	}

	public String getEstadoImpresionDstv() {
		return estadoImpresionDstv;
	}

	public void setEstadoImpresionDstv(String estadoImpresionDstv) {
		this.estadoImpresionDstv = estadoImpresionDstv;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getRespPetPermDstv() {
		return respPetPermDstv;
	}

	public void setRespPetPermDstv(String respPetPermDstv) {
		this.respPetPermDstv = respPetPermDstv;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

}