package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.EvolucionTramiteRegistroVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorEvolucionTramiteRegistro extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorEvolucionTramiteRegistro() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaCambio() throws ParseException {
		EvolucionTramiteRegistroVO evolucion = (EvolucionTramiteRegistroVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getFechaCambio() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucion.getId().getFechaCambio());
		} else {
			return "";
		}
	}

	public String getEstadoAnterior() throws ParseException {
		EvolucionTramiteRegistroVO evolucion = (EvolucionTramiteRegistroVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstadoAnterior() != null) {
			return EstadoTramiteRegistro.convertirTexto(evolucion.getId().getEstadoAnterior().toString());
		} else {
			return "";
		}
	}

	public String getEstadoNuevo() throws ParseException {
		EvolucionTramiteRegistroVO evolucion = (EvolucionTramiteRegistroVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstadoNuevo() != null) {
			return EstadoTramiteRegistro.convertirTexto(evolucion.getId().getEstadoNuevo().toString());
		} else {
			return "";
		}
	}
}
