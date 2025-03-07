package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class UtilesColecciones {

	/**
	 * Método que ordena una lista de objetos en orden ascendente o descendente,
	 * según un atributo de los objetos del listado.
	 * 
	 * @param lista
	 * @param ascendente
	 * @param atributo
	 * @return
	 */
	public List<? extends Object> sort(List<? extends Object> lista, boolean ascendente, String atributo) {
		final boolean asc = ascendente;
		final String atr = atributo;
		Collections.sort(lista, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				try {
					if (asc) {
						return BeanUtils.getProperty(o1, atr).compareTo(BeanUtils.getProperty(o2, atr));
					} else {
						return BeanUtils.getProperty(o2, atr).compareTo(BeanUtils.getProperty(o1, atr));
					}
				} catch (Exception e) {
					return 0;
				}
			}
		});
		return lista;
	}

}
