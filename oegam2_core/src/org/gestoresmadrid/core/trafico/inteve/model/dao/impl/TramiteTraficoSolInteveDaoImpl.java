package org.gestoresmadrid.core.trafico.inteve.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoSolInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteTraficoSolInteveDaoImpl extends GenericDaoImplHibernate<TramiteTraficoSolInteveVO> implements TramiteTraficoSolInteveDao {

	private static final long serialVersionUID = 5554323064401645967L;

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoSolInteveVO> getListaTramitesSolInteve(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		return criteria.list();
	}

	@Override
	public TramiteTraficoSolInteveVO getTramiteSolInteve(BigDecimal numExpediente, String matricula, String bastidor, String nive) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (matricula != null && !matricula.isEmpty()) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}
		if (nive != null && !nive.isEmpty()) {
			criteria.add(Restrictions.eq("nive", nive));
		}
		return (TramiteTraficoSolInteveVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoSolInteveVO> getListaSolicitudesNoFinalizadas(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.add(Restrictions.not(Restrictions.eq("estado", EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())));
		return criteria.list();
	}

	@Override
	public TramiteTraficoSolInteveVO getTramiteSolIntevePorId(Long idTramiteSolInteve) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.add(Restrictions.eq("idTramiteInteve", idTramiteSolInteve));
		return (TramiteTraficoSolInteveVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTraficoSolInteveVO> getDuplicados(Long idContrato, String matricula, String bastidor, String nive) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTraficoSolInteveVO.class);
		criteria.createAlias("tramiteTraficoInteve", "tramiteTraficoInteve", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tramiteTraficoInteve.contrato", "tramiteTraficoInteve.contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("tramiteTraficoInteve.contrato.idContrato", idContrato));
		criteria.add(Restrictions.in("estado",new String[]{
				EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum(),
				EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum(),
				EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()}
				));
		if(StringUtils.isNotBlank(matricula)){
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if (StringUtils.isNotBlank(bastidor)) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}
		if (StringUtils.isNotBlank(nive)) {
			criteria.add(Restrictions.eq("nive", nive));
		}
		return criteria.list();
	}

}