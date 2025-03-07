package hibernate.dao.trafico;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.general.Contrato;
import hibernate.entities.trafico.ColegiadoAnuntis;
import hibernate.entities.trafico.filters.ColegiadoAnuntisFilter;

public class ColegiadoAnuntisDAO extends HibernateUtil {

	/**
	 * Devuelve el listado de entidades ColegiadoAnuntis que cumplen el filtro
	 * 
	 * @param filter  - Los parametros que vengan informados, se usan en el Criteria
	 * @param initialized  - Conjunto de parametros que queremos cargar para evitar  LazzyExceptions
	 * @return
	 */
	public List<ColegiadoAnuntis> list(ColegiadoAnuntisFilter filter,
			String... initialized) {
		Session session = getSessionFactory().openSession();

		List<ColegiadoAnuntis> listaColegiados = null;
		try{
		
		Criteria criteria = session.createCriteria(ColegiadoAnuntis.class);

		// Recuperamos entidades Lazzy
		for (String param : initialized) {
			if (param.isEmpty()) {
				continue;
			}
			criteria.setFetchMode(param, FetchMode.JOIN);
		}
		Criteria criteriaColegiado = null;
		Criteria criteriaContrato = null;

		if (filter.getIdentificador() != null) {
			criteria.add(Restrictions.eq("identificador", filter.getIdentificador()));
		}

		if (filter.getNumColegiado() != null && !filter.getNumColegiado().isEmpty()) {
			if (criteriaColegiado == null) {
				criteriaColegiado = criteria.createCriteria("colegiado");
			}
			criteriaColegiado.add(Restrictions.eq("numColegiado", filter.getNumColegiado()));
		}

		if (filter.getIdContrato() != null && filter.getIdContrato().longValue() > 0L) {
			if (criteriaContrato == null) {
				criteriaContrato = criteria.createCriteria("contrato");
			}
			criteriaContrato.add(Restrictions.eq("idContrato", filter.getIdContrato()));
		}

		if (filter.getFechaInicio() != null) {
			criteria.add(Restrictions.ge("fechaInicio", filter.getFechaInicio()));
		}

		if (filter.getFechaFin() != null) {
			criteria.add(Restrictions.le("fechaFin", filter.getFechaFin()));
		}

		if (filter.getFecha() != null) {
			criteria.add(Restrictions.and(Restrictions.le("fechaInicio", filter.getFecha()), Restrictions.ge("fechaFin", filter.getFecha())));
		}

		if (filter.getSort() != null && filter.getDir() != null) {
			if (SortOrderEnum.DESCENDING.getName().equals(filter.getDir())) {
				criteria.addOrder(Order.desc(filter.getSort()));
			} else {
				criteria.addOrder(Order.asc(filter.getSort()));
			}
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		
		listaColegiados = criteria.list();

		} finally {
			session.close();
		}
		return listaColegiados;
	}

	/**
	 * Devuelve la entidad ColegiadoAnuntis por identificador
	 * 
	 * @param identificador - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public ColegiadoAnuntis get(Integer identificador, String... initialized) {
		Session session = getSessionFactory().openSession();
		ColegiadoAnuntis colegiado = (ColegiadoAnuntis) session.get(ColegiadoAnuntis.class, identificador);
		if (colegiado != null) {
			for (String param : initialized) {
				/*
				 * Para evitar los lazzys, buscamos los getters de los objetos
				 * indicados para recuperarlo. Si el getter perteneze a una
				 * colleción, realizamos Hibernate.initialize para traernos
				 * todos sin tener que recorrer la lista.
				 */
				initialize(colegiado, param);
			}
		}
		session.close();
		return colegiado;
	}


	/**
	 * Devuelve la entidad Contrato por identificador
	 * 
	 * @param idContrato - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public Contrato getContrato(Long idContrato, String... initialized) {
		Session session = getSessionFactory().openSession();
		Contrato contrato = (Contrato) session.get(Contrato.class, idContrato);
		if (contrato != null) {
			for (String param : initialized) {
				/*
				 * Para evitar los lazzys, buscamos los getters de los objetos
				 * indicados para recuperarlo. Si el getter perteneze a una
				 * colleción, realizamos Hibernate.initialize para traernos
				 * todos sin tener que recorrer la lista.
				 */
				initialize(contrato, param);
			}
		}
		session.close();
		return contrato;
	}

}
