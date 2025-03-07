package org.gestoresmadrid.oegam2.evolucionDatosBancariosFavoritos.controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.view.beans.EvolucionDatosBancariosFavoritosFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionDatosBancariosFavoritosAction extends AbstractPaginatedListAction
		implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -8403573381761801954L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionDatosBancariosFavoritosAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private static final String COLUMDEFECT = "id.fechaCambio";
	private String idDatoBancario;

	private EvolucionDatosBancariosFavoritosFilterBean evolucionDatosBancarios;

	@Resource
	private ModelPagination modeloEvolDatosBancariosFavoritosPaginatedImpl;

	public String consultar() {
		cargarFiltroInicial();
		evolucionDatosBancarios.setIdDatoBancario(Long.parseLong(idDatoBancario));
		return buscar();
	}

	public void cambiarElementosPagina() {
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			actualizarPaginatedList();
			out.print(getLista());
		} catch (IOException e) {
			log.error("Ha sucedido en error a la hora de recargar la lista de evolucion de los datos bancariosk, error: ", e);
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
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

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolDatosBancariosFavoritosPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionDatosBancarios == null) {
			evolucionDatosBancarios = new EvolucionDatosBancariosFavoritosFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionDatosBancarios;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolucionDatosBancarios = (EvolucionDatosBancariosFavoritosFilterBean) object;
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	public String getIdDatoBancario() {
		return idDatoBancario;
	}

	public void setIdDatoBancario(String idDatoBancario) {
		this.idDatoBancario = idDatoBancario;
	}

}