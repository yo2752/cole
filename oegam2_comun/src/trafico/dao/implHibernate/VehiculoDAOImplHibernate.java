package trafico.dao.implHibernate;

import hibernate.entities.trafico.Vehiculo;
import trafico.dao.VehiculoDaoInterfaz;

public class VehiculoDAOImplHibernate extends GenericDaoImplHibernate<Vehiculo> implements VehiculoDaoInterfaz {

	public VehiculoDAOImplHibernate() {
		super(new Vehiculo());
	}

}