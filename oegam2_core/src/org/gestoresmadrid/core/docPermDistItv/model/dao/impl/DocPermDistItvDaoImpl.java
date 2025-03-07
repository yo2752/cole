package org.gestoresmadrid.core.docPermDistItv.model.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.beans.FacturacionDocBean;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoDocumentoEnum;
import org.gestoresmadrid.core.trafico.materiales.model.values.CantidadDistintivoValue;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

@Repository
public class DocPermDistItvDaoImpl extends GenericDaoImplHibernate<DocPermDistItvVO> implements DocPermDistItvDao {

	private static final long serialVersionUID = -1027023413212360737L;
	static final String LOG_PROCESO_DOC_PERMISO_GENERATOR = "LOG_PROCESO_DOC_PERMISO_GENERATOR: ";

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListaJefaturasImpresionDia(Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		Calendar calendar = Calendar.getInstance();
		Conjunction and = Restrictions.conjunction();	
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		and.add(Restrictions.ge("fechaImpresion", fecMin));
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		and.add(Restrictions.lt("fechaImpresion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum())));
		criteria.setProjection(Projections.distinct(Projections.property("jefatura")));
		return criteria.list();
	}
	
	@Override
	public List<Long> getContratosConImpresionesPorDia(String tipoDocumento, String tipoDistintivo, Date fechaInicio, Date fechaFin) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		Calendar calendar = Calendar.getInstance();
		Conjunction and = Restrictions.conjunction();	
		calendar.setTime(fechaInicio);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		and.add(Restrictions.ge("fechaImpresion", fecMin));
		if(fechaFin != null){
			calendar.setTime(fechaFin);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			Date fecMax = calendar.getTime();
			and.add(Restrictions.lt("fechaImpresion", fecMax));
		}
		criteria.add(and);
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum())));
		if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDocumento)){
			criteria.add(Restrictions.eq("tipo", tipoDocumento));
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
				criteria.add(Restrictions.eq("tipoDistintivo", tipoDistintivo));
			}
		}
		criteria.setProjection(Projections.distinct(Projections.property("idContrato")));
		return criteria.list();
	}
	
	@Override
	public List<CantidadDistintivoValue> getListaFacturacionPorContratoDstvTrafico(Long idContrato, String tipoDistintivo, Date fechaInicio, Date fechaFin) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select count(*) as cantidad, to_char(dc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, cl.NUM_COLEGIADO as numColegiado,");
		sqlBuf.append("us.apellidos_nombre as nombreColegiado, ct.via as via, pr.nombre as provincia, dc.TIPO_DISTINTIVO as tipoDistintivo ");
		sqlBuf.append("from DOC_PERM_DIST_ITV dc, CONTRATO ct, CONTRATO_COLEGIADO cc, COLEGIADO cl, PROVINCIA pr, usuario us , TRAMITE_TRAF_MATR tm ");
		sqlBuf.append("where dc.ID_CONTRATO = ct.id_contrato and ct.ID_CONTRATO = cc.ID_CONTRATO and cc.NUM_COLEGIADO = cl.NUM_COLEGIADO ");
		sqlBuf.append("and ct.ID_PROVINCIA = pr.ID_PROVINCIA and us.id_usuario = cl.id_usuario and dc.id = tm.doc_distintivo ");
		sqlBuf.append("and dc.id_contrato = :idContrato ");
		if(fechaInicio != null && fechaFin != null){
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion and dc.FECHA_IMPRESION < :fechaFinImpresion ");
		} else {
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion ");
		}
		sqlBuf.append("GROUP BY to_char(dc.fecha_impresion, 'DD/MM/YYYY') , cl.NUM_COLEGIADO ,us.apellidos_nombre , ct.via , pr.nombre, dc.TIPO_DISTINTIVO "); 
		SQLQuery query = getCurrentSession().createSQLQuery(sqlBuf.toString());
		query.addScalar("numColegiado", new StringType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("nombreColegiado", new StringType());
		query.addScalar("via", new StringType());
		query.addScalar("provincia", new StringType());
		query.addScalar("cantidad", new LongType());
		query.addScalar("tipoDistintivo", new StringType());
		
		query.setParameter("idContrato", idContrato);
		
		if(fechaInicio != null && fechaFin != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaInicio);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIniImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
			calendar.setTime(fechaFin);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			query.setParameter("fechaFinImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaInicio);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIniImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
		}
		query.setResultTransformer(Transformers.aliasToBean(CantidadDistintivoValue.class));
		return query.list();
	}
	
	
	@Override
	public List<CantidadDistintivoValue> getListaFacturacionPorContratoDstvDuplicado(Long idContrato, String tipoDistintivo, Date fechaInicio, Date fechaFin) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select count(*) as cantidad, to_char(dc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, cl.NUM_COLEGIADO as numColegiado,");
		sqlBuf.append("us.apellidos_nombre as nombreColegiado, ct.via as via, pr.nombre as provincia, dc.TIPO_DISTINTIVO as tipoDistintivo ");
		sqlBuf.append("from DOC_PERM_DIST_ITV dc, CONTRATO ct, CONTRATO_COLEGIADO cc, COLEGIADO cl, PROVINCIA pr, usuario us ,"
				+ "VEHICULO_NO_MATRICULADO_OEGAM vh ");
		sqlBuf.append("where dc.ID_CONTRATO = ct.id_contrato and ct.ID_CONTRATO = cc.ID_CONTRATO and cc.NUM_COLEGIADO = cl.NUM_COLEGIADO ");
		sqlBuf.append("and ct.ID_PROVINCIA = pr.ID_PROVINCIA and us.id_usuario = cl.id_usuario and dc.id = vh.doc_distintivo ");
		sqlBuf.append("and dc.id_contrato = :idContrato ");
		if(fechaInicio != null && fechaFin != null){
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion and dc.FECHA_IMPRESION < :fechaFinImpresion ");
		} else {
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion ");
		}
		sqlBuf.append("GROUP BY to_char(dc.fecha_impresion, 'DD/MM/YYYY') , cl.NUM_COLEGIADO ,us.apellidos_nombre , ct.via , pr.nombre,dc.TIPO_DISTINTIVO "); 
		SQLQuery query = getCurrentSession().createSQLQuery(sqlBuf.toString());
		query.addScalar("numColegiado", new StringType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("nombreColegiado", new StringType());
		query.addScalar("via", new StringType());
		query.addScalar("provincia", new StringType());
		query.addScalar("cantidad", new LongType());
		query.addScalar("tipoDistintivo", new StringType());
		
		query.setParameter("idContrato", idContrato);
		
		if(fechaInicio != null && fechaFin != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaInicio);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIniImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
			calendar.setTime(fechaFin);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			query.setParameter("fechaFinImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaInicio);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			query.setParameter("fechaIniImpresion", new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()));
		}
		query.setResultTransformer(Transformers.aliasToBean(CantidadDistintivoValue.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FacturacionDocBean> getListaDocumentosFacturacionStcok(Long idContrato, String tipoDoc,
			String tipoDistintivo, Date fechaInicio, Date fechaFin) {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select to_char(dc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, cl.NUM_COLEGIADO as num_colegiado,");
		sqlBuf.append("us.apellidos_nombre as nombreColegiado, ct.via as via, pr.nombre as provincia, ");
		rellenarTipoDoc(tipoDoc,tipoDistintivo, sqlBuf);
		sqlBuf.append("from DOC_PERM_DIST_ITV dc, CONTRATO ct, CONTRATO_COLEGIADO cc, COLEGIADO cl, PROVINCIA pr, usuario us ");
		sqlBuf.append("where dc.ID_CONTRATO = ct.id_contrato and ct.ID_CONTRATO = cc.ID_CONTRATO and cc.NUM_COLEGIADO = cl.NUM_COLEGIADO ");
		sqlBuf.append("and ct.ID_PROVINCIA = pr.ID_PROVINCIA and us.id_usuario = cl.id_usuario ");
		
		if(idContrato != null){
			sqlBuf.append("and dc.id_contrato = :idContrato ");
		}
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		if(fechaInicio != null && fechaFin != null){
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion and dc.FECHA_IMPRESION < :fechaFinImpresion ");
		} else {
			sqlBuf.append("and dc.FECHA_IMPRESION >= :fechaIniImpresion ");
		}
		
		sqlBuf.append("order by dc.fecha_impresion asc");
		SQLQuery query = getCurrentSession().createSQLQuery(sqlBuf.toString());
		
		query.addScalar("num_colegiado", new StringType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("nombreColegiado", new StringType());
		query.addScalar("via", new StringType());
		query.addScalar("provincia", new StringType());
		
		if(TipoDistintivo.C.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalC", new LongType());
			query.addScalar("totalCVh", new LongType());
		}else if(TipoDistintivo.CERO.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalCero", new LongType());
			query.addScalar("totalCeroVh", new LongType());
		}else if(TipoDistintivo.B.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalB", new LongType());
			query.addScalar("totalBVh", new LongType());
		}else if(TipoDistintivo.ECO.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalEco", new LongType());
			query.addScalar("totalEcoVh", new LongType());
		}else if(TipoDistintivo.CMT.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalCMts", new LongType());
			query.addScalar("totalCMtsVh", new LongType());
		}else if(TipoDistintivo.BMT.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalBMts", new LongType());
			query.addScalar("totalBMtsVh", new LongType());
		}else if(TipoDistintivo.CEROMT.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalCeroMts", new LongType());
			query.addScalar("totalCeroMtsVh", new LongType());
		}else if(TipoDistintivo.ECOMT.getValorEnum().equals(tipoDistintivo)){
			query.addScalar("totalEcoMts", new LongType());
			query.addScalar("totalEcoMtsVh", new LongType());
		}else {
			query.addScalar("totalC", new LongType());
			query.addScalar("totalCVh", new LongType());
			query.addScalar("totalCero", new LongType());
			query.addScalar("totalCeroVh", new LongType());
			query.addScalar("totalB", new LongType());
			query.addScalar("totalBVh", new LongType());
			query.addScalar("totalEco", new LongType());
			query.addScalar("totalEcoVh", new LongType());
			query.addScalar("totalCMts", new LongType());
			query.addScalar("totalCMtsVh", new LongType());
			query.addScalar("totalBMts", new LongType());
			query.addScalar("totalBMtsVh", new LongType());
			query.addScalar("totalCeroMts", new LongType());
			query.addScalar("totalCeroMtsVh", new LongType());
			query.addScalar("totalEcoMts", new LongType());
			query.addScalar("totalEcoMtsVh", new LongType());
		}
		
		
		if(idContrato != null){
			query.setParameter("idContrato", idContrato);
		}
		if(fechaInicio != null && fechaFin != null){
			query.setParameter("fechaIniImpresion", df.format(fechaInicio));
			query.setParameter("fechaFinImpresion", df.format(fechaFin));
		} else {
			query.setParameter("fechaIniImpresion", df.format(fechaInicio));
		}
		
		query.setResultTransformer(Transformers.aliasToBean(FacturacionDocBean.class));

		return query.list();
	}
	
	private void rellenarTipoDoc(String tipoDoc, String tipoDistintivo, StringBuffer sqlBuf) {
		if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDoc)){
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()) {
				if(TipoDistintivo.C.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.C.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalC,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.C.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCVh ");
				}else if(TipoDistintivo.CERO.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CERO.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCero,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CERO.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCeroVh ");
				}else if(TipoDistintivo.B.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.B.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalB,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.B.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalBVh ");
				}else if(TipoDistintivo.ECO.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.ECO.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalEco,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.ECO.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalEcoVh ");
				}else if(TipoDistintivo.CMT.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCMts,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CMT.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCMtsVh ");
				}else if(TipoDistintivo.BMT.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.BMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalBMts,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.BMT.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalBMTSVh ");
				}else if(TipoDistintivo.CEROMT.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CEROMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCeroMts,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CEROMT.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCeroMtsVh ");
				}else if(TipoDistintivo.ECOMT.getValorEnum().equals(tipoDistintivo)){
					sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
						+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.ECOMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
						"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalEcoMts,");
					sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.ECOMT.getValorEnum() + "'"+
						" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalEcoMtsVh ");
				}
			} else {
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.C.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalC,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.C.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCVh,");
				
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.B.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalB,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.B.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalBVh,");
						
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CERO.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCero,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CERO.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCeroVh,");
					
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.ECO.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalEco,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.ECO.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalEcoVh,");
								
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCMts,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CMT.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCMtsVh,");
									
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.CEROMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalCeroMts,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.CEROMT.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalCeroMtsVh,");
										
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.BMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalBMts,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.BMT.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalBMTSVh,");
											
				sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAF_MATR tm, DOC_PERM_DIST_ITV dd where "
					+ "dd.id_contrato = dc.id_contrato and tm.TIPO_DISTINTIVO='" + TipoDistintivo.ECOMT.getValorEnum() + "' and dd.id = tm.doc_distintivo "+
					"and to_char(dd.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dd.TIPO_DISTINTIVO) as totalEcoMts,");
				sqlBuf.append("(select count(*) from VEHICULO_NO_MATRICULADO_OEGAM vh, DOC_PERM_DIST_ITV dp where vh.DOC_DISTINTIVO = dp.id and vh.TIPO_DSTV='" + TipoDistintivo.ECOMT.getValorEnum() + "'"+
					" and dp.id_contrato = dc.id_contrato and to_char(dp.fecha_impresion, 'DD/MM/YYYY') = to_char(dc.fecha_impresion, 'DD/MM/YYYY') GROUP BY dp.TIPO_DISTINTIVO) as totalEcoMtsVh ");
			}
		} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDoc)){
			sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAFICO tt where tt.DOC_PERMISO = dc.id) AS totalPermisos ");
		} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc)){
			sqlBuf.append("(select count(*) from OEGAM2.TRAMITE_TRAFICO tt where tt.DOC_FICHA_TECNICA = dc.id) AS totalFichas ");
		} 
	}

	@Override
	public DocPermDistItvVO getPermDistItvPorId(Long idDocPermDistItv, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.eq("idDocPermDistItv", idDocPermDistItv));
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites", "contratocorreosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite", "contratocorreosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite.aplicacion", "contratocorreosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		if (completo) {
			criteria.createAlias("listaTramitesPermiso", "listaTramitesPermiso", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesDistintivo", "listaTramitesDistintivo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesEitv", "listaTramitesEitv", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaDuplicadoDistintivos", "listaDuplicadoDistintivos", CriteriaSpecification.LEFT_JOIN);
		}
		return (DocPermDistItvVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocPermDistItvVO> getListaDocs(String[] sDocId, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.in("docIdPerm", sDocId));
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		if (completo) {
			criteria.createAlias("listaTramitesPermiso", "listaTramitesPermiso", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesDistintivo", "listaTramitesDistintivo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesEitv", "listaTramitesEitv", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaDuplicadoDistintivos", "listaDuplicadoDistintivos", CriteriaSpecification.LEFT_JOIN);
		}
		return criteria.list();
	}

	@Override
	public DocPermDistItvVO getDocPermPorDoc(String docIdPerm, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.eq("docIdPerm", docIdPerm));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites", "contratocorreosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite", "contratocorreosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.correosTramites.tipoTramite.aplicacion", "contratocorreosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		if (completo) {
			criteria.createAlias("listaTramitesPermiso", "listaTramitesPermiso", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesDistintivo", "listaTramitesDistintivo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesEitv", "listaTramitesEitv", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaDuplicadoDistintivos", "listaDuplicadoDistintivos", CriteriaSpecification.LEFT_JOIN);
		}
		return (DocPermDistItvVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocPermDistItvVO> getPermDistItvPorIds(List<Long> listaIdDocPermDstvEitv, Boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.in("idDocPermDistItv", listaIdDocPermDstvEitv));
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		if (completo) {
			criteria.createAlias("listaTramitesPermiso", "listaTramitesPermiso", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesDistintivo", "listaTramitesDistintivo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaTramitesEitv", "listaTramitesEitv", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("listaDuplicadoDistintivos", "listaDuplicadoDistintivos", CriteriaSpecification.LEFT_JOIN);
		}
		return criteria.list();
	}

	@Override
	public String getLastDocId(Date fecha) {
		String docId = null;

		ProjectionList listaProyection = Projections.projectionList();
		listaProyection.add(Projections.property("docIdPerm"));

		List<AliasQueryBean> listaAlias = null;

		List<Criterion> listaCriterion = new ArrayList<Criterion>();

		if (fecha != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			int anio = calendar.get(Calendar.YEAR);

			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.set(anio, 0, 1, 0, 0, 0);
			Calendar calendarFin = Calendar.getInstance();
			calendarFin.set(anio + 1, 0, 1, 0, 0, 0);

			listaCriterion.add(Restrictions.ge("fechaAlta", calendarInicio.getTime()));
			listaCriterion.add(Restrictions.lt("fechaAlta", calendarFin.getTime()));
		}

		String orderParam = "id";
		String orderDir = "desc";

		ArrayList<String> orden = new ArrayList<String>();
		orden.add(orderParam);

		List<?> result = buscarPorCriteria(-1, -1, listaCriterion, orderDir, orden, listaAlias, listaProyection, null);
		if (result != null && !result.isEmpty()) {
			docId = (String) result.get(0);
		}

		if (docId != null) {
			log.info(LOG_PROCESO_DOC_PERMISO_GENERATOR + "LastDocId: '" + docId + "'");
		}

		return docId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocPermDistItvVO> getImpresionesPorFecha(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();

		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaImpresion", fecMin));
		and.add(Restrictions.lt("fechaImpresion", fecMax));
		criteria.add(and);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getIdsImpresionesPorFecha(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();

		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaImpresion", fecMin));
		and.add(Restrictions.lt("fechaImpresion", fecMax));
		criteria.add(and);

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.property("idDocPermDistItv"));

		criteria.setProjection(listaProyecciones);

		return (List<Long>) criteria.list();
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, HashMap<String, Long>> obtenerFactuacionDistintivo(Long contrato, String tipoDistintivo, Date fecDesde, Date fecHasta,
			HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial) {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String fecMin = df.format(fecDesde);
		String fecMax = df.format(fecHasta);

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select doc.id_contrato as contrato, ");
		sqlBuf.append("to_char(doc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, ");
		sqlBuf.append("doc.tipo_distintivo as tipoDistintivo, ");
		sqlBuf.append("count(*) as cantidad ");
		sqlBuf.append("from oegam2.tramite_traf_matr traf ");
		sqlBuf.append("inner join oegam2.doc_perm_dist_itv doc ");
		sqlBuf.append("on traf.doc_distintivo = doc.id ");
		sqlBuf.append("where 1 = 1 ");
		sqlBuf.append("and doc.tipo = 'DSTV' ");
		if (contrato != null && !contrato.equals(-1L)) {
			sqlBuf.append("and doc.ID_CONTRATO = :contrato ");
		}
		if (tipoDistintivo != null && StringUtils.isNotEmpty(tipoDistintivo)) {
			sqlBuf.append("and doc.tipo_distintivo = :tipoDistintivo ");
		}
		sqlBuf.append("and (to_char(doc.fecha_impresion, 'YYYYMMDD') >= :fecMini ");
		sqlBuf.append("and to_char(doc.fecha_impresion, 'YYYYMMDD')  <  :fecMax) ");
		sqlBuf.append("and doc.tipo_distintivo is not null ");
		sqlBuf.append("group by doc.id_contrato, to_char(doc.fecha_impresion, 'DD/MM/YYYY'), doc.tipo_distintivo ");
		String sql = sqlBuf.toString();
		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		query.addScalar("contrato", new LongType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("tipoDistintivo", new StringType());
		query.addScalar("cantidad", new LongType());
		query.setResultTransformer(Transformers.aliasToBean(CantidadDistintivoValue.class));

		if (contrato != null && !contrato.equals(-1L)) {
			query.setParameter("contrato", contrato);
		}
		if (tipoDistintivo != null && StringUtils.isNotEmpty(tipoDistintivo)) {
			query.setParameter("tipoDistintivo", tipoDistintivo);
		}
		query.setParameter("fecMini", fecMin);
		query.setParameter("fecMax", fecMax);

		List<CantidadDistintivoValue> distintivos = query.list();

		if (lineasFacturacionMaterial == null) {
			lineasFacturacionMaterial = new HashMap<String, HashMap<String, Long>>();
		}

		for (CantidadDistintivoValue item : distintivos) {
			String keyDistintivo = item.getContrato().toString() + "-" + item.getFechaImpresion();
			if (lineasFacturacionMaterial.containsKey(keyDistintivo)) {
				HashMap<String, Long> material = lineasFacturacionMaterial.get(keyDistintivo);
				material.put(item.getTipoDistintivo(), new Long(item.getCantidad()));
			} else {
				HashMap<String, Long> material = new HashMap<String, Long>();
				material.put(item.getTipoDistintivo(), new Long(item.getCantidad()));
				lineasFacturacionMaterial.put(keyDistintivo, material);
			}
		}

		return lineasFacturacionMaterial;
	}

	@Override
	public HashMap<String, HashMap<String, Long>> obtenerFactuacionPermiso(Long contrato, Date fecDesde, Date fecHasta, HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial) {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String fecMin = df.format(fecDesde);
		String fecMax = df.format(fecHasta);

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select doc.id_contrato as contrato, ");
		sqlBuf.append("to_char(doc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, ");
		sqlBuf.append("DECODE (doc.tipo, 'PC', 'PC', 'FC/PC', 'PC') as tipoDistintivo, ");
		sqlBuf.append("count(*) as cantidad  ");
		sqlBuf.append("from doc_perm_dist_itv doc ");
		sqlBuf.append("where 1 = 1 ");
		sqlBuf.append("and doc.tipo IN ('PC', 'FC/PC') ");
		sqlBuf.append("and doc.tipo_distintivo is null ");
		if (contrato != null && !contrato.equals(-1L)) {
			sqlBuf.append("and doc.ID_CONTRATO = :contrato ");
		}
		sqlBuf.append("and (to_char(doc.fecha_impresion, 'YYYYMMDD') >= :fecMini ");
		sqlBuf.append("and to_char(doc.fecha_impresion, 'YYYYMMDD')  <  :fecMax) ");
		sqlBuf.append("group by doc.ID_CONTRATO,  to_char(doc.fecha_impresion, 'DD/MM/YYYY'), doc.tipo ");

		String sql = sqlBuf.toString();
		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		query.addScalar("contrato", new LongType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("tipoDistintivo", new StringType());
		query.addScalar("cantidad", new LongType());

		query.setResultTransformer(Transformers.aliasToBean(CantidadDistintivoValue.class));

		if (contrato != null && !contrato.equals(-1L)) {
			query.setParameter("contrato", contrato);
		}
		query.setParameter("fecMini", fecMin);
		query.setParameter("fecMax", fecMax);

		@SuppressWarnings({ "unchecked" })
		List<CantidadDistintivoValue> persmisos = query.list();

		if (lineasFacturacionMaterial == null) {
			lineasFacturacionMaterial = new HashMap<String, HashMap<String, Long>>();
		}

		for (CantidadDistintivoValue item : persmisos) {
			String keyDistintivo = item.getContrato().toString() + "-" + item.getFechaImpresion();
			if (lineasFacturacionMaterial.containsKey(keyDistintivo)) {
				HashMap<String, Long> material = lineasFacturacionMaterial.get(item.getContrato());
				material.put("Permisos", new Long(item.getCantidad()));
			} else {
				HashMap<String, Long> material = new HashMap<String, Long>();
				material.put("Permisos", new Long(item.getCantidad()));
				lineasFacturacionMaterial.put(keyDistintivo, material);
			}
		}

		return lineasFacturacionMaterial;
	}

	@Override
	public HashMap<String, HashMap<String, Long>> obtenerFactuacionFichaTecnica(Long contrato, Date fecDesde, Date fecHasta, HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String fecMin = df.format(fecDesde);
		String fecMax = df.format(fecHasta);

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("select doc.id_contrato as contrato, ");
		sqlBuf.append("to_char(doc.fecha_impresion, 'DD/MM/YYYY') as fechaImpresion, ");
		sqlBuf.append("DECODE (doc.tipo, 'FCT', 'FCT', 'FC/PC', 'FCT') as tipoDistintivo, ");
		sqlBuf.append("count(*) as cantidad  ");
		sqlBuf.append("from doc_perm_dist_itv doc ");
		sqlBuf.append("where 1 = 1 ");
		sqlBuf.append("and doc.tipo IN ('FCT', 'FC/PC') ");
		sqlBuf.append("and doc.tipo_distintivo is null ");
		if (contrato != null && !contrato.equals(-1L)) {
			sqlBuf.append("and doc.ID_CONTRATO = :contrato  ");
		}
		sqlBuf.append("and (to_char(doc.fecha_impresion, 'YYYYMMDD') >= :fecMini ");
		sqlBuf.append("and to_char(doc.fecha_impresion, 'YYYYMMDD')  <  :fecMax) ");
		sqlBuf.append("group by doc.ID_CONTRATO,  to_char(doc.fecha_impresion, 'DD/MM/YYYY'), doc.tipo ");

		String sql = sqlBuf.toString();
		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		query.addScalar("contrato", new LongType());
		query.addScalar("fechaImpresion", new StringType());
		query.addScalar("tipoDistintivo", new StringType());
		query.addScalar("cantidad", new LongType());

		query.setResultTransformer(Transformers.aliasToBean(CantidadDistintivoValue.class));

		if (contrato != null && !contrato.equals(-1L)) {
			query.setParameter("contrato", contrato);
		}
		query.setParameter("fecMini", fecMin);
		query.setParameter("fecMax", fecMax);

		@SuppressWarnings({ "unchecked" })
		List<CantidadDistintivoValue> eitv = query.list();

		if (lineasFacturacionMaterial == null) {
			lineasFacturacionMaterial = new HashMap<String, HashMap<String, Long>>();
		}

		for (CantidadDistintivoValue item : eitv) {
			String keyDistintivo = item.getContrato().toString() + "-" + item.getFechaImpresion();
			if (lineasFacturacionMaterial.containsKey(keyDistintivo)) {
				HashMap<String, Long> material = lineasFacturacionMaterial.get(keyDistintivo);
				material.put("FichaTecnica", new Long(item.getCantidad()));
			} else {
				HashMap<String, Long> material = new HashMap<String, Long>();
				material.put("FichaTecnica", new Long(item.getCantidad()));
				lineasFacturacionMaterial.put(keyDistintivo, material);
			}
		}

		return lineasFacturacionMaterial;
	}*/

	@SuppressWarnings("unchecked")
	public List<DocPermDistItvVO> findDocPermByEstado(BigDecimal estado) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		criteria.add(Restrictions.eq("estado", estado));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocPermDistItvVO> findDocByJefaturaDistintivoFechaImpr(String jefaturaProvincial, Long tipoDocumento, Date fecDesde, Date fecHasta) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class, "documento");

		Date fecDesdeIni = fechaInicioDia(fecDesde);
		Date fecHastaFin = fechaFinDia(fecHasta);

		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("documento.fechaImpresion", fecDesdeIni));
		and.add(Restrictions.lt("documento.fechaImpresion", fecHastaFin));
		criteria.add(and);

		if (StringUtils.isNotEmpty(jefaturaProvincial)) {
			criteria.add(Restrictions.eq("documento.jefatura", jefaturaProvincial));
		}

		if (tipoDocumento != null) {
			if (TipoDocumentoEnum.Distintivo.getValorEnum().equals(tipoDocumento)) {
				criteria.add(Restrictions.eq("documento.tipo", TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum()));
				criteria.add(Restrictions.isNotNull("documento.tipoDistintivo"));
			} else if (TipoDocumentoEnum.PermisoConducir.getValorEnum().equals(tipoDocumento)) {
				String[] permisos = { TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum() };
				criteria.add(Restrictions.in("documento.tipo", permisos));
			} else if (TipoDocumentoEnum.FichaTecnica.getValorEnum().equals(tipoDocumento)) {
				String[] permisos = { TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum() };
				criteria.add(Restrictions.in("documento.tipo", permisos));
			}
		}

		return criteria.list();
	}
	
	@Override
	public List<String> obtenerIdDocPermDistItvMax(Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(DocPermDistItvVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int anio = calendar.get(Calendar.YEAR);
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.set(anio, 0, 1, 0, 0, 0);
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.set(anio + 1, 0, 1, 0, 0, 0);
		criteria.add(Restrictions.between("fechaAlta", calendarInicio.getTime(), calendarFin.getTime()));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("docIdPerm"));
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("idDocPermDistItv"));
		return criteria.list();
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
