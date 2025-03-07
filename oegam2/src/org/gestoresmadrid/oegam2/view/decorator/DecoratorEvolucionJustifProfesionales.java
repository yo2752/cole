package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorEvolucionJustifProfesionales extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorEvolucionJustifProfesionales() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaCambio() throws ParseException {
		EvolucionJustifProfesionalesVO evolucion = (EvolucionJustifProfesionalesVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getFechaCambio() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucion.getId().getFechaCambio());
		} else {
			return "";
		}
	}

	public String getEstadoAnterior() throws ParseException {
		EvolucionJustifProfesionalesVO evolucion = (EvolucionJustifProfesionalesVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstadoAnterior() != null) {
			return EstadoTramiteRegistro.convertirTexto(evolucion.getId().getEstadoAnterior().toString());
		} else {
			return "";
		}
	}

	public String getEstadoNuevo() throws ParseException {
		EvolucionJustifProfesionalesVO evolucion = (EvolucionJustifProfesionalesVO) getCurrentRowObject();
		if (evolucion.getId() != null && evolucion.getId().getEstado() != null) {
			return EstadoJustificante.convertirTexto(evolucion.getId().getEstado().toString());
		} else {
			return "";
		}
	}
}
