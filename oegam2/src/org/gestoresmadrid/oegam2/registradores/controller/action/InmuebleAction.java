package org.gestoresmadrid.oegam2.registradores.controller.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaInmuebleBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class InmuebleAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2365431863444212812L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(InmuebleAction.class);

	@Resource
	private ModelPagination modeloInmueblePaginatedImpl;

	private ConsultaInmuebleBean consultaInmuebleBean;

	public String inicio() {
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloInmueblePaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaInmuebleBean == null) {
			consultaInmuebleBean = new ConsultaInmuebleBean();
		}
		consultaInmuebleBean.setIdufirFiltro(true);
		if (StringUtils.isNotBlank(consultaInmuebleBean.getIdMunicipio()) && "-1".equals(consultaInmuebleBean.getIdMunicipio())) {
			consultaInmuebleBean.setIdMunicipio(consultaInmuebleBean.getIdMunicipio().replace("-1", ""));
		}
	}

	@Override
	protected void cargarFiltroInicial() {

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaInmuebleBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaInmuebleBean = (ConsultaInmuebleBean) object;
	}

	public ConsultaInmuebleBean getConsultaInmuebleBean() {
		return consultaInmuebleBean;
	}

	public void setConsultaInmuebleBean(ConsultaInmuebleBean consultaInmuebleBean) {
		this.consultaInmuebleBean = consultaInmuebleBean;
	}

}
