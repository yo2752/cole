package org.gestoresmadrid.oegam2.consultaKo.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionConsultaKo.view.bean.EvolucionConsultaKoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionConsultaKoAction extends AbstractPaginatedListAction {
	
	private static final long serialVersionUID = -1992992777549250278L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionConsultaKoAction.class);
	
	private static final String[] fetchList = {"usuario"};
	
	EvolucionConsultaKoFilterBean evolucionFilter;
	
	private Long id;
	
	@Resource
	ModelPagination modeloEvolucionConsultaKoPaginated;

	
	public String cargar(){
		cargarFiltroInicial();
		evolucionFilter.setId(id);
		return actualizarPaginatedList();
		
	}
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionConsultaKoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}



	@Override
	protected void cargarFiltroInicial() {
		if(evolucionFilter == null){
			evolucionFilter = new EvolucionConsultaKoFilterBean();
		
		}
		setSort("fechaCambio");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionFilter = (EvolucionConsultaKoFilterBean) object;
		
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
		
	}
	public EvolucionConsultaKoFilterBean getEvolucionFilter() {
		return evolucionFilter;
	}
	public void setEvolucionFilter(EvolucionConsultaKoFilterBean evolucionFilter) {
		this.evolucionFilter = evolucionFilter;
	}
	public ModelPagination getModeloEvolucionConsultaKoPaginated() {
		return modeloEvolucionConsultaKoPaginated;
	}
	public void setModeloEvolucionConsultaKoPaginated(ModelPagination modeloEvolucionConsultaKoPaginated) {
		this.modeloEvolucionConsultaKoPaginated = modeloEvolucionConsultaKoPaginated;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
