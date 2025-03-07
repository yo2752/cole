package org.gestoresmadrid.core.direccion.model.dao.impl;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioSitesDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioSitesDaoImpl extends GenericDaoImplHibernate<MunicipioSitesVO> implements MunicipioSitesDao {

	private static final long serialVersionUID = -5031478620891925105L;

	@Override
	public MunicipioSitesVO getMunicipioSites(String idMunicipio, String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioSitesVO.class);
		String codigoIne = idProvincia + idMunicipio;
		criteria.add(Restrictions.eq("codigoIne", codigoIne));
		return (MunicipioSitesVO) criteria.uniqueResult();
	}
}
