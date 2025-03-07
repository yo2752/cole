package org.icogam.sanciones.DAOImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.icogam.sanciones.DAO.SancionDao;
import org.icogam.sanciones.beans.Sancion;

import trafico.dao.implHibernate.GenericDaoImplHibernate;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SancionDaoImpl extends GenericDaoImplHibernate<Sancion> implements SancionDao {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SancionDaoImpl.class);

	public SancionDaoImpl() {
		super(new Sancion());
	}
	
	@Override
	public Sancion getSancionId(Integer idSancion, String numColegiado) {
		Session sesion = createSession();
		Sancion sancion = null;
		try{
			Criteria criteria = sesion.createCriteria(Sancion.class);
			criteria.add(Restrictions.eq("idSancion", idSancion));
			if (numColegiado!=null){
				criteria.add(Restrictions.eq("numColegiado", numColegiado));
			}
			sancion = (Sancion) criteria.uniqueResult();
		}
		finally{
			sesion.close();
		}
		return sancion;
	}
	
	
	@Override
	public List<Sancion> listado(Sancion sancion) {
		Session sesion = createSession();
		List<Sancion> listaSan = null;
		
		try{
			Criteria criteria = sesion.createCriteria(Sancion.class);
			if(sancion.getNumColegiado()!=null && !sancion.getNumColegiado().equals("")){
				criteria.add(Restrictions.eq("numColegiado", sancion.getNumColegiado()));
			}
			if(sancion.getMotivo()!=null){
				criteria.add(Restrictions.eq("motivo", sancion.getMotivo()));
			}
			criteria.add(Restrictions.eq("fechaPresentacion", sancion.getFechaPresentacion()));
			criteria.add(Restrictions.eq("estado", 1));
			criteria.addOrder(Order.asc("numColegiado"));
			criteria.addOrder(Order.asc("apellidos"));
			criteria.addOrder(Order.asc("nombre"));
			
			listaSan = criteria.list();
			
		}finally{
			sesion.close();
		}
		
		return listaSan;
	}
	
}
