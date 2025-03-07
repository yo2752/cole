package org.gestoresmadrid.oegam2.vehiculo.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.vehiculo.view.beans.ConsultaEvolucionVehiculoBean;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionVehiculoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -3345397010003880441L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionVehiculoAction.class);

	@Resource
	private ModelPagination modeloEvolucionVehiculoPaginated;

	private Long idVehiculo;
	private Long idVehiculoOrigen;

	private ConsultaEvolucionVehiculoBean consultaEvolucionVehiculoBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (idVehiculoOrigen != null) {
			consultaEvolucionVehiculoBean.setIdVehiculo(idVehiculoOrigen);
		} else if (idVehiculo != null) {
			consultaEvolucionVehiculoBean.setIdVehiculo(idVehiculo);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionVehiculoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaEvolucionVehiculoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaEvolucionVehiculoBean = (ConsultaEvolucionVehiculoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaEvolucionVehiculoBean = new ConsultaEvolucionVehiculoBean();
		consultaEvolucionVehiculoBean.setIdVehiculo(idVehiculoOrigen != null ? idVehiculoOrigen : idVehiculo);

		setSort("id.fechaHora");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Long getIdVehiculoOrigen() {
		return idVehiculoOrigen;
	}

	public void setIdVehiculoOrigen(Long idVehiculoOrigen) {
		this.idVehiculoOrigen = idVehiculoOrigen;
	}

	public ConsultaEvolucionVehiculoBean getConsultaEvolucionVehiculoBean() {
		return consultaEvolucionVehiculoBean;
	}

	public void setConsultaEvolucionVehiculoBean(ConsultaEvolucionVehiculoBean consultaEvolucionVehiculoBean) {
		this.consultaEvolucionVehiculoBean = consultaEvolucionVehiculoBean;
	}
}