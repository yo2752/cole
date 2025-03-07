package org.gestoresmadrid.core.participacion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.participacion.model.dao.ParticipacionDao;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipacionDaoImp extends GenericDaoImplHibernate<ParticipacionVO> implements ParticipacionDao{

	private static final long serialVersionUID = 6347711883797712856L;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipacionVO> getListaParticipacionPorIdRemesa(Long idRemesa, boolean completo) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		if(completo){
			criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipacionVO> getListaParticipacionPorIdInterviniente(Long idInterviniente) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idInterviniente", idInterviniente));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipacionVO> getlistaParticipacionPorIdBien (Long idBien) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idBien", idBien));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public ParticipacionVO getParticipacionPorRemesaYBien(Long idRemesa, Long idBien) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.add(Restrictions.eq("idBien", idBien));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		return (ParticipacionVO) criteria.uniqueResult();
	}
	
	@Override
	public ParticipacionVO getParticipacionPorRemesaEInterviniente(Long idRemesa, Long idInterviniente) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.add(Restrictions.eq("idInterviniente", idInterviniente));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		return (ParticipacionVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipacionVO> getListaParticipacionPorRemesaEInterviniente(Long idRemesa, Long idInterviniente) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.add(Restrictions.eq("idInterviniente", idInterviniente));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParticipacionVO> getListaParticipacionRemesaBien(Long idRemesa, Long idBien) {
		Criteria criteria  = getCurrentSession().createCriteria(ParticipacionVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.add(Restrictions.eq("idBien", idBien));
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos", "intervinienteModelos",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		
		criteria.createAlias("intervinienteModelos.persona", "intervinienteModelos.persona",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.remesa", "intervinienteModelos.remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.modelo600_601", "intervinienteModelos.modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteModelos.direccion", "intervinienteModelos.direccion",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
}
