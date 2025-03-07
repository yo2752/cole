package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcPersonaDireccionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaDireccionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcPersonaDireccionDaoImpl extends GenericDaoImplHibernate<LcPersonaDireccionVO> implements LcPersonaDireccionDao {

	private static final long serialVersionUID = 1074031841024823103L;

	@Override
	@SuppressWarnings("unchecked")
	public List<LcPersonaDireccionVO> getPersonaDireccionPorNif(String numColegiado, String nif) {
		Criteria criteria = getCurrentSession().createCriteria(LcPersonaDireccionVO.class);
		criteria.add(Restrictions.eq("nif", nif));
		if (StringUtils.isNotBlank(numColegiado)) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		criteria.add(Restrictions.isNull("fechaFin"));

		criteria.createAlias("lcDireccion", "lcDireccion", CriteriaSpecification.LEFT_JOIN);

		criteria.addOrder(Order.desc("fechaInicio"));

		return criteria.list();
	}

	@Override
	public LcPersonaDireccionVO buscarDireccionExistente(LcDireccionVO direccion, String numColegiado, String nif) {
		Criteria criteria = getCurrentSession().createCriteria(LcPersonaDireccionVO.class);

		if (StringUtils.isNotBlank(direccion.getNombreVia())) {
			criteria.add(Restrictions.eq("lcDireccion.nombreVia", direccion.getNombreVia()));
		}
		if (StringUtils.isNotBlank(direccion.getMunicipio())) {
			criteria.add(Restrictions.eq("lcDireccion.municipio", direccion.getMunicipio()));
		}
		if (StringUtils.isNotBlank(direccion.getProvincia())) {
			criteria.add(Restrictions.eq("lcDireccion.provincia", direccion.getProvincia()));
		}
		if (direccion.getIdDireccion() != null) {
			criteria.add(Restrictions.eq("idDireccion", direccion.getIdDireccion()));
		}
		if (StringUtils.isNotBlank(numColegiado)) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (StringUtils.isNotBlank(nif)) {
			criteria.add(Restrictions.eq("nif", nif));
		}

		criteria.createAlias("lcDireccion", "lcDireccion", CriteriaSpecification.LEFT_JOIN);

		criteria.addOrder(Order.desc("idDireccion"));
		@SuppressWarnings("unchecked")
		List<LcPersonaDireccionVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
