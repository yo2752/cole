package org.gestoresmadrid.core.atex5.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.atex5.model.dao.ConsultaPersonaAtex5Dao;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
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
public class ConsultaPersonaAtex5DaoImpl extends GenericDaoImplHibernate<ConsultaPersonaAtex5VO> implements ConsultaPersonaAtex5Dao{

	private static final long serialVersionUID = 1146182735494086022L;

	@Autowired
	UtilesFecha utilesFecha;

	
	@Override
	public ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorId(Long idConsultaPersonaAtex5, Boolean tramiteCompleto,Boolean filtarPorTasa) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaPersonaAtex5VO.class);
		criteria.add(Restrictions.eq("idConsultaPersonaAtex5", idConsultaPersonaAtex5));
		if(tramiteCompleto){
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			if(filtarPorTasa){
				criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			}
		}
		return (ConsultaPersonaAtex5VO) criteria.uniqueResult();
	}
	
	@Override
	public ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto, Boolean filtarPorTasa) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaPersonaAtex5VO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if(tramiteCompleto){
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			if(filtarPorTasa){
				criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			}
		}
		return (ConsultaPersonaAtex5VO) criteria.uniqueResult();
	}
	
	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception{
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, ConsultaPersonaAtex5Dao.horaFInDia);
		utilesFecha.setSegundosEnDate(fin, ConsultaPersonaAtex5Dao.N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2) + TipoTramiteAtex5.Consulta_Persona.getTipo();
		
		Criteria criteria = getCurrentSession().createCriteria(ConsultaPersonaAtex5VO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));
		
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "0000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}
	
}
