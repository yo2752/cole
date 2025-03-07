package org.gestoresmadrid.oegam2.registradores.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaMedioBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class MedioAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2785421647636957581L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Resource
	private ModelPagination modeloMedioPaginated;

	private ConsultaMedioBean consultaMedioBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargarFiltroInicial() {}

	@Override
	protected void cargaRestricciones() {
		if (consultaMedioBean != null && "-1".equals(consultaMedioBean.getTipoMedio())) {
			consultaMedioBean.setTipoMedio(null);
		}
		if (consultaMedioBean != null && consultaMedioBean.getDescMedio() != null && !consultaMedioBean.getDescMedio().isEmpty()) {
			consultaMedioBean.setDescMedio(consultaMedioBean.getDescMedio().toUpperCase());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloMedioPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaMedioBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaMedioBean = (ConsultaMedioBean) object;
	}

	public ConsultaMedioBean getConsultaMedioBean() {
		return consultaMedioBean;
	}

	public void setConsultaMedioBean(ConsultaMedioBean consultaMedioBean) {
		this.consultaMedioBean = consultaMedioBean;
	}
}