package org.gestoresmadrid.core.intervinienteModelos.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.intervinienteModelos.model.dao.IntervinienteModelosDao;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IntervinienteModelosDaoImpl extends GenericDaoImplHibernate<IntervinienteModelosVO> implements IntervinienteModelosDao{

	private static final long serialVersionUID = -6585968901660496353L;
	
	@Override
	public IntervinienteModelosVO getIntervinientePorNifYNumColegiado(String nif, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("persona.id.nif", nif));
		criteria.add(Restrictions.eq("persona.id.numColegiado", numColegiado));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteModelosVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IntervinienteModelosVO> getListaIntervinientesPorModelo(Long idModelo) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("idModelo", idModelo));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public IntervinienteModelosVO getIntervinientePorNifYIdRemesaYTipoInterviniente(String nif, Long idRemesa, String tipoInterviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("persona.id.nif", nif));
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.add(Restrictions.eq("tipoInterviniente", tipoInterviniente));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteModelosVO) criteria.uniqueResult();
	}
	
	@Override
	public IntervinienteModelosVO getIntervinientePorNifYIdModeloYTipoInterviniente(String nif, Long idModelo, String tipoInterviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("persona.id.nif", nif));
		criteria.add(Restrictions.eq("idModelo", idModelo));
		criteria.add(Restrictions.eq("tipoInterviniente", tipoInterviniente));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteModelosVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IntervinienteModelosVO> getIntervinientesPorRemesa(Long idRemesa) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public IntervinienteModelosVO getIntervinientePorId(Long idInterviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteModelosVO.class);
		criteria.add(Restrictions.eq("idInterviniente", idInterviniente));
		criteria.createAlias("persona", "persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("direccion", "direccion",CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteModelosVO) criteria.uniqueResult();
	}
	
}
