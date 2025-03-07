package org.gestoresmadrid.core.importacionFichero.model.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.importacionFichero.model.dao.EstadisticaImportacionFicherosDao;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.EstadosImportacionEstadistica;
import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadisticaImportacionFicherosDaoImpl extends GenericDaoImplHibernate<EstadisticaImportacionFicherosVO> implements EstadisticaImportacionFicherosDao {

	private static final long serialVersionUID = 8281860936425500230L;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public EstadisticaImportacionFicherosVO getEstadisticaImportacion(Long idImportacionFich) {
		Criteria criteria = getCurrentSession().createCriteria(EstadisticaImportacionFicherosVO.class);
		criteria.add(Restrictions.eq("idImportacionFich", idImportacionFich));
		return (EstadisticaImportacionFicherosVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaImportacionFicherosVO> getListaImportacionExcel(Date fechaInicio, Date fechaFin, String tipo, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(EstadisticaImportacionFicherosVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaFin);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fecha", fecMin));
		and.add(Restrictions.lt("fecha", fecMax));
		criteria.add(and);
		// criteria.add(Restrictions.ge("estadisticaImpFichero.fecha", fecha));
		if (tipo != null && !tipo.isEmpty()) {
			criteria.add(Restrictions.eq("tipo", tipo));
		}
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaImportacionFicherosVO> getListaImportacion(Date fechaInicio, Date fechaFin, String origen) {
		Criteria criteria = getCurrentSession().createCriteria(EstadisticaImportacionFicherosVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaFin);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fecha", fecMin));
		and.add(Restrictions.lt("fecha", fecMax));
		criteria.add(and);
		if (origen != null & !origen.isEmpty()) {
			criteria.add(Restrictions.eq("origen", origen));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("contrato.colegiado.numColegiado"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticaImportacionFicherosVO> listaEstadisticasEjecutandose(Long idContrato, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(EstadisticaImportacionFicherosVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		if (tipo != null && !tipo.isEmpty()) {
			criteria.add(Restrictions.eq("tipo", tipo));
		}
		criteria.add(Restrictions.eq("estado", EstadosImportacionEstadistica.Ejecutandose.getValorEnum()));
		return criteria.list();
	}

	@Override
	public Integer numeroPeticionesEjecutandose(String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(EstadisticaImportacionFicherosVO.class);
		if (tipo != null && !tipo.isEmpty()) {
			criteria.add(Restrictions.eq("tipo", tipo));
		}
		criteria.add(Restrictions.eq("estado", EstadosImportacionEstadistica.Ejecutandose.getValorEnum()));
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
}
