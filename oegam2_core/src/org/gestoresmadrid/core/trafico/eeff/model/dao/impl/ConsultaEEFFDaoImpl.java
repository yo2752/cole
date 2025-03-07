package org.gestoresmadrid.core.trafico.eeff.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.eeff.model.dao.ConsultaEEFFDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class ConsultaEEFFDaoImpl extends GenericDaoImplHibernate<ConsultaEEFFVO> implements ConsultaEEFFDao{

	private static final long serialVersionUID = 8264937819032191851L;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ConsultaEEFFVO getConsultaEEFFPorNumExpediente(BigDecimal numExpediente, Boolean consultaCompleta) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaEEFFVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if(consultaCompleta){
			criteria.createAlias("contrato","contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado","contratoColegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contratoColegiado.usuario","contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio","contratoColegio", CriteriaSpecification.LEFT_JOIN);
		}
		return (ConsultaEEFFVO) criteria.uniqueResult();
	}
	
	@Override
	public String generarNumExpediente(BigDecimal numColegiado) throws Exception {
		String textNumExpediente = numColegiado.toString();
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
		
		Criteria criteria = getCurrentSession().createCriteria(ConsultaPersonaAtex5VO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado.toString()));
		criteria.setProjection(Projections.max("numExpediente"));
		
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "000000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1).toString();
	}
	
}
