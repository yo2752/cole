package org.gestoresmadrid.oegam2.trafico.consulta.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.EvolucionConsultaTramiteTraficoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionConsultaTramiteTrafAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 6535536389390477935L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionConsultaTramiteTrafAction.class);

	private BigDecimal numExpediente;

	EvolucionConsultaTramiteTraficoFilterBean evolucionConsTramTraf;

	@Resource
	ModelPagination modeloEvolucionConsTramTrafPaginated;

	public String cargar() {
		cargarFiltroInicial();
		evolucionConsTramTraf.setNumExpediente(numExpediente);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionConsTramTrafPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionConsTramTraf == null) {
			evolucionConsTramTraf = new EvolucionConsultaTramiteTraficoFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionConsTramTraf;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionConsTramTraf = (EvolucionConsultaTramiteTraficoFilterBean) object;
	}

	@Override
	protected void cargaRestricciones() {}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}


}
