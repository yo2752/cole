package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.view.bean.EvolucionPrmDstvFichaFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionPrmDstvFichaDgtAction extends AbstractPaginatedListAction {
	
	private static final long serialVersionUID = -1992992777549250278L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionPrmDstvFichaDgtAction.class);
	
	private static final String[] fetchList = {"usuario"};
	
	EvolucionPrmDstvFichaFilterBean evolucionFilter;
	
	private BigDecimal numExpediente;
	private String tipoDocumento;
	
	@Resource
	ModelPagination modeloEvolucionPrmDstvFichaPaginated;

	
	public String cargar(){
		cargarFiltroInicial();
		evolucionFilter.setNumExpediente(numExpediente);
		evolucionFilter.setTipoDocumento(tipoDocumento);
		return actualizarPaginatedList();
		
	}
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionPrmDstvFichaPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}



	@Override
	protected void cargarFiltroInicial() {
		if(evolucionFilter == null){
			evolucionFilter = new EvolucionPrmDstvFichaFilterBean();
		
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
		this.evolucionFilter = (EvolucionPrmDstvFichaFilterBean) object;
		
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
	public EvolucionPrmDstvFichaFilterBean getEvolucionFilter() {
		return evolucionFilter;
	}
	public void setEvolucionFilter(EvolucionPrmDstvFichaFilterBean evolucionFilter) {
		this.evolucionFilter = evolucionFilter;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
}
