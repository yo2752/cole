package org.gestoresmadrid.oegam2.view.decorator;

import java.text.ParseException;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorRegistroFueraSecuencia extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorRegistroFueraSecuencia() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaAlta() throws ParseException {
		RegistroFueraSecuenciaDto mensaje = (RegistroFueraSecuenciaDto) getCurrentRowObject();
		if (mensaje != null && mensaje.getFechaAlta() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", mensaje.getFechaAlta().getDate());
		} else {
			return "";
		}
	}

}