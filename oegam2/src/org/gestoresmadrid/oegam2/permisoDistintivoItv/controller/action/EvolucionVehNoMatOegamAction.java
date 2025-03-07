package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.EvolucionVehNoMatrOegamFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionVehNoMatOegamAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5590174601215060509L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionVehNoMatOegamAction.class);

	private static final String[] fetchList = { "usuario" };

	EvolucionVehNoMatrOegamFilterBean evolucionFilter;

	private Long idVehNoMatOegam;

	@Resource
	ModelPagination modeloEvolucionVehNoMatrOegamPaginated;

	public String cargar() {
		cargarFiltroInicial();
		evolucionFilter.setIdVehNoMatOegam(idVehNoMatOegam);
		return buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionVehNoMatrOegamPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionFilter == null) {
			evolucionFilter = new EvolucionVehNoMatrOegamFilterBean();

		}
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionFilter = (EvolucionVehNoMatrOegamFilterBean) object;

	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	public EvolucionVehNoMatrOegamFilterBean getEvolucionFilter() {
		return evolucionFilter;
	}

	public void setEvolucionFilter(EvolucionVehNoMatrOegamFilterBean evolucionFilter) {
		this.evolucionFilter = evolucionFilter;
	}

	public ModelPagination getModeloEvolucionVehNoMatrOegamPaginated() {
		return modeloEvolucionVehNoMatrOegamPaginated;
	}

	public void setModeloEvolucionVehNoMatrOegamPaginated(ModelPagination modeloEvolucionVehNoMatrOegamPaginated) {
		this.modeloEvolucionVehNoMatrOegamPaginated = modeloEvolucionVehNoMatrOegamPaginated;
	}

	public Long getIdVehNoMatOegam() {
		return idVehNoMatOegam;
	}

	public void setIdVehNoMatOegam(Long idVehNoMatOegam) {
		this.idVehNoMatOegam = idVehNoMatOegam;
	}

}
