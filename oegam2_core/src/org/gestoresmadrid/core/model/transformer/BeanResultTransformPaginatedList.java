package org.gestoresmadrid.core.model.transformer;

import java.util.Vector;

import org.hibernate.transform.ResultTransformer;

/**
 * Interfaz que define los resultTransformer utilizados en los buscadores.
 * Es una extensión, que permite además indicar que columnas son las que se va a utilizar en el resultTransformer.
 * @author ext_mpc
 *
 */
public interface BeanResultTransformPaginatedList extends ResultTransformer{

	/**
	 * Indica las columnas que se van a utilizar en el resultTransformer. Es importante el orden.
	 * @return
	 */
	public Vector<String> crearProyecciones();
}
