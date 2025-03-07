package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoSolInfoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteTraficoSolInfoDaoImpl extends GenericDaoImplHibernate<TramiteTrafSolInfoVO> implements TramiteTraficoSolInfoDao {

	private static final long serialVersionUID = -3134548007850966642L;

	@Override
	public TramiteTrafSolInfoVO getTramiteTrafSolInfo(BigDecimal numExpediente, boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		if (tramiteCompleto) {
			criteria.setFetchMode("usuario", FetchMode.JOIN);
			criteria.setFetchMode("contrato", FetchMode.JOIN);
			criteria.setFetchMode("contrato.colegio", FetchMode.JOIN);
			criteria.setFetchMode("contrato.colegiado", FetchMode.JOIN);
			criteria.setFetchMode("contrato.colegiado.usuario", FetchMode.JOIN);

			Criteria criteriaIn = criteria.createCriteria("intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);
			criteriaIn.createCriteria("persona", CriteriaSpecification.LEFT_JOIN);
			criteriaIn.createCriteria("direccion", CriteriaSpecification.LEFT_JOIN);
			Criteria criteriaSol = criteria.createCriteria("solicitudes", CriteriaSpecification.LEFT_JOIN);
			criteriaSol.setFetchMode("vehiculo", FetchMode.JOIN);
			criteriaSol.setFetchMode("tasa", FetchMode.JOIN);
		}
		return (TramiteTrafSolInfoVO) criteria.uniqueResult();
	}

	@Override
	public TramiteTrafSolInfoVO getTramiteTrafSolInfoNuevo(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.setFetchMode("usuario", FetchMode.JOIN);
		criteria.setFetchMode("contrato", FetchMode.JOIN);
		criteria.setFetchMode("contrato.colegiado", FetchMode.JOIN);
		criteria.setFetchMode("contrato.colegiado.usuario", FetchMode.JOIN);
		if (tramiteCompleto) {
			Criteria criteriaSol = criteria.createCriteria("solicitudes", CriteriaSpecification.LEFT_JOIN);
			criteriaSol.setFetchMode("tasa", FetchMode.JOIN);
		}
		return (TramiteTrafSolInfoVO) criteria.uniqueResult();
	}

	@Override
	public String getUltimoSolicitante(String matricula, String bastidor, String nive) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVO.class);
		Criteria critSolicitud = criteria.createCriteria("solicitudes", CriteriaSpecification.LEFT_JOIN);

		// Vehículo
		SimpleExpression resMatricula = null;
		SimpleExpression resBastidor = null;
		SimpleExpression resNive = null;
		int i = 0;
		if (matricula != null && !matricula.isEmpty()) {
			resMatricula = Restrictions.eq("matricula", matricula.toUpperCase());
			i++;
		}
		if (bastidor != null && !bastidor.isEmpty()) {
			resBastidor =  Restrictions.eq("bastidor", bastidor.toUpperCase());
			i++;
		}
		if (nive != null && !nive.isEmpty()) {
			resNive = Restrictions.eq("nive", nive.toUpperCase());
			i++;
		}

		if (i==3) {
			// los 3
			critSolicitud.createCriteria("vehiculo").add(Restrictions.or(resMatricula, Restrictions.or(resBastidor, resNive)));
		} else if (i==2) {
			// Busco el nulo
			if (resMatricula == null) {
				critSolicitud.createCriteria("vehiculo").add(Restrictions.or(resBastidor, resNive));
			} else if (resBastidor == null) {
				critSolicitud.createCriteria("vehiculo").add(Restrictions.or(resMatricula, resNive));
			} else if (resNive == null) {
				critSolicitud.createCriteria("vehiculo").add(Restrictions.or(resMatricula, resBastidor));
			}
		} else if (i==1) {
			// Busco el no nulo
			if (resMatricula != null) {
				critSolicitud.createCriteria("vehiculo").add(resMatricula);
			} else if (resBastidor != null) {
				critSolicitud.createCriteria("vehiculo").add(resBastidor);
			} else if (resNive != null) {
				critSolicitud.createCriteria("vehiculo").add(resNive);
			}
		} else {
			// Caca
			return null;
		}

		criteria.addOrder(Order.desc("fechaUltModif"));
		criteria.setMaxResults(1);

		return (String) criteria.setProjection(Projections.property("numColegiado")).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteTrafSolInfoVehiculoVO> getSolicitudes(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVehiculoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		return criteria.list();
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO getSolicitudInformacion(BigDecimal numExpediente, long idVehiculo, boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVehiculoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.idVehiculo", idVehiculo));
		Criteria criteriaTT = criteria.createCriteria("tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		if (tramiteCompleto) {
			criteria.setFetchMode("vehiculo", FetchMode.JOIN);
			criteria.setFetchMode("tasa", FetchMode.JOIN);
			criteriaTT.setFetchMode("usuario", FetchMode.JOIN);
		}
		return (TramiteTrafSolInfoVehiculoVO) criteria.uniqueResult();
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoVehiculoPorId(Long idTramiteSolInfo) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVehiculoVO.class);
		criteria.add(Restrictions.eq("idTramiteSolInfo", idTramiteSolInfo));
		criteria.setFetchMode("tasa", FetchMode.JOIN);
		return (TramiteTrafSolInfoVehiculoVO) criteria.uniqueResult();
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoVehiculo(Long idTramiteSolInfo, BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVehiculoVO.class);
		if(idTramiteSolInfo != null){
			criteria.add(Restrictions.eq("idTramiteSolInfo", idTramiteSolInfo));
		}
		if(numExpediente != null){
			criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		}
		criteria.setFetchMode("tasa", FetchMode.JOIN);
		criteria.setFetchMode("tramiteTrafico", FetchMode.JOIN);
		return (TramiteTrafSolInfoVehiculoVO) criteria.uniqueResult();
	}

	@Override
	public boolean borrarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVO) {
		Session sesion = getCurrentSession();
		boolean resultado = false;
		try {
			sesion.delete(tramiteTrafSolInfoVO);
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al borrar un objeto de tipo TramiteTrafSolInfoVehiculoVO");
			throw e;
		}
		return resultado;
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO actualizarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO trafSolInfoVO) {
		Session sesion = getCurrentSession();
		boolean resultado = false;
		try {
			sesion.update(trafSolInfoVO);
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una actualización del objeto de tipo TramiteTrafSolInfoVehiculoVO");
			log.error(e.getMessage());
			throw e;
		}
		if (resultado) {
			return trafSolInfoVO;
		}
		return null;
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO guardarOActualizarTramSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO){
		Session sesion = getCurrentSession();
		Boolean resultado = Boolean.FALSE;
		Long idTramite = null;
		try {
			if(tramiteTrafSolInfoVehiculoVO.getIdTramiteSolInfo() != null){
				sesion.update(tramiteTrafSolInfoVehiculoVO);
				resultado = Boolean.TRUE;
			} else {
				sesion = getCurrentSession();
				idTramite = (Long) sesion.save(tramiteTrafSolInfoVehiculoVO);
				if(idTramite != null){
					tramiteTrafSolInfoVehiculoVO.setIdTramiteSolInfo(idTramite);
					resultado = Boolean.TRUE;
				}
			}
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una actualización o guardado del objeto de tipo TramiteTrafSolInfoVehiculoVO");
			log.error(e.getMessage());
			throw e;
		}
		if (resultado) {
			return tramiteTrafSolInfoVehiculoVO;
		}
		return null;
	}

	@Override
	public Serializable guardarSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO) {
		Session sesion = null;
		Serializable resultado = null;
		try {
			sesion = getCurrentSession();
			resultado = sesion.save(tramiteTrafSolInfoVehiculoVO);
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al guardar un objeto de tipo TramiteTrafSolInfoVehiculoVO");
			throw e;
		}
		return resultado;
	}

	@Override
	public TramiteTrafSolInfoVehiculoVO getTramiteTrafSolInfoPorCodTasa(String codigoTasa, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafSolInfoVehiculoVO.class);
		criteria.createAlias("tasa", "tasa",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("tasa.codigoTasa", codigoTasa));
		Criteria criteriaTT = criteria.createCriteria("tramiteTrafico", CriteriaSpecification.LEFT_JOIN);
		if (tramiteCompleto) {
			criteria.setFetchMode("vehiculo", FetchMode.JOIN);
			criteriaTT.setFetchMode("usuario", FetchMode.JOIN);
		}
		return (TramiteTrafSolInfoVehiculoVO) criteria.uniqueResult();
	}

}