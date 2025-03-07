package org.gestoresmadrid.core.contrato.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoDaoImpl extends GenericDaoImplHibernate<ContratoVO> implements ContratoDao {

	private static final long serialVersionUID = 464054256343556206L;

	private static final String TOKEN_SEPARADOR = " - ";

	@Override
	public ContratoVO getContratoPorCifProcesos(String cifContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("cif", cifContrato));
		criteria.add(Restrictions.eq("estadoContrato", BigDecimal.ONE));
		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		return (ContratoVO) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoVO> getListaContratosPorId(List<Long> listaIdsContratos) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.in("idContrato", listaIdsContratos));
		criteria.add(Restrictions.eq("estadoContrato", BigDecimal.ONE));
		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites", "correosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite", "correosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite.aplicacion", "correosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public ContratoVO getContrato(Long idContrato, boolean contratoCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}

		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiado.usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("municipio", "municipio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites", "correosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite", "correosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite.aplicacion", "correosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		if (contratoCompleto) {
			criteria.setFetchMode("aplicaciones", FetchMode.JOIN);
		}
		return (ContratoVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoVO> getContratosPorColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("colegiado.numColegiado", numColegiado));
		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("municipio", "municipio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites", "correosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite", "correosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite.aplicacion", "correosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.desc("fechaInicio"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoVO> getListaContratosColegiado(String numColegiado, BigDecimal estado) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("colegiado.numColegiado", numColegiado));
		if (null != estado) {
			criteria.add(Restrictions.eq("estadoContrato", estado));
		}
		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegio", "colegio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("municipio", "municipio", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites", "correosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite", "correosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite.aplicacion", "correosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.desc("fechaInicio"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoVO> getContratosPorAplicacion(String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("aplicaciones.codigoAplicacion", codigoAplicacion));
		criteria.createAlias("aplicaciones", "aplicaciones", CriteriaSpecification.LEFT_JOIN);
		return criteria.list();
	}
	
	@Override
	public List<AplicacionVO> getAplicacionesPorContrato(BigDecimal idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato.longValue()));
		}
		criteria.setFetchMode("aplicaciones", FetchMode.JOIN);
		
		ContratoVO contratoVO = (ContratoVO) criteria.uniqueResult();
		return contratoVO.getAplicacionesAsList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("estadoContrato", BigDecimal.ONE));
		criteria.createAlias("colegiado", "colegiado");
		criteria.createAlias("provincia", "provincia");

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("idContrato"));
		projections.add(Projections.property("colegiado.numColegiado"));
		projections.add(Projections.property("provincia.nombre"));
		projections.add(Projections.property("via"));
		criteria.setProjection(projections);

		criteria.setResultTransformer(new ResultTransformer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2961364363576267313L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				StringBuilder sb = new StringBuilder(tuple[1].toString());
				if (tuple[2] != null) {
					sb.append(TOKEN_SEPARADOR).append(tuple[2].toString());
				}
				if (tuple[3] != null) {
					sb.append(TOKEN_SEPARADOR).append(tuple[3]);
				}
				DatoMaestroBean dato = new DatoMaestroBean(tuple[0].toString(), sb.toString());
				return dato;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});

		criteria.addOrder(Order.asc("colegiado.numColegiado"));
		criteria.addOrder(Order.asc("provincia.nombre"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato) {
		List<DatoMaestroBean> resultado = null;
		ContratoVO contrato = null;
		if (idContrato != null) {
			contrato = (ContratoVO) getCurrentSession().get(ContratoVO.class, idContrato.longValue());
		}

		if (contrato != null) {
			Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
			criteria.add(Restrictions.eq("colegio", contrato.getColegio()));
			criteria.add(Restrictions.eq("estadoContrato", BigDecimal.ONE));
			criteria.createAlias("colegiado", "colegiado");
			criteria.createAlias("provincia", "provincia");

			ProjectionList projections = Projections.projectionList();
			projections.add(Projections.property("idContrato"));
			projections.add(Projections.property("colegiado.numColegiado"));
			projections.add(Projections.property("provincia.idProvincia"));
			criteria.setProjection(projections);

			criteria.setResultTransformer(new ResultTransformer() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2961364363576267313L;

				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					StringBuilder sb = new StringBuilder(tuple[1].toString());
					if (tuple[2] != null) {
						sb.append(TOKEN_SEPARADOR).append(tuple[2].toString());
					}
					DatoMaestroBean dato = new DatoMaestroBean(tuple[0].toString(), sb.toString());
					return dato;
				}

				@SuppressWarnings("rawtypes")
				@Override
				public List transformList(List collection) {
					return collection;
				}
			});

			criteria.addOrder(Order.asc("colegiado.numColegiado"));
			resultado = criteria.list();
		} else {
			resultado = Collections.emptyList();
		}
		return resultado;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DatoMaestroBean> getComboContratosHabilitadosColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.createCriteria("colegiado").add(Restrictions.eq("numColegiado", numColegiado));
		}
		criteria.add(Restrictions.eq("estadoContrato", BigDecimal.ONE));
		criteria.createAlias("provincia", "provincia");

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("idContrato"));
		projections.add(Projections.property("colegiado.numColegiado"));
		projections.add(Projections.property("provincia.nombre"));
		projections.add(Projections.property("via"));
		criteria.setProjection(projections);

		criteria.setResultTransformer(new ResultTransformer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2961364363576267313L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				StringBuilder sb = new StringBuilder(tuple[1].toString());
				if (tuple[2] != null) {
					sb.append(TOKEN_SEPARADOR).append(tuple[2].toString());
				}
				if (tuple[3] != null) {
					sb.append(TOKEN_SEPARADOR).append(tuple[3]);
				}
				DatoMaestroBean dato = new DatoMaestroBean(tuple[0].toString(), sb.toString());
				return dato;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});

		criteria.addOrder(Order.asc("colegiado.numColegiado"));
		return criteria.list();
	}

	@Override
	public ContratoVO obtenerContratoPorId(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("provincia", "provincia", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias("correosTramites", "correosTramites", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite", "correosTramitesTipoTramite", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("correosTramites.tipoTramite.aplicacion", "correosTramitesTipoTramite.aplicacion", CriteriaSpecification.LEFT_JOIN);
		
		return (ContratoVO) criteria.uniqueResult();
	}

	@Override
	public String getJefaturaContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.setProjection(Projections.property("jefaturaTrafico.jefaturaProvincial"));

		
		return (String) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoVO> getListGrupo(String idGrupo) {

		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);

		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario.grupo", "colegiadoUsuarioGrupo", CriteriaSpecification.LEFT_JOIN);
		
		if (StringUtils.isNotBlank(idGrupo)) {
			criteria.add(Restrictions.eq("colegiadoUsuarioGrupo.idGrupo", idGrupo));
		}

		return criteria.list();
	}

	@Override
	public Boolean isContratoDeGrupo(Long idContrato, String idGrupo) {

		Criteria criteria = getCurrentSession().createCriteria(ContratoVO.class);

		criteria.createAlias("colegiado", "colegiado", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario", "colegiadoUsuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("colegiado.usuario.grupo", "colegiadoUsuarioGrupo", CriteriaSpecification.LEFT_JOIN);

		if (idContrato != null) {
			criteria.add(Restrictions.eq("idContrato", idContrato));
		}
		if (StringUtils.isNotBlank(idGrupo)) {
			criteria.add(Restrictions.eq("colegiadoUsuarioGrupo.idGrupo", idGrupo));
		}

		ContratoVO contrato = (ContratoVO) criteria.uniqueResult();

		return (contrato != null);
	}

}
