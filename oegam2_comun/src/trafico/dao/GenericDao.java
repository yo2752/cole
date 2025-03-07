package trafico.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;

import trafico.dao.implHibernate.AliasQueryBean;

public interface GenericDao <T> {
	/**
	 * B�squeda de un expediente de liberaci�n por n�mero de expediente
	 * @param numExpediete
	 * @return 
	 * @return
	 */
	public T buscarPorExpediente(Long numExpediente);
	public T buscarPorExpediente(BigDecimal numExpediente);
	/**
	 * 
	 * @param liberacion
	 * @return
	 */
	public Object guardar(T objeto);

	/**
	 * 
	 * @param liberacion
	 * @return
	 */
	public Object guardarOActualizar(T objeto);

	/**
	 * B�squeda de lista por par�metros. B�squeda por los campos distintos de null.
	 * @param objeto
	 * @return
	 */
	public List<T> buscar(T objeto);

	/**
	 * 
	 * @param liberacion
	 * @return
	 */
	public boolean borrar(T objeto);

	/**
	 * 
	 * @param liberacion
	 * @return
	 */
	public T actualizar(T objeto);

	/**
	 * M�todo gen�rico para realizar b�squedas en la base de datos. Permite hacer joins y proyecciones.
	 * @param objeto
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	/**
	 * M�todo gen�rico para realizar b�squedas en la base de datos. Permite hacer joins, proyecciones e introducir el orden.
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(List<Criterion> criterion, String dir, String campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	/**
	 * M�todo gen�rico que busca registros formando citerios de b�squeda como el NIVE, bastidor, fechas, etc. con paginaci�n
	 * @param objeto
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion,  String dir, String campoOrden);

	/**
	 * M�todo gen�rico que busca registros formando citerios de b�squeda como el NIVE, bastidor, fechas, etc. con paginaci�n
	 * Se puede usar para el join de varias tablas, y tambi�n hacer proyecciones. 
	 * @param objeto
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @param entitiesJoin
	 * @param listaProyecciones
	 * @return
	 */
	public List<T> buscarPorCriteria(int firstResult, int maxResults, List<Criterion> criterion,  String dir, String campoOrden, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones, ResultTransformer transformadorResultados);

	public int numeroElementos(List<Criterion> criterion, List<AliasQueryBean> entitiesJoin);
}