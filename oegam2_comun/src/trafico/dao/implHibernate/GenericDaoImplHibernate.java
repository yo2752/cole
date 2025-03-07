package trafico.dao.implHibernate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import trafico.dao.GenericDao;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GenericDaoImplHibernate<T> extends HibernateUtil implements GenericDao<T> {

	private static final ILoggerOegam log = LoggerOegam.getLogger(GenericDaoImplHibernate.class);
	public static final String ordenAsc = "asc";
	public static final String ordenDes = "desc";
	protected T t;

	public GenericDaoImplHibernate(T t) {
		this.t = t;
	}

	@Override
	public T buscarPorExpediente(BigDecimal numExpediete) {
		Criterion c = Restrictions.eq("numExpediente", numExpediete);
		List<Criterion> criterion = new ArrayList<>();
		criterion.add(c);
		List<T> l = buscarPorCriteria(-1, -1, criterion, null, null);
		if (l == null || l.isEmpty()) {
			return null;
		}
		return l.get(0);
	}

	@Override
	public T buscarPorExpediente(Long numExpediente) {
		Criterion c = Restrictions.eq("numExpediente", numExpediente);
		List<Criterion> criterion = new ArrayList<>();
		criterion.add(c);
		List<T> l = buscarPorCriteria(-1, -1, criterion, null, null);
		if (l == null || l.isEmpty()) {
			return null;
		}
		return l.get(0);
	}

	@Override
	public Object guardar(T objeto) {
		Session sesion = createSession();
		sesion.beginTransaction();
		Object resultado = null;
		try {
			resultado = sesion.save(objeto);
			sesion.getTransaction().commit();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al guardar un objeto de tipo " + objeto.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		return resultado;
	}

	@Override
	public Object guardarOActualizar(T objeto) {
		Session sesion = createSession();
		sesion.beginTransaction();
		Object resultado = null;
		try {
			sesion.saveOrUpdate(objeto);
			sesion.getTransaction().commit();
			resultado=objeto;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al guardar un objeto de tipo " + objeto.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscar(T objeto) {
		List<T> lista = null;
		Session session = createSession();
		session.beginTransaction();
		try {
			Criteria criteria = crearFiltro(objeto, session.createCriteria(objeto.getClass()));
			lista = criteria.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al buscar un objeto de tipo " +objeto.getClass().getName(), e);
		} finally {
			session.close();
		}
		return lista;
	}

	@Override
	public boolean borrar(T objeto) {
		Session sesion = createSession();
		sesion.beginTransaction();
		boolean resultado = false;
		try {
			sesion.delete(objeto);
			sesion.getTransaction().commit();
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al borrar un objeto de tipo " +objeto.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		return resultado;
	}

	@Override
	public T actualizar(T objeto) {
		Session sesion = createSession();
		sesion.beginTransaction();
		boolean resultado = false;
		try{
			sesion.update(objeto);
			sesion.getTransaction().commit();
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una actualización del objeto de tipo "+objeto.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		if (resultado) {
			return objeto;
		}
		return null;
	}

	private Criteria crearFiltro(T objeto, Criteria criteria) {
		try {
			for (Method method : objeto.getClass().getMethods()) {
				if ((method.getName().startsWith("get") || method.getName().startsWith("is"))
						&& method.getParameterTypes().length == 0) {
					Object _return;
					_return = method.invoke(objeto);

					if (_return != null) {
						String name = method.getName().startsWith("get") ? method.getName().substring(3)
								: method.getName().substring(2);
						name = name.substring(0, 1).toLowerCase() + name.substring(1);
						if (!name.equals("class")) {
							criteria.add(Restrictions.eq(name, _return));
						}
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return criteria;
	}

	@Override
	public int numeroElementos(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin) {
		Session sesion = createSession();
		sesion.beginTransaction();
		Criteria crit = sesion.createCriteria(t.getClass(), "this_");
		int result = -1;
		try {
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirCriterion(crit, criterion);
			result = ((Integer)(crit.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener el número de elementos por criterios del objeto de tipo "+t.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		return result;
	}

	@Override
	public List<T> buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		return buscarPorCriteria(-1, -1, criterion, null, null, entitiesJoin, listaProyecciones, null);
	}

	@Override
	public List<T> buscarPorCriteria(List<Criterion> criterion, String dir, String campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		return buscarPorCriteria(-1, -1, criterion, dir, campoOrden, entitiesJoin, listaProyecciones, null);
	}

	/**
	 * Búsqueda de todos los registros utilizando criteria y paginación
	 */
	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, String campoOrden) {
		return buscarPorCriteria(firstResult, maxResults, criterion, dir, campoOrden, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir,
			String campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			ResultTransformer transformadorResultados) {
		List<T> result = null;
		Session sesion = createSession();

		try {
			Criteria crit = sesion.createCriteria(t.getClass());
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirCriterion(crit, criterion);
			if (firstResult > 0) {
				crit.setFirstResult(firstResult);
			}
			if (maxResults > 0) {
				crit.setMaxResults(maxResults);
			}
			//Establecer el orden y el campo
			if (ordenAsc.equals(dir) && campoOrden != null) {
				crit.addOrder(Order.asc(campoOrden));
			}else if(ordenDes.equals(dir) && campoOrden != null) {
				crit.addOrder(Order.desc(campoOrden));
			}
			anadirListaProyecciones(crit, listaProyecciones);
			anadirTransformador(crit, transformadorResultados);
			result = crit.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con paginación del objeto de tipo "+t.getClass().getName(), e);
		} finally {
			sesion.close();
		}
		return result;
	}

	private void anadirEntitiesJoin(Criteria crit, List<AliasQueryBean> entitiesJoin) {
		if (entitiesJoin != null && crit != null) {
			for (AliasQueryBean aliasQueryBean : entitiesJoin) {
				if (aliasQueryBean != null && aliasQueryBean.getAliasName() != null) {
					if (aliasQueryBean.getTipoJoin()!=null){
						crit.createAlias(aliasQueryBean.getReferencia(), aliasQueryBean.getAliasName(), aliasQueryBean.getTipoJoin());
					} else {
						crit.createAlias(aliasQueryBean.getReferencia(), aliasQueryBean.getAliasName());
					}
				}
			}
		}
	}

	private void anadirCriterion(Criteria crit, List<Criterion> criterion) {
		if (criterion != null && crit != null) {
			for (final Criterion c : criterion) {
				if (c != null) {
					crit.add(c);
				}
			}
		}
	}

	private void anadirListaProyecciones(Criteria crit, ProjectionList listaProyecciones) {
		if (listaProyecciones != null && crit != null) {
			crit.setProjection(listaProyecciones);
		}
	}

	private void anadirTransformador(Criteria crit, ResultTransformer transformador) {
		if (transformador != null && crit != null) {
			crit.setResultTransformer(transformador);
		}
	}

}