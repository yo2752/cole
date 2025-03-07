package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;

public class DozerMotivoBajaConverter extends DozerConverter<String, String>{

	public DozerMotivoBajaConverter(Class<String> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}
	
	public DozerMotivoBajaConverter() {
		super(String.class, String.class);
	}

	@Override
	public String convertTo(String source, String destination) {
		if(source == null || source.isEmpty()){
			return null;
		}
		
		if("2".equals(source)){
			return MotivoBaja.DefE.getValorEnum();
		}else if("1".equals(source)){
			return MotivoBaja.DefTC.getValorEnum();
		}
		return null;
	}

	@Override
	public String convertFrom(String source, String destination) {
		if(source == null || source.isEmpty()){
			return null;
		}
		
		if(MotivoBaja.DefE.getValorEnum().equals(source)){
			return "2";
		}else if(MotivoBaja.DefTC.getValorEnum().equals(source)){
			return "1";
		}
		
		return null;
	}

}
