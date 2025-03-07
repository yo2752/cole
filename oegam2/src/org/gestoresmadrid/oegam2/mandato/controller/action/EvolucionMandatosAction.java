package org.gestoresmadrid.oegam2.mandato.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.mandato.view.bean.EvolucionMandatoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionMandatosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -169698814694006727L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionMandatosAction.class);

	private static final String[] fetchList = { "usuario" };

	private Long idMandato;

	EvolucionMandatoFilterBean evolucionMandatoFilterBean;

	@Resource
	ModelPagination modeloEvolucionMandatoPaginated;

	public String cargar() {
		cargarFiltroInicial();
		evolucionMandatoFilterBean.setIdMandato(idMandato);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionMandatoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionMandatoFilterBean == null) {
			evolucionMandatoFilterBean = new EvolucionMandatoFilterBean();
		}
		setSort("id.fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionMandatoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionMandatoFilterBean = (EvolucionMandatoFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {}

	public Long getIdMandato() {
		return idMandato;
	}

	public void setIdMandato(Long idMandato) {
		this.idMandato = idMandato;
	}

}
