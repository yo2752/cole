package org.gestoresmadrid.oegam2.mensajes.proceso.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.view.beans.ConsultaMensajesProcesoBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaMensajesWebServiceAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -4367451111330483058L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaMensajesWebServiceAction.class);

	@Resource
	private ModelPagination modeloMensajesProcesosPaginated;

	private ConsultaMensajesProcesoBean consultaMensajesProcesoBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloMensajesProcesosPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaMensajesProcesoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaMensajesProcesoBean = (ConsultaMensajesProcesoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {}

	public ConsultaMensajesProcesoBean getConsultaMensajesProcesoBean() {
		return consultaMensajesProcesoBean;
	}

	public void setConsultaMensajesProcesoBean(ConsultaMensajesProcesoBean consultaMensajesProcesoBean) {
		this.consultaMensajesProcesoBean = consultaMensajesProcesoBean;
	}
}
