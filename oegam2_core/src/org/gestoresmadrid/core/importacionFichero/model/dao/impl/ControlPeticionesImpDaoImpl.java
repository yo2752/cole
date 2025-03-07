package org.gestoresmadrid.core.importacionFichero.model.dao.impl;

import org.gestoresmadrid.core.importacionFichero.model.dao.ControlPeticionesImpDao;
import org.gestoresmadrid.core.importacionFichero.model.vo.ControlPeticionesImpVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ControlPeticionesImpDaoImpl extends GenericDaoImplHibernate<ControlPeticionesImpVO> implements ControlPeticionesImpDao {

	private static final long serialVersionUID = 583755640132074711L;
	
	@Override
	public ControlPeticionesImpVO getControlPeticionesImp(String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(ControlPeticionesImpVO.class);
		criteria.add(Restrictions.eq("tipo", tipo));
		return (ControlPeticionesImpVO) criteria.uniqueResult();
	}
}
