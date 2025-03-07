package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionTasaDaoImpl extends GenericDaoImplHibernate<EvolucionTasaVO> implements EvolucionTasaDao {

	private static final long serialVersionUID = 3294279825292259718L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionTasaVO> getListaEvolucionesPorTasa(String codigoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionTasaVO.class);
		if(codigoTasa != null && !codigoTasa.isEmpty()){
			criteria.add(Restrictions.eq("id.codigoTasa", codigoTasa));
		}
		List<EvolucionTasaVO> lista = (List<EvolucionTasaVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}