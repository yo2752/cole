package org.gestoresmadrid.oegam2.view.decorator;

import java.math.BigDecimal;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.oegam2comun.cola.view.bean.SolicitudesColaBean;

public class GestorColaDecorator extends TableDecorator {

	public String getEstado() {
		SolicitudesColaBean solicitudesColaBean = (SolicitudesColaBean) getCurrentRowObject();
		if (solicitudesColaBean != null && solicitudesColaBean.getEstado() != null
				&& !solicitudesColaBean.getEstado().isEmpty()) {
			return EstadoCola.convertirEstadoBigDecimal(new BigDecimal(solicitudesColaBean.getEstado()));
		}
		return "";
	}

}