package org.gestoresmadrid.core.conductor.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.conductor.model.dao.ConductorDao;
import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class ConductorDaoImpl extends GenericDaoImplHibernate<ConductorVO> implements ConductorDao{

	private static final long serialVersionUID = 3008260059512118257L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal generarNumExpediente(String numColegiado, String tipoOperacion) throws Exception {
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2)	+ tipoOperacion /*TipoOperacionCaycEnum.Alta_Conductor.getTipo()*/;

		Criteria criteria = getCurrentSession().createCriteria(ConductorVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "0000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}
	

	@Override
	public ConductorVO getConductorPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ConductorVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (tramiteCompleto) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
			//criteria.createAlias("persona.personaDireccion", "persona.personaDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		}		
		return (ConductorVO) criteria.uniqueResult();
	}


	@Override
	public ConductorVO getConductorPorId(Long idConductor, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ConductorVO.class);		
		criteria.add(Restrictions.eq("idConductor", idConductor));		
		if (tramiteCompleto){
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("persona", "persona", CriteriaSpecification.LEFT_JOIN);
			//criteria.createAlias("persona.personaDireccion", "persona.personaDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);
		}		
		return (ConductorVO) criteria.uniqueResult();
	}

}
