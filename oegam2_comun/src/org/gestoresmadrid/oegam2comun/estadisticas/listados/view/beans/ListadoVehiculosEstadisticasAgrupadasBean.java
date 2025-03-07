package org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans;

/**
 * Bean que devuelve la lista de vehículos agrupados
 */

public class ListadoVehiculosEstadisticasAgrupadasBean {

	private String campoAgrupacion;
	private Integer numRegistros;
	private Integer confechaPrimMatriculacion;

	public ListadoVehiculosEstadisticasAgrupadasBean() {}

	public String getCampoAgrupacion() {
		return campoAgrupacion;
	}

	public void setCampoAgrupacion(String campoAgrupacion) {
		this.campoAgrupacion = campoAgrupacion;
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public Integer getConfechaPrimMatriculacion() {
		return confechaPrimMatriculacion;
	}

	public void setConfechaPrimMatriculacion(Integer confechaPrimMatriculacion) {
		this.confechaPrimMatriculacion = confechaPrimMatriculacion;
	}

}