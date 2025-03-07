package org.gestoresmadrid.core.accionTramite.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.accionTramite.model.dao.AccionTramiteDao;
import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AccionTramiteDaoImpl  extends GenericDaoImplHibernate<AccionTramiteVO> implements AccionTramiteDao{
	private static final long serialVersionUID = 1L;

	@Override
	public List<AccionTramiteVO> existeAccionTramiteSinFechaFin(AccionTramiteVO accionTramiteVO) {
		List <Criterion> listCriterion = new ArrayList<Criterion>();
		
		listCriterion.add(Restrictions.eq("id.numExpediente", accionTramiteVO.getId().getNumExpediente()));
		listCriterion.add(Restrictions.eq("id.accion", accionTramiteVO.getId().getAccion()));
		
		listCriterion.add(Restrictions.isNull("fechaFin"));
		
		return buscarPorCriteria(listCriterion, null, null);
	}
	
	@Override
	public AccionTramiteVO getAccionPorNumExpedienteYAccion(Long numExpediente, String accion) {
		Criteria criteria = getCurrentSession().createCriteria(AccionTramiteVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.accion", accion));
		criteria.add(Restrictions.isNull("fechaFin"));
		return (AccionTramiteVO) criteria.uniqueResult();
	}

}
