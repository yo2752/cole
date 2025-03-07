package org.gestoresmadrid.oegam2.utiles;

import org.gestoresmadrid.core.proceso.model.enumerados.PatronProcesos;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaProceso {

	private static UtilesVistaProceso utilesVistaProceso;

	public static UtilesVistaProceso getInstance() {
		if (utilesVistaProceso == null) {
			utilesVistaProceso = new UtilesVistaProceso();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaProceso);
		}
		return utilesVistaProceso;
	}

	public static PatronProcesos[] getListaPatronesProcesos() {
		return PatronProcesos.values();
	}

}