package hibernate.dao.trafico;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.trafico.TramiteTrafico;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.interfaz.TramiteTraficoDaoIntr;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramiteTraficoDAO extends GenericDaoImplHibernate<TramiteTrafico> implements TramiteTraficoDaoIntr {

	private static final ILoggerOegam log = LoggerOegam.getLogger(TramiteTraficoDAO.class);

	protected static TramiteTrafico tr = new TramiteTrafico();
	public TramiteTraficoDAO() {
		super(tr);
	}

	public TramiteTrafico buscarPorExpediente(BigDecimal numExpediente){
		TramiteTrafico tramite = null; 
		Session session = createSession();
		try{
			Long num = numExpediente.longValue();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(TramiteTrafico.class);
			criteria.createCriteria("vehiculo.persona", "vehiculo.persona", CriteriaSpecification.LEFT_JOIN);
			tramite =(TramiteTrafico) criteria.add(Restrictions.eq("numExpediente", num)).uniqueResult();
			if(tramite!=null){
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite())){
					if(tramite.getTramiteTrafMatr()!=null){
						Hibernate.initialize(tramite.getTramiteTrafMatr());
					}
				} else {
					tramite.setTramiteTrafMatr(null);
				}
				if(tramite.getIntervinienteTraficos()!=null){
					Hibernate.initialize(tramite.getIntervinienteTraficos());
				}
				if(tramite.getVehiculo()!=null){
					Hibernate.initialize(tramite.getVehiculo());
					if(tramite.getVehiculo().getMarcaDgt()!=null){
						Hibernate.initialize(tramite.getVehiculo().getMarcaDgt());
					}
				}
				if(tramite.getTramiteTrafSolInfo()!=null){
					Hibernate.initialize(tramite.getTramiteTrafSolInfo());
				}
				if(tramite.getContrato()!=null){
					Hibernate.initialize(tramite.getContrato());
				}
			}

		}catch(Exception e){
			log.error(e);
		}finally {
			session.close();
		}
		return tramite;
	}

	/**
	 * Devuelve la entidad TramiteAnuntis por Identificador
	 * 
	 * @param id - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public TramiteTrafico get(Long id, Object... initialized) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(TramiteTrafico.class);
			criteria.add(Restrictions.eq("numExpediente", id));
			for (Object param : initialized) {
				if (param instanceof String) {
					criteria.setFetchMode(param.toString(), FetchMode.JOIN);
				}
			}

			TramiteTrafico tramiteTrafico = (TramiteTrafico)criteria.uniqueResult();

			for (Object param : initialized) {
				if (param instanceof Method) {
					try {
						Hibernate.initialize(((Method) param).invoke(tramiteTrafico));
					} catch (HibernateException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
						log.error("Se ha producido un error de Hibernate al obtener entidad TramiteAnuntis por Identificador", e);
					}
				}
			}
			return tramiteTrafico;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean actualizarEstados(BigDecimal[] numExpediente, EstadoTramiteTrafico estadoNuevo) {
		int rowCount=-1;
		Session sesion = HibernateUtil.createSession();
		Transaction t = sesion.beginTransaction();
		try{
			String hql = "update TramiteTrafico set estado = :newEstado where num_expediente in (:nexpediente)";
			Query query = sesion.createQuery(hql);
			query.setParameterList("nexpediente",numExpediente);
			query.setString("newEstado",estadoNuevo.getValorEnum());
			rowCount = query.executeUpdate();
			t.commit();
		} catch (HibernateException e){
			log.error("Ha ocurrido un error al cambiar el estado de los expedientes");
			log.error(e.getMessage());
		} finally{
			sesion.close();
		}
		return rowCount==numExpediente.length;
	}

	@Override
	public List<TramiteTrafico> obtenerTramitesNoEstado(Long[] expedientes, EstadoTramiteTrafico estado) {
		List<Criterion> listaCriterion = new ArrayList<>();
		listaCriterion.add(Restrictions.in("numExpediente", expedientes));
		listaCriterion.add(Restrictions.ne("estado",new BigDecimal(estado.getValorEnum())));
		return buscarPorCriteria(-1, -1, listaCriterion, null, null, null, null, null);
	}

	@Override
	public void actualizarFechaUltimaModificacion(List<Long> numExpedientes) {
		actualizarFecha(numExpedientes,"fechaUltModif");
	}

	@Override
	public void actualizarFechaImpresion(List<Long> numExpedientes) {
		actualizarFecha(numExpedientes, "fechaImpresion");
	}

	public void actualizarFecha(List<Long> numExpedientes, String campo) {
		Session sesion = createSession();
		Transaction t = sesion.beginTransaction();
		try{
			String hql = "update TramiteTrafico t set "+campo+"=sysdate where numExpediente in (:expedientes)";
			Query query = sesion.createQuery(hql);
			query.setParameterList("expedientes", numExpedientes);
			query.executeUpdate();
			t.commit();
		} catch (HibernateException e){
			log.error("Ha ocurrido un error al actualizar la fecha de ultima modificación");
			log.error(e.getMessage());
		} finally{
			sesion.close();
		}
	}
}