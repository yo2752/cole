package org.gestoresmadrid.oegam2comun.creditos.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.general.model.dao.HistoricoCreditoDao;
import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioHistoricoCredito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioHistoricoCreditoImpl implements ServicioHistoricoCredito {

	private static final long serialVersionUID = 3838085371911847195L;

	@Autowired
	private HistoricoCreditoDao historicoCreditoDao;

	@Override
	public void anotarGasto(String tipoCredito, Long idContrato, BigDecimal idUsuario, BigDecimal cantidadSumada, BigDecimal cantidadRestada) {
		HistoricoCreditoVO historicoCreditoVO = new HistoricoCreditoVO();
		historicoCreditoVO.setCantidadRestada(cantidadRestada);
		historicoCreditoVO.setCantidadSumada(cantidadSumada);
		historicoCreditoVO.setFecha(new Date());
		historicoCreditoVO.setIdContrato(idContrato);
		historicoCreditoVO.setTipoCredito(tipoCredito);
		historicoCreditoVO.setIdUsuario(idUsuario.longValue());
		historicoCreditoDao.guardar(historicoCreditoVO);
	}
}