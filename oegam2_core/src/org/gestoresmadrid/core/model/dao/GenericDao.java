package org.gestoresmadrid.core.model.dao;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;

public interface GenericDao<T> {
	/**
	 * Busqueda de un expediente de liberación por numero de expediente
	 * @param numExpediete
	 * @return
	 * @return
	 */
	public T buscarPorExpediente(BigDecimal numExpediente);

	/**
	 * Devuelve el Id si se ha guardado.
	 * @param liberacion
	 * @return
	 */
	public Object guardar(T objeto);

	/**
	 * Devuelve el Objeto si se ha actualizado correctamente si no devuelve null
	 * @param liberacion
	 * @return
	 */

	public T guardarOActualizar(T objeto);

	/**
	 * Busqueda de lista por parametros. Busqueda por los campos distintos de null.
	 * @param objeto
	 * @return
	 */
	public List<T> buscar(T objeto);

	/**
	 * @param liberacion
	 * @return
	 */
	public boolean borrar(T objeto);

	/**
	 * @param liberacion
	 */
	public void evict(Object objeto);

	/**
	 * @param liberacion
	 * @return
	 */
	public T actualizar(T objeto);

	/**
	 * Método genérico para realizar búsquedas en la base de datos. Permite hacer joins y proyecciones.
	 * @param objeto
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	/**
	 * Método genérico para realizar búsquedas en la base de datos. Permite hacer joins, proyecciones e introducir el orden.
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	/**
	 * Método genérico que busca registros formando criterios de búsqueda como el nive, bastidor, fechas etc. con paginación
	 * @param objeto
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden);

	/**
	 * Método genérico que busca registros formando criterios de búsqueda como el nive, bastidor, fechas etc. con paginación Se puede usar para el join de varias tablas, y también hacer proyecciones.
	 * @param objeto
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			ResultTransformer transformadorResultados);

	/**
	 * Método genérico que busca registros formando criterios de búsqueda como el nive, bastidor, fechas etc. con paginación Se puede usar para el join de varias tablas, y también hacer proyecciones.
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @param dir
	 * @param campoOrden
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @param transformadorResultados
	 * @param fetchModeJoinList
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion, String dir, List<String> campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones,
			ResultTransformer transformadorResultados, String[] fetchModeJoinList);
	
	public int numeroElementos(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin);

	/**
	 * Metodo que devuelve el número de registros que coinciden con el criterio formado por el Objeto pasado, con anotaciones de tipo Filter
	 * @param filter
	 * @return
	 */
	public int numeroElementos(Object filter);

	/**
	 * Método genérico que busca registros formando criterios de búsqueda por medio de un Objeto con anotaciones de tipo Filter, también se puede hacer proyecciones.
	 * @param firstResult
	 * @param maxResults
	 * @param dir
	 * @param campoOrden
	 * @param filter
	 * @param listaProyecciones
	 * @param transformadorResultados
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados);

	/**
	 * Método genérico que busca registros formando criterios de búsqueda por medio de un Objeto con anotaciones de tipo Filter, también se puede hacer proyecciones.
	 * @param firstResult
	 * @param maxResults
	 * @param dir
	 * @param campoOrden
	 * @param filter
	 * @param listaProyecciones
	 * @param transformadorResultados
	 * @param fetchModeJoinList String[] con los associationPath que deben ser FetchMode.JOIN
	 * @param initializedProperties String[] lista de propiedades que deben ser inicializadas antes de cerrar la sesión (acarrea mas select, cuidado).
	 * @param entitiesJoin
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados,
			String[] fetchModeJoinList, String[] initializedProperties, List<AliasQueryBean> entitiesJoin);

	/**
	 * @param dir
	 * @param campoOrden
	 * @param filter
	 * @param listaProyecciones
	 * @param transformadorResultados
	 * @param fetchModeJoinList
	 * @param initializedProperties
	 * @return
	 */
	List<T> buscarPorCriteria(String dir, String campoOrden, Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados, String[] fetchModeJoinList,
			String[] initializedProperties);

	/**
	 * @param filter
	 * @param listaProyecciones
	 * @param transformadorResultados
	 * @param fetchModeJoinList
	 * @param initializedProperties
	 * @return
	 */
	List<T> buscarPorCriteria(Object filter, ProjectionList listaProyecciones, ResultTransformer transformadorResultados, String[] fetchModeJoinList, String[] initializedProperties);

	/**
	 * Devuelve el objeto real, no el proxy de hibernate
	 * @param object
	 * @return
	 */
	T unenhanceObject(T object);

	/**
	 * Ejecuta una namedQuery previamente definida. Al que se le pasa en el
	 * mismo orden, un array con los nombres de los parametros y otro array con
	 * su valor correspondiente
	 * 
	 * @param namedQueryId
	 *            Nombre de la namedQuery
	 * @param namedParemeters
	 *            Nombres de los parametros que se pasan en la Query
	 * @param namedValues
	 *            Valores de los parametros a pasar en la Query, en el mismo
	 *            orden que vienen en namedParemetes
	 * 
	 * @return Numero de entidades actualizadas o borradas
	 * 
	 * @throws HibernateException
	 */
	public int executeNamedQuery(String namedQueryId, String[] namedParemeters, Object[] namedValues);

	int numeroElementos(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin);
}
