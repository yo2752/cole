package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorRespuestaSeguridadSocial extends TableDecorator {

	private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorRespuestaSeguridadSocial() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaNotificacion() {
		RespuestaSSVO notificacion = (RespuestaSSVO) getCurrentRowObject();
		return utilesFecha.formatoFecha(FORMATO_FECHA, notificacion.getFechaNotificacion());
	}

}