package org.gestoresmadrid.oegam2.controller.security;

import trafico.utiles.constantes.Constantes;

import com.opensymphony.xwork2.ActionSupport;


public class SecurityAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5573905842584366495L;

	private static final String MESSAGE_ERROR_EXPIRED_SESSION = "No ha iniciado sesión de trabajo o ha expirado. Reinicie para iniciar una nueva.";
	private static final String MESSAGE_ERROR_CONCURRENT_SESSION = "Esta sesión ha expirado (posiblemente debido a un intento de registro en la aplicación del mismo usuario). Reinicie para iniciar una nueva";
	private static final String MESSAGE_ERROR_SECURITY = "Ha ocurrido un error referente a los permisos de usuario.";

	private String mensajeError;

	public String excededAllowedSession() {
		mensajeError = MESSAGE_ERROR_CONCURRENT_SESSION;
		return Constantes.SESION_EXPIRADA;
	}

	public String expiredSession() {
		mensajeError = MESSAGE_ERROR_EXPIRED_SESSION;
		return Constantes.SESION_EXPIRADA;
	}

	public String error(){
		if (mensajeError == null || mensajeError.isEmpty()) {
			mensajeError = MESSAGE_ERROR_SECURITY;
		}
		return ERROR;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
