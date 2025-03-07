package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorEvolucionTramite extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorEvolucionTramite() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaCambio() throws ParseException {
		EvolucionTramiteTraficoVO evolucion = (EvolucionTramiteTraficoVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getFechaCambio() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucion.getId().getFechaCambio());
		} else {
			return "";
		}
	}

	public String getEstadoAnterior() throws ParseException {
		EvolucionTramiteTraficoVO evolucion = (EvolucionTramiteTraficoVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstadoAnterior() != null) {
			return EstadoTramiteTrafico.convertirTexto(evolucion.getId().getEstadoAnterior().toString());
		} else {
			return "";
		}
	}

	public String getEstadoNuevo() throws ParseException {
		EvolucionTramiteTraficoVO evolucion = (EvolucionTramiteTraficoVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstadoNuevo() != null) {
			return EstadoTramiteTrafico.convertirTexto(evolucion.getId().getEstadoNuevo().toString());
		} else {
			return "";
		}
	}
}
