package org.gestoresmadrid.core.remesa.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.remesa.model.dao.RemesaDao;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class RemesaDaoImpl extends GenericDaoImplHibernate<RemesaVO> implements RemesaDao{
	
	private static final long serialVersionUID = -5042424256081220643L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;
	

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		
		Criteria criteria = getCurrentSession().createCriteria(RemesaVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));
		
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "00000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}
	
	@Override
	public RemesaVO getRemesaPorID(Long idRemesa, boolean remesaCompleta) {
		Criteria criteria = getCurrentSession().createCriteria(RemesaVO.class);
		criteria.add(Restrictions.eq("idRemesa", idRemesa));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaCoefParticipacion","listaCoefParticipacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaModelos","listaModelos", CriteriaSpecification.LEFT_JOIN);
		if(remesaCompleta){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaCoefParticipacion.bien","listaCoefParticipacion.bien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaCoefParticipacion.intervinienteModelos","listaCoefParticipacion.intervinienteModelos", CriteriaSpecification.LEFT_JOIN);
			
		}
		return (RemesaVO) criteria.uniqueResult();
	}
	
	@Override
	public RemesaVO getRemesaPorExpediente(BigDecimal numExpediente, boolean remesaCompleta) {
		Criteria criteria = getCurrentSession().createCriteria(RemesaVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.createAlias("modelo", "modelo",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.bonificaciones", "modeloBonificaciones",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("modelo.fundamentosExencion", "modeloFundamentosExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bonificacion", "bonificacion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("fundamentoExencion", "fundamentoExencion",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("notario", "notario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("concepto", "concepto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tipoImpuesto", "tipoImpuesto",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("oficinaLiquidadora", "oficinaLiquidadora",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaIntervinientes","listaIntervinientes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaCoefParticipacion","listaCoefParticipacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaBienes","listaBienes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("listaModelos","listaModelos", CriteriaSpecification.LEFT_JOIN);
		if(remesaCompleta){
			criteria.createAlias("listaBienes.bien","listaBienesBien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.tipoInmueble","listaBienesBien.tipoInmueble", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.unidadMetrica","listaBienesBien.unidadMetrica", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.usoRustico","listaBienesBien.usoRustico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.sistemaExplotacion","listaBienesBien.sistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.situacion","listaBienesBien.situacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaBienesBien.direccion","listaBienesBien.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaIntervinientes.persona","listaIntervinientes.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaIntervinientes.direccion","listaIntervinientes.direccion", CriteriaSpecification.LEFT_JOIN);
			
			criteria.createAlias("listaCoefParticipacion.bien","listaCoefParticipacion.bien", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaCoefParticipacion.intervinienteModelos","listaCoefParticipacion.intervinienteModelos", CriteriaSpecification.LEFT_JOIN);
			
		}
		return (RemesaVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RemesaVO> getListaRemesasPorExpedientesYContrato(BigDecimal[] numExpedientes, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(RemesaVO.class);
		criteria.add(Restrictions.in("numExpediente", numExpedientes));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		return criteria.list();
	}

}
