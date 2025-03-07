package trafico.modelo.impl;

import hibernate.entities.general.Colegio;
import trafico.dao.GenericDao;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.modelo.interfaz.ModeloColegiadoInt;

public class ModeloColegiadoImpl implements ModeloColegiadoInt {

	@Override
	public Colegio obtenerColegioContrato(String nColegio) {
		GenericDao<Colegio> genericDao = new GenericDaoImplHibernate<Colegio>(new Colegio());
		Colegio colegio = new Colegio();
		colegio.setColegio(nColegio);
		colegio = genericDao.buscar(colegio).get(0);
		return colegio;
	}

}