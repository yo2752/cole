package org.gestoresmadrid.oegam2.colas.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioGestionCola;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ResultadoGestionColaBean;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionColaAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 3414690738936816493L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionColaAjaxAction.class);

	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax

	private static final String PAG_CONSULTA = "pagConsultaGestionCola";
	private static final String BEAN_CONSULTA_GESTION_COLA_SESSION = "consultaGestionColaBean";

	private int numPorPage;
	private int page;
	private String sort;
	private String dir;
	private ConsultaGestionColaBean consultaGestionCola;

	private String rol;

	@Autowired
	ServicioGestionCola servicioGestionCola;

	public String recargarPeticionesCola() {
		try {
			consultaGestionCola = (ConsultaGestionColaBean) getSession().get(BEAN_CONSULTA_GESTION_COLA_SESSION);
			ResultadoGestionColaBean resultado = servicioGestionCola.getListaRecargarPeticionesCola(consultaGestionCola, page, sort, dir, numPorPage);
			if (!resultado.getError()) {
				crearListaPaginated(resultado);
			} else {
				consultaGestionCola.getGestionCola().setErrorPetCola(Boolean.TRUE);
				aniadirListaErrores(resultado.getListaMensajes());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recargar la lista con las solicitudes pendientes, error: ", e);
			consultaGestionCola.getGestionCola().setErrorPetCola(Boolean.TRUE);
			addActionError("Ha sucedido un error a la hora de recargar la lista con las solicitudes pendientes.");
		}
		return PAG_CONSULTA;
	}

	public String recargarUltimaEjecucion() {
		try {
			consultaGestionCola = (ConsultaGestionColaBean) getSession().get(BEAN_CONSULTA_GESTION_COLA_SESSION);
			ResultadoGestionColaBean resultado = servicioGestionCola.getListaRecargarUltimasEjecuciones(consultaGestionCola, page, sort, dir, numPorPage);
			if (!resultado.getError()) {
				crearListaPaginated(resultado);
			} else {
				consultaGestionCola.getGestionCola().setErrorUltEjecCola(Boolean.TRUE);
				aniadirListaErrores(resultado.getListaMensajes());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de recargar la lista con las ultimas ejecuciones, error: ", e);
			addActionError("Ha sucedido un error a la hora de recargar la lista con las ultimas ejecuciones.");
			consultaGestionCola.getGestionCola().setErrorUltEjecCola(Boolean.TRUE);
		}
		return PAG_CONSULTA;
	}

	public String navegarUltimaEjec() {
		return recargarUltimaEjecucion();
	}

	public String navegarPeticionesCola() {
		return recargarPeticionesCola();
	}

	private void crearListaPaginated(ResultadoGestionColaBean resultado) {
		if (resultado.getListaSolicitudesCola() != null && !resultado.getListaSolicitudesCola().isEmpty()) {
			consultaGestionCola.getGestionCola().setListaSolicitudesCola(new PaginatedListImpl(numPorPage, page, dir, sort, resultado.getTamListaSolicitudes(), resultado.getListaSolicitudesCola()));
			consultaGestionCola.getGestionCola().setDirSolicitudesCola(dir);
			consultaGestionCola.getGestionCola().setSortSolicitudesCola(sort);
			consultaGestionCola.getGestionCola().setResultadosPorPaginaSolicitudesCola(numPorPage);
		}
		if (resultado.getListaUltimaPeticionCorrecta() != null && !resultado.getListaUltimaPeticionCorrecta().isEmpty()) {
			consultaGestionCola.getGestionCola().setListaUltimaEjecucion(new PaginatedListImpl(numPorPage, page, dir, sort, resultado.getTamListaUltEjecuciones(), resultado
					.getListaUltimaPeticionCorrecta()));
			consultaGestionCola.getGestionCola().setDirUltimaEjecuion(dir);
			consultaGestionCola.getGestionCola().setSortUltimaEjecuion(sort);
			consultaGestionCola.getGestionCola().setResultadosPorPaginaUltimaEjecucion(numPorPage);
		}
		consultaGestionCola.getGestionCola().setExisteBusqueda(Boolean.TRUE);
		getSession().put(BEAN_CONSULTA_GESTION_COLA_SESSION, consultaGestionCola);
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getNumPorPage() {
		return numPorPage;
	}

	public void setNumPorPage(int numPorPage) {
		this.numPorPage = numPorPage;
	}

	public ConsultaGestionColaBean getConsultaGestionCola() {
		return consultaGestionCola;
	}

	public void setConsultaGestionCola(ConsultaGestionColaBean consultaGestionCola) {
		this.consultaGestionCola = consultaGestionCola;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
