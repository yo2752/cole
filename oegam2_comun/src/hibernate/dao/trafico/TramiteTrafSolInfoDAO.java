package hibernate.dao.trafico;

import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.trafico.TramiteTrafSolInfo;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramiteTrafSolInfoDAO extends HibernateUtil {
	private static final ILoggerOegam log = LoggerOegam.getLogger("ProcesoAtem");

	public void ejecutarTransacciones (Transaction tx) throws Exception {
		try {
			tx.commit();
		} catch(RuntimeException re){
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
		} 
	}

	public void actualiza(TramiteTrafSolInfo tramiteTrafSolInfo){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			String hql = "update TramiteTrafSolInfo set estado = :estado, resultado = :mensaje, referenciaAtem = :referencia where tramiteTrafico.numExpediente = :numExpediente and numColegiado = :numColegiado and idVehiculo = :idVehiculo";
			Query query = session.createQuery(hql);
			query.setParameter("estado", tramiteTrafSolInfo.getEstado());
			query.setParameter("mensaje", tramiteTrafSolInfo.getResultado());
			query.setParameter("referencia", tramiteTrafSolInfo.getReferenciaAtem());
			query.setParameter("numExpediente", tramiteTrafSolInfo.getTramiteTrafico().getNumExpediente());
			query.setParameter("numColegiado", tramiteTrafSolInfo.getVehiculo().getId().getNumColegiado());
			query.setParameter("idVehiculo", tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());

			query.executeUpdate();

			session.flush();
			log.debug("Actualizado correctamente el estado para el vehiculo: " + tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());
			try {
				ejecutarTransacciones(tx);
			} catch (Exception e) {
				log.error("Se ha producido un error al actualizar un tramite trafico sol info. ", e);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void actualizaMasivo(List<TramiteTrafSolInfo> listaTramiteTrafSolInfo){
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			for(TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo){
				String hql = "update TramiteTrafSolInfo set referenciaAtem = :referencia, estado = :estado, resultado = :mensaje   where tramiteTrafico.numExpediente = :numExpediente and numColegiado = :numColegiado and idVehiculo = :idVehiculo";
				Query query = session.createQuery(hql);

				query.setParameter("referencia", tramiteTrafSolInfo.getReferenciaAtem());
				query.setParameter("estado", tramiteTrafSolInfo.getEstado());
				query.setParameter("mensaje", tramiteTrafSolInfo.getResultado());
				query.setParameter("numExpediente", tramiteTrafSolInfo.getTramiteTrafico().getNumExpediente());
				query.setParameter("numColegiado", tramiteTrafSolInfo.getVehiculo().getId().getNumColegiado());
				query.setParameter("idVehiculo", tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());

				query.executeUpdate();

				session.flush();
				log.debug("Actualizado correctamente el estado de la solicitud: " + tramiteTrafSolInfo.getTramiteTrafico().getNumExpediente() + " - " + tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());

			}
			try {
				ejecutarTransacciones(tx);
			} catch (Exception e) {
				log.error("Se ha producido un error al actualizar masivamente tramites trafico sol info. ", e);
			}
		} catch (Exception e) {
			if (tx != null) {
				try { tx.rollback(); } catch (Throwable t){}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<TramiteTrafSolInfo> getList(Long numExpediente) throws Exception{
		Session session = null;
		session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(TramiteTrafSolInfo.class, "tramiteTrafSolInfo");
		criteria.createAlias("tramiteTrafico", "tramiteTrafico");
		criteria.createAlias("vehiculo", "vehiculo");
		criteria.createAlias("tasa", "tasa");
		criteria.setFetchMode("tramiteTrafico", FetchMode.JOIN);
		criteria.setFetchMode("vehiculo", FetchMode.JOIN);
		criteria.setFetchMode("tasa", FetchMode.JOIN);
		criteria.add(Restrictions.eq("tramiteTrafico.numExpediente", numExpediente));
		@SuppressWarnings("unchecked")
		List<TramiteTrafSolInfo> listaTramiteTrafSolInfo = criteria.list();
		session.flush();
		session.close();
		return listaTramiteTrafSolInfo;
	}

}
