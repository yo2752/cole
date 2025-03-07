package org.gestoresmadrid.core.impr.model.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.impr.model.dao.DocImprDao;
import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocImprDaoImpl  extends GenericDaoImplHibernate<DocImprVO> implements DocImprDao{

	private static final long serialVersionUID = 4937910532112350598L;

	private static final Logger log = LoggerFactory.getLogger(DocImprDaoImpl.class);

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public DocImprVO getDocImprPorId(Long id, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		if (completo) {
			criteria.createAlias("listaImpr", "listaImpr", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaDistintivos", "listaDistintivos", CriteriaSpecification.LEFT_JOIN);
		}
		return (DocImprVO) criteria.uniqueResult();
	}

	@Override
	public int numeroElementosConsultaTramite(Long id, Long docImpr, String matricula, String tipoImpr,
			String tipoTramite, String numExpediente, String estado, Date fechaAltaInicio, Date fechaAltaFin,
			Date fechaImpresionInicio, Date fechaImpresionFin, String jefatura, Long idContrato, String carpeta) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(DocImprVO.class);
		if(id != null) {
			criteria.add(Restrictions.ge("id", id));
		}
		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (fechaAltaInicio != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaAltaInicio));
		}
		if (fechaAltaFin != null) {
			utilesFecha.setHoraEnDate(fechaAltaFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaAltaFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaAltaFin));
		}
		if (fechaImpresionInicio != null) {
			criteria.add(Restrictions.ge("fechaImpresion", fechaImpresionInicio));
		}
		if (fechaImpresionFin != null) {
			utilesFecha.setHoraEnDate(fechaImpresionFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaImpresionFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaImpresion", fechaImpresionFin));
		}
		criteria.setFetchMode("listaImpr", FetchMode.JOIN);
		if(StringUtils.isNotBlank(matricula)) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if(StringUtils.isNotBlank(numExpediente)) {
			criteria.add(Restrictions.eq("numExpediente",  new BigDecimal(numExpediente)));
		}
		if(StringUtils.isNotBlank(tipoImpr)) {
			criteria.add(Restrictions.eq("tipo", tipoImpr));
		}
		if(StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}
		if(StringUtils.isNotBlank(estado)) {
			criteria.add(Restrictions.eq("estado", estado));
		}
		if(StringUtils.isNotBlank(jefatura)) {
			criteria.add(Restrictions.eq("jefatura", jefatura));
		}
		if(docImpr != null) {
			criteria.add(Restrictions.eq("docImpr", docImpr));
		}
		if(StringUtils.isNotBlank(carpeta)) {
			criteria.add(Restrictions.eq("carpeta", carpeta));
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Object> buscarConsultaTramite(Long id, Long docImpr, String matricula, String tipoImpr,
			String tipoTramite, String numExpediente, String estado, Date fechaAltaInicio, Date fechaAltaFin,
			Date fechaImpresionInicio, Date fechaImpresionFin, String jefatura, Long idContrato, String carpeta,
			int firstResult, int maxResults, String dir, String sort) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(DocImprVO.class);
		if(id != null) {
			criteria.add(Restrictions.ge("id", id));
		}
		if (idContrato != null) {
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (fechaAltaInicio != null) {
			criteria.add(Restrictions.ge("fechaAlta", fechaAltaInicio));
		}
		if (fechaAltaFin != null) {
			utilesFecha.setHoraEnDate(fechaAltaFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaAltaFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaAlta", fechaAltaFin));
		}
		if (fechaImpresionInicio != null) {
			criteria.add(Restrictions.ge("fechaImpresion", fechaImpresionInicio));
		}
		if (fechaImpresionFin != null) {
			utilesFecha.setHoraEnDate(fechaImpresionFin, horaFinDia);
			utilesFecha.setSegundosEnDate(fechaImpresionFin, N_SEGUNDOS);
			criteria.add(Restrictions.le("fechaImpresion", fechaImpresionFin));
		}
		criteria.setFetchMode("listaImpr", FetchMode.JOIN);
		if(StringUtils.isNotBlank(matricula)) {
			criteria.add(Restrictions.eq("matricula", matricula));
		}
		if(StringUtils.isNotBlank(numExpediente)) {
			criteria.add(Restrictions.eq("numExpediente",  new BigDecimal(numExpediente)));
		}
		if(StringUtils.isNotBlank(tipoImpr)) {
			criteria.add(Restrictions.eq("tipo", tipoImpr));
		}
		if(StringUtils.isNotBlank(tipoTramite)) {
			criteria.add(Restrictions.eq("tipoTramite", tipoTramite));
		}
		if(StringUtils.isNotBlank(estado)) {
			criteria.add(Restrictions.eq("estado", estado));
		}
		if(StringUtils.isNotBlank(jefatura)) {
			criteria.add(Restrictions.eq("jefatura", jefatura));
		}
		if(docImpr != null) {
			criteria.add(Restrictions.eq("docImpr", docImpr));
		}
		if(StringUtils.isNotBlank(carpeta)) {
			criteria.add(Restrictions.eq("carpeta", carpeta));
		}
		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}

		if (StringUtils.isNotBlank(dir) && StringUtils.isNotBlank(sort)) {
			if ("desc".equals(dir)) {
				criteria.addOrder(Order.desc(sort));
			} else {
				criteria.addOrder(Order.asc(sort));
			}
		} else {
			criteria.addOrder(Order.asc("id"));
		}

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("id"));
		projections.add(Projections.property("docImpr"));
		projections.add(Projections.property("tipo"));
		projections.add(Projections.property("carpeta"));
		projections.add(Projections.property("estado"));
		projections.add(Projections.property("fechaImpresion"));
		projections.add(Projections.property("fechaAlta"));
		projections.add(Projections.property("tipoTramite"));
		criteria.setProjection(projections);

		return criteria.list();
	}
}