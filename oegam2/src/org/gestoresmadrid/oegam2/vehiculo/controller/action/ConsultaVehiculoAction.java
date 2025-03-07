package org.gestoresmadrid.oegam2.vehiculo.controller.action;


import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.vehiculo.view.beans.ConsultaVehiculoBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaVehiculoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 9105497076659198449L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaVehiculoAction.class);

	@Resource
	private ModelPagination modeloVehiculoPaginated;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ConsultaVehiculoBean consultaVehiculoBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaVehiculoBean == null) {
			consultaVehiculoBean = new ConsultaVehiculoBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaVehiculoBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloVehiculoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaVehiculoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaVehiculoBean = (ConsultaVehiculoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		setSort("idVehiculo");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	@Override
	protected boolean isBuscarInicio() {
		return false;
	}

	public ConsultaVehiculoBean getConsultaVehiculoBean() {
		return consultaVehiculoBean;
	}

	public void setConsultaVehiculoBean(ConsultaVehiculoBean consultaVehiculoBean) {
		this.consultaVehiculoBean = consultaVehiculoBean;
	}
}