package org.gestoresmadrid.core.importacionFichero.model.dao.impl;

import org.gestoresmadrid.core.importacionFichero.model.dao.ControlContratoImpDao;
import org.gestoresmadrid.core.importacionFichero.model.vo.ControlContratoImpVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ControlContratoImpDaoImpl extends GenericDaoImplHibernate<ControlContratoImpVO> implements ControlContratoImpDao {

	private static final long serialVersionUID = -3689405144912891173L;

	@Override
	public ControlContratoImpVO getControlContratoImp(Long idContrato, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(ControlContratoImpVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.add(Restrictions.eq("tipo", tipo));
		return (ControlContratoImpVO) criteria.uniqueResult();
	}
}
