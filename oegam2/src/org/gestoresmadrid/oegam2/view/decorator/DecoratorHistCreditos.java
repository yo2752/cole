package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorHistCreditos extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorHistCreditos() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFecha() throws ParseException {
		HistoricoCreditoVO historico = (HistoricoCreditoVO) getCurrentRowObject();
		if (historico.getFecha() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", historico.getFecha());
		} else {
			return "";
		}
	}
}
