package org.gestoresmadrid.oegam2.evolucionSemaforo.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.EvolucionSemaforoFilterBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionSemaforoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1925900943843830820L;

	private EvolucionSemaforoFilterBean evolucionSemaforoFilterBean;
	private Long idSemaforo;
	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionSemaforoAction.class);

	@Resource
	ModelPagination modeloEvolucionSemaforoPaginated;

	@Override
	protected String getResultadoPorDefecto() {
		return "listadoEvolSemaforo";
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionSemaforoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
	}

	@Override
	public String inicio() {
		// No nos interesa repetir el filtro
		if (getSession().get(getKeyCriteriosSession()) != null) {
			getSession().remove(getKeyCriteriosSession());
		}
		return super.inicio();
	}

	@Override
	protected void cargarFiltroInicial() {
		if (evolucionSemaforoFilterBean == null) {
			evolucionSemaforoFilterBean = new EvolucionSemaforoFilterBean();
		}
		evolucionSemaforoFilterBean.setIdSemaforo(idSemaforo);
		super.setSort("id");
	}

	@Override
	protected Object getBeanCriterios() {
		return evolucionSemaforoFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		evolucionSemaforoFilterBean = (EvolucionSemaforoFilterBean)object;
	}

	public ModelPagination getModeloEvolucionSemaforoPaginated() {
		return modeloEvolucionSemaforoPaginated;
	}

	public void setModeloEvolucionSemaforoPaginated(ModelPagination modeloEvolucionSemaforoPaginated) {
		this.modeloEvolucionSemaforoPaginated = modeloEvolucionSemaforoPaginated;
	}

	public EvolucionSemaforoFilterBean getEvolucionSemaforoFilterBean() {
		return evolucionSemaforoFilterBean;
	}

	public void setEvolucionSemaforoFilterBean(EvolucionSemaforoFilterBean evolucionSemaforoFilterBean) {
		this.evolucionSemaforoFilterBean = evolucionSemaforoFilterBean;
	}

	public Long getIdSemaforo() {
		return idSemaforo;
	}

	public void setIdSemaforo(Long idSemaforo) {
		this.idSemaforo = idSemaforo;
	}

}