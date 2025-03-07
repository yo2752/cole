package org.gestoresmadrid.oegam2.controller.security.session;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gestoresmadrid.oegam2.controller.security.authentication.AuthenticationConstants;
import org.gestoresmadrid.oegam2.controller.security.authentication.OegamAuthenticationToken;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class OegamSessionControlStrategy implements
		SessionAuthenticationStrategy, MessageSourceAware {
	private static final ILoggerOegam log = LoggerOegam.getLogger(OegamSessionControlStrategy.class);
    /** <principal:Object,MaxConcurrentAllowedSession> */
    private final Map<Object,Integer> mapAllowedSessions = Collections.synchronizedMap(new HashMap<Object,Integer>());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private final SessionRegistry sessionRegistry;

	@Autowired
	private ServicioSesion servicioSesion;

	private boolean exceptionIfMaximumExceeded = false;
	private int maximumSessions = 1;

	/**
	 * @param sessionRegistry
	 *            the session registry which should be updated when the
	 *            authenticated session is changed.
	 */
	public OegamSessionControlStrategy(SessionRegistry sessionRegistry) {
		Assert.notNull(sessionRegistry, "The sessionRegistry cannot be null");
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		checkAuthenticationAllowed(authentication, request);
		sessionRegistry.registerNewSession(request.getSession(true).getId(), authentication.getPrincipal());
	}

	private void checkAuthenticationAllowed(Authentication authentication,
			HttpServletRequest request) throws AuthenticationException {

		final List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);

		int sessionCount = sessions.size();
		int allowedSessions = getMaximumSessionsForThisUser(authentication);

		// AllowedSessions para recuperarlo en el sessionSynchronizer y ver si tengo que caducar algunas
		mapAllowedSessions.put(authentication.getPrincipal(), allowedSessions);

		if (sessionCount < allowedSessions) {
			// They haven't got too many login sessions running at present
			return;
		}

		if (allowedSessions == -1) {
			// We permit unlimited logins
			return;
		}

		if (sessionCount == allowedSessions) {
			HttpSession session = request.getSession(false);

			if (session != null) {
				// Only permit it though if this request is associated with one
				// of the already registered sessions
				for (SessionInformation si : sessions) {
					if (si.getSessionId().equals(session.getId())) {
						return;
					}
				}
			}
			// If the session is null, a new one will be created by the parent
			// class, exceeding the allowed number
		}

		allowableSessionsExceeded(sessions, allowedSessions, sessionRegistry);
	}

	/**
	 * Method intended for use by subclasses to override the maximum number of
	 * sessions that are permitted for a particular authentication. The default
	 * implementation simply returns the <code>maximumSessions</code> value for
	 * the bean.
	 * 
	 * @param authentication
	 *            to determine the maximum sessions for
	 * 
	 * @return either -1 meaning unlimited, or a positive integer to limit (never zero)
	 */
	private int getMaximumSessionsForThisUser(Authentication authentication) {
		int maximumSessionsForThisUser = this.maximumSessions;
		Iterator<GrantedAuthority> it = authentication.getAuthorities().iterator();
		while (it.hasNext()) {
			String auth = it.next().getAuthority();
			if (AuthenticationConstants.ROLE_MULTI_SESSIONS.equals(auth)) {
				return -1;
			} else if (auth != null && auth.startsWith(AuthenticationConstants.ROLE_SESSIONS_EXCEEDED)) {
				try {
					int a = Integer.parseInt(auth.substring(AuthenticationConstants.ROLE_SESSIONS_EXCEEDED.length()));
					maximumSessionsForThisUser = a > maximumSessionsForThisUser ? a : maximumSessionsForThisUser;
				} catch (NumberFormatException nfe) {
					log.error("El usuario " + authentication.getPrincipal()
							+ " tiene mal configurado el permiso "
							+ AuthenticationConstants.ROLE_SESSIONS_EXCEEDED);
				}
			}
		}
		return maximumSessionsForThisUser;
	}

	/**
	 * Allows subclasses to customise behaviour when too many sessions are detected.
	 * 
	 * @param sessions
	 *            either <code>null</code> or all unexpired sessions associated with the principal
	 * @param allowableSessions
	 *            the number of concurrent sessions the user is allowed to have
	 * @param registry
	 *            an instance of the <code>SessionRegistry</code> for subclass use
	 * 
	 */
	private void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
		if (exceptionIfMaximumExceeded || (sessions == null)) {
			throw new SessionAuthenticationException(messages.getMessage("ConcurrentSessionControllerImpl.exceededAllowed",
					new Object[] { new Integer(allowableSessions) }, "Maximum sessions of {0} for this principal exceeded"));
		}

		// Determine least recently used session, and mark it for invalidation
		SessionInformation leastRecentlyUsed = null;

		for (int i = 0; i < sessions.size(); i++) {
			if ((leastRecentlyUsed == null) || sessions.get(i).getLastRequest().before(leastRecentlyUsed.getLastRequest())) {
				leastRecentlyUsed = sessions.get(i);
			}
		}
		leastRecentlyUsed.expireNow();

		try {
			BigDecimal idUsuario = null;
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				idUsuario = (BigDecimal) authentication.getPrincipal();
				
				log.error("El usuario " + idUsuario + " ha excedido el numero maximo de sesiones simultaneas");
			}
			boolean cambioContrato = false;
			if (authentication != null && authentication instanceof OegamAuthenticationToken) {
				cambioContrato = ((OegamAuthenticationToken) authentication).isCambioContrato();
			}
			servicioSesion.cerrarSesion(leastRecentlyUsed.getSessionId(), idUsuario, cambioContrato);
		} catch (Exception e) {
			log.error("No se pudo poner fechaFin a la session almacenada en BBDD", e);
		}
	}

	/**
	 * Sets the <tt>exceptionIfMaximumExceeded</tt> property, which determines
	 * whether the user should be prevented from opening more sessions than
	 * allowed. If set to <tt>true</tt>, a
	 * <tt>SessionAuthenticationException</tt> will be raised.
	 * 
	 * @param exceptionIfMaximumExceeded
	 *            defaults to <tt>false</tt>.
	 */
	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	/**
	 * Sets the <tt>maxSessions</tt> property. The default value is 1. Use -1
	 * for unlimited sessions.
	 * 
	 * @param maximumSessions
	 *            the maximimum number of permitted sessions a user can have
	 *            open simultaneously.
	 */
	public void setMaximumSessions(int maximumSessions) {
		Assert.isTrue(maximumSessions != 0,
				"MaximumLogins must be either -1 to allow unlimited logins, or a positive integer to specify a maximum");
		this.maximumSessions = maximumSessions;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public int getMaximumSessions() {
		return maximumSessions;
	}

	public int getMaximumSessions(Object principal) {
		Integer result = mapAllowedSessions.get(principal);
		return result != null ? result : getMaximumSessions();
	}

}
