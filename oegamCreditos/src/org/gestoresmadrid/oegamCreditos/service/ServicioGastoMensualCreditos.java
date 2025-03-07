package org.gestoresmadrid.oegamCreditos.service;

import java.io.Serializable;

import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.oegamCreditos.view.bean.GastoMensualCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;

/**
 * Servicio destinado a CreditoFacturadoVO
 */
public interface ServicioGastoMensualCreditos extends Serializable {

	public CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized);

	public ResultCreditosBean listarTablaCompleta(GastoMensualCreditosBean filtro);
}
