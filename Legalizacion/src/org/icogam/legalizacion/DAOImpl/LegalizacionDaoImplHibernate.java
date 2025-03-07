package org.icogam.legalizacion.DAOImpl;

import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.icogam.legalizacion.DAO.LegalizacionDao;
import org.icogam.legalizacion.beans.LegalizacionCita;
import org.icogam.legalizacion.utiles.ConstantesLegalizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.modelo.impl.PaginatedListImpl;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * 
 * @author ext_jmbc
 *
 */
public class LegalizacionDaoImplHibernate extends GenericDaoImplHibernate<LegalizacionCita> implements LegalizacionDao{

	private static final ILoggerOegam log = LoggerOegam.getLogger(LegalizacionDaoImplHibernate.class);
//	private static final int ESTADO_BAJA = 0;
//	private static final String ORDEN_ASC = "asc";
	PaginatedListImpl listaLegalizacionCitaP;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesFecha utilesFecha;

	public LegalizacionDaoImplHibernate(LegalizacionCita t) {
		super(t);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public LegalizacionCita getLegalizacionId(Integer idPeticion) {
		Session sesion = createSession();
		LegalizacionCita leg = null;
		try{
			Criteria criteria = sesion.createCriteria(LegalizacionCita.class);
			criteria.add(Restrictions.eq("idPeticion", idPeticion));
			leg = (LegalizacionCita) criteria.uniqueResult();
		} finally{
			sesion.close();
		}

		return leg;
	}

	@Override
	public List<LegalizacionCita> listadoDiario(LegalizacionCita legBean) {
		Session sesion = createSession();
		List<LegalizacionCita> listaLeg = null;

		try{
			Criteria criteria = sesion.createCriteria(LegalizacionCita.class);
			if(legBean.getNumColegiado()!=null && !legBean.getNumColegiado().equals("")){
				criteria.add(Restrictions.eq("numColegiado", legBean.getNumColegiado()));
			}
			criteria.add(Restrictions.eq("fechaLegalizacion", legBean.getFechaLegalizacion()));
			criteria.add(Restrictions.eq("estado", 1));
			criteria.addOrder(Order.asc("numColegiado"));
			criteria.addOrder(Order.asc("orden"));
			criteria.addOrder(Order.asc("nombre"));

			listaLeg = criteria.list();

		}finally{
			sesion.close();
		}

		return listaLeg;
	}

	@Override
	public List<LegalizacionCita> listadoDiarioColegiado(LegalizacionCita legBean) {
		Session sesion = createSession();
		List<LegalizacionCita> listaLeg=null;

		try{
			Criteria criteria = sesion.createCriteria(LegalizacionCita.class);
			if(legBean.getNumColegiado()!=null && !legBean.getNumColegiado().equals("")){
				criteria.add(Restrictions.eq("numColegiado", legBean.getNumColegiado()));
			}
			criteria.add(Restrictions.eq("fechaLegalizacion", legBean.getFechaLegalizacion()));
			criteria.add(Restrictions.eq("estado", 1));
			criteria.addOrder(Order.asc("orden"));
			criteria.addOrder(Order.asc("nombre"));

			listaLeg = criteria.list();

		}finally{
			sesion.close();
		}
		return listaLeg;
	}

	@Override
	public boolean esPosiblePeticion(String numColegiado) {
		Session sesion = createSession();

		try{
			//Comprueba que no se supera el límite de peticiones por día ni por colegiado.
			Criteria criteria = sesion.createCriteria(LegalizacionCita.class);
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
			criteria.add(Restrictions.eq("fechaLegalizacion", utilesFecha.getFechaActual().getFecha()));
			Integer totalColegiado = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

			if(totalColegiado<numMaximoColegiado()){
				Criteria criteria2 = sesion.createCriteria(LegalizacionCita.class);
				criteria2.add(Restrictions.eq("fechaLegalizacion", utilesFecha.getFechaActual().getFecha() ));
				Integer totalDia = ((Number)criteria2.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				if(totalDia<numMaximoDia()){
					return true;
				}
			}
		} catch(Exception e){
			log.error(e);
		} finally{
			sesion.close();
		}
		return false;
	}

	private Integer numMaximoColegiado(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_GESTOR));
	}

	private Integer numMaximoDia(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_DIA));
	}

}