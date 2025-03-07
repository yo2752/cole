package org.gestoresmadrid.oegamPermisoInternacional.conversor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.gestoresmadrid.oegamPermisoInternacional.conversor.service.ConversorPermInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversorPermInterImpl implements ConversorPermInter {

	private static final long serialVersionUID = -5753223731409856642L;

	@Autowired
	Mapper mapper;

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
		List<T> listaSalida = new ArrayList<>();
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
		List<T> listaSalida = new ArrayList<>();
		for (Object o : object) {
			listaSalida.add(transform(o, clase, mapId));
		}
		return listaSalida;
	}
}