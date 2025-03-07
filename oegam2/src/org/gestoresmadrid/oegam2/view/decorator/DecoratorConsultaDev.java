package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ConsultaDevBean;

public class DecoratorConsultaDev extends TableDecorator {

	public String addRowClass() {
		ConsultaDevBean consultaDevBean = (ConsultaDevBean) getCurrentRowObject();
		if (EstadoConsultaDev.Anulada.getNombreEnum().equals(consultaDevBean.getEstado())) {
			return "enlaceAnulado anulado";
		} else if (EstadoConsultaDev.Finalizado.getNombreEnum().equals(consultaDevBean.getEstado())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

}