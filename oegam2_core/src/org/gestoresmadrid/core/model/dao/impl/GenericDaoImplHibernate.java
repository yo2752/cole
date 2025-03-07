package org.gestoresmadrid.core.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EmbeddedId;

import org.apache.commons.beanutils.PropertyUtils;
import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.Subcriteria;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public abstract class GenericDaoImplHibernate<T> implements GenericDao<T>, Serializable {

	private static final long serialVersionUID = 1569626080864678731L;
	protected static final ILoggerOegam log = LoggerOegam.getLogger(GenericDaoImplHibernate.class);
	public static final String ordenAsc = "asc";
	public static final String ordenDes = "desc";

	private Class<T> type;

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Este método recupera el tipo parametrizado de la clase. Es de tipo protected por si necesita ser sobreescrito
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getType() {
		if (type == null) {
			type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return type;
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

	/**
	 * Realiza una inserción de la entidad pasada por parámetro. Si va bien devuelve su ID
	 */
	public Serializable guardar(T objeto) {
		Session sesion = null;
		Serializable resultado = null;
		try {
			sesion = getCurrentSession();
			resultado = sesion.save(objeto);
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al guardar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return resultado;
	}

	/**
	 * Actualiza o inserta la entidad pasada por parámetro. Si va bien, devuelve la misma entidad
	 */
	@Override
	public T guardarOActualizar(T objeto) {
		Session sesion = null;
		T resultado = null;
		try {
			sesion = getCurrentSession();
			sesion.saveOrUpdate(objeto);
			resultado = objeto;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al guardar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return resultado;
	}

	/**
	 * Recupera la lista de objetos que cumple restricciones de igualdad, de los atributos de la entidad pasada por parámetro que no sean nulos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscar(T objeto) {
		List<T> lista = null;
		Session session = null;
		try {
			session = getCurrentSession();
			Criteria criteria = crearFiltro(objeto, session.createCriteria(getType()));
			lista = criteria.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al buscar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return lista;
	}

	@Override
	public boolean borrar(T objeto) {
		Session sesion = getCurrentSession();
		boolean resultado = false;
		try {
			sesion.delete(objeto);
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al borrar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return resultado;
	}

	@Override
	public void evict(Object objeto) {
		Session sesion = getCurrentSession();
		try {
			sesion.evict(objeto);
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al borrar un objeto de tipo " + getType().getName(), e);
			throw e;
		}
	}

	@Override
	public T actualizar(T objeto) {
		Session sesion = getCurrentSession();
		boolean resultado = false;
		try {
			sesion.update(objeto);
			resultado = true;
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una actualización del objeto de tipo " + getType().getName(), e);
			throw e;
		}
		if (resultado) {
			return objeto;
		}
		return null;
	}

	/**
	 * Crea restricciones de igualdad de las propiedades que vengan informadas en el objeto
	 * @param objeto
	 * @param criteria
	 * @return
	 */
	protected Criteria crearFiltro(T objeto, Criteria criteria) {
		if (objeto != null) {
			try {
				for (Method method : objeto.getClass().getMethods()) {
					if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterTypes().length == 0) {
						Object _return;
						_return = method.invoke(objeto);

						if (_return != null && !_return.toString().isEmpty()) {
							String name = method.getName().startsWith("get") ? method.getName().substring(3) : method.getName().substring(2);
							name = name.substring(0, 1).toLowerCase() + name.substring(1);
							if (!name.equals("class")) {
								criteria.add(Restrictions.eq(name, _return));
							}
						}
					}
				}
			} catch (IllegalAccessException e) {
				log.error("Error construyendo el filtro de " + getType(), e);
			} catch (IllegalArgumentException e) {
				log.error("Error construyendo el filtro de " + getType(), e);
			} catch (InvocationTargetException e) {
				log.error("Error construyendo el filtro de " + getType(), e);
			}
		}
		return criteria;
	}

	@Override
	public int numeroElementos(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin) {
		int result = -1;
		Session sesion = null;
		try {
			sesion = getCurrentSession();
			Criteria crit = sesion.createCriteria(getType());
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirCriterion(crit, criterion);
			result = ((Integer) (crit.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return result;
	}

	@Override
	public List<T> buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		return buscarPorCriteria(-1, -1, criterion, null, null, entitiesJoin, listaProyecciones, null);
	}

	@Override
	public List<T> buscarPorCriteria(List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones) {
		return buscarPorCriteria(-1, -1, criterion, dir, campoOrden, entitiesJoin, listaProyecciones, null);
	}

	/**
	 * Búsqueda de todos los registros utilizando criteria y paginación
	 */
	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden) {
		return buscarPorCriteria(firstResult, maxResults, criterion, dir, campoOrden, null, null, null);
	}

	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			ResultTransformer transformadorResultados) {
		return buscarPorCriteria(firstResult, maxResults, criterion, dir, campoOrden, entitiesJoin, listaProyecciones, transformadorResultados, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			ResultTransformer transformadorResultados, String[] fetchModeJoinList) {
		List<T> result = null;
		Session sesion = null;
		try {
			sesion = getCurrentSession();
			Criteria crit = sesion.createCriteria(getType());
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirCriterion(crit, criterion);
			if (firstResult > 0) {
				crit.setFirstResult(firstResult);
			}
			if (maxResults > 0) {
				crit.setMaxResults(maxResults);
			}

			// Establecer el orden y el campo
			if (ordenAsc.equals(dir) && campoOrden != null) {
				for (String campo : campoOrden) {
					crit.addOrder(Order.asc(campo));
				}
			} else if (ordenDes.equals(dir) && campoOrden != null) {
				for (String campo : campoOrden) {
					crit.addOrder(Order.desc(campo));
				}
			}
			anadirFetchModesJoin(crit, fetchModeJoinList);
			anadirListaProyecciones(crit, listaProyecciones);
			anadirTransformador(crit, transformadorResultados);
			result = crit.list();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con paginación del objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return result;
	}

	protected void anadirEntitiesJoin(Criteria crit, List<AliasQueryBean> entitiesJoin) {
		if (entitiesJoin != null && crit != null) {
			for (AliasQueryBean aliasQueryBean : entitiesJoin) {
				if (aliasQueryBean != null && aliasQueryBean.getAliasName() != null) {
					if (aliasQueryBean.getTipoJoin() != null) {
						crit.createAlias(aliasQueryBean.getReferencia(), aliasQueryBean.getAliasName(), aliasQueryBean.getTipoJoin());
					} else {
						crit.createAlias(aliasQueryBean.getReferencia(), aliasQueryBean.getAliasName());
					}
				}
			}
		}
	}

	protected void anadirCriterion(Criteria crit, List<Criterion> criterion) {
		if (criterion != null && crit != null) {
			for (final Criterion c : criterion) {
				if (c != null) {
					crit.add(c);
				}
			}
		}
	}

	protected void anadirListaProyecciones(Criteria crit, ProjectionList listaProyecciones) {
		if (listaProyecciones != null && crit != null) {
			crit.setProjection(listaProyecciones);
		}
	}

	protected void anadirTransformador(Criteria crit, ResultTransformer transformador) {
		if (crit != null) {
			if (transformador != null) {
				crit.setResultTransformer(transformador);
			} else if (((CriteriaImpl) crit).getProjection() == null ) {
				crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			}
		}
	}

	protected void anadirFetchModesJoin(Criteria crit, String[] fetchModeJoinList) {
		if (fetchModeJoinList != null && crit != null) {
			for (String associationPath : fetchModeJoinList) {
				crit.setFetchMode(associationPath, FetchMode.JOIN);
			}
		}
	}

	protected void initializedLazzyProperties(List<?> result, String[] listInitLazzyProperties) {
		if (result != null && !result.isEmpty() && listInitLazzyProperties != null) {
			for (String param : listInitLazzyProperties) {
				for (Object o : result) {
					try {
						Hibernate.initialize(PropertyUtils.getProperty(o, param));
					} catch (HibernateException e) {
						log.error("Error inicializando " + param + " en " + getType().getName(), e);
					} catch (IllegalAccessException e) {
						log.error("Error inicializando atributos sin acceso, " + param + " en " + getType().getName(), e);
					} catch (InvocationTargetException e) {
						log.error("Error inicializando atributos " + param + " en " + getType().getName(), e);
					} catch (NoSuchMethodException e) {
						log.error("Error inicializando atributos INEXISTENTES " + param + " en " + getType().getName(), e);
					}
				}
			}
		}
	}

	/**
	 * @see GenericDao#numeroElementos(Object)
	 * @param filter
	 * @return
	 */
	@Override
	public int numeroElementos(Object filter) {
		return numeroElementos(filter, null, null);
	}

	/**
	 * @param filter
	 * @param fetchModeJoinList
	 * @param entitiesJoin
	 * @see GenericDao#numeroElementos(Object)
	 * @return
	 */
	@Override
	public int numeroElementos(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin) {
		int result = -1;
		try {
			Criteria crit = createCriterion(filter);
			anadirEntitiesJoin(crit, entitiesJoin);
			anadirFetchModesJoin(crit, fetchModeJoinList);
			result = ((Integer) (crit.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al obtener el número de elementos por criterios del objeto de tipo " + getType().getName(), e);
		}
		return result;
	}

	/**
	 * @see GenericDao#buscarPorCriteria(int, int, String, String, Object, ProjectionList, ResultTransformer)
	 * @return
	 */
	@Override
	public List<T> buscarPorCriteria(int firstResult, int maxResults, String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados) {
		return buscarPorCriteria(firstResult, maxResults, dir, campoOrden, filter, listaProyecciones, transformadorResultados, null, null, null);
	}

	/**
	 * @see GenericDao#buscarPorCriteria(Object, ProjectionList, ResultTransformer, String[], String[])
	 * @return
	 */
	@Override
	public List<T> buscarPorCriteria(Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados, String[] fetchModeJoinList, String[] initializedProperties) {
		return buscarPorCriteria(null, null, filter, listaProyecciones, transformadorResultados, fetchModeJoinList, initializedProperties);
	}

	/**
	 * @see GenericDao#buscarPorCriteria(String, String, Object, ProjectionList, ResultTransformer, String[], String[])
	 * @return
	 */
	@Override
	public List<T> buscarPorCriteria(String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados, String[] fetchModeJoinList,
			String[] initializedProperties) {
		return buscarPorCriteria(0, 0, dir, campoOrden, filter, listaProyecciones, transformadorResultados, fetchModeJoinList, initializedProperties, null);
	}

	/**
	 * @see GenericDao#buscarPorCriteria(int, int, String, String, Object, ProjectionList, ResultTransformer, String[], String[])
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarPorCriteria(int firstResult, int maxResults, String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados,
			String[] fetchModeJoinList, String[] initializedProperties, List<AliasQueryBean> entitiesJoin) {
		List<T> result = null;
		try {
			Criteria crit = createCriterion(filter);
			anadirEntitiesJoin(crit, entitiesJoin);
			if (firstResult > 0) {
				crit.setFirstResult(firstResult);
			}
			if (maxResults > 0) {
				crit.setMaxResults(maxResults);
			}
			// Establecer el orden y el campo
			try {
				addOrder(crit, campoOrden, dir);
			} catch (SecurityException e) {
				log.error("No acceciendo al campo de ordenacion", e);
			} catch (NoSuchFieldException e) {
				log.error("No existe el campo de ordenacion", e);
			}

			anadirFetchModesJoin(crit, fetchModeJoinList);
			anadirListaProyecciones(crit, listaProyecciones);
			anadirTransformador(crit, transformadorResultados);
			result = crit.list();
			initializedLazzyProperties(result, initializedProperties);
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con paginación del objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return result;
	}

	/**
	 * Método que crea el Criteria según el objeto pasado por medio de anotaciones @FilterSimpleExpression. Es un método protected para que pueda ser sobreescrito en la implementación si fuese necesario.
	 * @param filter
	 * @return
	 */
	protected Criteria createCriterion(Object filter) {
		Session sesion = null;
		Criteria criteria = null;
		try {
			sesion = getCurrentSession();
			criteria = sesion.createCriteria(getType());
			if (filter != null) {
				for (Field field : filter.getClass().getDeclaredFields()) {
					FilterSimpleExpression annotation = field.getAnnotation(FilterSimpleExpression.class);
					if (annotation != null) {
						Object value = null;
						try {
							if (!field.isAccessible()) {
								field.setAccessible(true);
							}
							value = field.get(filter);

							if (value != null && !value.toString().isEmpty()) {

								Criteria criteriaAux = criteria;

								// Si viene informado ruta de entidades, crear subcriterias
								FilterRelationships relationShips = field.getAnnotation(FilterRelationships.class);
								if (relationShips != null && relationShips.value() != null && relationShips.value().length > 0) {
									for (FilterRelationship relation : relationShips.value()) {
										criteriaAux = createSubcriteria(criteria, criteriaAux, relation);
									}
								} else {
									// Si viene informado una unica entidad padre, se crea el subcriteria
									criteriaAux = createSubcriteria(criteria, criteriaAux, field.getAnnotation(FilterRelationship.class));
								}

								// Recupera el nombre de la propiedad que se incluye en el criterio
								String name = annotation.name();
								if (name == null || name.isEmpty()) {
									name = field.getName();
								}

								// Recupera el tipo de restriccion desde la anotacion
								Criterion calcuatedCriterion = annotation.restriction().getRestriction(name, value);
								if (calcuatedCriterion != null) {
									// Se comprueba si esta anotado como negacion
									if (annotation.not()) {
										criteriaAux.add(Restrictions.not(calcuatedCriterion));
									} else {
										criteriaAux.add(calcuatedCriterion);
									}
								}
							}
						} catch (SecurityException s) {
							log.error("El campo " + field.getName() + " de la clase " + filter.getClass() + " esta anotado como FilterSimpleExpression, pero no es accesible", s);
						} catch (IllegalArgumentException e) {
							log.error("Error con el campo " + field.getName() + " de la clase " + filter.getClass(), e);
						} catch (IllegalAccessException e) {
							log.error("No se puede acceder a el campo " + field.getName() + " de la clase " + filter.getClass(), e);
						}
					}
				}
			}
		} catch (HibernateException e) {
			log.error("Un error ha ocurrido al hacer una búsqueda por criterios con paginación del objeto de tipo " + getType().getName(), e);
			throw e;
		}
		return criteria;
	}

	/**
	 * Método que crea subcriteria de un objeto criteria, según la información almacenada en la anotación @FilterRelationship
	 * @param criteria
	 * @param criteriaAux
	 * @param relationship
	 * @return
	 */
	protected Criteria createSubcriteria(Criteria criteria, Criteria criteriaAux, FilterRelationship relationship) {
		if (relationship != null) {
			boolean found = false;
			// Comprobar que el subcriteria no exista. Y en caso de que si exista, reutilizarlo.
			@SuppressWarnings("unchecked")
			Iterator<Subcriteria> it = ((CriteriaImpl) criteria).iterateSubcriteria();
			while (it.hasNext()) {
				Subcriteria subcriteria = it.next();
				if (criteriaAux.equals(subcriteria.getParent()) && subcriteria.getPath().equals(relationship.name())) {
					criteriaAux = subcriteria;
					found = true;
					break;
				}
			}
			if (!found) {
				String alias = criteriaAux.getAlias() != null && !"this".equals(criteriaAux.getAlias()) ? StringHelper.qualify(criteriaAux.getAlias(), relationship.name()) : relationship.name();
				criteriaAux = criteriaAux.createCriteria(relationship.name(), alias, relationship.joinType());
			}
		}
		return criteriaAux;
	}

	/**
	 * @param criteria
	 * @param campoOrden
	 * @param dir
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws HibernateException 
	 */
	protected void addOrder(Criteria criteria, String campoOrden, String dir) throws HibernateException, SecurityException, NoSuchFieldException {
		// Establecer el orden y el campo
		if (campoOrden != null && !campoOrden.isEmpty()) {
			// Comprobar que existan todos los alias/join creados
			String[] path = campoOrden.split("\\.");
			// Si existe ruta y no es ID
			if (path.length > 1 && getType().getDeclaredField(path[0]).getAnnotation(EmbeddedId.class) == null) {
				Criteria criteriaAux = criteria;
				for (int i = 0; i < path.length - 1; i++) {
					String name = path[i];
					boolean found = false;
					@SuppressWarnings("unchecked")
					Iterator<Subcriteria> it = ((CriteriaImpl) criteria).iterateSubcriteria();
					while (it.hasNext()) {
						Subcriteria subcriteria = it.next();
						if (criteriaAux.equals(subcriteria.getParent()) && subcriteria.getPath().equals(name)) {
							criteriaAux = subcriteria;
							found = true;
							break;
						}
					}
					if (!found) {
						String alias = criteriaAux.getAlias() != null && !"this".equals(criteriaAux.getAlias()) ? StringHelper.qualify(criteriaAux.getAlias(), name) : name;
						criteriaAux = criteriaAux.createCriteria(name, alias, CriteriaSpecification.LEFT_JOIN);
					}
				}
				Order order;
				if (ordenAsc.equals(dir)) {
					order = Order.asc(path[path.length-1]);
				} else {
					order = Order.desc(path[path.length-1]);
				}
				criteriaAux.addOrder(order);
			} else {
				Order order;
				if (ordenAsc.equals(dir)) {
					order = Order.asc(campoOrden);
				} else {
					order = Order.desc(campoOrden);
				}
				criteria.addOrder(order);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T unenhanceObject(T object) {
		if (object instanceof HibernateProxy) {
			HibernateProxy hibernateProxy = (HibernateProxy) object;
			LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();
			return (T) lazyInitializer.getImplementation();
		}
		return object;
	}

	public int executeNamedQuery(String namedQueryId, String[] namedParemeters, Object[] namedValues) {
		Query query = getNamedQuery(namedQueryId, namedParemeters, namedValues);
		if (query != null) {
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listNamedQuery(String namedQueryId, String[] namedParemeters, Object[] namedValues) {
		Query query = getNamedQuery(namedQueryId, namedParemeters, namedValues);
		if (query != null) {
			return query.list();
		} else {
			return null;
		}
	}

	private Query getNamedQuery(String namedQueryId, String[] namedParemeters, Object[] namedValues) {
		Query query = getCurrentSession().getNamedQuery(namedQueryId);
		if (query == null) {
			log.error("NamedQuery " + namedQueryId + " no existente");
		} else {
			int numParemeters = query.getNamedParameters() != null ? query.getNamedParameters().length : 0;
			int numNamedParameters = namedParemeters != null ? namedParemeters.length : 0;
			int numNamedValues = namedValues != null ? namedValues.length : 0;

			if (numParemeters != numNamedParameters || numParemeters != numNamedValues) {
				log.error("El numero de parametros pasados a la NamedQuery " + namedQueryId + " no es correcto");
				query = null;
			} else {
				for (int i = 0; i< namedParemeters.length; i++) {
					String name = namedParemeters[i];
					Object value = namedValues[i];
					if (value instanceof Collection<?>) {
						query.setParameterList(name, (Collection<?>) value);
					} else {
						query.setParameter(name, value);
					}
				}
				if (log.isDebugEnabled()) {
					log.debug("Se ejecuta la namedQuery: " + query.getQueryString());
				}
			}
		}
		return query;
	}

}