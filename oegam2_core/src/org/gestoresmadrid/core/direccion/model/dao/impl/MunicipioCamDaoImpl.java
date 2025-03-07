package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioCamDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioCamVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioCamDaoImpl extends GenericDaoImplHibernate<MunicipioCamVO> implements MunicipioCamDao {

	private static final long serialVersionUID = -8618659274236737169L;

	@SuppressWarnings("unchecked")
	@Override
	public List<MunicipioCamVO> getListaMunicipiosPorProvincia(String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioCamVO.class);
		criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public MunicipioCamVO getMunicipio(String idMunicipio, String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioCamVO.class);
		criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		return (MunicipioCamVO) criteria.uniqueResult();
	}

	@Override
	public String getMunicipioNombre(String idMunicipio, String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioCamVO.class);
		criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		MunicipioCamVO municipio = (MunicipioCamVO) criteria.uniqueResult();
		return municipio.getNombre();
	}
}
