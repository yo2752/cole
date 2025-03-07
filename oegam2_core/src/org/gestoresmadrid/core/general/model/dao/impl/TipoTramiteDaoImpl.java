package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.TipoTramiteDao;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoTramiteDaoImpl extends GenericDaoImplHibernate<TipoTramiteVO> implements TipoTramiteDao {

	private static final long serialVersionUID = -8145865336872810922L;

	@Override
	public TipoTramiteVO getTipoTramite(String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(TipoTramiteVO.class);
		criteria.add(Restrictions.eq("tipoTramite", tipoTramite));

		@SuppressWarnings("unchecked")
		List<TipoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<String> busquedaTiposCreditosPorCodigoAplicacion(List<String> codigosAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(TipoTramiteVO.class);
		if (codigosAplicacion != null && !codigosAplicacion.isEmpty()) {
			criteria.add(Restrictions.in("aplicacion.codigoAplicacion", codigosAplicacion));
		}
		criteria.add(Restrictions.eq("activo", BigDecimal.ONE));
		criteria.add(Restrictions.eq("tipoCredito.estado", BigDecimal.ONE));

		criteria.addOrder(Order.asc("tipoCredito.ordenListado"));

		criteria.createAlias("tipoCredito", "tipoCredito");

		criteria.setProjection(Projections.property("tipoCredito.tipoCredito"));

		@SuppressWarnings("unchecked")
		List<String> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TipoTramiteVO> getTipoTramitePorAplicacion(String aplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(TipoTramiteVO.class);
		if (StringUtils.isNotBlank(aplicacion)) {
			criteria.add(Restrictions.eq("aplicacion.codigoAplicacion", aplicacion));
		}
		criteria.add(Restrictions.eq("activo", BigDecimal.ONE));
		criteria.addOrder(Order.asc("descripcion"));

		@SuppressWarnings("unchecked")
		List<TipoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<TipoTramiteVO> getTiposTramitesCreditos() {
		Criteria criteria = getCurrentSession().createCriteria(TipoTramiteVO.class);
		criteria.add(Restrictions.eq("activo", BigDecimal.ONE));
		criteria.addOrder(Order.asc("aplicacion.codigoAplicacion"));
		criteria.addOrder(Order.asc("tipoTramite"));
		criteria.createAlias("aplicacion", "aplicacion", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<TipoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

}