package org.gestoresmadrid.oegam2.vehiculo.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class VehiculoAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 4700623452152283687L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(VehiculoAjaxAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
}