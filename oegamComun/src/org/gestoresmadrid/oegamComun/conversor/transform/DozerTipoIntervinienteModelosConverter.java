package org.gestoresmadrid.oegamComun.conversor.transform;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

public class DozerTipoIntervinienteModelosConverter extends DozerConverter<String, String> {

	public DozerTipoIntervinienteModelosConverter(){
		super(String.class, String.class);
	}
	public DozerTipoIntervinienteModelosConverter(Class<String> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(String source, String destination) {
		if (source==null){
			return null;
		}
		return TipoInterviniente.convertirTexto(source);
	}

	@Override
	public String convertFrom(String source, String destination) {
		if (source==null|| source.isEmpty()){
			return null; 
		}
		return TipoInterviniente.convertirTexto(source);
	}

}
