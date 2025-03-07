package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.imputaciones.model.vo.ImputacionHorasVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorImputacionHoras extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorImputacionHoras() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFecha() {
		ImputacionHorasVO fila = (ImputacionHorasVO) getCurrentRowObject();

		if (fila.getFechaImputacion() == null) {
			return "";
		}

		return utilesFecha.formatoFecha("dd/MM/yyyy", fila.getFechaImputacion());
	}

}