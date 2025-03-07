package org.gestoresmadrid.oegam2.permisoInternacional.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.EvolucionPermisoInternacionalFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionPermisoInternacionalAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 7443340873360499975L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionPermisoInternacionalAction.class);

	private static final String[] fetchList = { "usuario" };

	private Long idPermisoIntern;

	EvolucionPermisoInternacionalFilterBean evolucionPermisoIntern;

	@Resource
	ModelPagination modeloEvolucionPermInterPaginated;

	public String cargar() {
		cargarFiltroInicial();
		evolucionPermisoIntern.setIdPermiso(idPermisoIntern);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionPermInterPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionPermisoIntern == null) {
			evolucionPermisoIntern = new EvolucionPermisoInternacionalFilterBean();
		}
		setSort("fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionPermisoIntern;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionPermisoIntern = (EvolucionPermisoInternacionalFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {}

	public Long getIdPermisoIntern() {
		return idPermisoIntern;
	}

	public void setIdPermisoIntern(Long idPermisoIntern) {
		this.idPermisoIntern = idPermisoIntern;
	}

}
