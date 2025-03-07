package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.IncidenciasFilterBean;

import escrituras.utiles.UtilesVista;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionIncidenciasAction extends AbstractPaginatedListAction {


	private static final long serialVersionUID = -839842868206668653L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionIncidenciasAction.class);

	private IncidenciasFilterBean incidenciasFilterBean;
	private Long incidenciaId;

	@Resource ModelPagination modeloGestionIncidenciasPaginatedImpl;

	@Override
	public String inicio(){
		if(incidenciasFilterBean == null){
			incidenciasFilterBean = new IncidenciasFilterBean();
		}

		return super.buscar();
	}

	@Override
	public String buscar() {
		if (incidenciasFilterBean == null) {
			incidenciasFilterBean = new IncidenciasFilterBean();
		}
		return super.buscar();
	}

	public String modifica() {
		return "modificacion";
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionIncidenciasPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// Intencionadamente vacío
	}

	@Override
	protected void cargarFiltroInicial() {
		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS);
		if(incidenciasFilterBean == null){
			incidenciasFilterBean = new IncidenciasFilterBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return incidenciasFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.incidenciasFilterBean = (IncidenciasFilterBean) object;
	}

	public IncidenciasFilterBean getIncidenciasFilterBean() {
		return incidenciasFilterBean;
	}

	public void setIncidenciasFilterBean(IncidenciasFilterBean incidenciasFilterBean) {
		this.incidenciasFilterBean = incidenciasFilterBean;
	}

	public Long getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

}