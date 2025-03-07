package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.core.evolucionSemaforo.model.vo.EvolucionSemaforoVO;

public class DecoratorEvolSemaforo extends TableDecorator {

	public String getOperacion() {
		EvolucionSemaforoVO fila = (EvolucionSemaforoVO) getCurrentRowObject();

		return OperacionEvolSemaforo.convertirTexto(fila.getOperacion());
	}

}