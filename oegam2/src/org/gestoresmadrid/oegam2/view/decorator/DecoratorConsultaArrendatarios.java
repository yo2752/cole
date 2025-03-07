package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ConsultaArrendatarioBean;

public class DecoratorConsultaArrendatarios extends TableDecorator {

	public String addRowClass() {
		ConsultaArrendatarioBean tramiteArrendatario = (ConsultaArrendatarioBean) getCurrentRowObject();
		if (EstadoCaycEnum.Finalizado.getValorEnum()
				.equals(EstadoCaycEnum.convertirNombre(tramiteArrendatario.getEstadotxt()))) {
			return "enlaceImpreso impreso";
		} else if (EstadoCaycEnum.Anulado.getValorEnum()
				.equals(EstadoCaycEnum.convertirNombre(tramiteArrendatario.getEstadotxt()))) {
			return "enlaceAnulado anulado";
		}
		return null;
	}

}