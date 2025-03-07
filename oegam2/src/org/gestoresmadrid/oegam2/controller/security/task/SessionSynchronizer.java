package org.gestoresmadrid.oegam2.controller.security.task;


import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.oegam2.controller.security.authentication.OegamAuthenticationToken;
import org.gestoresmadrid.oegam2.controller.security.session.OegamSessionControlStrategy;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


public class SessionSynchronizer {
	private static final ILoggerOegam log = LoggerOegam.getLogger(SessionSynchronizer.class);
	private static Integer frontal;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private ServicioSesion servicioSesion;

	@Autowired
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;

	/**
	 * Metodo que se ejecuta periodicamente (se configura en applicationContext-security.xml)
	 * y comprueba si debe caducar alguna session por concurrencia de sesiones activas
	 * 
	 */
	public void run() {
		//log.info("Inicio de SessionSynchronizer");
		doCheckCurrentSessions();
		//log.info("Fin de SessionSynchronizer");
	}

	/**
	 * Tarea que comprueba las sesiones activas en BBDD para comprobar si debe expirar algunas
	 */
	private void doCheckCurrentSessions() {
		if (sessionRegistry.getAllPrincipals() != null && !sessionRegistry.getAllPrincipals().isEmpty()) {
			// Recuperar la lista de sesiones activas en base de datos
			Map<Object, List<UsuarioLoginVO>> usuariosActivos = getUsuariosActivosEnPlataforma();
			try {
				for (Object principal : usuariosActivos.keySet()) {
					// Comprobar si el numero de sesiones activas a superado el maximo
					checkSessionsExceeded(principal, usuariosActivos.get(principal));
					checkSessionsOlder(usuariosActivos.get(principal));
				}
			} catch (UnknownHostException e) {
				log.error("No se ha podido identificar el host/frontal", e);
			}
		}
	}

	/**
	 * Comprueba si el numero de sesiones activas para el usuario, supera el maximo permitido,
	 * y caduca las mas antiguas si estan en el frontal en el que se ejecuta el hilo
	 * 
	 * @param principal identificador de usuario
	 * @param usuariosActivos Lista con los usuarios activos en bbdd
	 * @throws UnknownHostException 
	 */
	private void checkSessionsExceeded(Object principal, List<UsuarioLoginVO> usuariosActivos) throws UnknownHostException {
		int maxCurrentSession = getMaxCurrentSession(principal);
		if (maxCurrentSession > 0 && maxCurrentSession < usuariosActivos.size()) {
			log.info("El usuario con identificador " + principal + " ha superado el numero maximo de sesiones activas concurrentes ("
					+ usuariosActivos.size() + ") que tiene permitidas (" + maxCurrentSession + "), se van a expirar las que se encuentren en este frontal");

			// Como se recupera el listado ordenado por fechaLogin, se caducan las mas antiguas
			for (int i = 0; i < usuariosActivos.size() - maxCurrentSession; i++) {
				UsuarioLoginVO sessionCandidate = usuariosActivos.get(i);
				if (getFrontal() == sessionCandidate.getFrontal()) {
					// Si es propia, se expira
					expireSession(principal, sessionCandidate);
				}
			}
		}
	}

	/**
	 * Comprueba si existen sesiones en la BBDD sin fecha fin y que no
	 * correspondan a ninguna sesion viva en el sistema
	 * 
	 * @param usuariosActivos
	 *            Lista con los usuarios activos en bbdd
	 * @throws UnknownHostException
	 */
	private void checkSessionsOlder(List<UsuarioLoginVO> usuariosActivos) throws UnknownHostException {
		for (UsuarioLoginVO usuarioLogin: usuariosActivos) {
			// Comprueba si es una sesión que corresponde a este frontal y realmente se mantiene viva
			if (getFrontal() == usuarioLogin.getFrontal() && sessionRegistry.getSessionInformation(usuarioLogin.getIdSesion()) == null ) {
				log.info("El usuario con identificador " + usuarioLogin.getIdUsuario() + " no se encuentra activo en este frontal, se pone fecha fin en la BBDD");
				try {
					BigDecimal idUsuario = null;
					Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
					boolean cambioContrato = false;
					if (authentication != null) {
						idUsuario = (BigDecimal) authentication.getPrincipal();
						if (authentication instanceof OegamAuthenticationToken) {
							cambioContrato = ((OegamAuthenticationToken) authentication).isCambioContrato();
						}
					}
					servicioSesion.cerrarSesion(usuarioLogin.getIdSesion(), idUsuario, cambioContrato);
				} catch (Exception e) {
					log.error("No se pudo poner fechaFin a la session almacenada en BBDD", e);
				}
			}
		}
	}

