package hibernate.dao.trafico;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import hibernate.entities.trafico.Vehiculo;

public class VehiculoDAO extends HibernateUtil {
	
	public void actualizar(Vehiculo vehiculo){
		Session session = getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(vehiculo);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}

}
