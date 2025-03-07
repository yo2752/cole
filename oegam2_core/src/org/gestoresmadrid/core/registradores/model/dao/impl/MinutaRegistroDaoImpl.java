package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.MinutaRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.MinutaRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MinutaRegistroDaoImpl extends GenericDaoImplHibernate<MinutaRegistroVO> implements MinutaRegistroDao {

	private static final long serialVersionUID = -8799674911360008270L;

	@Override
	public MinutaRegistroVO getMinutaRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(MinutaRegistroVO.class);
		criteria.add(Restrictions.eq("idMinuta", Long.parseLong(id)));
		return (MinutaRegistroVO) criteria.uniqueResult();
	}

	@Override
	public List<MinutaRegistroVO> getMinutasRegistroPorTramite(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(MinutaRegistroVO.class);
		if (null != idTramiteRegistro) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		@SuppressWarnings("unchecked")
		List<MinutaRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}
