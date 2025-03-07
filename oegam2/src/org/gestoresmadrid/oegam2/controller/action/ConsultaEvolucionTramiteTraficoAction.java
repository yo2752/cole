package org.gestoresmadrid.oegam2.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ConsultaEvolucionTramiteTraficoBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionTramiteTraficoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2674616775930806169L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionTramiteTraficoAction.class);

	@Resource
	private ModelPagination modeloEvolucionTramiteTraficoPaginated;

	private BigDecimal numExpediente;

	private ConsultaEvolucionTramiteTraficoBean consultaEvolucionTramiteTraficoBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (numExpediente != null) {
			consultaEvolucionTramiteTraficoBean.setNumExpediente(numExpediente);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionTramiteTraficoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionTramiteTraficoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionTramiteTraficoBean = (ConsultaEvolucionTramiteTraficoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionTramiteTraficoBean = new ConsultaEvolucionTramiteTraficoBean();
		if (numExpediente != null) {
			consultaEvolucionTramiteTraficoBean.setNumExpediente(numExpediente);
		}
		setSort("id.fechaCambio");
		setDir(GenericDaoImplHibernate.ordenAsc);
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}
