package org.gestoresmadrid.core.docPermDistItv.model.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

@Repository
public class VehNoMatOegamDaoImpl extends GenericDaoImplHibernate<VehNoMatOegamVO> implements VehNoMatOegamDao {

	private static final long serialVersionUID = -1541663058640483891L;
	
	@Override
	public List<VehNoMatOegamVO> getListaDuplicadosWS(Long idContrato, Date fechaAltaInicio, Date fechaAltaFin,
			String matricula, String bastidor, String nive, String tipoDistintivo, Long[] listaIdVehNoMatOegam) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		if(listaIdVehNoMatOegam == null || listaIdVehNoMatOegam.length == 0) {
			if(fechaAltaInicio != null) {
				Conjunction and = Restrictions.conjunction();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaAltaInicio);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				and.add(Restrictions.ge("fechaAlta", calendar.getTime()));
				if(fechaAltaFin != null) {
					calendar.setTime(fechaAltaFin);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
					and.add(Restrictions.lt("fechaAlta", calendar.getTime()));
				}
				criteria.add(and);
			}
			if(matricula != null && !matricula.isEmpty()) {
				criteria.add(Restrictions.eq("matricula", matricula));
			}
			if(bastidor != null && !bastidor.isEmpty()) {
				criteria.add(Restrictions.eq("bastidor", bastidor));
			}
			if(nive != null && !nive.isEmpty()) {
				criteria.add(Restrictions.eq("nive", nive));
			}
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()) {
				criteria.add(Restrictions.eq("tipoDistintivo", tipoDistintivo));
			}
		} else {
			criteria.add(Restrictions.in("id", listaIdVehNoMatOegam));
		}
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("matricula"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<VehNoMatOegamVO>) criteria.list();
	}
	
	@Override
	public List<VehNoMatOegamVO> getListaVehNoMatOegamVOPorMatriculaContrato(String matricula, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("matricula", matricula));
		criteria.add(Restrictions.eq("idContrato", idContrato));
		String[] estados = new String[]{EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum(),
				EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum()};
		criteria.add(Restrictions.not(Restrictions.in("estadoImpresion", estados)));
		return criteria.list();
	}
	
	@Override
	public List<ConsumoMaterialValue> getListaConsumoDstvDuplicadosJefaturaPorDia(String jefatura, Date fecha) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select count(*) as cantidad, dc.TIPO_DISTINTIVO as tipoDistintivo "); 
		sqlBuf.append("from OEGAM2.VEHICULO_NO_MATRICULADO_OEGAM vhn, OEGAM2.DOC_PERM_DIST_ITV dc ");
		sqlBuf.append("where vhn.DOC_DISTINTIVO = dc.id and to_char(dc.fecha_impresion, 'DD/MM/YYYY') = :fecha ");
		sqlBuf.append("and dc.jefatura = :jefatura ");
		sqlBuf.append("group by dc.TIPO_DISTINTIVO");
		SQLQuery query = getCurrentSession().createSQLQuery(sqlBuf.toString());
		query.addScalar("tipoDistintivo", new StringType());
		query.addScalar("cantidad", new IntegerType());
		query.setParameter("jefatura", jefatura);
		query.setParameter("fecha", new SimpleDateFormat("dd/MM/yy").format(fecha));
		query.setResultTransformer(Transformers.aliasToBean(ConsumoMaterialValue.class));
		return query.list();
	}
	
	@Override
	public VehNoMatOegamVO getVehNoMatOegamVOPorMatriculaColegiadoPendiente(String matricula, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("matricula", matricula));
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.add(Restrictions.eq("estadoImpresion", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		return (VehNoMatOegamVO) criteria.uniqueResult();
	}
	
	@Override
	public HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos) {
		HashMap<String, List<ConsumoMaterialValue>> consumoMaterialJefatura = 
				new HashMap<String, List<ConsumoMaterialValue>>();

		for ( String key : docDistintivos.keySet() ) {
			List<Long> distintivosForJefatura = docDistintivos.get(key);
			
			Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
			criteria.add(Restrictions.in("docDistintivo", distintivosForJefatura));
			criteria.setProjection(Projections.projectionList()
			.add(Projections.groupProperty("tipoDistintivo"), "tipoMaterial")
			.add(Projections.rowCount(), "consumo"))
			.setResultTransformer(Transformers.aliasToBean(ConsumoMaterialValue.class));
			
			List<ConsumoMaterialValue> consumos = criteria.list();
			if (consumos.size() > 0) {
				consumoMaterialJefatura.put(key, consumos);
			}
		}
		return consumoMaterialJefatura;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaIdsContratosConDistintivosDuplicados() {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("estadoImpresion", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		criteria.add(Restrictions.isNotNull("tipoDistintivo"));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("contrato.idContrato")));
		return criteria.list();
	}
	
	@Override
	public int comprobarExisteMatricula(String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("matricula", matricula));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public VehNoMatOegamVO getVehNoMatrOegamPorId(Long idVehNotMatOegam) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("id", idVehNotMatOegam));
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO",CriteriaSpecification.LEFT_JOIN);
		return (VehNoMatOegamVO) criteria.uniqueResult();
	}
	
	@Override
	public VehNoMatOegamVO getVehNoMatOegamVOPorMatricula(String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("matricula", matricula));
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO",CriteriaSpecification.LEFT_JOIN);
		return (VehNoMatOegamVO) criteria.uniqueResult();
	}
	
	@Override
	public Integer getCountNumVehNotMatOegamDstv(Long idDocPermDistItv) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("docDistintivo", idDocPermDistItv));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehNoMatOegamVO> getListaVehiculosPorListaIds(Long[] idsVehsNotMatOegam) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.in("id", idsVehsNotMatOegam));
		criteria.createAlias("contrato.colegiado", "contratoColegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehNoMatOegamVO> getListaVehiculosPdteImpresionPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.add(Restrictions.eq("estadoImpresion", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		criteria.add(Restrictions.eq("estadoSolicitud", EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum()));
		criteria.add(Restrictions.isNull("docDistintivo"));
		criteria.add(Restrictions.isNotNull("tipoDistintivo"));
		criteria.createAlias("contrato.colegiado", "contratoColegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico",CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehNoMatOegamVO> getListaVehiculoPorDocId(Long idDocPermDistItv) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("docDistintivo", idDocPermDistItv));
		criteria.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.jefaturaTrafico", "contratoJefaturaTrafico",CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("matricula"));
		return criteria.list();
	}
	
	
	@Override
	public List<VehNoMatOegamVO> getListaDuplicadosPorMatricula(String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("matricula", matricula));
		String[] estados = new String[]{EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum(),
				EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(),
				EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum(),
				EstadoPermisoDistintivoItv.IMPRIMIENDO_GESTORIA.getValorEnum(),
				EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum(),
				EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()};
		criteria.add(Restrictions.in("estadoImpresion", estados));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public List<String> listaMatriculasPorDocDisintintivo(Long docDistintivo) {
		Criteria criteria = getCurrentSession().createCriteria(VehNoMatOegamVO.class);
		criteria.add(Restrictions.eq("docDistintivo", docDistintivo));
		criteria.setProjection(Projections.property("matricula"));
		return criteria.list();
	}
}
