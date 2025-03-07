package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.dao.TipoCreditoTramiteDao;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipoCreditoTramiteDaoImpl extends GenericDaoImplHibernate<TipoCreditoTramiteVO> implements TipoCreditoTramiteDao {

	private static final long serialVersionUID = -2653675891666654909L;

	@Override
	public TipoCreditoTramiteVO getTipoCreditoTramite(String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoTramiteVO.class);
		if (StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}

		@SuppressWarnings("unchecked")
		List<TipoCreditoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public TipoCreditoTramiteVO getTipoCreditoTramitePorTipoCredito(String tipoCredito) {
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoTramiteVO.class);
		criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
		return (TipoCreditoTramiteVO) criteria.uniqueResult();
	}

	@Override
	public List<TipoCreditoTramiteVO> getListaTiposCreditosTramite(String tipoCredito){
		Criteria criteria = getCurrentSession().createCriteria(TipoCreditoTramiteVO.class);
		if (StringUtils.isNotBlank(tipoCredito)) {
			criteria.add(Restrictions.eq("tipoCredito", tipoCredito));
		}

		@SuppressWarnings("unchecked")
		List<TipoCreditoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

}