package listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gestoresmadrid.oegam2comun.properties.view.dto.PropertiesDto;
import org.gestoresmadrid.oegam2comun.propiedades.model.service.ServicioPropiedades;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.utiles.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PropertiesListener implements ServletContextListener {

	private static final ILoggerOegam log = LoggerOegam.getLogger(PropertiesListener.class);

	@Autowired
	ServicioPropiedades servicioPropiedades;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			gestorPropiedades.eliminarProperties();
			arg0.getServletContext().removeAttribute(Constantes.PROPIEDADES);
		} catch (Throwable t) {
			log.error("Error al destruir el contexto.");
			log.error(t);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		List<PropertiesDto> listaPropertiesBBDD = servicioPropiedades.getlistaPropiedades();
		gestorPropiedades.iniciarProperties();
		if (listaPropertiesBBDD != null && !listaPropertiesBBDD.isEmpty()) {
			for (PropertiesDto propertiesDto : listaPropertiesBBDD) {
				gestorPropiedades.cargarPropertie(propertiesDto.getNombre(), propertiesDto.getValor());
			}
			gestorPropiedades.cargarPreferentes();
			gestorPropiedades.cargarAlmacenesTrafico();
		} else {
			log.error("No se ha podido obtener la lista con las properties de BBDD o no existen.");
		}
	}
}
