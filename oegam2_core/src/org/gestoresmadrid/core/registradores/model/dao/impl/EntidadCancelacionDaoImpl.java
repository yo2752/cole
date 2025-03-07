package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.EntidadCancelacionDao;
import org.gestoresmadrid.core.registradores.model.vo.EntidadCancelacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EntidadCancelacionDaoImpl extends GenericDaoImplHibernate<EntidadCancelacionVO> implements EntidadCancelacionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -182085991548785603L;

	@Override
	public EntidadCancelacionVO buscarPorContratoNif(BigDecimal idContrato, String cifEntidad) {
		Criteria criteria = getCurrentSession().createCriteria(EntidadCancelacionVO.class);
		if (StringUtils.isNotBlank(cifEntidad)) {
			criteria.add(Restrictions.eq("codigoIdentificacionFiscal", cifEntidad));
		}
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (EntidadCancelacionVO) criteria.uniqueResult();
	}

}
