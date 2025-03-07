package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.gestoresmadrid.core.tasas.model.dao.TasaCtitDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaCtitVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TasaCtitDaoImpl extends GenericDaoImplHibernate<TasaCtitVO> implements TasaCtitDao {

	private static final long serialVersionUID = -8473866328195153126L;

	@Autowired
	UtilesFecha utilesFecha;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TasaCtitVO> obtenerTasasCtitContrato(Long idContrato, String tipoTasa, int maxResult) {
		Criteria criteria = getCurrentSession().createCriteria(TasaCtitVO.class);
		if (tipoTasa != null && !tipoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}
		if (idContrato != null) {
			Criteria criteriaContrato = criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteriaContrato.add(Restrictions.eq("idContrato", idContrato));
		}
		criteria.add(Restrictions.isNull("fechaAsignacion"));
		criteria.add(Restrictions.isNull("numExpediente"));
	
		criteria.add(Restrictions.or(Restrictions.isNull("bloqueada"), Restrictions.eq("bloqueada", EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum())));

		criteria.add(Restrictions.ge("fechaAlta", utilesFecha.getFechaDate("20180701")));

		if (maxResult != 0) {
			criteria.setMaxResults(maxResult);
		}

		criteria.addOrder(Order.asc("fechaAlta"));
		criteria.addOrder(Order.asc("codigoTasa"));
		return criteria.list();
	}
}
