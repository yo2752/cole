package org.gestoresmadrid.oegam2comun.direccion.view.beans;

import java.util.List;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class SeleccionarDireccionBean {

	@FilterSimpleExpression(name = "id.nif")
	private String nif;

	@FilterSimpleExpression(name = "id.numColegiado")
	private String numColegiado;

	@FilterSimpleExpression(name = "estado", restriction = FilterSimpleExpressionRestriction.IN_WITH_NULL)
	private List<Short> estadosDirecciones;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public List<Short> getEstadosDirecciones() {
		return estadosDirecciones;
	}

	public void setEstadosDirecciones(List<Short> estadosDirecciones) {
		this.estadosDirecciones = estadosDirecciones;
	}
}
