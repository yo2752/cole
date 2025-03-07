package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.hibernate.HibernateException;

public interface TasaPegatinaDao extends GenericDao<TasaPegatinaVO>, Serializable {

	/**
	 * Recupera el detalle de una tasa de pegatina por su codigo
	 * @param codigoTasa
	 * @return
	 * @throws HibernateException si encuentra mas de una tasa
	 */
	TasaPegatinaVO detalle(String codigoTasa);

	List<TasaPegatinaVO> obtenerTasasPegatinaContrato(Long idContrato, String tipoTasa);

	List<TasaPegatinaVO> getTasasPorLista(List<String> listaTasas);
	
	TasaPegatinaVO getTasaPegatina(String codigoTasa, BigDecimal numExpediente, String tipoTasa);
}
