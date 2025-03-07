package hibernate.dao.trafico;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.trafico.TramiteTrafMatr;
import hibernate.entities.trafico.TramiteTrafico;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.interfaz.TramiteTrafMatrDaoIntr;

public class TramiteTrafMatrDAO extends GenericDaoImplHibernate<TramiteTrafMatr> implements TramiteTrafMatrDaoIntr {

	protected static TramiteTrafMatr tr = new TramiteTrafMatr();

	public TramiteTrafMatrDAO() {
		super(tr);
	}

	/**
	 * Devuelve la entidad TramiteTrafMatr por Identificador
	 * 
	 * @param id - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	
	public TramiteTrafico getTramiteTrafMatr(Long id) {
		Session session = getSessionFactory().openSession();

		TramiteTrafico tramite = null;

		try{
			Criteria criteria = session.createCriteria(TramiteTrafico.class);

			criteria.createAlias("tramiteTrafMatr", "tramiteTrafMatr");
			criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("vehiculo.marcaDgt", "marcaDgt", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN);

			criteria.add(Restrictions.eq("numExpediente", id));

			tramite = (TramiteTrafico)criteria.uniqueResult();

			if(tramite!=null){
				Hibernate.initialize(tramite.getTramiteTrafMatr());
				Hibernate.initialize(tramite.getIntervinienteTraficos());
				Hibernate.initialize(tramite.getVehiculo());
				Hibernate.initialize(tramite.getVehiculo().getMarcaDgt());
			}

		} finally {
			session.close();
		}
		return tramite;

	}

}