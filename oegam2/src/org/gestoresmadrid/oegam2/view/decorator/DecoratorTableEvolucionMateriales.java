package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesResultadosBean;

public class DecoratorTableEvolucionMateriales extends TableDecorator {
	private static final String GRIS = "gris";
	private static final String NORMAL = "normal";

	public String addRowClass() {
		String clase = NORMAL;

		if (getCurrentRowObject() instanceof EvolucionMaterialesResultadosBean) {
			EvolucionMaterialesResultadosBean evolucion = (EvolucionMaterialesResultadosBean) getCurrentRowObject();
			Long selEvoMatId = (Long) this.getPageContext().getAttribute("selEvoMatId");
			if (evolucion.getEvolucionMaterialId().equals(selEvoMatId)) {
				clase = GRIS;
			}
		}
		return clase;
	}

}