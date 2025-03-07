package org.gestoresmadrid.oegam2.evolucionModelo600601.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.evolucionRemesa.controller.action.EvolucionRemesaAction;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.view.bean.EvolucionModelo600_601FilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionModelo600601Action extends AbstractPaginatedListAction implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = -7584597320842891290L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionRemesaAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private EvolucionModelo600_601FilterBean evolucionModelo;

	private static final String COLUMDEFECT = "id.fechaCambio";

	@Resource
	private ModelPagination modeloEvolucionModelos600601PaginatedImpl;

	private BigDecimal numExpediente;

	public String consultar(){
		cargarFiltroInicial();
		evolucionModelo.setNumExpediente(numExpediente);
		return buscar();
	}

	public void cambiarElementosPagina(){
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = servletResponse.getWriter();
			actualizarPaginatedList();
			out.print(getLista());
		} catch (IOException e) {
			log.error("Ha sucedido en error a la hora de recargar la lista de evolucion de las remesas, error: ", e);
		}
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	};

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionModelos600601PaginatedImpl;
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
		if(evolucionModelo == null){
			evolucionModelo = new EvolucionModelo600_601FilterBean();
		}
		setSort("id.fechaCambio");
		setDir("desc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionModelo;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolucionModelo = (EvolucionModelo600_601FilterBean) object;
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

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}