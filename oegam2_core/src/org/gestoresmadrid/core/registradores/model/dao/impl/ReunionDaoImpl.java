package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.ReunionDao;
import org.gestoresmadrid.core.registradores.model.vo.ReunionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ReunionDaoImpl extends GenericDaoImplHibernate<ReunionVO> implements ReunionDao {

	private static final long serialVersionUID = 5310891338764728872L;

	@Override
	public ReunionVO getReunion(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(ReunionVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		@SuppressWarnings("unchecked")
		List<ReunionVO> lista = criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}
}
