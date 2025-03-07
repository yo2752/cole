package org.gestoresmadrid.core.personas.model.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaDaoImpl extends GenericDaoImplHibernate<PersonaVO> implements PersonaDao {

	private static final long serialVersionUID = -6642601572176363977L;

	// PERSONA
	@Override
	public PersonaVO getPersona(String nif, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(PersonaVO.class);
		if (nif != null && !nif.isEmpty()) {
			criteria.add(Restrictions.eq("id.nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		}

		@SuppressWarnings("unchecked")
		List<PersonaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaVO> getListaNuevasPersonasPorFecha(Date fechaUltimoEnvioData) {
		Criteria criteria = getCurrentSession().createCriteria(PersonaVO.class);
		criteria.add(Restrictions.isNull("codigoMandato"));
		criteria.add(Restrictions.in("evolucionPersona.tipoActuacion", new String[] { "CREACIÓN", "CREACION" }));
		criteria.add(Restrictions.gt("evolucionPersona.id.fechaHora", fechaUltimoEnvioData));
		criteria.add(Restrictions.le("evolucionPersona.id.fechaHora", Calendar.getInstance().getTime()));
		criteria.createAlias("evolucionPersona", "evolucionPersona", CriteriaSpecification.INNER_JOIN);

		return criteria.list();
	}
	
	@Override
	public String obtenerCodigoMandato(String numColegiado) {
		String codigoMandato = null;
		Criteria criteria = getCurrentSession().createCriteria(PersonaVO.class);
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		criteria.setProjection(Projections.max("codigoMandato"));
		
		String maxCodigoMandato = (String) criteria.uniqueResult();
		if (maxCodigoMandato != null && !maxCodigoMandato.isEmpty()) {
			try {
				Long codigo = Long.valueOf(maxCodigoMandato);
				codigo = codigo+1;
				codigoMandato = codigo.toString();
			} catch (Exception e) {
				log.error("Error al obtener el codigo mandato. Codigo mandato en BBDD probablemente no numérico", e);
			}
			
		} else {
			codigoMandato = "1";
		}
		return codigoMandato;
	}
}
