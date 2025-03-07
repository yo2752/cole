package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorEvolucionPersona extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorEvolucionPersona() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaHora() throws ParseException {
		EvolucionPersonaDto evolucion = (EvolucionPersonaDto) getCurrentRowObject();
		if (evolucion.getFechaHora() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucion.getFechaHora().getTimestamp());
		} else {
			return "";
		}
	}

	public String getTipoTramite() {
		EvolucionPersonaDto evolucion = (EvolucionPersonaDto) getCurrentRowObject();
		if (evolucion.getTipoTramite() != null) {
			if (ServicioPersona.TIPO_TRAMITE_MANTENIMIENTO.equals(evolucion.getTipoTramite())) {
				return "MANTENIMIENTO";
			} else if (ServicioPersona.TIPO_TRAMITE_CONTRATO.equals(evolucion.getTipoTramite())) {
				return "CONTRATO";
			} else {
				return TipoTramiteTrafico.convertirTexto(evolucion.getTipoTramite());
			}
		} else {
			return "";
		}
	}
}
