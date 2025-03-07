package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.Estado;

public class DozerEstadoToLongConverter extends DozerConverter<Long, Estado> {

	public DozerEstadoToLongConverter() {
		super(Long.class, Estado.class);
	}

	public DozerEstadoToLongConverter(Class<Long> prototypeA, Class<Estado> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public Estado convertTo(Long source, Estado destination) {
		if (source == null) {
			return null;
		}
		return Estado.convertir(source.intValue());
	}

	@Override
	public Long convertFrom(Estado source, Long destination) {
		if (source == null) {
			return null;
		}
		return source.getValorEnum().longValue();
	}

}
