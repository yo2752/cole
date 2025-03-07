package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.HistoricoCreditoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface HistoricoCreditoDao extends GenericDao<HistoricoCreditoVO>, Serializable {

	public List<BigDecimal[]> cantidadesTotales(Long idContrato, String tipoCredito, Date fechaDesde, Date fechaHasta) throws Exception;

	public List<HistoricoCreditoVO> consultaCreditosAcumuladosMesPorColegiado(Long idContrato);
}
