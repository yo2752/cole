package colas.servlet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import procesos.beans.ProcesoBean;
import procesos.modelo.ModeloProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import colas.procesos.gestor.GestorColas;


/**
 *   Listener de inicialización y terminación de la aplicación
 */
public class ColasServletContextListener implements ServletContextListener {

	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ColasServletContextListener.class);
	private ModeloProcesos modeloProcesos;

	@Autowired
	private ServicioSesion  servicioSesion;
	
	@Override
	/**
	 * Guardamos el estado actual de los procesos monitorizados para que la próxima vez que se levante la 
	 * aplicación vuelva a la misma situación. Posteriormente se paran los procesos para evitar problemas de
	 * sincronización
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Fin del gestor de procesos");
		GestorColas gestor = (GestorColas)arg0.getServletContext().getAttribute("Gestor");
		if (gestor != null) {
			try {
//				grabarConfiguracion(gestor.listarProcesos());
//				ModeloProcesos.grabarConfiguracionEnBBDD(gestor.listarProcesos()); 
				gestor.parar();
			} catch (Exception ex) {
				log.error("Se han encontrado problemas al detener los procesos.");
			}
		}

	}

	@Override
	/**
	 * Creamos el gestor de procesos. Leemos la configuración del fichero procesos.xml y 
	 * damos de alta los procesos obtenidos.
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		// Borrado de registros inutiles en la tabla de usuarios conectados. Modulo estadisticas.
		String ipFrontal = "";
		try {
			int num = InetAddress.getLocalHost().toString().indexOf('/');
			if (num > 0) {
				ipFrontal = InetAddress.getLocalHost().toString().substring(num + 1);
			}
		} catch (UnknownHostException e1) {
			log.error("Error al inicializar el contexto. Host Desconocido: ", e1);
		}

		servicioSesion.updateTablaUsuariosSinSession(ipFrontal);

		// Prueba: Obtiene la lista de procesos desde la base de datos:
		List<ProcesoBean> lista = getModeloProcesos().obtenerConfiguracionDesdeBD();

		GestorColas gestor = (GestorColas) arg0.getServletContext().getAttribute("Gestor");

		log.info("Creamos el gestor de procesos");
		if (lista != null && !lista.isEmpty() && gestor == null) {
			gestor = new GestorColas();
			arg0.getServletContext().setAttribute("Gestor", gestor);
		}

		for (ProcesoBean proceso : lista) {
			try {
				gestor.nuevoProceso(proceso);
			} catch (SchedulerException e) {
				log.error("Error: " + e.getMessage());
			}
		}

	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloProcesos getModeloProcesos() {
		if (modeloProcesos == null) {
			modeloProcesos = new ModeloProcesos();
		}
		return modeloProcesos;
	}

	public void setModeloProcesos(ModeloProcesos modeloProcesos) {
		this.modeloProcesos = modeloProcesos;
	}

}
