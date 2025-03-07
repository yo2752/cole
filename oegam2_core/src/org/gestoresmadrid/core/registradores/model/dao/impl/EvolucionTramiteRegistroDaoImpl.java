package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.EvolucionTramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.EvolucionTramiteRegistroVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionTramiteRegistroDaoImpl extends GenericDaoImplHibernate<EvolucionTramiteRegistroVO> implements EvolucionTramiteRegistroDao {

	private static final long serialVersionUID = 2843683425745267313L;

	@Override
	public List<EvolucionTramiteRegistroVO> getTramiteRegistro(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionTramiteTraficoVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("id.idTramiteRegistro", idTramiteRegistro));
		}

		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("id.fechaCambio"));

		@SuppressWarnings("unchecked")
		List<EvolucionTramiteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}

		return Collections.emptyList();
	}
}
