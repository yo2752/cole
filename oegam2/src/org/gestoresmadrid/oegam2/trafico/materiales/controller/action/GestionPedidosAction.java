package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PedidosFilterBean;

import escrituras.utiles.UtilesVista;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionPedidosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -435338881210119649L;

	private static final String[] fetchList = { "jefaturaProvincial" };

	private PedidosFilterBean pedidosFilterBean;
	private String msg;
	private String typeMsg;

	private Boolean hasMsg;
	private List<String> errores;

	@Resource ModelPagination modeloGestionPedidosPaginatedImpl;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionPedidosAction.class);

	@Override
	public String inicio() {
		this.setHasMsg(new Boolean(false));
		if (msg != null && !msg.isEmpty()){
			this.setHasMsg(new Boolean(true));
			if ("ERROR".equals(typeMsg)) {
				this.setErrores(getMsgErrors(msg));
			}
		}

		if (pedidosFilterBean == null) {
			pedidosFilterBean = new PedidosFilterBean();
		}

		return super.buscar();
	}

	@Override
	public String buscar() {
		this.setHasMsg(new Boolean(false));

		if (pedidosFilterBean == null) {
			pedidosFilterBean = new PedidosFilterBean();
		}

		return super.buscar();
	}

	private List<String> getMsgErrors(String msg) {
		String[] parts = msg.split(";");
		List<String> errors = new ArrayList<>();

		for (int i = 0; i < parts.length; i++) {
			errors.add(parts[i]);
		}

		return errors;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionPedidosPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String getDecorator() {
		//return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTableGestionPedidos";
		return null;
	}

	@Override
	protected void cargaRestricciones() {
		// Intencionadamente vacío
	}

	@Override
	protected void cargarFiltroInicial() {
		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS);
		if (pedidosFilterBean == null) {
			pedidosFilterBean = new PedidosFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return pedidosFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.pedidosFilterBean = (PedidosFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	// Setters & getters
	public PedidosFilterBean getPedidosFilterBean() {
		return pedidosFilterBean;
	}

	public void setPedidosFilterBean(PedidosFilterBean pedidosFilterBean) {
		this.pedidosFilterBean = pedidosFilterBean;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getHasMsg() {
		return hasMsg;
	}

	public void setHasMsg(Boolean hasMsg) {
		this.hasMsg = hasMsg;
	}

	public String getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(String typeMsg) {
		this.typeMsg = typeMsg;
	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

}