package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;

public class DozerSNConverter extends DozerConverter<Boolean, String> {

	public DozerSNConverter(){
		super(Boolean.class, String.class);
	}
	public DozerSNConverter(Class<Boolean> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(Boolean source, String destination) {
		if (source==null){
			return null;
		}
		if (source){
			return "S";
		}
		return "N";
	}

	@Override
	public Boolean convertFrom(String source, Boolean destination) {
		if (source==null|| source.isEmpty()){
			return null; 
		}
		if (source.equals("S")) {
			return Boolean.TRUE;
		} else if (source.equals("SI")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
