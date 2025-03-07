package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesResultadosBean;

public class DecoratorTableMateriales extends TableDecorator {
	private static final String ACTIVO = "activo";
	private static final String INACTIVO = "inactivo";

	public String addRowClass() {
		String clase = ACTIVO;

		if (getCurrentRowObject() instanceof EvolucionMaterialesResultadosBean) {
			EvolucionMaterialesResultadosBean evolucion = (EvolucionMaterialesResultadosBean) getCurrentRowObject();
			if (EstadoMaterial.Activo.getNombreEnum().equals(evolucion.getEstado())) {
				clase = ACTIVO;
			} else if (EstadoMaterial.Inactivo.getNombreEnum().equals(evolucion.getEstado())) {
				clase = INACTIVO;
			}
		}

		return clase;
	}
}