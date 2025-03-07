package org.gestoresmadrid.oegam2.controller.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Manejador que se ejecuta cuando un usuario intenta acceder a un lugar de la
 * aplicación sin tener los permisos necesarios
 * 
 * @author erds
 * 
 */
public class OegamAccessDeniedHandler implements AccessDeniedHandler {
	private static ILoggerOegam log = LoggerOegam.getLogger(OegamAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("Intento de acceso no autorizado a la ruta " + request.getServletPath(), accessDeniedException);
		response.sendRedirect("errorSecurity.action");
		request.setAttribute("mensajeError", "No tienes permisos para acceder a esta página");
	}

}
