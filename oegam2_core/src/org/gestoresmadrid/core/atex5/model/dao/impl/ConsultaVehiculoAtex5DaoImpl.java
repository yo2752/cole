package org.gestoresmadrid.core.atex5.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.dao.ConsultaVehiculoAtex5Dao;
import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
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
public class ConsultaVehiculoAtex5DaoImpl extends GenericDaoImplHibernate<ConsultaVehiculoAtex5VO> implements ConsultaVehiculoAtex5Dao {

	private static final long serialVersionUID = 6918274945704118578L;
	
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaVehiculoAtex5VO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (tramiteCompleto) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			
		}
		return (ConsultaVehiculoAtex5VO) criteria.uniqueResult();
	}
	
	@Override
	public ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorId(Long idConsultaVehiculoAtex5, Boolean tramiteCompleto, Boolean filtrarPorTasa) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaVehiculoAtex5VO.class);
		criteria.add(Restrictions.eq("idConsultaVehiculoAtex5", idConsultaVehiculoAtex5));
		if (tramiteCompleto) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
			if(filtrarPorTasa){
				criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			}
		}
		return (ConsultaVehiculoAtex5VO) criteria.uniqueResult();
	}
	
	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception{
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2) + TipoTramiteAtex5.Consulta_Vehiculo.getTipo();
		
		Criteria criteria = getCurrentSession().createCriteria(ConsultaVehiculoAtex5VO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));
		
		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "0000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaVehiculoAtex5VO> getListaConsultaFinalizada() {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaVehiculoAtex5VO.class);
		criteria.add(Restrictions.isNull("codigoTasa"));
		BigDecimal[] estadosFinalizados = new BigDecimal[]{new BigDecimal(EstadoAtex5.Finalizado_PDF.getValorEnum()),new BigDecimal(EstadoAtex5.Finalizado_Sin_Antecedentes.getValorEnum())};
		criteria.add(Restrictions.in("estado", estadosFinalizados));
		return (List<ConsultaVehiculoAtex5VO>) criteria.list(); 
	}
	
}
