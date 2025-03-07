package general.acciones;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedList;

/**
 * Factoría de los buscadores. Es un abstract factory.
 * @author ext_mpc
 *
 */
public interface AbstractFactoryPaginatedList {

	/**
	 * Modelo utilizado para realizar la búsqueda. Hay una implementación abstracta en general.modelo.ModeloSkeletonPaginatedListImpl 
	 * @return
	 */
	public ModeloSkeletonPaginatedList crearModelo();
	
	/**
	 * Bean de pantalla (a veces llamado DTO) que contendrá los posibles criterios de búsqueda
	 * @return
	 */
	public BeanCriteriosSkeletonPaginatedList crearCriterios();
	
	/**
	 * ResultTransoformer utilizado para solo recuperar las columnas que se deben mostrar en la tabla de resultados, y no todo el entity.
	 * @return
	 */
	public BeanResultTransformPaginatedList crearTransformer(); 
	
	/**
	 * Entity principal sobre el que se realizará la búsqueda. 
	 * @return
	 */
	public Object crearEntityModelo(); //Es el entity
	
	/**
	 * Decorator de la tabla, si se va a utilizar. Debe indicarse el paquete y la clase correspondiente.
	 * @return
	 */
	public String decorator();
	
	/**
	 * Action correspondiente. Es la definición en el struts.xml. Se usa para la navegación dentro de la tabla de resultados.
	 * @return
	 */
	public String getAction();
}
