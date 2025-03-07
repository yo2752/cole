package org.gestoresmadrid.oegam2.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.colegiado.view.beans.ConsultaContratoColegiadoBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaColegiadosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -6463255913761424155L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaColegiadosAction.class);

	private static final String[] fetchList = { "colegiado", "colegiado.usuario", "provincia" };

	@Resource
	private ModelPagination modeloContratoColegiadoPaginated;

	private ConsultaContratoColegiadoBean consultaColegiadoBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaColegiadoBean != null) {
			if (consultaColegiadoBean.getApellidosNombre() != null && !consultaColegiadoBean.getApellidosNombre().isEmpty()) {
				consultaColegiadoBean.setApellidosNombre("%" + consultaColegiadoBean.getApellidosNombre().toUpperCase() + "%");
			}
			if (consultaColegiadoBean.getNif() != null && !consultaColegiadoBean.getNif().isEmpty()) {
				consultaColegiadoBean.setNif(consultaColegiadoBean.getNif().toUpperCase());
			}
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloContratoColegiadoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaColegiadoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaColegiadoBean = (ConsultaContratoColegiadoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		setSort("colegiado.numColegiado");
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public ConsultaContratoColegiadoBean getConsultaColegiadoBean() {
		return consultaColegiadoBean;
	}

	public void setConsultaColegiadoBean(ConsultaContratoColegiadoBean consultaColegiadoBean) {
		this.consultaColegiadoBean = consultaColegiadoBean;
	}
}