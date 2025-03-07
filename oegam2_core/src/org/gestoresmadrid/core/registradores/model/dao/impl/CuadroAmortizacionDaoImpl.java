package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.CuadroAmortizacionDao;
import org.gestoresmadrid.core.registradores.model.vo.CuadroAmortizacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CuadroAmortizacionDaoImpl extends GenericDaoImplHibernate<CuadroAmortizacionVO> implements CuadroAmortizacionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7657877357247522405L;

	@Override
	public CuadroAmortizacionVO getCuadroAmortizacion(String id) {
		Criteria criteria = getCurrentSession().createCriteria(CuadroAmortizacionVO.class);
		criteria.add(Restrictions.eq("idCuadroAmortizacion", Long.parseLong(id)));
		return (CuadroAmortizacionVO) criteria.uniqueResult();
	}

}
