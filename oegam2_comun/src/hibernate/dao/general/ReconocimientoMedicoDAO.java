package hibernate.dao.general;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.general.ReconocimientoMedico;
import hibernate.entities.general.TipoTramiteRenovacion;
import hibernate.entities.trafico.filters.ReconocimientoMedicoFilter;

public class ReconocimientoMedicoDAO extends HibernateUtil {

	/**
	 * Devuelve el listado de entidades ReconocimientoMedico que cumplen el filtro
	 * 
	 * @param filter - Los parámetros que vengan informados, se usan en el Criteria
	 * @param initialized - Conjunto de parámetros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public List<ReconocimientoMedico> list(ReconocimientoMedicoFilter filter, String... initialized) {
		Session session = getSessionFactory().openSession();

		List<ReconocimientoMedico> lista = null;
		try {
			Criteria criteria = session.createCriteria(ReconocimientoMedico.class);
			Criteria personaCriteria = null;
			// Recuperamos entidades Lazy
			for (String param : initialized) {
				if (param.isEmpty()) {
					continue;
				}
				criteria.setFetchMode(param, FetchMode.JOIN);
			}

			if (filter.getIdReconocimiento() != null && filter.getIdReconocimiento() > 0) {
				criteria.add(Restrictions.eq("idReconocimiento", filter.getIdReconocimiento()));
			}

			if (filter.getIdClinica() != null && filter.getIdClinica() > 0) {
				criteria.createCriteria("clinica").add(Restrictions.eq("idContrato", filter.getIdClinica()));
			}

			if (filter.getIdContrato() != null && filter.getIdContrato() > 0) {
				criteria.createCriteria("contrato").add(Restrictions.eq("idContrato", filter.getIdContrato()));
			}

			if (filter.getNumColegiado() != null && !filter.getNumColegiado().trim().isEmpty()) {
				criteria.createCriteria("colegiado").add(Restrictions.eq("numColegiado", filter.getNumColegiado()));
			}

			if (filter.getNif() != null && !filter.getNif().trim().isEmpty()) {
				if (personaCriteria == null) {
					personaCriteria = criteria.createCriteria("persona");
				}
				personaCriteria.add(Restrictions.eq("id.nif", filter.getNif()));
			}

			if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
				if (personaCriteria == null) {
					personaCriteria = criteria.createCriteria("persona");
				}
				personaCriteria.add(Restrictions.eq("nombre", filter.getNombre()));
			}

			if (filter.getApellido1RazonSocial() != null && !filter.getApellido1RazonSocial().trim().isEmpty()) {
				if (personaCriteria == null) {
					personaCriteria = criteria.createCriteria("persona");
				}
				personaCriteria.add(Restrictions.eq("apellido1RazonSocial", filter.getApellido1RazonSocial()));
			}
			if (filter.getApellido2() != null && !filter.getApellido2().trim().isEmpty()) {
				if (personaCriteria == null) {
					personaCriteria = criteria.createCriteria("persona");
				}
				personaCriteria.add(Restrictions.eq("apellido2", filter.getApellido2()));
			}

			if (personaCriteria != null && filter.getNumColegiado() != null
					&& !filter.getNumColegiado().trim().isEmpty()) {
				personaCriteria.add(Restrictions.eq("id.numColegiado", filter.getNumColegiado()));
			}

			if (filter.getFechaAltaIni() != null) {
				criteria.add(Restrictions.ge("fechaAlta", filter.getFechaAltaIni()));
			}

			if (filter.getFechaAltaFin() != null) {
				criteria.add(Restrictions.le("fechaAlta", filter.getFechaAltaFin()));
			}

			if (filter.getFechaReconocimientoIni() != null) {
				criteria.add(Restrictions.ge("fechaReconocimiento", filter.getFechaReconocimientoIni()));
			}

			if (filter.getFechaReconocimientoFin() != null) {
				criteria.add(Restrictions.le("fechaReconocimiento", filter.getFechaReconocimientoFin()));
			}

			if (filter.getEstado() != null && filter.getEstado().signum() == 1) {
				criteria.add(Restrictions.eq("estado", filter.getEstado()));
			}

			if (filter.getSort() != null && filter.getDir() != null) {
				criteria.addOrder(SortOrderEnum.DESCENDING.getName().equals(filter.getDir()) ? Order.desc(filter.getSort()) : Order.asc(filter.getSort()));
			}

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			lista = criteria.list();

		} finally {
			session.close();
		}
		return lista;
	}
	/**
	 * Devuelve la entidad ReconocimientoMedico por identificador
	 * 
	 * @param identificador - identificador
	 * @param initialized - Conjunto de parámetros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public ReconocimientoMedico get(Long identificador, String... initialized) {
		Session session = null;
		ReconocimientoMedico reconocimientoMedico = null;
		try {
			session = getSessionFactory().openSession();
			reconocimientoMedico = (ReconocimientoMedico) session.get(ReconocimientoMedico.class, identificador);
			if (reconocimientoMedico != null) {
				for (String param : initialized) {
					/*
					 * Para evitar los lazzys, buscamos los getters de los objetos
					 * indicados para recuperarlo. Si el getter pertenece a una
					 * colección, realizamos Hibernate.initialize para traernos
					 * todos sin tener que recorrer la lista.
					 */
					initialize(reconocimientoMedico, param);
				}
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Throwable t){}
			}
		}
		return reconocimientoMedico;
	}

	/**
	 * Guarda en BBDD el objeto ReconocimientoMedico
	 * 
	 * @param ReconocimientoMedico
	 */
	public void saveOrUpdate(ReconocimientoMedico reconocimientoMedico) {
		Session session = getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(reconocimientoMedico);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Elimina de BBDD el objeto ReconocimientoMedico
	 * 
	 * @param reconocimiento
	 */
	public void remove(ReconocimientoMedico reconocimiento) {
		Session session = getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.delete(reconocimiento);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * Devuelve el listado de entidades TipoTramiteRenovacion
	 * 
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public List<TipoTramiteRenovacion> listTipoTramiteRenovacion(String... initialized) {
		Session session = getSessionFactory().openSession();

		List<TipoTramiteRenovacion> lista = null;

		try {
			Criteria criteria = session.createCriteria(TipoTramiteRenovacion.class);
			// Recuperamos entidades Lazzy
			for (String param : initialized) {
				if (param.isEmpty()) {
					continue;
				}
				criteria.setFetchMode(param, FetchMode.JOIN);
			}

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			lista = criteria.list();

		} finally {
			session.close();
		}
		return lista;
	}

	/**
	 * Devuelve la entidad TipoTramiteRenovacion por identificador
	 * 
	 * @param identificador - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public TipoTramiteRenovacion getTipoTramiteRenovacion(String identificador, String... initialized) {
		Session session = null;
		TipoTramiteRenovacion tipoTramiteRenovacion = null;
		try {
			session = getSessionFactory().openSession();
			tipoTramiteRenovacion = (TipoTramiteRenovacion) session.get(TipoTramiteRenovacion.class, identificador);
			if (tipoTramiteRenovacion != null) {
				for (String param : initialized) {
					/*
					 * Para evitar los lazzys, buscamos los getters de los objetos
					 * indicados para recuperarlo. Si el getter pertenece a una
					 * colección, realizamos Hibernate.initialize para traernos
					 * todos sin tener que recorrer la lista.
					 */
					initialize(tipoTramiteRenovacion, param);
				}
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Throwable t) {}
			}
		}
		return tipoTramiteRenovacion;
	}

}