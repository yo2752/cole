package struts2.interceptors;

import java.util.ArrayList;

import oegam.constantes.ConstantesAplicacion;

import org.apache.struts2.ServletActionContext;

import procesos.beans.ProcesoBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import colas.acciones.ControlGestorColasAction;
import colas.modelo.ModeloSolicitud;
import colas.procesos.gestor.GestorColas;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Verifica que los procesos que se hayan levantado tras el inicio de la aplicación
 * sigan arrancados, si se configura en el web.xml el context param VERIFICACION_PROCESOS a 'si'.
 * Si el proceso está parado lo intenta arrancar. Si continua parado llama a generar una notificacion por email.
 * @author GLOBALTMS ESPAÑA
 *
 */

// FIXME: Esta lógica sería mejor situarla en un proceso que se ejecutara cada cierto tiempo,
// y no en un interceptor que se ejecuta cada vez que se realiza una petición al servidor

public class InterceptorVerificacionProcesos implements Interceptor {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(InterceptorVerificacionProcesos.class);

	private static String verificacionProcesos;

	/**
	 * Encapsula la lógica de la verificación de los procesos arrancados desde el último despliegue de la aplicación.
	 */
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		try{
			// Recupera el context param que indica si realizar o no la verificación de procesos:
			if (verificacionProcesos == null) {
				verificacionProcesos = ServletActionContext.getServletContext().
				getInitParameter(ConstantesAplicacion.VERIFICACION_PROCESOS);
			}
			if (verificacionProcesos != null && verificacionProcesos.equalsIgnoreCase("si")) {
				// Recupera la lista de procesos arrancados:
				ArrayList<ProcesoBean> listaProcesosArrancados = (ArrayList<ProcesoBean>) ServletActionContext.
				getServletContext().getAttribute(ConstantesAplicacion.PROCESOS_ARRANCADOS);
				// Si la lista no existe o no tiene elementos return:
				if (listaProcesosArrancados == null || listaProcesosArrancados.isEmpty()) {
					return actionInvocation.invoke();
				}
				// Recupera el gestor colas para ver los que estan actualmente activos:
				GestorColas gestorColas = (GestorColas)ServletActionContext.getServletContext().getAttribute(
						ControlGestorColasAction.GESTOR);
				if (gestorColas == null || gestorColas.listarProcesos() == null) {
					log.error("No se ha recuperado la lista de procesos del GestorColas desde el " + 
					"interceptor: InterceptorVerificacionProcesos. Se ha cancelado la verificacion");
					return actionInvocation.invoke();

				}
				for (int i = 0; i < listaProcesosArrancados.size(); i ++) {
					// Proceso a verificar:
					ProcesoBean procesoVerificar = listaProcesosArrancados.get(i);
					for (int z = 0; z < gestorColas.listarProcesos().length; z ++) {
						// Si está en la lista de procesos verifica su estado:
						if (gestorColas.listarProcesos()[z].getNombre().equals(procesoVerificar.getNombre())) {
							// Debería estar arrancado. ¿Lo está?
							if (gestorColas.listarProcesos()[z].getEstado().equalsIgnoreCase(Claves.ESTADO_PROCESO_ACTIVO)) {
								// Si
								continue;
							} else {
								// No. Lo intenta arrancar.
								gestorColas.arrancarProceso(z);
								// Debería estar arrancado. ¿Lo está?
								if (gestorColas.listarProcesos()[z].getEstado().equalsIgnoreCase(Claves.ESTADO_PROCESO_ACTIVO)){
									// Si
									continue;
								} else {
									// No. El proceso se arrancó, ahora está parado y no se ha podido arrancar. Notificacion por email.
									ModeloSolicitud modeloSolicitud = new ModeloSolicitud();
									modeloSolicitud.notificacionCaidaProceso(gestorColas.listarProcesos()[procesoVerificar.getNumero()]);
								}
							}
						}
					}
				}
				return actionInvocation.invoke();
			} else {
				// No hay que verificar el estado de los procesos arrancados.
				return actionInvocation.invoke();
			}
		} catch(Throwable ex) {
			log.error(ex);
			return actionInvocation.invoke();
		}

	}

	public void destroy() {
	}

	public void init() {
	}

	public static String getVerificacionProcesos() {
		return verificacionProcesos;
	}

	public static void setVerificacionProcesos(String verificacionProcesos) {
		InterceptorVerificacionProcesos.verificacionProcesos = verificacionProcesos;
	}

}