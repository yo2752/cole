package org.gestoresmadrid.oegamComun.conversor.transform;

import java.text.ParseException;
import java.util.Date;

import org.dozer.DozerConverter;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DozerFechaConverter extends DozerConverter<Fecha, Date> {

	@Autowired
	UtilesFecha utilesFecha;

	public DozerFechaConverter() {
		super(Fecha.class, Date.class);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public DozerFechaConverter(Class<Fecha> prototypeA, Class<Date> prototypeB) {
		super(prototypeA, prototypeB);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(DozerFechaConverter.class);

	@Override
	public Date convertTo(Fecha source, Date destination) {
		if (source == null || source.isfechaNula()) {
			return null;
		}
		try {
			if (source.getFechaHora() != null) {
				return source.getFechaHora();
			}
			if(source.getFechaHoraMinutos() != null){
				return source.getFechaHoraMinutos();
			}
			if (source.getFecha() != null) {
				return source.getFecha();
			}
			return null;
		} catch (ParseException e) {
			log.error("Error al convertir de Fecha a Date: source vale " + source);
			destination = null;
		}
		return destination;
	}

	@Override
	public Fecha convertFrom(Date source, Fecha destination) {
		if (source == null) {
			return null;
		}
		return utilesFecha.getFechaTimeStampConDate(source);
	}

}