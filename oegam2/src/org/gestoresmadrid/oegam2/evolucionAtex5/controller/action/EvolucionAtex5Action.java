package org.gestoresmadrid.oegam2.evolucionAtex5.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionAtex5.view.bean.EvolucionAtex5FilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionAtex5Action extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 5584734488044960153L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionAtex5Action.class);

	private static final String[] fetchList = {"usuario"};

	EvolucionAtex5FilterBean evolucionAtex5;

	private BigDecimal numExpediente;

	@Resource
	ModelPagination modeloEvolucionAtex5PaginatedImpl;

	public String cargar(){
		cargarFiltroInicial();
		evolucionAtex5.setNumExpediente(numExpediente);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionAtex5PaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionAtex5 == null) {
			evolucionAtex5 = new EvolucionAtex5FilterBean();
		}
		setSort("id.fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionAtex5;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionAtex5 = (EvolucionAtex5FilterBean) object;
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
		// TODO Auto-generated method stub
	}

}