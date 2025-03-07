package org.gestoresmadrid.core.administracion.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.dao.RecargaCacheDao;
import org.gestoresmadrid.core.administracion.model.enumerados.EstadoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.vo.RecargaCacheVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class RecargaCacheDaoImpl extends GenericDaoImplHibernate<RecargaCacheVO> implements RecargaCacheDao{

	private static final long serialVersionUID = 1L;

	public void guardarPeticion(RecargaCacheVO peticion){
		guardar(peticion);
	}
	
	@SuppressWarnings("unchecked")
	public List<RecargaCacheVO> recuperarSolicitudesPendientes(){	
		List<RecargaCacheVO> lista = null;
		Session session = null;
		try {
			session = getCurrentSession();
			Criteria criteria = session.createCriteria(RecargaCacheVO.class);
			criteria.add(Restrictions.isNull("fechaRecarga"));
			criteria.add(Restrictions.isNull("estado"));
			lista = criteria.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al buscar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return lista;
	}

	@Override
	public void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado) {
		peticion.setFechaRecarga(new Date());
		peticion.setEstado(estado);
		
		guardarOActualizar(peticion);
		
	}
	
	public RecargaCacheVO getPeticion(Integer id){
		RecargaCacheVO peticion = null;
		Session session = null;
		try {
			session = getCurrentSession();
			Criteria criteria = session.createCriteria(RecargaCacheVO.class);
			criteria.add(Restrictions.eq("idPeticion", id));
			peticion = (RecargaCacheVO) criteria.uniqueResult();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al recuperar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return peticion;
	}

	
	
}
