package org.gestoresmadrid.utilidades.listas;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Operador sobre listas.
 * 
 * @author ext_ihgl.
 *
 * @param <T> Objeto a comparar.
 */

public class ListsOperator<T> {

	/* ATRIBUTOS */
	private final ILoggerOegam log = LoggerOegam.getLogger(ListsOperator.class);

	/* CONSTRUCTORES */
	public ListsOperator() {
	}

	/* MÉTODOS */
	/**
	 * Método que divide una lista en varias listas en función del tamaño recibido.
	 * 
	 * @param lista lista de elementos.
	 * @param numMaxElementosPorLista número máximo de elementos por cada lista.
	 * @return List<List<T>> - N listas con máximo 'numMaxElementosPorLista' elementos.
	 */
	public List<List<T>> dividirListaEnVarias(List<T> lista, int numMaxElementosPorLista) {
		List<List<T>> listaDeListas = null;

		if (lista != null) {
			listaDeListas = new ArrayList<List<T>>();
	
			List<T> listaAux = new ArrayList<T>();

			for (T elemento : lista) {
				// Comprobamos si cabe o no en la lista
				if (listaAux.size() < numMaxElementosPorLista) {
					// Aún caben más elementos
					listaAux.add(elemento);
				} else {
					// Lista llena: la añadimos a la lista total y la vaciamos
					listaDeListas.add(listaAux);
					listaAux = new ArrayList<T>();
					// Añado el elemento procesado 
					listaAux.add(elemento);
				}
			}

			// Añado la última lista (que no llegó al máximo de elementos)
			listaDeListas.add(listaAux);
		}

		return listaDeListas;
	}

	/**
	 * Método que busca un elemento en una lista por el valor del atributo recibido.
	 * 
	 * @param lista lista de elementos.
	 * @param nombreAtributo nombre del atributo.
	 * @param valorAtributo valor del atributo.
	 * @return T - elemento de la lista.
	 */
	public T buscarElementoPorValorAtributo(List<T> lista, String nombreAtributo, Object valorAtributo) {
		return buscarElementoPorValorAtributo(lista, new String[]{nombreAtributo}, valorAtributo);
	}

	/**
	 * Método que busca un elemento en una lista por el valor del atributo recibido.
	 * 
	 * @param lista lista de elementos.
	 * @param cadenaNombreAtributos
	 * <ul>
	 * 		<li>- lista de un solo nombre si queremos comparar con un atributo del objeto de la lista.</li>
	 * 		<li>- lista de N nombres de atributos que representan la cadena por la que acceder al atributo del objeto si éste está a su vez dentro de otro objeto.</li>
	 * </ul>
	 * @param valorAtributo valor del atributo.
	 * @return T - elemento de la lista.
	 */
	public T buscarElementoPorValorAtributo(List<T> lista, String[] cadenaNombreAtributos, Object valorAtributo) {

		if (lista != null) {
			for (T elemento : lista) {
				// Obtenemos el atributo del objeto
				Object f1 = obtenerAtributo(elemento, cadenaNombreAtributos);

				// Si no encontramos un atributo con ese nombre
				if (f1 == null) {
					return null;
				}

				// Comparamos con el valor recibido
				if ((valorAtributo == null && f1 == null) || valorAtributo.equals(f1)) {
					return elemento;
				}
			}
		}
		// No encontrado
		return null;
	}

	/**
	 * Método que a partir de una lista, obtiene otra lista de elementos por el nombre del atributo recibido.
	 * 
	 * @param lista lista de elementos.
	 * @param nombreAtributo nombre del atributo.
	 * @return List<Object> - lista de elementos.
	 */
	public List<Object> obtenerListaElementos(List<T> lista, String nombreAtributo) {
		return obtenerListaElementos(lista, new String[]{nombreAtributo});
	}

