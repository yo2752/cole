package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PaquetePedidoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PaquetesPedidoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1332031245288668667L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PaquetesPedidoAction.class);

	private PaquetePedidoFilterBean paquetePedidoFilterBean;

	private Long pedido;

	private Long pedidoId;
	private Long pedPaqueteId;
	private Long estado;

	@Resource private ModelPagination modeloPaquetePedidoPaginatedImpl;

	public String cargar(){
		cargarFiltroInicial();
		paquetePedidoFilterBean.setPedidoId(pedido);

		return actualizarPaginatedList();
	}

	@Override
	public String buscar() {
		log.info("Filtrar resultados");
		if (paquetePedidoFilterBean == null) {
			paquetePedidoFilterBean = new PaquetePedidoFilterBean();
		}

		paquetePedidoFilterBean.setPedidoId(pedidoId);
		paquetePedidoFilterBean.setPedPaqueteId(pedPaqueteId);
		paquetePedidoFilterBean.setEstado(estado);

		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPaquetePedidoPaginatedImpl;
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
		if (paquetePedidoFilterBean == null) {
			paquetePedidoFilterBean = new PaquetePedidoFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return paquetePedidoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.paquetePedidoFilterBean = (PaquetePedidoFilterBean) object;
	}

	// Getter & Setter
	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public PaquetePedidoFilterBean getPaquetePedidoFilterBean() {
		return paquetePedidoFilterBean;
	}

	public void setPaquetePedidoFilterBean(PaquetePedidoFilterBean paquetePedidoFilterBean) {
		this.paquetePedidoFilterBean = paquetePedidoFilterBean;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getPedPaqueteId() {
		return pedPaqueteId;
	}

	public void setPedPaqueteId(Long pedPaqueteId) {
		this.pedPaqueteId = pedPaqueteId;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

}