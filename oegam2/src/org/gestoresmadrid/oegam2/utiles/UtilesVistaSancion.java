package org.gestoresmadrid.oegam2.utiles;

import org.gestoresmadrid.core.sancion.model.enumerados.EstadoSancion;
import org.gestoresmadrid.core.sancion.model.enumerados.Motivo;
import org.gestoresmadrid.core.sancion.model.enumerados.TipoImpresion;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaSancion {

	private static UtilesVistaSancion utilesVistaSancion;

	private UtilesVistaSancion() {
		super();
	}

	public static UtilesVistaSancion getInstance() {
		if (utilesVistaSancion == null) {
			utilesVistaSancion = new UtilesVistaSancion();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaSancion);
		}
		return utilesVistaSancion;
	}

	public Motivo[] getMotivos() {
		return Motivo.values();
	}

	public EstadoSancion[] getEstadoSancion() {
		return EstadoSancion.values();
	}

	public TipoImpresion[] getTipoImpresion() {
		return TipoImpresion.values();
	}
}