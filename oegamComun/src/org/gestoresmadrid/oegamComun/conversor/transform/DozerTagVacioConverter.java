package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;

public class DozerTagVacioConverter extends DozerConverter<String, String> {

	public DozerTagVacioConverter(){
		super(String.class, String.class);
	}
	public DozerTagVacioConverter(Class<String> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(String source, String destination) {
		if (source==null){
			return "";
		}
		return source;
	}

	@Override
	public String convertFrom(String source, String destination) {
		if (source == null){
			return ""; 
		}
		return source;
	}
}
