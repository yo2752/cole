package org.gestoresmadrid.core.conductor.model.dao.impl;




import org.gestoresmadrid.core.conductor.model.dao.ConsultaConductorDao;
import org.gestoresmadrid.core.conductor.model.vo.ConsultaConductorVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaConductorDaoImpl extends GenericDaoImplHibernate<ConsultaConductorVO> implements ConsultaConductorDao{

	private static final long serialVersionUID = 1146182735494086022L;

	@Override
	public ConsultaConductorVO getConsultaConductor() {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(ConsultaConductorVO.class);
		
		
		return (ConsultaConductorVO) criteria.uniqueResult();
	
	}
	
	
	
	
}
