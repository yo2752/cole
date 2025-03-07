package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.general.model.dao.CorreoContratoTramiteDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CorreoContratoTramiteDaoImpl extends GenericDaoImplHibernate<CorreoContratoTramiteVO> implements CorreoContratoTramiteDao {

	private static final long serialVersionUID = 6198303284458837087L;
	private static final String TIPO_TRAMITE = "tipoTramite";

	@Override
	public CorreoContratoTramiteVO getCorreoContratoTramite(Long idCorreo) {
		Criteria criteria = getCurrentSession().createCriteria(CorreoContratoTramiteVO.class);
		if (idCorreo != null) {
			criteria.add(Restrictions.eq("idCorreo", idCorreo));
			criteria.createAlias(TIPO_TRAMITE, TIPO_TRAMITE, CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("tipoTramite.aplicacion", "tipoTramiteaplicacion", CriteriaSpecification.LEFT_JOIN);
		}
		return (CorreoContratoTramiteVO) criteria.uniqueResult();
	}

	@Override
	public List<CorreoContratoTramiteVO> getCorreosPorContrato(long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(CorreoContratoTramiteVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias(TIPO_TRAMITE, TIPO_TRAMITE, CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoTramite.aplicacion", "tipoTramiteaplicacion", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<CorreoContratoTramiteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

}