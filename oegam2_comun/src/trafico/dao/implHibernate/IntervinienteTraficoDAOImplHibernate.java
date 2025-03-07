package trafico.dao.implHibernate;

import hibernate.entities.trafico.IntervinienteTrafico;
import trafico.dao.IntervinienteTraficoDaoInterfaz;

public class IntervinienteTraficoDAOImplHibernate extends GenericDaoImplHibernate<IntervinienteTrafico> implements IntervinienteTraficoDaoInterfaz {

	public IntervinienteTraficoDAOImplHibernate() {
		super(new IntervinienteTrafico());
	}

}