package org.gestoresmadrid.utilidades.listas;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Comparador genérico de objetos.
 * 
 * @author ext_ihgl.
 *
 * @param <T> Objeto a comparar.
 */

public class GenericComparator<T> implements Comparator<T> {

	/* ATRIBUTOS */
	// Tipo de ordenación de los elementos
	private OrderType order;
	// Entero para ordenar los elementos
	private int intOrder;
	// Lista de atributos por los que ordenar
	private String[] fields;

	private final ILoggerOegam log = LoggerOegam.getLogger(GenericComparator.class);

	/* CONSTRUCTORES */
	public GenericComparator(String field) {
		// Ordenación por defecto: asc
		this(field, OrderType.asc);
	}

	public GenericComparator(String field, OrderType order) {
		super();
		this.order = order;
		this.fields = new String[]{field};
		setIntOrder(order);
	}

	public GenericComparator(String[] fields) {
		// Ordenación por defecto: asc
		this(fields, OrderType.asc);
	}

	public GenericComparator(String[] fields, OrderType order) {
		super();
		this.order = order;
		this.fields = fields;
		setIntOrder(order);
	}

	/**
	 * Función de comparación.
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T o1, T o2) {
		int result = 0;

		if (fields != null && fields.length > 0) {
			for (String field : fields) {
				try {
//					Field field1 = o1.getClass().getDeclaredField(field);
//					Field field2 = o2.getClass().getDeclaredField(field);
//					field1.setAccessible(true);
//					field2.setAccessible(true);
//					Object f1 = field1.get(o1);
//					Object f2 = field2.get(o2);

					/* Para herencia de clases */
					Object f1 = getField(o1, field);
					Object f2 = getField(o2, field);

					// Comparamos los campos
					result = compareFields(f1, f2);

					// Si no son iguales, no seguimos comparando por más campos
					if (result != 0) {
						break;
					}
				} catch (IllegalArgumentException e) {
					log.error("Error generico al comparar", e);
				} catch (SecurityException e) {
					log.error("Error generico al comparar", e);
				}
			}
		}

		// Order by
		return result * this.intOrder;
	}

	/* MÉTODOS PRIVADOS */

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
					log.error("Error generico al comparar", e);
				}
			}

			if (objetoAux == null) {
				log.error("Elemento no encontrado. No se ha encontrado en el objeto (ni en sus clases heredadas) '" + objeto.getClass().getName() + "' un atributo con el nombre '" + nombreAtributo + "'.");
			}

		}
		return objetoAux;
	}

	/**
	 * Compara objetos según su tipo.
	 * 
	 * @param o1 objeto1.
	 * @param o2 objeto2.
	 * @return int - resultado de la comparación.
	 */
	private int compareFields(Object o1, Object o2) {
		int result = 0;
		// Null values
		if (o1 == null && o2 == null) {
			result = 0; 
		} else if (o1 == null) {
			result = -1;
		} else if (o2 == null) {
			result = 1;		
			
		// Integer comparation
		} else if (o1 instanceof Integer && o2 instanceof Integer) {
			Integer i1 = (Integer)o1;
			Integer i2 = (Integer)o2;

			result = i1.compareTo(i2);

		// Double comparation
		} else if (o1 instanceof Double && o2 instanceof Double) {
			Double d1 = (Double)o1;
			Double d2 = (Double)o2;

			result = d1.compareTo(d2);

		// String comparation
		} else if (o1 instanceof String && o2 instanceof String) {
			String s1 = (String)o1;
			String s2 = (String)o2;

			result = s1.compareTo(s2);

		// Long comparation
		} else if (o1 instanceof Long && o2 instanceof Long) {
			Long l1 = (Long)o1;
			Long l2 = (Long)o2;

			result = l1.compareTo(l2);

		// Boolean comparation
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			Boolean b1 = (Boolean)o1;
			Boolean b2 = (Boolean)o2;

			result = b1.compareTo(b2);

		// Date comparation
		} else if (o1 instanceof Date && o2 instanceof Date) {
			Date d1 = (Date)o1;
			Date d2 = (Date)o2;

			result = d1.compareTo(d2);

		// Object comparation
		} else if (o1 instanceof Comparable && o2 instanceof Comparable) {

			Comparable c1 = (Comparable)o1;
			Comparable c2 = (Comparable)o2;

			result = c1.compareTo(c2);
		}
		return result;
	}

	private void setIntOrder(OrderType order) {
		this.intOrder = OrderType.asc.equals(order) ? 1 : -1;
	}

	// GETTERS/SETTERS
	public OrderType getOrder() {
		return order;
	}

	public void setOrder(OrderType order) {
		this.order = order;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
}