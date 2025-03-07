package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MotorBuqueDao;
import org.gestoresmadrid.core.registradores.model.vo.MotorBuqueVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MotorBuqueDaoImpl extends GenericDaoImplHibernate<MotorBuqueVO> implements MotorBuqueDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6177097528284241763L;
	
	@Override
	public MotorBuqueVO getMotorBuque(String id) {
		Criteria criteria = getCurrentSession().createCriteria(MotorBuqueVO.class);
		criteria.add(Restrictions.eq("idMotor", Long.parseLong(id)));
		return (MotorBuqueVO) criteria.uniqueResult();
	}
	
	public List<MotorBuqueVO> getMotoresPorBuque(long id){
		Criteria criteria = getCurrentSession().createCriteria(MotorBuqueVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idBuque", id));
		}
		@SuppressWarnings("unchecked")
		List<MotorBuqueVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
		
	}

}
