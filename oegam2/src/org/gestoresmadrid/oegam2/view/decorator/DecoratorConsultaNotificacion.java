package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorConsultaNotificacion extends TableDecorator {

	private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorConsultaNotificacion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaFinDisponibilidad() {
		NotificacionSSVO notificacion = (NotificacionSSVO) getCurrentRowObject();
		return utilesFecha.formatoFecha(FORMATO_FECHA, notificacion.getFechaFinDisponibilidad());
	}

	public String getFechaPuestaDisposicion() {
		NotificacionSSVO notificacion = (NotificacionSSVO) getCurrentRowObject();
		return utilesFecha.formatoFecha(FORMATO_FECHA, notificacion.getFechaPuestaDisposicion());
	}

}
