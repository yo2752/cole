package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaVehNoMatrOegamFilterBean implements Serializable{


	private static final long serialVersionUID = 2668743226757194204L;

	@FilterSimpleExpression(name ="matricula")
	private String matricula;
		
	@FilterSimpleExpression(name="tipoDistintivo")
	private String tipoDistintivo;
	
	@FilterSimpleExpression(name="bastidor",restriction = FilterSimpleExpressionRestriction.LIKE)
	private String bastidor;
	
	@FilterSimpleExpression(name="nive",restriction = FilterSimpleExpressionRestriction.LIKE)
	private String nive;

	@FilterSimpleExpression(name = "idContrato")
	@FilterRelationship(name="contrato", joinType = Criteria.LEFT_JOIN)
	private Long idContrato;
	
	@FilterSimpleExpression(name = "fechaAlta", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaAlta;
	
	@FilterSimpleExpression(name="estadoImpresion")
	private String estadoImpresionDstv;
	
	@FilterSimpleExpression(name="estadoSolicitud")
	private String estadoPeticionDstv;
	
	@FilterSimpleExpression(name="primeraImpresion")
	private String primeraImpresion;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getBastidor() {
		if(bastidor != null && !bastidor.isEmpty()){
			if(bastidor.contains("%")) {
				bastidor = bastidor.replace("%", "");
			}
		}
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

	public String getEstadoImpresionDstv() {
		return estadoImpresionDstv;
	}

	public void setEstadoImpresionDstv(String estadoImpresionDstv) {
		this.estadoImpresionDstv = estadoImpresionDstv;
	}

	public String getEstadoPeticionDstv() {
		return estadoPeticionDstv;
	}

	public void setEstadoPeticionDstv(String estadoPeticionDstv) {
		this.estadoPeticionDstv = estadoPeticionDstv;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNive() {
		if(nive != null && !nive.isEmpty()){
			if(nive.contains("%")) {
				nive = nive.replace("%", "");
			}
		}
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getPrimeraImpresion() {
		return primeraImpresion;
	}

	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}
	

}
