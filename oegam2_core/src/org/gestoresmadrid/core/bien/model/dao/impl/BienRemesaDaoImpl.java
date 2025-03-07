package org.gestoresmadrid.core.bien.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.bien.model.dao.BienRemesaDao;
import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BienRemesaDaoImpl extends GenericDaoImplHibernate<BienRemesaVO> implements BienRemesaDao{

	private static final long serialVersionUID = -3727762221351897422L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BienRemesaVO> getBienRemesaPorIdRemesa(Long idRemesa) {
		Criteria criteria = getCurrentSession().createCriteria(BienRemesaVO.class);
		criteria.add(Restrictions.eq("id.idRemesa", idRemesa));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("id.idBien"));
		return criteria.list();
	}

	@Override
	public BienRemesaVO getBienRemesaPorId(Long idBien, Long idRemesa, Boolean remesaCompleta) {
		Criteria criteria = getCurrentSession().createCriteria(BienRemesaVO.class);
		criteria.add(Restrictions.eq("id.idRemesa", idRemesa));
		criteria.add(Restrictions.eq("id.idBien", idBien));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.modelo", "remesaModelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.modelo.id", "remesaModeloId",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesaModelo.bonificaciones", "remesaModelo.bonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesaModelo.fundamentosExencion", "remesaModelo.fundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.bonificacion", "remesa.bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.fundamentoExencion", "remesa.fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.contrato", "remesa.contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.notario", "remesa.notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.concepto", "remesa.concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.tipoImpuesto", "remesa.tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.oficinaLiquidadora", "remesa.oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.listaIntervinientes","remesaListaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.listaCoefParticipacion","remesaListaCoefParticipacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.listaBienes","remesaListaBienes", CriteriaSpecification.LEFT_JOIN);
		
		if(remesaCompleta){
			criteria.createAlias("remesaListaBienes.bien","remesaListaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.setFetchMode("remesaListaBienesBien.tipoInmueble", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaBienesBien.unidadMetrica", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaBienesBien.usoRustico", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaBienesBien.sistemaExplotacion", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaBienesBien.situacion", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaBienesBien.direccion", FetchMode.JOIN);
			
			criteria.setFetchMode("remesaListaIntervinientes.persona", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaIntervinientes.direccion", FetchMode.JOIN);
			
			criteria.setFetchMode("remesaListaCoefParticipacion.bien", FetchMode.JOIN);
			criteria.setFetchMode("remesaListaCoefParticipacion.intervinienteModelos", FetchMode.JOIN);
		}
		
		return (BienRemesaVO) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BienRemesaVO> getListaPorIdBien(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(BienRemesaVO.class);
		criteria.add(Restrictions.eq("id.idBien", idBien));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.modelo", "remesaModelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.modelo.id", "remesaModeloId",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesaModelo.bonificaciones", "remesaModelo.bonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesaModelo.fundamentosExencion", "remesaModelo.fundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.bonificacion", "remesa.bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.fundamentoExencion", "remesa.fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.contrato", "remesa.contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.notario", "remesa.notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.concepto", "remesa.concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.tipoImpuesto", "remesa.tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa.oficinaLiquidadora", "remesa.oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
