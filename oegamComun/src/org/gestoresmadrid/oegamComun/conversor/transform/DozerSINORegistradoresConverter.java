package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;

public class DozerSINORegistradoresConverter extends DozerConverter<String, String> {

	public DozerSINORegistradoresConverter() {
		super(String.class, String.class);
	}

	public DozerSINORegistradoresConverter(Class<String> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(String source, String destination) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		if (source.equals("SI")) {
			return "TRUE";
		} else if (source.equals("S")) {
			return "TRUE";
		}
		return "FALSE";
	}

	@Override
	public String convertFrom(String source, String destination) {
		if (source == null) {
			return null;
		}
		if (source.equalsIgnoreCase("TRUE")) {
			return "SI";
		}
		return "NO";
	}
}
