package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionMaterialesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5995200418629098878L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionMaterialesAction.class);

	private Long selEvoMatId;
	private Long materia;

	private EvolucionMaterialesFilterBean evolucionMaterialesFilterBean;

	@Resource
	private ModelPagination modeloEvolucionMaterialesPaginatedImpl;

	public String cargar(){
		cargarFiltroInicial();
		evolucionMaterialesFilterBean.setMaterialId(materia);
		String pagina = actualizarPaginatedList();
		return pagina;
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
		// Intencionadamente vacío
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionMaterialesFilterBean == null) {
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

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTableEvolucionMateriales";
	}

	// Getters & Setters
	public Long getMateria() {
		return materia;
	}

	public Long getSelEvoMatId() {
		return selEvoMatId;
	}

	public void setSelEvoMatId(Long selEvoMatId) {
		this.selEvoMatId = selEvoMatId;
	}

	public void setMateria(Long materia) {
		this.materia = materia;
	}

	public ModelPagination getModeloEvolucionMaterialesPaginatedImpl() {
		return modeloEvolucionMaterialesPaginatedImpl;
	}

	public void setModeloEvolucionMaterialesPaginatedImpl(ModelPagination modeloEvolucionMaterialesPaginatedImpl) {
		this.modeloEvolucionMaterialesPaginatedImpl = modeloEvolucionMaterialesPaginatedImpl;
	}

}