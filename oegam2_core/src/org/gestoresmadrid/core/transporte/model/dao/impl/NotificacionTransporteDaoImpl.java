package org.gestoresmadrid.core.transporte.model.dao.impl;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.transporte.model.dao.NotificacionTransporteDao;
import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotificacionTransporteDaoImpl extends GenericDaoImplHibernate<NotificacionTransporteVO> implements NotificacionTransporteDao{

	private static final long serialVersionUID = 3113561486692960659L;

	
	@Override
	public NotificacionTransporteVO getNotificacionPorId(Long idNotificacionTransporte, Boolean notificacionCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(NotificacionTransporteVO.class);
		criteria.add(Restrictions.eq("idNotificacionTransporte", idNotificacionTransporte));
		if(notificacionCompleto){
			criteria.createAlias("contrato","contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado","contratoColegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contratoColegiado.usuario","contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio","contratoColegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario","usuario", CriteriaSpecification.LEFT_JOIN);
		}
		return (NotificacionTransporteVO) criteria.uniqueResult();
	}
}
