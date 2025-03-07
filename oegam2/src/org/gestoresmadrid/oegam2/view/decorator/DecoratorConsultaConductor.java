package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorBean;

public class DecoratorConsultaConductor extends TableDecorator {

	public String addRowClass() {
		ConsultaConductorBean tramiteConductor = (ConsultaConductorBean) getCurrentRowObject();
		if (EstadoCaycEnum.Finalizado.getValorEnum()
				.equals(EstadoCaycEnum.convertirNombre(tramiteConductor.getEstadotxt()))) {
			return "enlaceImpreso impreso";
		} else if (EstadoCaycEnum.Anulado.getValorEnum()
				.equals(EstadoCaycEnum.convertirNombre(tramiteConductor.getEstadotxt()))) {
			return "enlaceAnulado anulado";
		}
		return null;
	}

}