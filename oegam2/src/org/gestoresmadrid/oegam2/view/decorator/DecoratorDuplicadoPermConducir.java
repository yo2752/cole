package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ConsultaDuplicadoPermConducirBean;

public class DecoratorDuplicadoPermConducir extends TableDecorator {

	public String addRowClass() {
		ConsultaDuplicadoPermConducirBean bean = (ConsultaDuplicadoPermConducirBean) getCurrentRowObject();
		if (EstadoTramitesInterga.Anulado.getNombreEnum().equals(bean.getEstado())) {
			return "enlaceAnulado anulado";
		} else if (EstadoTramitesInterga.Finalizado.getNombreEnum().equals(bean.getEstado()) && EstadoTramitesInterga.Impresion_OK.getNombreEnum().equals(bean.getEstadoDoc())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}
}
