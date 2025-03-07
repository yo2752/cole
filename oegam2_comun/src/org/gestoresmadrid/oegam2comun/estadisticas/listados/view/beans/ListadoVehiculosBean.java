package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.hibernate.Criteria;

import utilidades.estructuras.FechaFraccionada;

public class ListadoVehiculosBean {

	@FilterSimpleExpression(name = "fechaPrimMatri", restriction = FilterSimpleExpressionRestriction.ISNOTNULL)
	@FilterRelationship(name="vehiculo", joinType = Criteria.INNER_JOIN)
	private boolean fechaPrimMatri = false;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name = "tipoTramite", restriction = FilterSimpleExpressionRestriction.IN)
	private List<String> listaTipoTramite = Arrays.asList(TipoTramiteTrafico.Transmision.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum());

	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;

	@FilterSimpleExpression(name = "estado", restriction = FilterSimpleExpressionRestriction.IN)
	private List<BigDecimal> listaEstados = Arrays.asList(new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente
			.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));

	@FilterSimpleExpression(name = "estado")
	private BigDecimal estado;
	
	private String agrupacionVehiculos;

	public String getAgrupacionVehiculos() {
		return agrupacionVehiculos;
	}

	public void setAgrupacionVehiculos(String agrupacionVehiculos) {
		this.agrupacionVehiculos = agrupacionVehiculos;
	}

	public ListadoVehiculosBean() {}

	public boolean isFechaPrimMatri() {
		return fechaPrimMatri;
	}

	public void setFechaPrimMatri(boolean fechaPrimMatri) {
		this.fechaPrimMatri = fechaPrimMatri;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public List<String> getListaTipoTramite() {
		return listaTipoTramite;
	}

	public void setListaTipoTramite(List<String> listaTipoTramite) {
		this.listaTipoTramite = listaTipoTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public List<BigDecimal> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<BigDecimal> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

}
