package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;

public class DozerSINOConverter extends DozerConverter<Boolean, String> {

	public DozerSINOConverter() {
		super(Boolean.class, String.class);
	}

	public DozerSINOConverter(Class<Boolean> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(Boolean source, String destination) {
		if (source == null) {
			return null;
		}
		if (source) {
			return "SI";
		}
		return "NO";
	}

	@Override
	public Boolean convertFrom(String source, Boolean destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		if (source.equals("SI")) {
			return Boolean.TRUE;
		} else if (source.equals("S")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
