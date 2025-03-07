package org.gestoresmadrid.core.consultaKo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.dao.ConsultaKoDao;
import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaKoDaoImpl extends GenericDaoImplHibernate<ConsultaKoVO> implements ConsultaKoDao {

	private static final long serialVersionUID = 44039951877899235L;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaKoVO> getMatriculaKo() {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaKoVO> getListaKoPorEstado(String estadoKo) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		criteria.add(Restrictions.eq("estado", estadoKo));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaKoVO> getListaConsultasKOPorIds(Long[] idsConsultaKO) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		criteria.add(Restrictions.in("id", idsConsultaKO));
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
	
	@Override
	public ConsultaKoVO getConsultaKo(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("tramiteTrafico", "tramiteTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		return (ConsultaKoVO)criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaKoVO> getListaPorEstadoDoc(String estadoDocKO) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		criteria.add(Restrictions.eq("estadoDoc", estadoDocKO));
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario",CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaKoVO> getListaPorContrato(Long idContrato, String estadoDoc) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaKoVO.class);
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.eq("estadoDoc", estadoDoc));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<ConsultaKoVO>) criteria.list();
	}
}
