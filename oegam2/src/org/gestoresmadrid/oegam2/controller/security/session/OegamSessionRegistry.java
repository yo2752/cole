package org.gestoresmadrid.oegam2.controller.security.session;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

import oegam.constantes.ConstantesSession;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class OegamSessionRegistry implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {

    protected final ILoggerOegam logger = LoggerOegam.getLogger(OegamSessionRegistry.class);

    /** <principal:Object,SessionIdSet> */
    private final Map<Object,Set<String>> principals = Collections.synchronizedMap(new HashMap<Object,Set<String>>());
    /** <sessionId:Object,SessionInformation> */
    private final Map<String, SessionInformation> sessionIds = Collections.synchronizedMap(new HashMap<String, SessionInformation>());
    
    public List<Object> getAllPrincipals() {
        return Arrays.asList(principals.keySet().toArray());
    }

    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        final Set<String> sessionsUsedByPrincipal = getPrincipal(principal);

        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        }

        List<SessionInformation> list = new ArrayList<SessionInformation>(sessionsUsedByPrincipal.size());

        synchronized (sessionsUsedByPrincipal) {
            for (String sessionId : sessionsUsedByPrincipal) {
                SessionInformation sessionInformation = getSessionInformation(sessionId);

                if (sessionInformation == null) {
                    continue;
                }

                if (includeExpiredSessions || !sessionInformation.isExpired()) {
                    list.add(sessionInformation);
                }
            }
        }

        return list;
    }

    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");

        return (SessionInformation) getSessionId(sessionId);
    }

    public void onApplicationEvent(SessionDestroyedEvent event) {
		String sessionExpirada = (String) ((HttpSession) event.getSource()).getAttribute("validada");
		String NIFViafirma = (String) ((HttpSession) event.getSource()).getAttribute("NIF_Viafirma");
		String sessionId = event.getId();
		String IP = "";
		if (ServletActionContext.getContext() != null) { 
			IP = ServletActionContext.getRequest().getRemoteAddr();
			if(ServletActionContext.getRequest().getHeader(ConstantesSession.CLIENTE_IP) != null){
				IP = ServletActionContext.getRequest().getHeader(ConstantesSession.CLIENTE_IP);
			}
		} else {
			IP = (String) ((HttpSession) event.getSource()).getAttribute(ConstantesSession.CLIENTE_IP);
		}
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		String ipAccesso = address.getHostAddress();
		StringBuilder logEvidencia;
		if (sessionExpirada.equalsIgnoreCase("FALSE")) {

			logEvidencia = new StringBuilder("El usuario ").append(NIFViafirma).append(" ha cerrado la sesion ")
					.append(sessionId).append(" en el frontal ").append(ipAccesso).append(" desde la maquina ")
					.append(IP).append(".");

		} else {
			logEvidencia = new StringBuilder("Al usuario ").append(NIFViafirma).append(" se le ha expirado la sesion ")
					.append(sessionId).append(" en el frontal ").append(ipAccesso).append(" desde la maquina ")
					.append(IP).append(".");

		}
		logger.error(logEvidencia.toString());

		removeSessionInformation(sessionId);
	}

    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");

        SessionInformation info = getSessionInformation(sessionId);

        if (info != null) {
            info.refreshLastRequest();
        }
    }

    public synchronized void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");

        if (logger.isDebugEnabled()) {
            logger.debug("Registering session " + sessionId +", for principal " + principal);
        }

        if (getSessionInformation(sessionId) != null) {
            removeSessionInformation(sessionId);
        }

        putSessionId(sessionId, new SessionInformation(principal, sessionId, new Date()));

        Set<String> sessionsUsedByPrincipal = getPrincipal(principal);

        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet<String>(4));
            putPrincipal(principal, sessionsUsedByPrincipal);
        }

        sessionsUsedByPrincipal.add(sessionId);

        if (logger.isTraceEnabled()) {
            logger.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
        }
    }

    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");

        SessionInformation info = getSessionInformation(sessionId);

        if (info == null) {
            return;
        }

        if (logger.isTraceEnabled()) {
            logger.debug("Removing session " + sessionId + " from set of registered sessions");
        }

        removeSessionId(sessionId);

        Set<String> sessionsUsedByPrincipal = getPrincipal(info.getPrincipal());

        if (sessionsUsedByPrincipal == null) {
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Removing session " + sessionId + " from principal's set of registered sessions");
        }

        synchronized (sessionsUsedByPrincipal) {
            sessionsUsedByPrincipal.remove(sessionId);

            if (sessionsUsedByPrincipal.size() == 0) {
                // No need to keep object in principals Map anymore
                if (logger.isDebugEnabled()) {
                    logger.debug("Removing principal " + info.getPrincipal() + " from registry");
                }
                removePrincipal(info.getPrincipal());
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
        }
    }

    private Set<String> getPrincipal(Object principal){
    	return principals.get(principal);
    }

    private void putPrincipal(Object principal, Set<String> sessionsUsedByPrincipal){
        principals.put(principal, sessionsUsedByPrincipal);
    }

    private void removePrincipal(Object principal) {
    	principals.remove(principal);
    }

    private SessionInformation getSessionId(String sessionId) {
    	return sessionIds.get(sessionId);
    }

    private void putSessionId(String sessionId, Object principal) {
        sessionIds.put(sessionId, new SessionInformation(principal, sessionId, new Date()));
    }

    private void removeSessionId(String sessionId) {
    	sessionIds.remove(sessionId);
    }

}
