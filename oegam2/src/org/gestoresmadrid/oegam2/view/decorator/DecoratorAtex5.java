package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaPersonaAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaVehiculoAtex5Bean;

public class DecoratorAtex5 extends TableDecorator {

	public String addRowClass() {
		String estado = null;
		if (getCurrentRowObject() instanceof ConsultaPersonaAtex5Bean) {
			ConsultaPersonaAtex5Bean conPersonaAtex5Bean = (ConsultaPersonaAtex5Bean) getCurrentRowObject();
			estado = conPersonaAtex5Bean.getEstado();
		} else if (getCurrentRowObject() instanceof ConsultaVehiculoAtex5Bean) {
			ConsultaVehiculoAtex5Bean conVehiculoAtex5Bean = (ConsultaVehiculoAtex5Bean) getCurrentRowObject();
			estado = conVehiculoAtex5Bean.getEstado();
		}
		if (EstadoAtex5.Anulado.getNombreEnum().equals(estado)) {
			return "enlaceAnulado anulado";
		}
		if (EstadoAtex5.Finalizado_PDF.getNombreEnum().equals(estado)
				|| EstadoAtex5.Finalizado_Sin_Antecedentes.getNombreEnum().equals(estado)) {
			return "enlaceImpreso impreso";
		}
		return null;
	}
}