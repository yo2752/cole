package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.UnidadPoblacionalDao;
import org.gestoresmadrid.core.direccion.model.vo.UnidadPoblacionalVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UnidadPoblacionalDaoImpl extends GenericDaoImplHibernate<UnidadPoblacionalVO> implements UnidadPoblacionalDao {

	private static final long serialVersionUID = -2974381897622058429L;

	@Override
	public List<UnidadPoblacionalVO> getUnidadPoblacional(String idMunicipio, String idProvincia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(UnidadPoblacionalVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}
		if (idMunicipio != null && !idMunicipio.isEmpty()) {
			criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		}

		List<UnidadPoblacionalVO> lista = (List<UnidadPoblacionalVO>) criteria.list();
		if (lista != null && lista.size() > 0) {
			return lista;
		}
		return null;
	}
}
