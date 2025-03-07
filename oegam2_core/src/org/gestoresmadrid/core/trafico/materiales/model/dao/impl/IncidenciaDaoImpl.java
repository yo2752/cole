package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IncidenciaDaoImpl extends GenericDaoImplHibernate<IncidenciaVO> implements IncidenciaDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936560036092202348L;

	@Override
	public IncidenciaVO findIncidenciaByPk(Long incidencia) {
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaVO.class, "incidencia").
				add(Restrictions.eq("incidenciaId", incidencia));
		
		return (IncidenciaVO) criteria.uniqueResult();
	}

	@Override
	public IncidenciaVO findIncidenciaByPk(Long incidencia, boolean complete) {
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaVO.class, "incidencia");
		
		if (complete) {
			criteria.createAlias("incidencia.autorVO",            "autor");
			criteria.createAlias("incidencia.materialVO",         "material");
			criteria.createAlias("incidencia.jefaturaProvincial", "jefatura");
		}
		
		criteria.add(Restrictions.eq("incidenciaId", incidencia));
		
		return (IncidenciaVO) criteria.uniqueResult();
	}

	@Override
	public IncidenciaVO findIncidenciaByJefaturaMaterialNumSerie(String jefatura, Long material, String numSerie) {
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaVO.class, "incidencia");
		
		criteria.createAlias("incidencia.autorVO",            "autor");
		criteria.createAlias("incidencia.materialVO",         "material");
		criteria.createAlias("incidencia.listaSeriales",      "seriales");
		criteria.createAlias("incidencia.jefaturaProvincial", "jefatura");
		
		criteria.add(Restrictions.eq("material.materialId", material));
		criteria.add(Restrictions.eq("seriales.pk.numSerie", numSerie));
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
		
		return (IncidenciaVO) criteria.uniqueResult();
	}

	@Override
	public IncidenciaVO findIncidenciaBySerial(String numSerie) {
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaVO.class, "incidencia");
		
		criteria.createAlias("incidencia.autorVO",         "autor");
		criteria.createAlias("incidencia.materialVO",      "material");
		criteria.createAlias("incidencia.listaSeriales",   "seriales");
		
		criteria.createAlias("incidencia.jefaturaProvincial", "jefatura");
			
		criteria.add(Restrictions.eq("seriales.pk.numSerie", numSerie));
		
		return (IncidenciaVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IncidenciaVO> findIncidenciaByJefaturaAndEstadoAndFecha(String jefatura, Date fechaDesde,
			Date fechaHasta) {
		Criteria criteria = getCurrentSession().createCriteria(IncidenciaVO.class, "incidencia");
		criteria.createAlias("incidencia.jefaturaProvincial", "jefaturaProvincial");
		criteria.createAlias("incidencia.materialVO", "materialVO");
		criteria.createAlias("incidencia.listaSeriales", "seriales");

		Long estado = new Long(EstadoIncidencia.Activo.getValorEnum());
		criteria.add(Restrictions.eq("incidencia.estado", estado));
		criteria.add(Restrictions.isNotNull("seriales.incidenciaInvId"));
		
		if (StringUtils.isNotEmpty(jefatura)) {
			criteria.add(Restrictions.eq("jefaturaProvincial.jefaturaProvincial", jefatura));
		}
		
		Date fecDesdeIni = fechaInicioDia(fechaDesde);
		Date fecHastaFin = fechaFinDia(fechaHasta);
		
		Conjunction and = Restrictions.conjunction();
		and.add( Restrictions.ge("incidencia.fecha", fecDesdeIni) );
		and.add( Restrictions.lt("incidencia.fecha", fecHastaFin) );
		criteria.add(and);
		
		List<IncidenciaVO> incis = criteria.list(); 
		return incis;
	}

	private Date fechaInicioDia(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date time = cal.getTime();		
		
		return time;
	}
	
	private Date fechaFinDia(Date fecha) {
		Calendar cal = Calendar.getInstance();		
		cal.setTime(fecha);		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date time = cal.getTime();		
		
		return time;
	}

}
