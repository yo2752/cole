package org.gestoresmadrid.core.licencias.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.dao.LcDocumentoLicenciaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDocumentoLicenciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LcDocumentoLicenciaDaoImpl extends GenericDaoImplHibernate<LcDocumentoLicenciaVO> implements LcDocumentoLicenciaDao {

	private static final long serialVersionUID = -2933559880244543590L;

	@Override
	public LcDocumentoLicenciaVO getLcDocumentoLicencia(Long idDocumentoLicencia) {
		Criteria criteria = getCurrentSession().createCriteria(LcDocumentoLicenciaVO.class);
		criteria.add(Restrictions.eq("idDocumentoLicencia", idDocumentoLicencia));
		return (LcDocumentoLicenciaVO) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LcDocumentoLicenciaVO> getLcDocumentoLicenciaPorNumExp(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(LcDocumentoLicenciaVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		return criteria.list();
	}
}
