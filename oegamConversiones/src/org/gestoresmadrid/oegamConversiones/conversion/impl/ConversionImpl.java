package org.gestoresmadrid.oegamConversiones.conversion.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionImpl implements Conversion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8702107599373561360L;

	@Autowired
	private Mapper mapper;

	@Override
	public <T> T transform(Object object, Class<T> clase) {
		if (object == null) {
			return null;
		}
		return (T) mapper.map(object, clase);
	}

	@Override
	public <T> T transform(Object object, Class<T> clase, String mapId) {
		if (object == null) {
			return null;
		}
		return (T) mapper.map(object, clase, mapId);
	}

	@Override
	public void transform(Object object, Object destination) {
		if (object != null) {
			mapper.map(object, destination);
		}
	}

	@Override
	public void transform(Object object, Object destination, String mapId) {
		if (object != null) {
			mapper.map(object, destination, mapId);
		}
	}

	@Override
	public <T> List<T> transform(List<?> object, Class<T> clase) {
		if (object == null) {
			return null;
		}
		List<T> listaSalida = new ArrayList<T>();
		for (Object o : object) {
			listaSalida.add(transform(o, clase));
		}
		return listaSalida;
	}
	
	@Override
	public <T> List<T> transform(List<?> object, Class<T> clase, String mapId) {
		if (object == null) {
			return null;
		}
		List<T> listaSalida = new ArrayList<T>();
		for (Object o : object) {
			listaSalida.add(transform(o, clase,mapId));
		}
		return listaSalida;
	}

}
