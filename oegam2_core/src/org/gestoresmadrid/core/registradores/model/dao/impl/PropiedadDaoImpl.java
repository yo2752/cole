package org.gestoresmadrid.core.registradores.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PropiedadDaoImpl extends GenericDaoImplHibernate<PropiedadVO> implements PropiedadDao {

	private static final long serialVersionUID = -5497929372335687806L;
	
	@Override
	public PropiedadVO getPropiedad(String id) {
		Criteria criteria = getCurrentSession().createCriteria(PropiedadVO.class);
		criteria.add(Restrictions.eq("idPropiedad", Long.parseLong(id)));
		criteria.createAlias("intervinienteRegistro", "intervinienteRegistro", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("buque", "buque", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("buque.motorBuques", "buqueMotorBuques", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		return (PropiedadVO) criteria.uniqueResult();
	}
}