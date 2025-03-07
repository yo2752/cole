package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.RemesaBean;

public class DecoratorRemesa extends TableDecorator {

	public String addRowClass() {
		RemesaBean remesaBean = (RemesaBean) getCurrentRowObject();
		if (EstadoRemesas.Anulada.getValorEnum().equals(remesaBean.getEstado().toString())) {
			return "enlaceAnulado anulado";
		}
		if (EstadoRemesas.Generada.getValorEnum().equals(remesaBean.getEstado().toString())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

}