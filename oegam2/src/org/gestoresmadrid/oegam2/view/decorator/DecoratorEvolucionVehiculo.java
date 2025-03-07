package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorEvolucionVehiculo extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorEvolucionVehiculo() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaHora() throws ParseException {
		EvolucionVehiculoDto evolucion = (EvolucionVehiculoDto) getCurrentRowObject();
		if (evolucion.getFechaHora() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucion.getFechaHora().getTimestamp());
		} else {
			return "";
		}
	}

	public String getTipoTramite() {
		EvolucionVehiculoDto evolucion = (EvolucionVehiculoDto) getCurrentRowObject();
		if (evolucion.getTipoTramite() != null) {
			if (ServicioVehiculo.TIPO_TRAMITE_MANTENIMIENTO.equals(evolucion.getTipoTramite())) {
				return "MANTENIMIENTO";
			} else if (ServicioVehiculo.TIPO_TRAMITE_MATE_EITV.equals(evolucion.getTipoTramite())) {
				return "MATE EITV";
			} else {
				return TipoTramiteTrafico.convertirTexto(evolucion.getTipoTramite());
			}
		} else {
			return "";
		}
	}
}
