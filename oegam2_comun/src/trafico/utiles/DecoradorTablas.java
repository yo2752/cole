package trafico.utiles;

import org.displaytag.decorator.TableDecorator;

import trafico.beans.TramiteTraficoBean;

public class DecoradorTablas extends TableDecorator {

	public DecoradorTablas() {

	}

	public String addRowClass() {
		TramiteTraficoBean row = (TramiteTraficoBean) getCurrentRowObject();

		Integer impreso = Integer.parseInt(row.getEstado().getValorEnum());

		if (impreso != null && (impreso == 13 || impreso == 14 || impreso == 21)) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

}
