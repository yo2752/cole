package org.gestoresmadrid.core.bien.model.dao.impl;

import org.gestoresmadrid.core.bien.model.dao.BienRusticoDao;
import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BienRusticoDaoImpl extends GenericDaoImplHibernate<BienRusticoVO> implements BienRusticoDao{

	private static final long serialVersionUID = 6103017986189516021L;
	
	@Override
	public BienRusticoVO getBienRusticoPorId(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(BienRusticoVO.class);
		criteria.add(Restrictions.eq("idBien", idBien));
		criteria.createAlias("usoRustico", "usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("unidadMetrica", "unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sistemaExplotacion", "sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoInmueble", "tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (BienRusticoVO) criteria.uniqueResult();
	}

}
