package org.gestoresmadrid.core.modelo.bien.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelo.bien.model.dao.ModeloBienDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ModeloBienDaoImpl extends GenericDaoImplHibernate<ModeloBienVO> implements ModeloBienDao{

	private static final long serialVersionUID = 2237741322568598937L;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloBienVO> getModeloBienPorIdModelo(Long idModelo) {
		Criteria criteria = getCurrentSession().createCriteria(ModeloBienVO.class);
		criteria.add(Restrictions.eq("id.idModelo", idModelo));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("id.idBien"));
		return criteria.list();
	}
	
	@Override
	public ModeloBienVO getModeloBienPorId(Long idBien, Long idModelo, boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(ModeloBienVO.class);
		criteria.add(Restrictions.eq("id.idModelo", idModelo));
		criteria.add(Restrictions.eq("id.idBien", idBien));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.modelo", "modelo600_601Modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.modelo.id", "modelo600_601ModeloId",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601Modelo.bonificaciones", "modelo600_601ModeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601Modelo.fundamentosExencion", "modelo600_601ModeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.concepto", "modelo600_601Concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.tipoImpuesto", "modelo600_601TipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.notario", "modelo600_601Notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.bonificacion", "modelo600_601Bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.contrato", "modelo600_601Contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.fundamentoExencion", "modelo600_601FundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.remesa", "modelo600_601Remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.oficinaLiquidadora", "modelo600_601OficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		if(completo){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
		}
		
		return (ModeloBienVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloBienVO> getListaPorIdBien(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(ModeloBienVO.class);
		criteria.add(Restrictions.eq("id.idBien", idBien));
		criteria.createAlias("bien", "bien",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.tipoInmueble", "bien.tipoInmueble",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bien.direccion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bien.situacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bien.usoRustico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bien.sistemaExplotacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bien.unidadMetrica",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601", "modelo600_601",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.modelo", "modelo600_601Modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.modelo.id", "modelo600_601ModeloId",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601Modelo.bonificaciones", "modelo600_601ModeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601Modelo.fundamentosExencion", "modelo600_601ModeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.concepto", "modelo600_601Concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.tipoImpuesto", "modelo600_601TipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.notario", "modelo600_601Notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.bonificacion", "modelo600_601Bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.contrato", "modelo600_601Contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.fundamentoExencion", "modelo600_601FundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.remesa", "modelo600_601Remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo600_601.oficinaLiquidadora", "modelo600_601OficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}
