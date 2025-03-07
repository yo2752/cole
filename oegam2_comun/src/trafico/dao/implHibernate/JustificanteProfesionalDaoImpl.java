package trafico.dao.implHibernate;

import trafico.dao.interfaz.JustificanteProfesionalDaoInt;
import hibernate.entities.trafico.JustificanteProf;

public class JustificanteProfesionalDaoImpl extends GenericDaoImplHibernate<JustificanteProf> implements JustificanteProfesionalDaoInt {

	public JustificanteProfesionalDaoImpl() {
		super(new JustificanteProf());
	}

}
