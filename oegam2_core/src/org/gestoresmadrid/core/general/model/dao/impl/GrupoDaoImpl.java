package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.GrupoDao;
import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class GrupoDaoImpl extends GenericDaoImplHibernate<GrupoVO> implements GrupoDao {

	private static final long serialVersionUID = -4916063641209793675L;

	@SuppressWarnings("unchecked")
	public List<GrupoVO> getGrupos() {
		Criteria criteria = getCurrentSession().createCriteria(GrupoVO.class);
		return criteria.list();
	}

	public GrupoVO getGrupo(String idGrupo) {
		Criteria criteria = getCurrentSession().createCriteria(GrupoVO.class);
		criteria.add(Restrictions.eq("idGrupo", idGrupo));
		return (GrupoVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoMaestroBean> getComboGrupos() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(GrupoVO.class);

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("idGrupo"));
		projections.add(Projections.property("descGrupo"));
		criteria.setProjection(projections);
		criteria.setResultTransformer(new ResultTransformer() {

			private static final long serialVersionUID = 2961364363576267313L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				DatoMaestroBean dato = new DatoMaestroBean(tuple[0].toString(), tuple[1].toString());
				return dato;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		criteria.addOrder(Order.asc("descGrupo"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDescripcionGrupo(String idGrupo) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(GrupoVO.class);
		criteria.add(Restrictions.eq("idGrupo", idGrupo));

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("descGrupo"));
		criteria.setProjection(projections);

		return criteria.list();
	}
}