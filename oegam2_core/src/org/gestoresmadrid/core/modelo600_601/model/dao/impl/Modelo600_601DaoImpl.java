package org.gestoresmadrid.core.modelo600_601.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.modelo600_601.model.dao.Modelo600_601Dao;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.Fecha;

@Repository
public class Modelo600_601DaoImpl extends GenericDaoImplHibernate<Modelo600_601VO> implements Modelo600_601Dao{
	
	private static final long serialVersionUID = 9059378494336599138L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	public Modelo600_601DaoImpl() { 
		super();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Modelo600_601VO getModeloById(Long idModelo){
		Criteria crit = getCurrentSession().createCriteria(Modelo600_601VO.class);
		crit.add(Restrictions.eq("idModelo", idModelo));
		crit.createAlias("remesa", "remesa", CriteriaSpecification.LEFT_JOIN);
		
		return (Modelo600_601VO) crit.uniqueResult();
		
	}
	
	@Override
	@Transactional
	public void cambiarEstadoModelo (Long idModelo, int estado){
		Modelo600_601VO modelo = getModeloById(idModelo);
		modelo.setEstado(new BigDecimal(estado));
		actualizar(modelo);
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));
		
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "00000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Modelo600_601VO> getListaModelosPorRemesa(Long idRemesa, boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		if(completo){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaResultadoModelo","listaResultadoModelo", CriteriaSpecification.LEFT_JOIN);
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public Modelo600_601VO getModeloPorExpediente(BigDecimal numExpediente, Boolean modeloCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		
		if(modeloCompleto){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaResultadoModelo","listaResultadoModelo", CriteriaSpecification.LEFT_JOIN);
			
		}
		return (Modelo600_601VO) criteria.uniqueResult();
	}
	
	@Override
	public Modelo600_601VO getModelo(BigDecimal numExpediente, Long idModelo, Boolean modeloCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		if(numExpediente != null){
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		if(idModelo != null){
			criteria.add(Restrictions.eq("idModelo", idModelo));
		}
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		
		if(modeloCompleto){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaResultadoModelo","listaResultadoModelo", CriteriaSpecification.LEFT_JOIN);
			
		}
		return (Modelo600_601VO) criteria.uniqueResult();
	}
	
	@Override
	public Modelo600_601VO getModeloPorId(Long idModelo, boolean modeloCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		criteria.add(Restrictions.eq("idModelo", idModelo));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("remesa", "remesa",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("datosBancarios", "datosBancarios",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		if(modeloCompleto){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaResultadoModelo","listaResultadoModelo", CriteriaSpecification.LEFT_JOIN);
		}
		return (Modelo600_601VO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Modelo600_601VO> getListaModelosPorExpedientesYContrato(BigDecimal[] numExpedientes, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(Modelo600_601VO.class);
		criteria.add(Restrictions.in("numExpediente", numExpedientes));
		criteria.add(Restrictions.eq("idContrato", idContrato));
		return criteria.list();
	}
}

