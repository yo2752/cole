package org.gestoresmadrid.oegam2.duplicadoPermiso.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.EvolucionDuplPermCondFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvoDuplicadoPermisoConducirAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 7443340873360499975L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvoDuplicadoPermisoConducirAction.class);

	private static final String[] fetchList = { "usuario" };

	private Long idDuplicadoPermisoConducir;

	EvolucionDuplPermCondFilterBean evolucionDuplPermCondFilterBean;

	@Resource
	ModelPagination modeloEvoDuplPermCondPaginated;

	public String cargar() {
		cargarFiltroInicial();
		evolucionDuplPermCondFilterBean.setIdDuplicadoPermCond(idDuplicadoPermisoConducir);
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvoDuplPermCondPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionDuplPermCondFilterBean == null) {
			evolucionDuplPermCondFilterBean = new EvolucionDuplPermCondFilterBean();
		}
		setSort("fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionDuplPermCondFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionDuplPermCondFilterBean = (EvolucionDuplPermCondFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {}

	public Long getIdDuplicadoPermisoConducir() {
		return idDuplicadoPermisoConducir;
	}

	public void setIdDuplicadoPermisoConducir(Long idDuplicadoPermisoConducir) {
		this.idDuplicadoPermisoConducir = idDuplicadoPermisoConducir;
	}
}
