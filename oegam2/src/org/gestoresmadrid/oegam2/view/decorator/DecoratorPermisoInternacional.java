package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ConsultaPermisoInternacionalBean;

public class DecoratorPermisoInternacional extends TableDecorator {

	public String addRowClass() {
		ConsultaPermisoInternacionalBean bean = (ConsultaPermisoInternacionalBean) getCurrentRowObject();
		if (EstadoTramitesInterga.Anulado.getNombreEnum().equals(bean.getEstado())) {
			return "enlaceAnulado anulado";
		} else if (EstadoTramitesInterga.Finalizado.getNombreEnum().equals(bean.getEstado())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}
}
