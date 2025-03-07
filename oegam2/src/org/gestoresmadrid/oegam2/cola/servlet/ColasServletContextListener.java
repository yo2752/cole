package org.gestoresmadrid.oegam2.cola.servlet;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.poi.ss.formula.functions.T;
import org.gestoresmadrid.oegam2comun.cola.model.service.impl.GestorColas;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ColasServletContextListener implements ServletContextListener {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ColasServletContextListener.class);

	@Autowired
	ServicioProcesos servicioProceso;

	@Autowired
	GestorColas gestorColas;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Fin del gestor de procesos");
		try {
			gestorColas.parar();
		} catch (Exception ex) {
			log.error("Se han encontrado problemas al detener los procesos.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		if ("PROCESO".equals(gestorPropiedades.valorPropertie("entorno"))) {
			List<ProcesoDto> listaProcesosBBDD = servicioProceso.getListadoProcesos();
			gestorColas.iniciarGestorColas();
			log.info("Creamos el gestor de procesos");
			if (listaProcesosBBDD != null && !listaProcesosBBDD.isEmpty()) {
				for (ProcesoDto proceso : listaProcesosBBDD) {
					try {
						proceso.setClasse((Class<T>) Class.forName(proceso.getClase()));
						gestorColas.nuevoProceso(proceso);
					} catch (SchedulerException e) {
						log.error("Error: " + e.getMessage());
					} catch (Exception e) {
						log.error("Error a la hora de convertir ProcesoVO to ProcesoDto para el proceso: " + proceso.getProceso() + ", error: ", e);
					}
				}
			}
		}
	}

}