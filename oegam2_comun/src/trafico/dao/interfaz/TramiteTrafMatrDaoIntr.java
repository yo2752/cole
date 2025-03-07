package trafico.dao.interfaz;

import hibernate.entities.trafico.TramiteTrafMatr;
import hibernate.entities.trafico.TramiteTrafico;
import trafico.dao.GenericDao;

public interface TramiteTrafMatrDaoIntr extends GenericDao<TramiteTrafMatr> {

	/**
	 * Devuelve la entidad TramiteTrafMatr por Identificador
	 * 
	 * @param id - identificador
	 * @return
	 */
	public TramiteTrafico getTramiteTrafMatr(Long id);

}