package org.gestoresmadrid.core.facturacionDistintivo.model.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.facturacionDistintivo.model.dao.FacturacionDistintivoDao;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDistintivoVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.ResultFacturacionDstv;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.FechaFraccionada;

@Repository
public class FacturacionDistintivoDaoImpl extends GenericDaoImplHibernate<FacturacionDistintivoVO>
		implements FacturacionDistintivoDao {

	private static final long serialVersionUID = -4182613603018863879L;

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultFacturacionDstv> getListaFacturacionExcel(FechaFraccionada fecha, Long idContrato, String docDistintivo, String tipoDistintivo,String estado) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select sum(TOTAL_FACTURADO_DSTV) as cantidadDstv, sum(TOTAL_FACTURADO_DUP) as cantidadDup,");
		sqlBuf.append(" cc.num_colegiado as numColegiado, to_char(dstv.fecha, 'DD/MM/YYYY') as fecha, ct.VIA as via ");
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			sqlBuf.append(", dstv.TIPO_DSTV ");
		}
		sqlBuf.append("from DISTINTIVOS_FACTURADOs dstv, contrato ct, CONTRATO_COLEGIADO cc, DOC_PERM_DIST_ITV doc ");
		sqlBuf.append("where ");
		sqlBuf.append("dstv.id_contrato = ct.id_contrato and ct.ID_CONTRATO = cc.id_contrato and doc.id = dstv.ID_DOC_DSTV ");
		if(fecha.getFechaInicio() != null && fecha.getFechaFin() != null){
			sqlBuf.append("and dstv.FECHA >= :fechaIni and dstv.FECHA <= :fechaFin ");
		} else {
			sqlBuf.append("and dstv.FECHA >= :fechaIni ");
		}
		if(idContrato != null){
			sqlBuf.append("and dstv.id_contrato >= :idContrato ");
		}
		// TODO: creo que esto no filtra bien
		if(docDistintivo != null && !docDistintivo.isEmpty()){
			sqlBuf.append("and doc.DOC_PERMDSTVEITV_ID >= :docDistintivo ");
		}
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			sqlBuf.append("and dstv.tipo_dstv >= :tipoDistintivo ");
		}
		sqlBuf.append("and doc.estado = :estado ");
		sqlBuf.append("and doc.jefatura IN ('M', 'M1') ");
		sqlBuf.append("group by cc.num_colegiado,  to_char(dstv.fecha, 'DD/MM/YYYY') , ct.VIA ");
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			sqlBuf.append(", dstv.TIPO_DSTV ");
		}
		sqlBuf.append("order by cc.num_colegiado, to_char(dstv.fecha, 'DD/MM/YYYY')");
		SQLQuery query = getCurrentSession().createSQLQuery(sqlBuf.toString());
		query.addScalar("cantidadDstv", new LongType());
		query.addScalar("cantidadDup", new LongType());
		query.addScalar("numColegiado", new StringType());
		query.addScalar("fecha", new StringType());
		query.addScalar("via", new StringType());
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			query.addScalar("tipo", new StringType());
		}
		if(idContrato != null){
			query.setParameter("idContrato", idContrato);
		}
		if(docDistintivo != null && !docDistintivo.isEmpty()){
			query.setParameter("docDistintivo", docDistintivo);
		}
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			query.setParameter("tipoDistintivo", tipoDistintivo);
		}
		if(fecha.getFechaInicio() != null && fecha.getFechaFin() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha.getFechaInicio());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIni", new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
			calendar.setTime(fecha.getFechaFin());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			query.setParameter("fechaFin", new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha.getFechaInicio());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIni", new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
		}
		query.setParameter("estado", estado);
		query.setResultTransformer(Transformers.aliasToBean(ResultFacturacionDstv.class));
		return query.list();
	}

	@Override
	public FacturacionDistintivoVO getFacturacionPorId(Long idFacturacion) {
		Criteria criteria = getCurrentSession().createCriteria(FacturacionDistintivoVO.class);
		criteria.add(Restrictions.eq("idDistintivoFacturado", idFacturacion));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivo", "docDistintivo", CriteriaSpecification.LEFT_JOIN);
		return (FacturacionDistintivoVO) criteria.uniqueResult();
	}

	@Override
	public FacturacionDistintivoVO getFacturacionPorDocId(Long idDoc) {
		Criteria criteria = getCurrentSession().createCriteria(FacturacionDistintivoVO.class);
		criteria.add(Restrictions.eq("idDocPermDistItv", idDoc));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivo", "docDistintivo", CriteriaSpecification.LEFT_JOIN);
		return (FacturacionDistintivoVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturacionDistintivoVO> getListaFacturacionExcelDetallado(FechaFraccionada fecha, Long idContrato, String docDistintivo, String tipoDistintivo) {
		Criteria criteria = getCurrentSession().createCriteria(FacturacionDistintivoVO.class);
		if(idContrato != null){
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if(docDistintivo != null && !docDistintivo.isEmpty()){
			criteria.add(Restrictions.eq("idDocPermDistItv", docDistintivo));
		}
		if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
			criteria.add(Restrictions.eq("tipoDistintivo", tipoDistintivo));
		}
		if (fecha != null){
			Date fechaIni = fecha.getFechaMinInicio();
			Date fechaFin = fecha.getFechaMaxFin();
			if (fechaIni != null && fechaFin != null) {
				criteria.add(Restrictions.between("fecha", fechaIni, fechaFin));
			} else if (fechaIni != null) {
				criteria.add(Restrictions.ge("fecha", fechaIni));
			} else if (fechaFin != null) {
				criteria.add(Restrictions.le("fecha", fechaFin));
			}
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public List<Long> getListaDocIds(String numColegiado, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(FacturacionDistintivoVO.class);
		criteria.add(Restrictions.eq("contratoColegiado.numColegiado", numColegiado));
		criteria.add(Restrictions.eq("fecha", fecha));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("idDocPermDistItv")));
		return criteria.list();
	}
}