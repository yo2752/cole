package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class GestionPermFichaMasivoFilterBean implements Serializable{

	private static final long serialVersionUID = 7034517856921341509L;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;
	
	@FilterSimpleExpression(name = "fechaPresentacion", restriction=FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name="tipoTramite", restriction=FilterSimpleExpressionRestriction.IN)
	private List<String> tiposTramites;
	
	@FilterSimpleExpression(name="tipoTramite")
	private String tipoTramite;
	
	@FilterSimpleExpression(name="estado", restriction=FilterSimpleExpressionRestriction.IN)
	private List<BigDecimal> estadoTramite;
	
	@FilterSimpleExpression(name="imprPermisoCircu")
	private String imprPermisoCircu;
	
	@FilterSimpleExpression(name="docPermiso", restriction=FilterSimpleExpressionRestriction.ISNULL)
	private Boolean bDocPermiso;
	
	@FilterSimpleExpression(name="docFichaTecnica", restriction=FilterSimpleExpressionRestriction.ISNULL)
	private Boolean bDocFicha;
	
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

	public List<String> getTiposTramites() {
		return tiposTramites;
	}

	public void setTiposTramites(List<String> tiposTramites) {
		this.tiposTramites = tiposTramites;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public List<BigDecimal> getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(List<BigDecimal> estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getImprPermisoCircu() {
		return imprPermisoCircu;
	}

	public void setImprPermisoCircu(String imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public Boolean getbDocPermiso() {
		return bDocPermiso;
	}

	public void setbDocPermiso(Boolean bDocPermiso) {
		this.bDocPermiso = bDocPermiso;
	}

	public Boolean getbDocFicha() {
		return bDocFicha;
	}

	public void setbDocFicha(Boolean bDocFicha) {
		this.bDocFicha = bDocFicha;
	}

}
