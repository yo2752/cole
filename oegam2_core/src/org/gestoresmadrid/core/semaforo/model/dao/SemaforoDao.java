package org.gestoresmadrid.core.semaforo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.semaforo.model.vo.SemaforoVO;

public interface SemaforoDao extends Serializable, GenericDao<SemaforoVO> {
	public SemaforoVO obtenerSemaforoVO(SemaforoVO semaforo);
	public SemaforoVO obtenerSemaforoVO(Long idSemaforo);
	public List<SemaforoVO> obtenerListadoSemaforosSes(String nodo);
}
