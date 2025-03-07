package general.accionTramite.dao;

import hibernate.entities.general.AccionTramite;
import trafico.dao.implHibernate.GenericDaoImplHibernate;

public class AccionTramiteDAO extends GenericDaoImplHibernate<AccionTramite>{
	
	public AccionTramiteDAO() {
		super(new AccionTramite());
	}

}
