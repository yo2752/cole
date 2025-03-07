package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.StockFilterBean;

import escrituras.utiles.UtilesVista;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionStockAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2634340199247903194L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionStockAction.class);

	private StockFilterBean stockFilterBean;

	@Resource private ModelPagination modeloGestionStockPaginatedImpl;

	@Override
	public String inicio(){
		if(stockFilterBean == null){
			stockFilterBean = new StockFilterBean();
		}

		return super.buscar();
	}

	@Override
	public String buscar() {
		if (stockFilterBean == null) {
			stockFilterBean = new StockFilterBean();
		}
		return super.buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionStockPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTableStockMateriales";
	}

	@Override
	protected void cargaRestricciones() {
		// Intencionadamente vacio
	}

	@Override
	protected void cargarFiltroInicial() {
		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS);
		if (stockFilterBean == null) {
			stockFilterBean = new StockFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return stockFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.stockFilterBean = (StockFilterBean) object;
	}

	// Getter & Setter
	public StockFilterBean getStockFilterBean() {
		return stockFilterBean;
	}

	public void setStockFilterBean(StockFilterBean stockFilterBean) {
		this.stockFilterBean = stockFilterBean;
	}

}