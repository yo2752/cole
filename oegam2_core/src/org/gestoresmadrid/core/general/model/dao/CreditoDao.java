package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.CreditoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface CreditoDao extends GenericDao<CreditoVO>, Serializable {

	public CreditoVO getCredito(String tipoCredito, Long idContrato);

	public CreditoVO getCreditoConTipoCredito(String tipoCredito, Long idContrato);

	public List<CreditoVO> consultaCreditosDisponiblesPorColegiado(Long idContrato);
}
