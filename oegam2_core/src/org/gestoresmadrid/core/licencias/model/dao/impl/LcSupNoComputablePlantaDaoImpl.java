package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcSupNoComputablePlantaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcSupNoComputablePlantaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcSupNoComputablePlantaDaoImpl extends GenericDaoImplHibernate<LcSupNoComputablePlantaVO> implements LcSupNoComputablePlantaDao {

	private static final long serialVersionUID = -6531629929974359646L;

	@Override
	public LcSupNoComputablePlantaVO getSupNoComputablePlanta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcSupNoComputablePlantaVO.class);
		criteria.add(Restrictions.eq("idSupNoComputablePlanta", id));
		return (LcSupNoComputablePlantaVO) criteria.uniqueResult();
	}

	@Override
	public List<LcSupNoComputablePlantaVO> getSupNoComputablesPlanta(long id) {
		Criteria criteria = getCurrentSession().createCriteria(LcSupNoComputablePlantaVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idDatosPlantaAlta", id));
		}
		@SuppressWarnings("unchecked")
		List<LcSupNoComputablePlantaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
