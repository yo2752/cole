package org.gestoresmadrid.oegam2.view.decorator;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.oegamImpresion.view.bean.ConsultaImpresionBean;

public class DecoratorImpresion extends TableDecorator {

	public String getEstado() {
		ConsultaImpresionBean consultaImpresion = (ConsultaImpresionBean) getCurrentRowObject();
		String mensaje = consultaImpresion.getEstado();
		if (mensaje != null && EstadosImprimir.Finalizado_Error.getNombreEnum().equals(mensaje) && StringUtils.isNotBlank(consultaImpresion.getMensaje())) {
			mensaje += " <img style='height:20px;width:20px;cursor:pointer;' src='img/mostrar.gif' alt='" + consultaImpresion.getMensaje() + "' title='" + consultaImpresion.getMensaje() + "'/>";
		}
		return mensaje;
	}
}
