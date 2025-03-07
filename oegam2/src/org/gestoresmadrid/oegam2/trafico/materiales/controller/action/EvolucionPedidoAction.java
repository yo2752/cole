package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionPedidoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionPedidoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1332031245288668667L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionPedidoAction.class);

	private Long pedido;
	private EvolucionPedidoFilterBean evolucionPedidoFilterBean;

	@Resource ModelPagination modeloEvolucionPedidoPaginatedImpl;

	public String cargar(){
		cargarFiltroInicial();
		evolucionPedidoFilterBean.setPedidoId(pedido);
		String pagina = actualizarPaginatedList();
		return pagina;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionPedidoPaginatedImpl;
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
		if (evolucionPedidoFilterBean == null) {
			evolucionPedidoFilterBean = new EvolucionPedidoFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionPedidoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionPedidoFilterBean = (EvolucionPedidoFilterBean) object;
	}

	// Getters & Setters

	public EvolucionPedidoFilterBean getEvolucionPedidoFilterBean() {
		return evolucionPedidoFilterBean;
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public void setEvolucionPedidoFilterBean(EvolucionPedidoFilterBean evolucionPedidoFilterBean) {
		this.evolucionPedidoFilterBean = evolucionPedidoFilterBean;
	}

}