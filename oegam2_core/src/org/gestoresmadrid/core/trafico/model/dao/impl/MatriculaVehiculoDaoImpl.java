package org.gestoresmadrid.core.trafico.model.dao.impl;

import hibernate.entities.trafico.TramiteTrafico;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.dao.MatriculaVehiculoDaoInt;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MatriculaVehiculoDaoImpl extends GenericDaoImplHibernate<TramiteTrafico> implements MatriculaVehiculoDaoInt {

	private static final long serialVersionUID = 1246588296919011130L;

	// Mantis 14386. David Sierra: Se recupera la matricula asociada al expediente:
	// select tt.num_expediente, tt.id_vehiculo, ve.matricula from tramite_trafico tt 
	// inner join vehiculo ve on tt.id_vehiculo=ve.id_vehiculo where tt.num_expediente=?;
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Object[]> getMatriculaExpedienteDao(Long numExpediente) {
		Session session = getCurrentSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class, "tramiteTrafico");
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		criteria.setFetchMode("tramiteTrafico.vehiculo", FetchMode.JOIN);
		criteria.createAlias("tramiteTrafico.vehiculo", "vehiculo"); 

		ProjectionList columns = Projections.projectionList()
				.add(Projections.property("numExpediente"))
				.add(Projections.property("vehiculo.matricula"))
				.add(Projections.property("vehiculo.bastidor"));
		criteria.setProjection(columns);

		return criteria.list();
	}

}