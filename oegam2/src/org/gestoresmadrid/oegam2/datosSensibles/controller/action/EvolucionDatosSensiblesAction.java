package org.gestoresmadrid.oegam2.datosSensibles.controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.EvolucionDatosSensiblesBean;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionDatosSensiblesAction extends ActionBase implements SessionAware {

	private static final long serialVersionUID = -7584597320842891290L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionDatosSensiblesAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	@Autowired
	private ServicioDatosSensibles servicioDatosSensibles;

	private EvolucionDatosSensiblesBean evolucionDatos;

	private String id;

	private List<EvolucionDatosSensiblesBean> lista;

	public String consultar() {
		if (id != null) {
			String[] ids = id.split(",");
			setLista(servicioDatosSensibles.getEvolucionDatosSensibles(ids[0], ids[1], ids[2]));
		}
		return SUCCESS;
	}

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

	/**
	 * @return the evolucionDatos
	 */
	public EvolucionDatosSensiblesBean getEvolucionDatos() {
		return evolucionDatos;
	}

	/**
	 * @param evolucionDatos the evolucionDatos to set
	 */
	public void setEvolucionDatos(EvolucionDatosSensiblesBean evolucionDatos) {
		this.evolucionDatos = evolucionDatos;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the servicioDatosSensibles
	 */
	public ServicioDatosSensibles getServicioDatosSensibles() {
		return servicioDatosSensibles;
	}

	/**
	 * @param servicioDatosSensibles the servicioDatosSensibles to set
	 */
	public void setServicioDatosSensibles(ServicioDatosSensibles servicioDatosSensibles) {
		this.servicioDatosSensibles = servicioDatosSensibles;
	}

	/**
	 * @return the lista
	 */
	public List<EvolucionDatosSensiblesBean> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<EvolucionDatosSensiblesBean> lista) {
		this.lista = lista;
	}

}