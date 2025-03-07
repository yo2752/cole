package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;

public class Dozer0s1sConverter extends DozerConverter<Boolean, String> {

	public Dozer0s1sConverter() {
		super(Boolean.class, String.class);
	}

	public Dozer0s1sConverter(Class<Boolean> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(Boolean source, String destination) {
		if (source == null) {
			return null;
		}
		if (source) {
			return "1";
		}
		return "0";
	}

	@Override
	public Boolean convertFrom(String source, Boolean destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		if (source.equals("1")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
