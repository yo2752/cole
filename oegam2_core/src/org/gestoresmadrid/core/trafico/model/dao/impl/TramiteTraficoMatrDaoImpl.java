package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.Provincias;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteTraficoMatrDaoImpl extends GenericDaoImplHibernate<TramiteTrafMatrVO> implements TramiteTraficoMatrDao {

	private static final long serialVersionUID = -6684390698408032187L;

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesDistintivosWS(Long idContrato, Date fechaPresentacionInicio,
			Date fechaPresentacionFin, String matricula, String bastidor, String nive, String tipoDistintivo, String nifTitular, BigDecimal[] numExpedientes) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if(numExpedientes == null || numExpedientes.length == 0) {
			if(fechaPresentacionInicio != null) {
				Conjunction and = Restrictions.conjunction();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaPresentacionInicio);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
				and.add(Restrictions.ge("fechaPresentacion", calendar.getTime()));
				if(fechaPresentacionFin != null) {
					calendar.setTime(fechaPresentacionFin);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
					and.add(Restrictions.lt("fechaPresentacion", calendar.getTime()));
				}
				criteria.add(and);
			}
			if(matricula != null && !matricula.isEmpty()) {
				criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
			}
			if(bastidor != null && !bastidor.isEmpty()) {
				criteria.add(Restrictions.eq("vehiculo.bastidor", bastidor));
			}
			if(nive != null && !nive.isEmpty()) {
				criteria.add(Restrictions.eq("vehiculo.nive", nive));
			}
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()) {
				criteria.add(Restrictions.eq("tipoDistintivo", tipoDistintivo));
			}
			if(nifTitular != null  && !nifTitular.isEmpty()) {
				criteria.add(Restrictions.eq("intervinienteTraficos.nif", nifTitular));
				criteria.add(Restrictions.eq("intervinienteTraficos.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
			}
			criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		} else {
			criteria.add(Restrictions.in("numExpediente", numExpedientes));
		}
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafMatrVO>) criteria.list();
	}
	
	@Override
	public int getNumElementosMasivos(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if(fecha!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			Date fecMin = calendar.getTime();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			Date fecMax = calendar.getTime();
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.ge("fechaPresentacion", fecMin));
			and.add(Restrictions.lt("fechaPresentacion", fecMax));
			criteria.add(and);
		}
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.or(Restrictions.isNull("docPermiso"), Restrictions.isNull("docFichaTecnica")));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		if(idContrato!=null)
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getTramitesMasivos(Long idContrato, Date fecha){
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if(fecha!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
			Date fecMin = calendar.getTime();
			calendar.setTime(fecha);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
			Date fecMax = calendar.getTime();
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.ge("fechaPresentacion", fecMin));
			and.add(Restrictions.lt("fechaPresentacion", fecMax));
			criteria.add(and);
		}
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.or(Restrictions.isNull("docPermiso"), Restrictions.isNull("docFichaTecnica")));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		if(idContrato!=null)
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafMatrVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumoMaterialValue> getListaConsumoDstvJefaturaPorDia(String jefatura, Date fecha) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select count(*) as cantidad, dc.TIPO_DISTINTIVO as tipoDistintivo ");
		sqlBuf.append("from OEGAM2.TRAMITE_TRAF_MATR ttm, OEGAM2.DOC_PERM_DIST_ITV dc ");
		sqlBuf.append("where ttm.DOC_DISTINTIVO = dc.id and to_char(dc.fecha_impresion, 'DD/MM/YYYY') = :fecha ");
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaIdsContratosConDistintivos() {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("estadoImpDstv", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		criteria.add(Restrictions.isNotNull("tipoDistintivo"));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("contrato.idContrato")));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesPorDocId(Long docId) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("docDistintivo", docId));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public Integer getCountNumTramitesDstv(Long idDocDstv) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("docDistintivo", idDocDstv));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaIdsContratosSinDstvFinalizados(Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };
		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.or(Restrictions.isNull("docPermiso"), Restrictions.isNull("docItv")));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("contrato.idContrato")));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getListaIDContratosConMatriculacionesPorFecha(Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.distinct(Projections.property("contrato.idContrato")));
		return criteria.list();
	}

	@Override
	public Integer comprobarTramitesEmpresaST(Long idContrato, Date fechaPresentacion, String cifEmpresa, String codigoPostal, String municipio, String provincia) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.eq("intervinienteTraficos.id.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
		criteria.add(Restrictions.eq("intervinienteTraficos.id.nif", cifEmpresa));
		criteria.add(Restrictions.eq("intervinienteTraficosDireccion.codPostal", codigoPostal));
		criteria.add(Restrictions.eq("intervinienteTraficosDireccion.idMunicipio", municipio));
		criteria.add(Restrictions.eq("intervinienteTraficosDireccion.idProvincia", provincia));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public Integer comprobarTramitesST(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.eq("checkJustifFicheroIvtm", "S"));
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public Integer comprobarTramitesFinalizadosTelematicamente(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> listaTramitesFinalizadosTelemPorContrato(Long idContrato, Date fechaPresentacion, String tipoSolicitud, Boolean esMoto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoSolicitud)) {
			criteria.add(Restrictions.isNull("docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
			if(esMoto){
				criteria.add(Restrictions.in("vehiculo.tipoVehiculo", new String[]{TipoVehiculoEnum.MOTOCICLETA_DE_2_RUEDAS_SIN_SIDECAR.getValorEnum(),
						TipoVehiculoEnum.MOTOCICLETA_CON_SIDECAR.getValorEnum(),TipoVehiculoEnum.MOTOCARRO.getValorEnum(),
						TipoVehiculoEnum.AUTOMOVIL_DE_3_RUEDAS.getValorEnum(),TipoVehiculoEnum.CUATRICICLO.getValorEnum(),
						TipoVehiculoEnum.CICLOMOTOR_DE_2_RUEDAS.getValorEnum(),TipoVehiculoEnum.CICLOMOTOR_DE_3_RUEDAS.getValorEnum(),
						TipoVehiculoEnum.CUADRICICLO_LIGERO.getValorEnum()}));
			} else {
				criteria.add(Restrictions.not(Restrictions.in("vehiculo.tipoVehiculo", new String[]{TipoVehiculoEnum.MOTOCICLETA_DE_2_RUEDAS_SIN_SIDECAR.getValorEnum(),
						TipoVehiculoEnum.MOTOCICLETA_CON_SIDECAR.getValorEnum(),TipoVehiculoEnum.MOTOCARRO.getValorEnum(),
						TipoVehiculoEnum.AUTOMOVIL_DE_3_RUEDAS.getValorEnum(),TipoVehiculoEnum.CUATRICICLO.getValorEnum(),
						TipoVehiculoEnum.CICLOMOTOR_DE_2_RUEDAS.getValorEnum(),TipoVehiculoEnum.CICLOMOTOR_DE_3_RUEDAS.getValorEnum(),
						TipoVehiculoEnum.CUADRICICLO_LIGERO.getValorEnum()})));
			}
		}
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TramiteTrafMatrVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getlistaTramiteSTPorContratoYFecha(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
		}
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.eq("checkJustifFicheroIvtm", "S"));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramiteFinalizadosTelematicamenteSinImpr(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		if (TipoTramiteTrafico.Solicitud_Permiso.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docPermiso"));
		} else if (TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum().equals(tipoTramiteSolicitud)) {
			criteria.add(Restrictions.isNull("docFichaTecnica"));
			criteria.add(Restrictions.isNotNull("vehiculo.nive"));
		}
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesContratoDistintivos(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("estadoImpDstv", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		criteria.add(Restrictions.eq("estadoPetPermDstv", EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum()));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.isNull("docDistintivo"));
		criteria.add(Restrictions.isNotNull("tipoDistintivo"));
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.direccion", "vehiculoDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesContratoDistintivosId(Long idDocPermDistItv) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("estadoImpDstv", EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum()));
		criteria.add(Restrictions.eq("docDistintivo", idDocPermDistItv));
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public int recuperarTipoTramiteMatriculacionSiEsElMismo(BigDecimal[] numExpedientes) {
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("tipoTramiteMatr")));
		List<Criterion> criterionList = new ArrayList<>();
		criterionList.add(Restrictions.in("numExpediente", numExpedientes));
		List<?> listaTipo = buscarPorCriteria(criterionList, null, listaProyecciones);
		int tipo = -1;
		if (listaTipo != null && listaTipo.size() == 1) {
			try {
				tipo = Integer.parseInt(listaTipo.get(0) != null ? (String) listaTipo.get(0) : "0");
			} catch (NumberFormatException e) {
				tipo = -1;
			}
		}
		return tipo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesPorExpediente(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto) {
		if (listaNumsExpedientes != null) {
			Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
			criteria.add(Restrictions.in("numExpediente", listaNumsExpedientes));

			if (tramiteCompleto) {
				criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.persona", "vehiculo.persona", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("ivtmMatriculacionVO", "ivtmMatriculacionVO", CriteriaSpecification.LEFT_JOIN);
				criteria.setFetchMode("tramiteFacturacion", FetchMode.JOIN);
				criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
				criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
				criteria.addOrder(Order.asc("vehiculo.matricula"));
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			}
			return criteria.list();
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public TramiteTrafMatrVO getTramiteTrafMatr(BigDecimal numExpediente, Boolean tramiteCompleto, Boolean permisoLiberacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tramiteCompleto) {
			criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.persona", "vehiculo.persona", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.jefaturaTrafico", "contratoJefatura", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("ivtmMatriculacionVO", "ivtmMatriculacionVO", CriteriaSpecification.LEFT_JOIN);
			criteria.setFetchMode("tramiteFacturacion", FetchMode.JOIN);
			criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
			criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
			if (permisoLiberacion) {
				criteria.createAlias("liberacionEEFF", "liberacionEEFF", CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("listaConsultaEEFF", "listaConsultaEEFF", CriteriaSpecification.LEFT_JOIN);
			}
			criteria.addOrder(Order.asc("vehiculo.matricula"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		@SuppressWarnings("unchecked")
		List<TramiteTrafMatrVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public TramiteTrafMatrVO datosCardMatw(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<TramiteTrafMatrVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos) {

		HashMap<String, List<ConsumoMaterialValue>> consumoMaterialJefatura = new HashMap<String, List<ConsumoMaterialValue>>();

		for (String key : docDistintivos.keySet()) {
			List<Long> distintivosForJefatura = docDistintivos.get(key);

			Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
			criteria.add(Restrictions.in("docDistintivo", distintivosForJefatura));
			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("tipoDistintivo"), "tipoMaterial").add(Projections.rowCount(), "consumo")).setResultTransformer(
					Transformers.aliasToBean(ConsumoMaterialValue.class));

			List<ConsumoMaterialValue> consumos = criteria.list();
			if (!consumos.isEmpty()) {
				consumoMaterialJefatura.put(key, consumos);
			}
		}

		return consumoMaterialJefatura;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getMatriculacionesPorFecha(Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();

		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();

		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);

		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14) };
		criteria.add(Restrictions.in("estado", estados));

		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		// criteria.setProjection(Projections.distinct(Projections.property("numColegiado")));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getMatriculacionPorCifContratoYFecha(String cif, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();

		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();

		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fechaPresentacion", fecMin));
		and.add(Restrictions.lt("fechaPresentacion", fecMax));
		criteria.add(and);
		BigDecimal[] estados = new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) };

		criteria.add(Restrictions.in("estado", estados));
		criteria.add(Restrictions.eq("contrato.cif", cif));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contrato.colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contrato.colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado.usuario", "contrato.colegiado.usuario", CriteriaSpecification.LEFT_JOIN);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getMatriculacionPorIdContratoFecha(Long idContrato, Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date fecMin = calendar.getTime();
		calendar.setTime(fecha);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		Date fecMax = calendar.getTime();
		criteria.add(Restrictions.between("fechaPresentacion", fecMin, fecMax));
		criteria.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(12), new BigDecimal(14), new BigDecimal(35) }));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.persona", "vehiculoPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.direccion", "vehiculoDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getDistintivosPendientesImpresionSinSolicitar(Date fechaPresentacion) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("estadoImpDstv", EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum()));
		criteria.add(Restrictions.isNull("tipoDistintivo"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaPresentacion);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		criteria.add(Restrictions.lt("fechaPresentacion", calendar.getTime()));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficosPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.persona", "vehiculoPersona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo.direccion", "vehiculoDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contratoColegiado.usuario", "contratoColegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("contrato.colegio", "contratoColegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.not(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("vehiculo.matricula", matricula));
		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("docDistintivoVO", "docDistintivoVO", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);
		criteria.addOrder(Order.asc("vehiculo.matricula"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listaMatriculasPorDocDisintintivo(Long docDistintivo) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		criteria.add(Restrictions.eq("docDistintivo", docDistintivo));
		criteria.add(Restrictions.isNotNull("vehiculo.matricula"));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.property("vehiculo.matricula"));
		return criteria.list();
	}

	// Estadisticas

	@Override
	public Integer obtenerTotalesTramitesMATE(boolean esTelematico, String delegacion, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);
		if (esTelematico) {
			ArrayList<BigDecimal> estados = new ArrayList<>();
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

			criteria.add(Restrictions.in("estado", estados));
		} else {
			criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		if (TOTAL_DELEGACIONES.equals(delegacion)) {
			criteria.createAlias("contrato", "contrato");
			criteria.createAlias("contrato.provincia", "prov");
			List<String> listaDelegaciones = new ArrayList<>();
			listaDelegaciones.add(Provincias.Guadalajara.toString());
			listaDelegaciones.add(PROVINCIA_AVILA);
			listaDelegaciones.add(Provincias.Cuenca.toString());
			listaDelegaciones.add(Provincias.Segovia.toString());
			listaDelegaciones.add(Provincias.Ciudad_Real.toString());
			criteria.add(Restrictions.in("prov.idProvincia", listaDelegaciones));
		} else if (delegacion != null && !delegacion.isEmpty() && !delegacion.equals(TOTALES)) {
			criteria.createAlias("contrato", "contrato");
			criteria.createAlias("contrato.provincia", "prov");
			criteria.add(Restrictions.eq("prov.idProvincia", delegacion));
		}

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerTramitesMATETelJefatura(String provincia, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = createCriteriaEstadisticasMate(true, provincia, fechaDesde, fechaHasta);

		criteria.createAlias("contrato.jefaturaTrafico", "jefaturaTrafico");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("jefaturaTrafico.jefaturaProvincial"));
		criteria.setProjection(projectionList);

		return (List<Object[]>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerTramitesMATETelTipoVehiculo(String provincia, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = createCriteriaEstadisticasMate(true, provincia, fechaDesde, fechaHasta);

		criteria.createAlias("vehiculo.tipoVehiculoVO", "tipoVehic");

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("tipoVehic.descripcion"));
		criteria.setProjection(projectionList).addOrder(Order.asc("tipoVehic.descripcion"));

		return (List<Object[]>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerTramitesMATETelCarburante(String provincia, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = createCriteriaEstadisticasMate(true, provincia, fechaDesde, fechaHasta);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount()).hashCode();
		projectionList.add(Projections.groupProperty("vehiculo.idCarburante"));
		criteria.setProjection(projectionList).addOrder(Order.asc("vehiculo.idCarburante"));

		return (List<Object[]>) criteria.list();
	}

	@Override
	public Integer obtenerTramitesMATEFinalizadoPdfVehiculosSiFichaTecnica(String provincia, String jefatura, Date fechaDesde, Date fechaHasta, boolean fichaTecnica) {
		Criteria criteria = createCriteriaEstadisticasMate(false, provincia, fechaDesde, fechaHasta);

		List<String> listaFichasTecnicas = new ArrayList<>();
		listaFichasTecnicas.add("A");
		listaFichasTecnicas.add("C");

		if (fichaTecnica) {
			criteria.add(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas));
		} else {
			criteria.add(Restrictions.not(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas)));
		}

		if (jefatura != null) {
			criteria.createAlias("contrato.jefaturaTrafico", "jefatura");
			criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
		}

		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(String provincia, String jefatura, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = createCriteriaEstadisticasMate(false, provincia, fechaDesde, fechaHasta);

		if (jefatura != null) {
			criteria.createAlias("contrato.jefaturaTrafico", "jefatura");
			criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
		}

		List<String> listaFichasTecnicas = new ArrayList<>();
		listaFichasTecnicas.add("A");
		listaFichasTecnicas.add("C");
		criteria.add(Restrictions.not(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas)));

		ProjectionList projectionList = Projections.projectionList();
		criteria.createAlias("vehiculo.tipoVehiculoVO", "tipoVehic");
		projectionList.add(Projections.rowCount(), "tipoVehic.descripcion");
		projectionList.add(Projections.groupProperty("tipoVehic.descripcion"), "tipoVehic.descripcion");
		criteria.setProjection(projectionList).addOrder(Order.asc("tipoVehic.descripcion"));

		return (List<Object[]>) criteria.list();

	}

	private Criteria createCriteriaEstadisticasMate(boolean esTelematico, String provincia, Date fechaDesde, Date fechaHasta) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafMatrVO.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		criteria.add(Restrictions.eq("prov.idProvincia", provincia));

		if (esTelematico) {
			ArrayList<BigDecimal> estados = new ArrayList<>();
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
			estados.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));

			criteria.add(Restrictions.in("estado", estados));
		} else {
			criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		return criteria;
	}
}