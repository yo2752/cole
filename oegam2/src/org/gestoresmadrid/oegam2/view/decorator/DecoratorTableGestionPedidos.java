package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PedidosResultadosBean;

public class DecoratorTableGestionPedidos extends TableDecorator {

	private static final String CHECKED = "checked";
	private static final String NOCHECKED = "nochecked";

	public String addRowClass() {
		String clase = "";

		if (getCurrentRowObject() instanceof PedidosResultadosBean) {
			PedidosResultadosBean pedidosRow = (PedidosResultadosBean) getCurrentRowObject();
			String pedidoDgt = pedidosRow.getPedidoDgt();
			if ("Y".equals(pedidoDgt)) {
				clase = CHECKED;
			} else {
				clase = NOCHECKED;
			}
		}

		return clase;
	}

}