package administracion.acciones;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.gestoresmadrid.oegam2.controller.security.authentication.OegamAuthenticationToken;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class SessionCounterListener implements HttpSessionListener {

	private static int totalActiveSessions;

	private static final ILoggerOegam log = LoggerOegam.getLogger(SessionCounterListener.class);

	@Autowired
	private ServicioSesion servicioSesion;

	public static int getTotalActiveSession() {
		return totalActiveSessions;
	}

	public void saveSession(String ip, String numColegiado, String idSession, BigDecimal id_user, String apellidosNombre) {
		totalActiveSessions++;
		log.debug("sessionCreated");
		String ipFrontal = "";
		try {
			int num = InetAddress.getLocalHost().toString().indexOf('/');
			if (num > 0) {
				ipFrontal = InetAddress.getLocalHost().toString().substring(num + 1);
			}
		} catch (UnknownHostException e1) {
			log.error("Error UnknownHostException: ", e1);
		}
		try {
			if (servicioSesion == null) {
				SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			}
			servicioSesion.insert(idSession, numColegiado, ip, ipFrontal, id_user, apellidosNombre);
		} catch (Exception e) {
			log.info("Al Trata el DAO: " + e);
			System.err.println("Al Trata el DAO: " + e);
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		totalActiveSessions++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		totalActiveSessions--;
		if (arg0.getSession().getAttributeNames().hasMoreElements()) {

			String at1 = arg0.getSession().getAttributeNames().nextElement().toString();
			/**
			 * Cuando se le da al botón de Reiniciar Sesión pasa por aquí cuando previamente ya había pasado, por lo que lo controlamos
			 */
			if (!Claves.CLAVE_Mensaje_Error.equals(at1)) {
				String idSession = arg0.getSession().getId();
				try {
					if (servicioSesion == null) {
						SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
					}
					BigDecimal idUsuario = null;
					Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
					boolean cambioContrato = false;
					if (authentication != null) {
						idUsuario = (BigDecimal) authentication.getPrincipal();
						if (authentication instanceof OegamAuthenticationToken) {
							cambioContrato = ((OegamAuthenticationToken) authentication).isCambioContrato();
						}
					}
					servicioSesion.cerrarSesion(idSession, idUsuario, cambioContrato);
				} catch (Exception e) {
					log.info("Al Trata el DAO: " + e);
					System.err.println("Al Trata el DAO: " + e);
				}
			}
		}
	}

}