package org.gestoresmadrid.oegam2.evolucionRemesa.controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionRemesa.view.bean.EvolucionRemesaFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionRemesaAction extends AbstractPaginatedListAction implements ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 7270308135706524550L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionRemesaAction.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private EvolucionRemesaFilterBean evolucionRemesa;

	private static final String COLUMDEFECT = "id.fechaCambio";

	@Resource
	private ModelPagination modeloEvolucionRemesaPaginatedImpl;

	private BigDecimal numExpediente;

	public String consultar(){
		cargarFiltroInicial();
		evolucionRemesa.setNumExpediente(numExpediente);
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
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionRemesaPaginatedImpl;
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
		if(evolucionRemesa == null){
			evolucionRemesa = new EvolucionRemesaFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("desc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionRemesa;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolucionRemesa = (EvolucionRemesaFilterBean) object;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

}