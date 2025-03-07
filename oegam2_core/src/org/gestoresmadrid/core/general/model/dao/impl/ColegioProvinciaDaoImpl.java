package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ColegioProvinciaDao;
import org.gestoresmadrid.core.general.model.vo.ColegioProvinciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ColegioProvinciaDaoImpl extends GenericDaoImplHibernate<ColegioProvinciaVO> implements ColegioProvinciaDao {

	private static final long serialVersionUID = -1503948762886995867L;

	@Override
	public List<ColegioProvinciaVO> listadoColegioProvincia(String idProvincia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ColegioProvinciaVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}

		@SuppressWarnings("unchecked")
		List<ColegioProvinciaVO> lista = (List<ColegioProvinciaVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
}