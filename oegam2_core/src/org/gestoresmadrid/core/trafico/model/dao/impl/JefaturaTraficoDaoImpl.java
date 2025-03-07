package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class JefaturaTraficoDaoImpl extends GenericDaoImplHibernate<JefaturaTraficoVO> implements JefaturaTraficoDao {

	private static final long serialVersionUID = -2396448632069133169L;

	@Override
	public JefaturaTraficoVO getJefatura(String jefaturaProvincial) {
		Criteria criteria = getCurrentSession().createCriteria(JefaturaTraficoVO.class);
		if (jefaturaProvincial != null && !jefaturaProvincial.isEmpty()) {
			criteria.add(Restrictions.eq("jefaturaProvincial", jefaturaProvincial));
		}

		@SuppressWarnings("unchecked")
		List<JefaturaTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public JefaturaTraficoVO getJefaturaPorDescripcion(String descripcion) {
		Criteria criteria = getCurrentSession().createCriteria(JefaturaTraficoVO.class);
		if (descripcion != null && !descripcion.isEmpty()) {
			criteria.add(Restrictions.eq("descripcion", descripcion.toUpperCase()));
		}

		@SuppressWarnings("unchecked")
		List<JefaturaTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<JefaturaTraficoVO> listadoJefaturas(String idProvincia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JefaturaTraficoVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("provincia.idProvincia", idProvincia));
		}
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("jefaturaProvincial"));

		@SuppressWarnings("unchecked")
		List<JefaturaTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<JefaturaTraficoVO> getJefaturasPorContrato(Long idContrato) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JefaturaTraficoVO.class);
		if (idContrato != null) {
			criteria.add(Restrictions.eq("contratos.idContrato", idContrato));
		}
		criteria.createAlias("contratos", "contratos", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<JefaturaTraficoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
