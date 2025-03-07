package hibernate.dao.trafico;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import escrituras.beans.ResultBean;
import hibernate.entities.tasas.EvolucionTasa;
import hibernate.entities.tasas.EvolucionTasaPK;
import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.TramiteAnuntis;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.entities.trafico.filters.TramiteAnuntisFilter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramiteAnuntisDAO extends HibernateUtil {

	private static final String ACCION_ASIGNACION = "ASIGNACION";
	private static final ILoggerOegam log = LoggerOegam.getLogger(TramiteAnuntisDAO.class);

	/**
	 * Guarda un nuevo registro de tipo TramiteAnuntis
	 * @param tramite
	 */
	public void create(TramiteAnuntis tramite) {
		Session session = getSessionFactory().openSession();

		try {
			session.beginTransaction();
			tramite.setNumExpediente(formNumExpediente(session, tramite.getNumColegiado()));
			session.save(tramite);
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			log.error("Create error", e);
		} finally {
			session.close();
		}
	}

	/**
	 * Guada o Actualiza un registro de tipo TramiteAnuntis
	 * @param tramite
	 */
	public void saveOrUpdate(TramiteAnuntis tramite) {
		Session session = getSessionFactory().openSession();

		try {
			session.beginTransaction();
			tramite.setFechaUltModif(Calendar.getInstance().getTime());
			session.saveOrUpdate(tramite);
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			log.error("saveOrUpdate error", e);
		} finally {
			session.close();
		}
	}

	/**
	 * Devuelve el listado de entidades TramiteAnuntis según el filtro.
	 * 
	 * @param filtro - Los parametros que vengan informados, se usan en el Criteria
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public List<TramiteAnuntis> list(TramiteAnuntisFilter filtro, String... initialized) {
		Session session = getSessionFactory().openSession();

		List<TramiteAnuntis> listaTramites = null;
		try{
			Criteria criteria = session.createCriteria(TramiteAnuntis.class);
			Criteria criteriaContrato = null;

			// Recuperamos entidades Lazy
			for (String param : initialized) {
				if (param.isEmpty()) {
					continue;
				}
				criteria.setFetchMode(param, FetchMode.JOIN);
			}

			if (filtro.getNumExpediente() != null) {
				criteria.add(Restrictions.eq("numExpediente", filtro.getNumExpediente()));
			}

			if (filtro.getNumColegiado() != null && !filtro.getNumColegiado().isEmpty()) {
				criteria.add(Restrictions.eq("numColegiado", filtro.getNumColegiado()));
			}

			if (filtro.getIdContrato() != null) {
				if (criteriaContrato == null) {
					criteriaContrato = criteria.createCriteria("contrato");
				}
				criteriaContrato.add(Restrictions.eq("idContrato",
						filtro.getIdContrato()));
			}

			if (filtro.getEstado() != null && filtro.getEstado().compareTo(BigDecimal.ZERO) > 0) {
				criteria.add(Restrictions.eq("estado", filtro.getEstado()));
			}

			if (filtro.getNifAnunits() != null && !filtro.getNifAnunits().isEmpty()) {
				criteria.add(Restrictions.eq("nif", filtro.getNifAnunits()));
			}

			if (filtro.getMatricula() != null && !filtro.getMatricula().isEmpty()) {
				criteria.add(Restrictions.eq("matricula", filtro.getMatricula()));
			}

			if (filtro.getEmailAnuntis() != null && !filtro.getEmailAnuntis().isEmpty()) {
				criteria.add(Restrictions.like("email", filtro.getEmailAnuntis(), MatchMode.ANYWHERE).ignoreCase());
			}

			if (filtro.getFechaSolicitudInicio() != null) {
				criteria.add(Restrictions.ge("fechaRequest", filtro.getFechaSolicitudInicio()));
			}

			if (filtro.getFechaSolicitudFin() != null) {
				criteria.add(Restrictions.le("fechaRequest", filtro.getFechaSolicitudFin()));
			}

			if (filtro.getFechaAltaInicio() != null) {
				criteria.add(Restrictions.ge("fechaAlta", filtro.getFechaAltaInicio()));
			}

			if (filtro.getFechaAltaFin() != null) {
				criteria.add(Restrictions.le("fechaAlta", filtro.getFechaAltaFin()));
			}

			if (filtro.getFechaPresentacionInicio() != null) {
				criteria.add(Restrictions.ge("fechaPresentacion", filtro.getFechaPresentacionInicio()));
			}

			if (filtro.getFechaPresentacionFin() != null) {
				criteria.add(Restrictions.le("fechaPresentacion", filtro.getFechaPresentacionFin()));
			}

			if (filtro.getIdRequest() != null) {
				criteria.add(Restrictions.eq("idRequest", filtro.getIdRequest()));
			}

			if (filtro.getSort() != null && filtro.getDir() != null) {
				if (SortOrderEnum.DESCENDING.getName().equals(filtro.getDir())) {
					criteria.addOrder(Order.desc(filtro.getSort()));
				} else {
					criteria.addOrder(Order.asc(filtro.getSort()));
				}
			}

			listaTramites = criteria.list();

		} finally {
			session.close();
		}
		return listaTramites;
	}

	/**
	 * Devuelve la entidad TramiteAnuntis por Identificador
	 * 
	 * @param id - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazyExceptions
	 * @return
	 */
	public TramiteAnuntis get(Long id, String... initialized) {
		Session session = getSessionFactory().openSession();
		TramiteAnuntis tramite = (TramiteAnuntis) session.get(TramiteAnuntis.class, id);
		if (tramite != null) {
			for (String param : initialized) {
				initialize(tramite, param);
			}
		}
		session.close();
		return tramite;
	}

	/**
	 * Monta el número de expediente
	 * 
	 * @param numColegiado
	 * @return
	 */
	private Long formNumExpediente(Session session, String numColegiado) {
		String numExpediente = numColegiado + new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());

		Criteria criteria = session.createCriteria(TramiteTrafico.class);
		criteria.add(Restrictions.between("numExpediente", Long.valueOf(numExpediente + "00000"), Long.valueOf(numExpediente + "99999")));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());

		String cuenta = ((Integer) criteria.list().get(0) + 1) + "";
		if (cuenta.length() > 4) {
			cuenta = cuenta.substring(cuenta.length() - 4);
		}
		numExpediente += "00000".substring(0, 5 - cuenta.length()) + cuenta;

		return Long.valueOf(numExpediente);
	}

	/**
	 * Método que asigna una tasa a un tramite de anuntis, actualizado la fecha de asignación.
	 * 
	 * @param tramite - TramiteAnutis con el código de tasa que se le va a asignar.
	 */
	public ResultBean asignarTasa(TramiteAnuntis tramite, String codigoTasa) {
		Session session = getSessionFactory().openSession();
		try {
			ResultBean rb = new ResultBean();

			session.beginTransaction();
			Tasa tasa = (Tasa) session.get(Tasa.class, codigoTasa);

			if (tasa.getContrato().getIdContrato().longValue() != tramite.getContrato().getIdContrato().longValue()) {
				rb.setError(true);
				rb.setMensaje("La tasa y el tramite no son del mismo contrato");
			} else if (tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null && !tramite.getTasa().getCodigoTasa().isEmpty()) {
				rb.setError(true);
				rb.setMensaje("El tramite ya tiene una tasa asignada");
			} else if (tasa.getNumExpediente() != null) {
				rb.setError(true);
				rb.setMensaje("La tasa ya esta asignada a otro tramite");
			} else {
				// Actualización de la tasa
				tasa.setFechaAsignacion(Calendar.getInstance().getTime());
				tasa.setNumExpediente(tramite.getNumExpediente());

				// Actualización del trámite
				tramite.setTasa(tasa);

				//Evolución de la tasa
				if (tasa.getEvolucionTasas() == null) {
					tasa.setEvolucionTasas(new ArrayList<EvolucionTasa>());
				}
				EvolucionTasa et = new EvolucionTasa();
				et.setId(new EvolucionTasaPK());
				et.getId().setCodigoTasa(codigoTasa);
				et.getId().setFechaHora(Calendar.getInstance().getTime());
				et.setAccion(ACCION_ASIGNACION);
				et.setUsuario(tramite.getUsuario());
				et.setNumExpediente(new BigDecimal(tramite.getNumExpediente()));
				tasa.getEvolucionTasas().add(et);

				session.update(tasa);
				session.flush();
				session.saveOrUpdate(tramite);
				session.flush();
				session.getTransaction().commit();
			}
			return rb;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
	}

}