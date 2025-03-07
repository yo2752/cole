package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;
import org.gestoresmadrid.core.trafico.model.dao.FundamentoExencion620Dao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FundamentoExencion620DaoImpl extends GenericDaoImplHibernate<FundamentoExencion620VO>
		implements FundamentoExencion620Dao {

	private static final long serialVersionUID = 351762856234850052L;

	@Override
	public FundamentoExencion620VO getFundamentoExencion620VO(String fundamentoExencion) {
		Criteria criteria = getCurrentSession().createCriteria(FundamentoExencion620VO.class);
		if (StringUtils.isNotBlank(fundamentoExencion)) {
			criteria.add(Restrictions.eq("fundamentoExcencion", fundamentoExencion.toUpperCase()));
		}

		@SuppressWarnings("unchecked")
		List<FundamentoExencion620VO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<FundamentoExencion620VO> getlistaFundamentoExencion620() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FundamentoExencion620VO.class);
		criteria.add(Restrictions.eq("tipoFundamento", "E"));
		@SuppressWarnings("unchecked")
		List<FundamentoExencion620VO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public List<FundamentoExencion620VO> getlistaFundamentoNoSujeto620() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FundamentoExencion620VO.class);
		criteria.add(Restrictions.eq("tipoFundamento", "N"));
		@SuppressWarnings("unchecked")
		List<FundamentoExencion620VO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public FundamentoExencion620VO getFundamentoNoSujeto620VO(String fundamentoNoSujeccion620) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(FundamentoExencion620VO.class);
		criteria.add(Restrictions.eq("tipoFundamento", "N"));
		
		FundamentoExencion620VO fundamento = null;
		if (fundamentoNoSujeccion620 != null) {
			criteria.add(Restrictions.eq("fundamentoExcencion", fundamentoNoSujeccion620));
			fundamento = (FundamentoExencion620VO) criteria.uniqueResult();
		}
		
		return fundamento;
	}

}
