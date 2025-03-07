package org.gestoresmadrid.oegam2.utiles;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaTransmision {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaTransmision.class);

	private static UtilesVistaTransmision utilesVistaTransmision;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaTransmision getInstance() {
		if (utilesVistaTransmision == null) {
			utilesVistaTransmision = new UtilesVistaTransmision();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaTransmision);
		}
		return utilesVistaTransmision;
	}

	public boolean esComprobableTransmision(TramiteTrafDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)
					&& (tramiteTrafico.getEstado() == null || tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado))) {
				return true;
			}
			return false;
		}
		return false;
	}
}