package org.gestoresmadrid.oegam2comun.trafico.inteve.model.service;

import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;

public interface ServicioInteveWeb {

	/**
	 * Realiza las llamadas a la web de inteve3
	 * 
	 * @param solicitudInforme
	 * @param numColegiado 
	 * @param resultBean
	 * @return
	 */
	ResultInteveBean navegacionweb(SolicitudInformeVehiculoDto solicitudInforme, String numColegiado, ResultInteveBean resultBean);

}
