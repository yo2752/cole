package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;

import escrituras.beans.ResultBean;

public interface ServicioInformeAvpo {

	/**
	 * 
	 * @param solicitudes
	 * @param idUsuario
	 * @param numColegiado
	 * @param idContrato
	 * @return
	 */
	ResultBean obtenerInformeTramite(List<TramiteTrafSolInfoVehiculoVO> solicitudes, BigDecimal idUsuario, String numColegiado, BigDecimal idContrato);

}