	/**
	 * Método que a partir de una lista, obtiene otra lista de elementos por el nombre del atributo recibido.
	 * 
	 * @param lista lista de elementos.
	 * @param cadenaNombreAtributos
	 * <ul>
	 * 		<li>- lista de un solo nombre si queremos comparar con un atributo del objeto de la lista.</li>
	 * 		<li>- lista de N nombres de atributos que representan la cadena por la que acceder al atributo del objeto si éste está a su vez dentro de otro objeto.</li>
	 * </ul>
	 * @return List<Object> - lista de elementos.
	 */
	public List<Object> obtenerListaElementos(List<T> lista, String[] cadenaNombreAtributos) {
		if (lista != null) {
			List<Object> listaElementos = new ArrayList<>();

			for (T elemento : lista) {
				// Obtenemos el atributo del objeto
				Object f1 = obtenerAtributo(elemento, cadenaNombreAtributos);

				// Si no encontramos un atributo con ese nombre
				if (f1 == null) {
					return null;
				}

				// Añadimos el elemento a la lista
				listaElementos.add(f1);
			}

			return listaElementos;
		}

		// Lista nula
		return null;
	}

	/**
	 * Método que devuelve el primer elemento de una lista.
	 * La utilidad es no tener que montar un Iterator si queremos obtener un elemento cualquiera de la lista.
	 * 
	 * @param lista lista origen.
	 * @return T - primer elemento de la lista.
	 */
	public T getFirstElement(Collection<T> lista) {
		if (lista != null) {
			for (T elemento : lista) {
				return elemento;
			}
		}
		return null;
	}

	/* MÉTODOS PRIVADOS */

	/**
	 * Método que obtiene de un objeto el atributo recibido por parámetro.
	 * 
	 * @param objeto objeto donde buscar el atributo
	 * @param cadenaNombreAtributos
	 * <ul>
	 * 		<li>- lista de un solo nombre si queremos comparar con un atributo del objeto de la lista.</li>
	 * 		<li>- lista de N nombres de atributos que representan la cadena por la que acceder al atributo del objeto si éste está a su vez dentro de otro objeto.</li>
	 * </ul>
	 * @return
	 */
	private Object obtenerAtributo(T objeto, String[] cadenaNombreAtributos) {
		// Obtenemos el atributo del objeto
		try {
			if (cadenaNombreAtributos == null || cadenaNombreAtributos.length == 0) {
				log.error("No se puede buscar el atributo del objeto '" + objeto.getClass().getName() + "'. Se ha recibido una lista de nombres de atributos vacía");
				return null;
			}

			Object objetoAux = objeto;

			// Recorremos la cadena de nombre (objetos)
			for (String nombreAtributo : cadenaNombreAtributos) {

				objetoAux = getField(objetoAux, nombreAtributo);
				if (objetoAux == null) {
					break;
				}
			}
			return objetoAux;
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		// No encontrado
		return null;
	}

	/**
	 * Método que obtiene el campo recursivamente en una clase y si no, en sus clases heredadas.
	 * 
	 * @param objeto objeto donde buscar el atributo.
	 * @param nombreAtributo nombre del atributo.
	 * @return Object - atributo buscado.
	 */
	private Object getField(Object objeto, String nombreAtributo) {
		Object objetoAux = null;
		boolean encontrado = false;

		if (objeto != null) {
			// Primero buscamos en el propio objeto
			Class<?> clase = objeto.getClass();

			while (clase != null && !encontrado) {
				try {
					Field field = clase.getDeclaredField(nombreAtributo);
					field.setAccessible(true);
					objetoAux = field.get(objeto);
					encontrado = true;
				} catch (NoSuchFieldException e) {
					/* Clases heredadas: buscamos el atributo también en la clase de la que hereda */
					//log.error("Atributo no encontrado en la clase '" + clase.getName() + "'. Buscamos en su clase padre.");
					clase = clase.getSuperclass();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			if (objetoAux == null) {
				log.error("Elemento no encontrado. No se ha encontrado en el objeto (ni en sus clases heredadas) '" + objeto.getClass().getName() + "' un atributo con el nombre '" + nombreAtributo + "'.");
			}
		}

		return objetoAux;
	}
	/* GETTERS/SETTERS */

}