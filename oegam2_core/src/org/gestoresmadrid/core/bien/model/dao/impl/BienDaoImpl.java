package org.gestoresmadrid.core.bien.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.bien.model.dao.BienDao;
import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BienDaoImpl extends GenericDaoImplHibernate<BienVO> implements BienDao {

	private static final long serialVersionUID = 2821977716818130551L;

	@Override
	public BienVO getBienPorId(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(BienVO.class);
		criteria.add(Restrictions.eq("idBien", idBien));
		aniadirCriteria(criteria);
		return (BienVO) criteria.uniqueResult();
	}

	@Override
	public BienVO getBienPorIdufir(Long idufir) {
		Criteria criteria = getCurrentSession().createCriteria(BienVO.class);
		criteria.add(Restrictions.eq("idufir", idufir));
		aniadirCriteria(criteria);
		return (BienVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BienVO> getListaBienPorIdDireccion(Long idDireccion) {
		Criteria criteria = getCurrentSession().createCriteria(BienVO.class);
		criteria.add(Restrictions.eq("idDireccion", idDireccion));
		aniadirCriteria(criteria);
		return criteria.list();
	}

	private void aniadirCriteria(Criteria criteria) {
		criteria.createAlias("tipoInmueble", "tipoInmueble", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("unidadMetrica", "unidadMetrica", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usoRustico", "usoRustico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sistemaExplotacion", "sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("situacion", "situacion", CriteriaSpecification.LEFT_JOIN);
	}

}
