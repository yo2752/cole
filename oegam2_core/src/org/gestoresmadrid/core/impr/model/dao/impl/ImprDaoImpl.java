package org.gestoresmadrid.core.impr.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.impr.model.dao.ImprDao;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImprDaoImpl extends GenericDaoImplHibernate<ImprVO> implements ImprDao {

	private static final long serialVersionUID = -8079576297887093154L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public int numeroElementosConsultaTramite(Long id, String matricula, String bastidor, String nive, String tipoImpr,
			String tipoTramite, String numExpediente, String estadoSolicitud, String estadoImpresion, Date fechaInicio,
			Date fechaFin, String jefatura, Long docId, Long idContrato, String carpeta, String estadoImpr) throws Exception {

		Criteria criteria = getCurrentSession().createCriteria(ImprVO.class);
		if(id != null) {
			criteria.add(Restrictions.ge("id", id));
		}
		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (fechaInicio != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaInicio));
		}
		if (fechaFin != null) {
			utilesFecha.setHoraEnDate(fechaFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaFin));
		}
		if(StringUtils.isNotBlank(matricula)) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if(StringUtils.isNotBlank(bastidor)) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}
		if(StringUtils.isNotBlank(nive)) {
			criteria.add(Restrictions.eq("nive", nive));
		}
		if(StringUtils.isNotBlank(tipoImpr)) {
			criteria.add(Restrictions.eq("tipoImpr", tipoImpr));
		}
		if(StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}
		if(StringUtils.isNotBlank(numExpediente)) {
			criteria.add(Restrictions.eq("numExpediente",  new BigDecimal(numExpediente)));
		}
		if(StringUtils.isNotBlank(estadoSolicitud)) {
			criteria.add(Restrictions.eq("estadoSolicitud", estadoSolicitud));
		}
		if(StringUtils.isNotBlank(estadoImpresion)) {
			criteria.add(Restrictions.eq("estadoImpresion", estadoImpresion));
		}
		if(StringUtils.isNotBlank(jefatura)) {
			criteria.add(Restrictions.eq("jefatura", jefatura));
		}
		if(docId != null) {
			criteria.add(Restrictions.eq("docId", docId));
		}
		if(StringUtils.isNotBlank(carpeta)) {
			criteria.add(Restrictions.eq("carpeta", carpeta));
		}
		if(StringUtils.isNotBlank(estadoImpr)) {
			criteria.add(Restrictions.eq("estadoImpr", estadoImpr));
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Object> buscarConsultaTramite(Long id, String matricula, String bastidor, String nive, String tipoImpr,
			String tipoTramite, String numExpediente, String estadoSolicitud, String estadoImpresion, Date fechaInicio,
			Date fechaFin, String jefatura, Long docId, Long idContrato, String carpeta, String estadoImpr,
			int firstResult, int maxResult, String dir, String sort) throws Exception {

		Criteria criteria = getCurrentSession().createCriteria(ImprVO.class);
		if(id != null) {
			criteria.add(Restrictions.ge("id", id));
		}
		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (fechaInicio != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaInicio));
		}
		if (fechaFin != null) {
			utilesFecha.setHoraEnDate(fechaFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaFin));
		}
		if(StringUtils.isNotBlank(matricula)) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if(StringUtils.isNotBlank(bastidor)) {
			criteria.add(Restrictions.eq("bastidor", bastidor));
		}
		if(StringUtils.isNotBlank(nive)) {
			criteria.add(Restrictions.eq("nive", nive));
		}
		if(StringUtils.isNotBlank(tipoImpr)) {
			criteria.add(Restrictions.eq("tipoImpr", tipoImpr));
		}
		if(StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}
		if(StringUtils.isNotBlank(numExpediente)) {
			criteria.add(Restrictions.eq("numExpediente", new BigDecimal(numExpediente)));
		}
		if(StringUtils.isNotBlank(estadoSolicitud)) {
			criteria.add(Restrictions.eq("estadoSolicitud", estadoSolicitud));
		}
		if(StringUtils.isNotBlank(estadoImpresion)) {
			criteria.add(Restrictions.eq("estadoImpresion", estadoImpresion));
		}
		if(StringUtils.isNotBlank(jefatura)) {
			criteria.add(Restrictions.eq("jefatura", jefatura));
		}
		if(docId != null) {
			criteria.add(Restrictions.eq("docId", docId));
		}
		if(StringUtils.isNotBlank(carpeta)) {
			criteria.add(Restrictions.eq("carpeta", carpeta));
		}
		if(StringUtils.isNotBlank(estadoImpr)) {
			criteria.add(Restrictions.eq("estadoImpr", estadoImpr));
		}
		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResult > 0) {
			criteria.setMaxResults(maxResult);
		}

		if (StringUtils.isNotBlank(dir) && StringUtils.isNotBlank(sort)) {
			if ("desc".equals(dir)) {
				criteria.addOrder(Order.desc(sort));
			} else {
				criteria.addOrder(Order.asc(sort));
			}
		} else {
			criteria.addOrder(Order.asc("numExpediente"));
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"));
		projections.add(Projections.property("numExpediente"));
		projections.add(Projections.property("matricula"));
		projections.add(Projections.property("bastidor"));
		projections.add(Projections.property("carpeta"));
		projections.add(Projections.property("tipoImpr"));
		projections.add(Projections.property("tipoTramite"));
		projections.add(Projections.property("estadoImpr"));
		projections.add(Projections.property("estadoSolicitud"));
		projections.add(Projections.property("docId"));
		projections.add(Projections.property("fechaAlta"));
		criteria.setProjection(projections);

		return criteria.list();
	}

	@Override
	public ImprVO getImpr(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(ImprVO.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
		return (ImprVO) criteria.uniqueResult();
	}

	@Override
	public List<ImprVO> getListaImprPorIds(List<Long> listaIds) {
		Criteria criteria = getCurrentSession().createCriteria(ImprVO.class);
		for (Long id : listaIds) {
			criteria.add(Restrictions.eq("id", id));
		}
		return criteria.list();
	}

	@Override
	public List<ImprVO> getListaImprPorIds(Long[] ids) {
		Criteria criteria = getCurrentSession().createCriteria(ImprVO.class);
		criteria.add(Restrictions.in("id", ids));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

}