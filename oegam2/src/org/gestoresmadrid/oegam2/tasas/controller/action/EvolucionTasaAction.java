package org.gestoresmadrid.oegam2.tasas.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.evolucionTasa.view.bean.EvolucionTasaFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;



public class EvolucionTasaAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -821986623075156608L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionTasaAction.class);

	private String codigoTasa;
	
	private static final String[] fetchList = {"fechaHora","usuario","accion"};
	
	EvolucionTasaFilterBean evolucionTasa;
	
	@Resource
	ModelPagination modeloEvolucionTasaPaginatedImpl;
	
	public String cargar(){
		cargarFiltroInicial();
		evolucionTasa.setCodigoTasa(codigoTasa);
		return actualizarPaginatedList();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		
		return modeloEvolucionTasaPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void cargarFiltroInicial() {
		if(evolucionTasa == null){
			evolucionTasa = new EvolucionTasaFilterBean();
		
		}
	
		setDir(GenericDaoImplHibernate.ordenAsc);
		
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionTasa;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionTasa = (EvolucionTasaFilterBean) object;
		
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
}