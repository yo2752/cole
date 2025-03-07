package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.view.bean.EvolucionDocPrmDstvFichaFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionDocPrmDstvFichaDgtAction extends AbstractPaginatedListAction {
	
	private static final long serialVersionUID = 3536812026307116718L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionDocPrmDstvFichaDgtAction.class);
	
	private static final String[] fetchList = {"usuario"};
	
	EvolucionDocPrmDstvFichaFilterBean evolucionFilter;
	
	private String docId;
	private String tipoDocumento;
	
	@Resource
	ModelPagination modeloEvolucionDocPrmDstvFichaPaginated;

	
	public String cargar(){
		cargarFiltroInicial();
		evolucionFilter.setDocId(docId);
		evolucionFilter.setTipoDocumento(tipoDocumento);
		return actualizarPaginatedList();
		
	}
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionDocPrmDstvFichaPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}



	@Override
	protected void cargarFiltroInicial() {
		if(evolucionFilter == null){
			evolucionFilter = new EvolucionDocPrmDstvFichaFilterBean();
		
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
		this.evolucionFilter = (EvolucionDocPrmDstvFichaFilterBean) object;
		
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
		
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public void setEvolucionFilter(EvolucionDocPrmDstvFichaFilterBean evolucionFilter) {
		this.evolucionFilter = evolucionFilter;
	}
	public ModelPagination getModeloEvolucionDocPrmDstvFichaPaginated() {
		return modeloEvolucionDocPrmDstvFichaPaginated;
	}
	public void setModeloEvolucionDocPrmDstvFichaPaginated(ModelPagination modeloEvolucionDocPrmDstvFichaPaginated) {
		this.modeloEvolucionDocPrmDstvFichaPaginated = modeloEvolucionDocPrmDstvFichaPaginated;
	}
	public EvolucionDocPrmDstvFichaFilterBean getEvolucionFilter() {
		return evolucionFilter;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
}
