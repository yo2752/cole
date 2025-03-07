package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesFilterBean;

import escrituras.utiles.UtilesVista;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionMaterialesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -3069496576117415589L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionMaterialesAction.class);

	private EvolucionMaterialesFilterBean evolucionMaterialesFilterBean;

	@Resource private ModelPagination modeloEvolucionMaterialesPaginatedImpl;

	@Override
	public String inicio(){
		if(evolucionMaterialesFilterBean == null){
			evolucionMaterialesFilterBean = new EvolucionMaterialesFilterBean();
		}
		return super.buscar();
	}

	@Override
	public String buscar() {
		if (evolucionMaterialesFilterBean == null) {
			evolucionMaterialesFilterBean = new EvolucionMaterialesFilterBean();
		}
		return super.buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionMaterialesPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTableMateriales";
	}

	@Override
	protected void cargarFiltroInicial() {
		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS);
		if(evolucionMaterialesFilterBean == null){
			evolucionMaterialesFilterBean = new EvolucionMaterialesFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionMaterialesFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.evolucionMaterialesFilterBean = (EvolucionMaterialesFilterBean) object;
	}

	// Getter & setter
	public EvolucionMaterialesFilterBean getEvolucionMaterialesFilterBean() {
		return evolucionMaterialesFilterBean;
	}

	public void setEvolucionMaterialesFilterBean(EvolucionMaterialesFilterBean evolucionMaterialesFilterBean) {
		this.evolucionMaterialesFilterBean = evolucionMaterialesFilterBean;
	}

}