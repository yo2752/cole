package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveBean;

public class DecoratorTramiteInteve extends TableDecorator {

	public String addRowClass() {
		ConsultaTramiteInteveBean tramiteInteveBean = (ConsultaTramiteInteveBean) getCurrentRowObject();
		if (EstadoTramiteTrafico.Anulado.getNombreEnum().equals(tramiteInteveBean.getEstado())) {
			return "enlaceAnulado anulado";
		}
		if (EstadoTramiteTrafico.Finalizado_Telematicamente.getNombreEnum().equals(tramiteInteveBean.getEstado())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}
}