package org.gestoresmadrid.core.trafico.interga.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.interga.model.dao.IntervinienteIntergaDao;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IntervinienteIntergaDaoImpl extends GenericDaoImplHibernate<IntervinienteIntergaVO> implements IntervinienteIntergaDao {

	private static final long serialVersionUID = 4572044286242123804L;

	@Override
	public IntervinienteIntergaVO getIntervinienteTrafico(BigDecimal numExpediente, String nif, String numColegiado, String tipoTramiteInterga) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteIntergaVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		if (nif != null && !nif.isEmpty()) {
			criteria.add(Restrictions.eq("nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (tipoTramiteInterga != null && !tipoTramiteInterga.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTramiteInterga", tipoTramiteInterga));
		}

		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<IntervinienteIntergaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public IntervinienteIntergaVO getIntervinienteTraficoTipo(BigDecimal numExpediente, String tipoTramiteInterga) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteIntergaVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		if (tipoTramiteInterga != null && !tipoTramiteInterga.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTramiteInterga", tipoTramiteInterga));
		}

		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<IntervinienteIntergaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
