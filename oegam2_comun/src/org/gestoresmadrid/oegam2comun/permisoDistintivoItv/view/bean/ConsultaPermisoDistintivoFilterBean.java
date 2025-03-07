package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaPermisoDistintivoFilterBean implements Serializable{

	private static final long serialVersionUID = -1192748477684148231L;

	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;
		
	@FilterSimpleExpression(name="numSeriePermiso")
	private String numSeriePermiso;
	
	@FilterSimpleExpression(name="tipoDistintivo")
	private String tipoDistintivo;
	
	@FilterSimpleExpression(name="permiso")
	private String permiso;
	
	@FilterSimpleExpression(name="distintivo")
	private String distintivo;
	
	@FilterSimpleExpression(name="eitv")
	private String eitv;
	
	@FilterSimpleExpression(name="matricula")
	@FilterRelationship(name="vehiculo", joinType = Criteria.LEFT_JOIN)
	private String matricula;
	
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
	
	@FilterSimpleExpression(name="estadoImpDstv")
	private String estadoImpresion;
	
	private String estadoPeticionPermDstv;
	
	public List<String> getListaEstadoEitv() {
		return listaEstadoEitv;
	}
	public void setListaEstadoEitv(List<String> listaEstadoEitv) {
		this.listaEstadoEitv = listaEstadoEitv;
	}
	public void setListaEstadoPeticionPermDstv(List<String> listaEstadoPeticionPermDstv) {
		this.listaEstadoPeticionPermDstv = listaEstadoPeticionPermDstv;
	}

	private String estadoPeticionEitv;
	
	@FilterSimpleExpression(name="estadoPetPermDstv", restriction=FilterSimpleExpressionRestriction.IN)
	private List<String> listaEstadoPeticionPermDstv;
	
	@FilterSimpleExpression(name="estadoPetEitv", restriction=FilterSimpleExpressionRestriction.IN)
	private List<String> listaEstadoEitv;
	
	@FilterSimpleExpression(name="estadoImpresionDstv", restriction=FilterSimpleExpressionRestriction.IN)
	private List<String> listaEstadoImpresionDstv;
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumSeriePermiso() {
		return numSeriePermiso;
	}

	public void setNumSeriePermiso(String numSeriePermiso) {
		this.numSeriePermiso = numSeriePermiso;
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

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}

	public String getEitv() {
		return eitv;
	}

	public void setEitv(String eitv) {
		this.eitv = eitv;
	}

	public void setEstadoTramite(List<BigDecimal> estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getEstadoPeticionPermDstv() {
		return estadoPeticionPermDstv;
	}

	public void setEstadoPeticionPermDstv(String estadoPeticionPermDstv) {
		this.estadoPeticionPermDstv = estadoPeticionPermDstv;
	}

	public String getEstadoPeticionEitv() {
		return estadoPeticionEitv;
	}

	public void setEstadoPeticionEitv(String estadoPeticionEitv) {
		this.estadoPeticionEitv = estadoPeticionEitv;
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
	
}
