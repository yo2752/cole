package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.EstadoCivil;

public class DozerEstadoCivilToStringConvert extends DozerConverter<EstadoCivil, String> {

	public DozerEstadoCivilToStringConvert() {
		super(EstadoCivil.class, String.class);
	}

	public DozerEstadoCivilToStringConvert(Class<EstadoCivil> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public EstadoCivil convertFrom(String source, EstadoCivil destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		return EstadoCivil.convertir(source);
	}

	@Override
	public String convertTo(EstadoCivil source, String destination) {
		if (source == null) {
			return null;
		}
		return source.getValorEnum();
	}
}
