package com.matriculasIvtmWS.integracion.conversion;

import java.io.Serializable;
import java.util.List;

public interface Conversor extends Serializable {

	
	<T> T transform(Object object, Class<T> clase);

	<T> T transform(Object object, Class<T> clase, String mapId);

	void transform(Object object, Object destination);

	void transform(Object object, Object destination, String mapId);

	<T> List<T> transform(List<?> object, Class<T> clase);

}
