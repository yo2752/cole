package org.gestoresmadrid.core.actualizacionMF.model.dao.impl;

import org.gestoresmadrid.core.actualizacionMF.model.dao.ActualizacionMFDao;
import org.gestoresmadrid.core.actualizacionMF.model.enumerados.ActualizacionMFEnum;
import org.gestoresmadrid.core.actualizacionMF.model.vo.ActualizacionMFVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class ActualizacionMFDaoImpl extends GenericDaoImplHibernate<ActualizacionMFVO> implements ActualizacionMFDao {

	private static final long serialVersionUID = -2863735279132665683L;

	@Override
	public ActualizacionMFVO buscarActualizacion(Long idActualizacion) {
		Criteria criteria = getCurrentSession().createCriteria(ActualizacionMFVO.class);
		criteria.add(Restrictions.eq("idActualizacion", idActualizacion));
	
		return (ActualizacionMFVO) criteria.uniqueResult();
	}

	@Override
	public ActualizacionMFVO buscarActualizacionPendiente() {
		Criteria criteria = getCurrentSession().createCriteria(ActualizacionMFVO.class);
		criteria.add(Restrictions.eq("estado", ActualizacionMFEnum.Activado.getValorEnum()));
		
		return (ActualizacionMFVO) criteria.uniqueResult();
	}

	

}
