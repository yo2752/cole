package org.gestoresmadrid.oegam2.material.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.EvolucionMaterialStockFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionMaterialStockAction extends AbstractPaginatedListAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2508756072729816071L;
	private static final String[] fetchList = {"usuarioVO"};
	private static final String VER_EVOLUCION="evolucion";
	
	private EvolucionMaterialStockFilterBean evolucionMaterialStockFilterBean;
	
	private String idStock;
	
	@Resource
	ModelPagination modeloEvolucionMaterialStockPaginated;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionMaterialStockAction.class);
	
	@Override
	protected String getResultadoPorDefecto() {
		return VER_EVOLUCION;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionMaterialStockPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(idStock != null) {
			evolucionMaterialStockFilterBean.setIdStock(Long.parseLong(idStock));
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		evolucionMaterialStockFilterBean = new EvolucionMaterialStockFilterBean();
		if(idStock != null) {
			evolucionMaterialStockFilterBean.setIdStock(Long.parseLong(idStock));
		}
		setSort("fecha_recarga");
		setDir(GenericDaoImplHibernate.ordenAsc);
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionMaterialStockFilterBean;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionMaterialStockFilterBean = (EvolucionMaterialStockFilterBean) object;
	}
	
	public String cargarPopUp() {
		return VER_EVOLUCION;
	}

	public EvolucionMaterialStockFilterBean getEvolucionMaterialStockFilterBean() {
		return evolucionMaterialStockFilterBean;
	}

	public void setEvolucionMaterialStockFilterBean(EvolucionMaterialStockFilterBean evolucionMaterialStockFilterBean) {
		this.evolucionMaterialStockFilterBean = evolucionMaterialStockFilterBean;
	}

	public String getIdStock() {
		return idStock;
	}

	public void setIdStock(String idStock) {
		this.idStock = idStock;
	}
	
}
