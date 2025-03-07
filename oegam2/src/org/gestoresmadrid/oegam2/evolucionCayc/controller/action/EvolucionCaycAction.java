package org.gestoresmadrid.oegam2.evolucionCayc.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionCayc.view.bean.EvolucionCaycFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionCaycAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -963276403879604420L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionCaycAction.class);
	private static final String[] fetchList = {"usuario"};

	private BigDecimal numExpediente;

	EvolucionCaycFilterBean evolucionCaycFilterBean;

	@Resource
	ModelPagination modeloEvolucionCaycPaginatedImpl;

	public String cargar(){
		cargarFiltroInicial();
		evolucionCaycFilterBean.setNumExpediente(numExpediente);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionCaycPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionCaycFilterBean == null) {
			evolucionCaycFilterBean = new EvolucionCaycFilterBean();
		}
		setSort("fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionCaycFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionCaycFilterBean = (EvolucionCaycFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	@Override
	protected void cargaRestricciones() {
	}

}