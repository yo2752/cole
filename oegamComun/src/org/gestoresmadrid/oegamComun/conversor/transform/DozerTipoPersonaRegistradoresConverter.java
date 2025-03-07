package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;

public class DozerTipoPersonaRegistradoresConverter extends DozerConverter<String, String> {

	public DozerTipoPersonaRegistradoresConverter() {
		super(String.class, String.class);
	}

	public DozerTipoPersonaRegistradoresConverter(Class<String> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(String source, String destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		return TipoPersonaRegistro.convertirTexto(source);
	}

	@Override
	public String convertFrom(String source, String destination) {
		if (source == null) {
			return null;
		}
		return TipoPersonaRegistro.convertirValorXml(source);
	}
}
