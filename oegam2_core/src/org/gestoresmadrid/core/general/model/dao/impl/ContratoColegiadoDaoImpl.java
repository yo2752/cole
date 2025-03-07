package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ContratoColegiadoDao;
import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoColegiadoDaoImpl extends GenericDaoImplHibernate<ContratoColegiadoVO> implements ContratoColegiadoDao {

	private static final long serialVersionUID = 6198303284458837087L;
	private static final String COLEGIADO = "colegiado";

	@Override
	public ContratoColegiadoVO getContratoColegiado(Long idContrato, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoColegiadoVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		return (ContratoColegiadoVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoColegiadoVO> getContratoColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoColegiadoVO.class);
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		return (List<ContratoColegiadoVO>) criteria.list();
	}

	@Override
	public ContratoColegiadoVO getContratoColegiado(Long idContrato, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoColegiadoVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("colegiado.idUsuario", idUsuario));
		criteria.createAlias(COLEGIADO, COLEGIADO, CriteriaSpecification.LEFT_JOIN);
		return (ContratoColegiadoVO) criteria.uniqueResult();
	}

	@Override
	public ContratoColegiadoVO getColegiadoPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoColegiadoVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.createAlias(COLEGIADO, COLEGIADO, CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		return (ContratoColegiadoVO) criteria.uniqueResult();
	}
}