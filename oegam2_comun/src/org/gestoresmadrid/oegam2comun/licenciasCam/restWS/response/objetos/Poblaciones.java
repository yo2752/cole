
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;
import java.util.List;

public class Poblaciones implements Serializable {

	private static final long serialVersionUID = -2965955506513389567L;

	private List<Poblacion> poblacion;

	private Integer numeroPoblaciones;

	public List<Poblacion> getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(List<Poblacion> poblacion) {
		this.poblacion = poblacion;
	}

	public Integer getNumeroPoblaciones() {
		return numeroPoblaciones;
	}

	public void setNumeroPoblaciones(Integer numeroPoblaciones) {
		this.numeroPoblaciones = numeroPoblaciones;
	}
}
