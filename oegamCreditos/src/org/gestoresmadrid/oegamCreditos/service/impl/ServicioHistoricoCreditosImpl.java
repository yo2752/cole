package org.gestoresmadrid.oegamCreditos.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.oegamCreditos.service.ServicioHistoricoCreditos;
import org.gestoresmadrid.oegamCreditos.service.ServicioPersistenciaCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioHistoricoCreditosImpl implements ServicioHistoricoCreditos {

	private static final long serialVersionUID = 3838085371911847195L;

	@Autowired
	private ServicioPersistenciaCreditos servicioPersistenciaCreditos;

	@Override
	public ResultCreditosBean cantidadesTotalesHistorico(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception {
		ResultCreditosBean result = new ResultCreditosBean();
		List<BigDecimal[]> listaCantidades = servicioPersistenciaCreditos.cantidadesTotalesHistorico(idContrato, tipoCredito, fechaDesde, fechaHasta);

		if (listaCantidades != null && listaCantidades.size() == 1) {
			Object[] cantidades = listaCantidades.get(0);
			if (cantidades != null && cantidades.length == 2) {
				result.addAttachment(CANTIDAD_SUMADA_TOTAL, cantidades[0]);
				result.addAttachment(CANTIDAD_RESTADA_TOTAL, cantidades[1]);
			}
		} else {
			result.setError(true);
		}

		return result;
	}

	@Override
	public String obtenerDescripcionTipoCredito(String tipoCredito) {
		TipoCreditoVO tipoCreditoVO = servicioPersistenciaCreditos.getTipoCredito(tipoCredito);
		if (tipoCreditoVO != null) {
			return tipoCreditoVO.getDescripcion();
		}
		return null;
	}
}