package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;

public class DecoratorConsultaEEFF extends TableDecorator {

	public String getRespuesta() {
		EeffConsultaVO fila = (EeffConsultaVO) getCurrentRowObject();
		if (fila.getRespuesta() == null) {
			return null;
		}

		return fila.getRespuesta().substring(0, 4).equals("EEFF") ? "ERROR" : fila.getRespuesta();

	}
}