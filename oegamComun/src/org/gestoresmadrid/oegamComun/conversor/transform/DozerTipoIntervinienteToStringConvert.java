package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

public class DozerTipoIntervinienteToStringConvert extends DozerConverter<TipoInterviniente, String> {

	public DozerTipoIntervinienteToStringConvert() {
		super(TipoInterviniente.class, String.class);
	}

	public DozerTipoIntervinienteToStringConvert(Class<TipoInterviniente> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public TipoInterviniente convertFrom(String source, TipoInterviniente destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		return TipoInterviniente.convertir(source);
	}

	@Override
	public String convertTo(TipoInterviniente source, String destination) {
		if (source == null) {
			return null;
		}
		return source.getValorEnum();
	}

}
