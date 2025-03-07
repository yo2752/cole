package org.gestoresmadrid.oegam2.pegatinas.controller.action;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.AccionPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.PegatinasStockHistoricoBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PegatinasStockHistoricoAction extends AbstractPaginatedListAction implements SessionAware {
	
	private static final long serialVersionUID = 7068221511089304431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PegatinasStockHistoricoAction.class);
	
	@Resource
	private ModelPagination modeloPegatinasStockHistoricoPaginatedImpl;
	
	private PegatinasStockHistoricoBean pegatinasStockHistoricoBean;
	
	private String idStock;
	
	@Override
	public String inicio(){
		getSession().remove(getKeyCriteriosSession());
		if(pegatinasStockHistoricoBean == null){
			pegatinasStockHistoricoBean = new PegatinasStockHistoricoBean();
			pegatinasStockHistoricoBean.setIdStock(Integer.parseInt(idStock));
		}
		return super.inicio();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPegatinasStockHistoricoPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}
	
	@Override
	public String buscar(){
		if (pegatinasStockHistoricoBean == null){
			pegatinasStockHistoricoBean = new PegatinasStockHistoricoBean();
		}
		pegatinasStockHistoricoBean.setIdStock(((PegatinasStockHistoricoBean) getSession().get(getKeyCriteriosSession())).getIdStock());
		if (pegatinasStockHistoricoBean != null) {
			if (AccionPegatinas.TODOS.getNombreEnum().equals(pegatinasStockHistoricoBean.getAccion())) {
				pegatinasStockHistoricoBean.setAccion(null);
			}
		}
		return super.buscar();
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void cargarFiltroInicial() {
		if(pegatinasStockHistoricoBean == null){
			pegatinasStockHistoricoBean = new PegatinasStockHistoricoBean();
			pegatinasStockHistoricoBean.setIdStock(Integer.parseInt(idStock));
		}
		
	}

	@Override
	protected Object getBeanCriterios() {
		return pegatinasStockHistoricoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.pegatinasStockHistoricoBean = (PegatinasStockHistoricoBean) object;
		
	}

	public PegatinasStockHistoricoBean getPegatinasStockHistoricoBean() {
		return pegatinasStockHistoricoBean;
	}

	public void setPegatinasStockHistoricoBean(PegatinasStockHistoricoBean pegatinasStockHistoricoBean) {
		this.pegatinasStockHistoricoBean = pegatinasStockHistoricoBean;
	}

	public String getIdStock() {
		return idStock;
	}

	public void setIdStock(String idStock) {
		this.idStock = idStock;
	}
	
}
