package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ListadoGeneralYPersonalizadoBean {

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "idTipoCreacion")
	private BigDecimal idTipoCreacion;

	@FilterSimpleExpression(name = "contrato.idProvincia")
	private String idProvincia;

	@FilterSimpleExpression(name = "contratoJefaturaTrafico.jefaturaProvincial")
	private String jefaturaProvincial;

	@FilterSimpleExpression(name = "estado", restriction = FilterSimpleExpressionRestriction.IN)
	List<BigDecimal> listaEstados;

	private String[] listaEstadosPantalla;

	@FilterSimpleExpression(name = "tipoTramite")
	private String tipoTramite;

	@FilterSimpleExpression(name = "vehiculo.tipoVehiculo")
	private String tipoVehiculo;

	@FilterSimpleExpression(name = "vehiculo.codigoMarca")
	private String codigoMarca;

	@FilterSimpleExpression(name = "intervinienteTraficosTipoIntervinienteVO.tipoInterviniente")
	private String tipoInterviniente;

	private String agrupacion;

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public List<BigDecimal> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<BigDecimal> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public String[] getListaEstadosPantalla() {
		return listaEstadosPantalla;
	}

	public void setListaEstadosPantalla(String[] listaEstadosPantalla) {
		this.listaEstadosPantalla = listaEstadosPantalla;
		if (listaEstadosPantalla != null && StringUtils.isNotBlank(listaEstadosPantalla[0])) {
			listaEstados = new ArrayList<>();
			for (String estado : listaEstadosPantalla) {
				if (StringUtils.isNotBlank(estado)) {
					listaEstados.add(new BigDecimal(estado));
				}
			}
		}
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

}
