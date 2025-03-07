package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.VehiculoRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.VehiculoRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoRegistroDaoImpl extends GenericDaoImplHibernate<VehiculoRegistroVO> implements VehiculoRegistroDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3331933745380122617L;

	@Override
	public VehiculoRegistroVO getVehiculoRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(VehiculoRegistroVO.class);
		criteria.add(Restrictions.eq("idVehiculo", Long.parseLong(id)));
		return (VehiculoRegistroVO) criteria.uniqueResult();
	}
	
	public VehiculoRegistroVO getVehiculoPorPropiedad(BigDecimal id){
		Criteria criteria = getCurrentSession().createCriteria(VehiculoRegistroVO.class);
		criteria.add(Restrictions.eq("idPropiedad", id));
		return (VehiculoRegistroVO)criteria.uniqueResult();
	}
	
}
