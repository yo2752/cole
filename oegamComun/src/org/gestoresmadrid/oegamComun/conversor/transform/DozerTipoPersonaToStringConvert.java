package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;

public class DozerTipoPersonaToStringConvert extends DozerConverter<TipoPersona, String> {

	public DozerTipoPersonaToStringConvert() {
		super(TipoPersona.class, String.class);
	}

	public DozerTipoPersonaToStringConvert(Class<TipoPersona> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public TipoPersona convertFrom(String source, TipoPersona destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		return TipoPersona.convertir(source);
	}

	@Override
	public String convertTo(TipoPersona source, String destination) {
		if (source == null) {
			return null;
		}
		return source.getValorEnum();
	}
}
