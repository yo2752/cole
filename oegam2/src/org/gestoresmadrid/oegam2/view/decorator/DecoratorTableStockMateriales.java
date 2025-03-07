package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.StockResultadosBean;

public class DecoratorTableStockMateriales extends TableDecorator {
	private static final String NEGRO = "negro";
	private static final String ROJO = "rojo";
	private static final String AMARILLO = "amarillo";
	private static final String NORMAL = "normal";

	public String addRowClass() {
		String clase = NORMAL;

		if (getCurrentRowObject() instanceof StockResultadosBean) {
			StockResultadosBean stockRow = (StockResultadosBean) getCurrentRowObject();
			double stock = stockRow.getUnidades();
			if (stock == 0.0) {
				clase = NEGRO;
			} else if (stock > 0.0 && stock <= 10.0) {
				clase = ROJO;
			} else if (stock > 10.0 && stock <= 20.0) {
				clase = AMARILLO;
			} else {
				clase = NORMAL;
			}
		}

		return clase;
	}
}