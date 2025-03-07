package org.gestoresmadrid.oegamComun.conversor.transform;

import java.util.Date;

import org.dozer.DozerConverter;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DozerDateTagVacioConverter extends DozerConverter<Date, String> {

	@Autowired
	UtilesFecha utilesFecha;

	public DozerDateTagVacioConverter(){
		super(Date.class, String.class);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public DozerDateTagVacioConverter(Class<Date> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public String convertTo(Date source, String destination) {
		if (source==null){
			return "";
		}
		return utilesFecha.formatoFecha(source);
	}

	@Override
	public Date convertFrom(String source, Date destination) {
		if (source == null){
			return null; 
		}
		return utilesFecha.formatoFecha("dd/MM/yyyy", source);
	}

}