	/** 
	 * Expira la session en el contexto de spring y actualiza la bbdd
	 * 
	 * @param principal
	 * @param usuarioLogin
	 */
	private void expireSession(Object principal, UsuarioLoginVO usuarioLogin) {
		log.info("Se expira la session " + usuarioLogin.getIdSesion() + " del usuario [idUsuario=" + principal + "] por concurrencia de sesiones");
		List<SessionInformation> ownSessions = sessionRegistry.getAllSessions(principal, false);
		for (SessionInformation session: ownSessions) {
			if (session.getSessionId().equals(usuarioLogin.getIdSesion())) {
				session.expireNow();
				try {
					BigDecimal idUsuario = null;
					Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
					boolean cambioContrato = false;
					if (authentication != null) {
						idUsuario = (BigDecimal) authentication.getPrincipal();
						if (authentication instanceof OegamAuthenticationToken) {
							cambioContrato = ((OegamAuthenticationToken) authentication).isCambioContrato();
						}
					}
					servicioSesion.cerrarSesion(usuarioLogin.getIdSesion(), idUsuario, cambioContrato);
				} catch (Exception e) {
					log.error("No se pudo poner fechaFin a la session almacenada en BBDD", e);
				}
				log.info("Session " + usuarioLogin.getIdSesion() + " expirada");
				break;
			}
		}
	}

	/**
	 * Devuelve el numero máximos de sesiones activas para esta autorizacion
	 * 
	 * @param principal
	 * @return
	 */
	private int getMaxCurrentSession(Object principal) {
		int result = -1;
		if (sessionAuthenticationStrategy instanceof OegamSessionControlStrategy) {
			result = ((OegamSessionControlStrategy) sessionAuthenticationStrategy).getMaximumSessions(principal);
		}
		return result;
	}

	/**
	 * Llama al servicio para recuperar el listado de usuarios de BBDD que estan logados en el
	 * sessionRegistry propio y lo ordena por fechaLogin
	 * 
	 * @param principals
	 * @return
	 */
	private Map<Object, List<UsuarioLoginVO>> getUsuariosActivosEnPlataforma() {
		Map<Object, List<UsuarioLoginVO>> result = new HashMap<Object, List<UsuarioLoginVO>>();


		// Recupero del modelo la lista de sesiones activas de los IDs pasados en base de datos y ¡¡¡ordenado por fechaLogin!!!
		List<UsuarioLoginVO> sessionsBBDD = servicioSesion.buscaUsuariosActivosLogin(sessionRegistry.getAllPrincipals());

		// Agrupar usuarios activos por su id
		for(UsuarioLoginVO usuario: sessionsBBDD){
			if (!result.containsKey(usuario.getIdUsuario())) {
				result.put(usuario.getIdUsuario(), new ArrayList<UsuarioLoginVO>());
			}
			result.get(usuario.getIdUsuario()).add(usuario);
		}

		return result;
	}

	/**
	 * Recupera el nombre del frontal
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	private static Integer getFrontal() throws UnknownHostException {
		if (frontal == null) {
	  		String address = InetAddress.getLocalHost().getHostAddress();
			address = address.substring(address.lastIndexOf(".")+1);
			address = "6".equals(address)?"16":address;
	  		frontal = Integer.valueOf(address);	
	  		log.info("Hilo configurado con nombre de frontal: " + frontal);
		}
		return frontal;
	}

}
